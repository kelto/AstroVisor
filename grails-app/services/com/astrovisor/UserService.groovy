package com.astrovisor

import grails.transaction.Transactional

@Transactional
class UserService {

    User insertOrUpdate(User user) {
        user.save()
        return user
    }
}
