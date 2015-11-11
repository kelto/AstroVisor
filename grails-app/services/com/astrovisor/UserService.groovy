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
        User user = springSecurityService.currentUser;
        if(!user) {
            throw new Exception("You are not authenticated");
        }
        user.totalVotes = totalVoteOfUser(user)
        return springSecurityService.currentUser;
    }

    int totalVoteOfUser(User user) {
        def criteria = Description.createCriteria()
        def result = criteria.list {
            eq('user',user)
            projections {
                sum('upvotes')
                sum('downvotes')
            }
        }
        int upvotes = result[0][0] == null ? 0 : result[0][0]
        int downvotes = result[0][1] == null ? 0 : result[0][1]
        return upvotes - downvotes
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
