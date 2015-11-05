package com.astrovisor
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional

@Transactional
class UserService {

    SpringSecurityService springSecurityService;


    User insertOrUpdate(User user) {
        user.save()
        return user
    }

    User currentUser() {
        return springSecurityService.currentUser;
    }
}
