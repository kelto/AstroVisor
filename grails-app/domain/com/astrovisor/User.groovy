package com.astrovisor

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
@ToString
class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	int totalVotes

	static transients = ['springSecurityService', 'totalVotes']

	static constraints = {
		username blank: false, unique: true, minSize: 4
		password blank: false, minSize: 5
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
