package com.anmed.storage.validator;

import com.anmed.storage.model.User;
import com.anmed.storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {


    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object instance, Errors errors) {
        User user = (User) instance;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");

        if (userService.findByUsername(user.getName()) != null) {
            errors.rejectValue("name", "duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    }
}
