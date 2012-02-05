package com.apress.prospringmvc.bookstore.validation;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.apress.prospringmvc.bookstore.domain.Account;

public class AccountValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean supports(Class<?> clazz) {
        return (Account.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "username", "required", new Object[] { "Username" });
        ValidationUtils.rejectIfEmpty(errors, "password", "required", new Object[] { "Password" });
        ValidationUtils.rejectIfEmpty(errors, "emailAddress", "required", new Object[] { "Emailaddress" });
        ValidationUtils.rejectIfEmpty(errors, "address.street", "required", new Object[] { "Street" });
        ValidationUtils.rejectIfEmpty(errors, "address.city", "required", new Object[] { "City" });
        ValidationUtils.rejectIfEmpty(errors, "address.country", "required", new Object[] { "Country" });

        if (!errors.hasFieldErrors("emailAddress")) {
            Account account = (Account) target;
            String email = account.getEmailAddress();
            if (!this.emailPattern.matcher(email).matches()) {
                errors.rejectValue("emailAddress", "invalid");
            }
        }
    }
}
