package com.astrovisor
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.springframework.security.authentication.encoding.PasswordEncoder


@Transactional
class UserService {

    SpringSecurityService springSecurityService;
    //This interface is deprecated. But do not work with the new interface.
    PasswordEncoder passwordEncoder;

    User insertOrUpdate(User user) {
        user.save()
        return user
    }

    User currentUser() {
        return springSecurityService.currentUser;
    }
    /**
     *
     * @param password The password sent from the user to update his information
     * @return True if the password matches the user's password.
     */
    boolean  checkPassword(String password) {
        return passwordEncoder.isPasswordValid(currentUser().password,password,null);
    }

    /**
     *
     * @param params The users new information
     * @return the user
     */
    User updateUser(def params) {
        //TODO: check if there is a better way to do so. A PUT action should send all the data of the user
        // we have to update this function for new User attribute.
        currentUser().password = params.password

        // return null if the user is not valid or the user if it's valid.
        return currentUser().validate() ? insertOrUpdate(currentUser()) : null;
    }
}
