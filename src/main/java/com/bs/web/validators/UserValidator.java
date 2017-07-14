package com.bs.web.validators;


import com.bs.backend.domain.Users;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {



    @Override
    public boolean supports(Class clazz) {
        //just validate the Users instances
        return Users.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Field name is required.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
                "required.confirmPassword", "Field name is required.");

        Users user = (Users)target;

        if(!(user.getPassword().equals(user.getConfirmPassword()))){
            errors.rejectValue("password", "notmatch.password");
        }

        // Username userService - null
    /*    if (userService.getUserByLogin(beeUser.getLogin()) != null) {
            errors.rejectValue("email", "duplicate", "already exists");
        }
    */

        /*  String email = beeUser.getBeePerson().getEmail();

      if (beeUser.isNew() && beeUser.getBeePerson().getEmail(email, true) != null) {
            errors.rejectValue("email", "duplicate", "already exists");
        }*/

    }

}
