package com.anmed.storage.validator;

import com.anmed.storage.model.Card;
import com.anmed.storage.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

import static com.anmed.storage.validator.CardValidationUtil.validatedByLuhnAlgorithm;

@Component
public class CardValidator implements Validator {
    //in this example we are expecting to receive lnly 16 digits number cards
    private static final String NOT_NUMB_WHITE_SPACE_REGEX = "[^0-9\\s]";
    private static final String NOT_CHAR_WHITE_SPACE_REGEX = "[^A-Za-z\\s]";
    private static final int CARD_LENGTH = 16;
    private final CardService cardService;

    Logger logger = LoggerFactory.getLogger(CardValidator.class);

    @Autowired
    public CardValidator(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Card.class.equals(aClass);
    }

    @Override
    public void validate(Object instance, Errors errors) {
        Card card = (Card) instance;
        int currentYear = LocalDateTime.now().getYear();

        validateString(card.getCardHolder(), errors, "cardHolder", NOT_CHAR_WHITE_SPACE_REGEX);
        validateCardNumber(card.getCardNumber(), errors, "cardNumber", NOT_NUMB_WHITE_SPACE_REGEX);

        if (card.getExpiryYear() < 0 || card.getExpiryYear() > 2100 ||
                card.getExpiryMonth() < 1 || card.getExpiryMonth() > 12) {
            errors.rejectValue("expiryYear", "malformed.expiry.date");
        }
        if (card.getExpiryYear() < currentYear) {
            errors.rejectValue("expiryYear", "card.expired.exception");
        } else if (card.getExpiryYear() == currentYear &&
                card.getExpiryMonth() < LocalDateTime.now().getMonth().getValue()) {
            errors.rejectValue("expiryYear", "card.expired.exception");
        }

        card = cardService.findCardByNumber(card.getCardNumber().trim());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUsername().equals("admin")) {
            errors.rejectValue("cardNumber", "admin.card.exception");
        } else if (card != null && !card.getUser().getName().equalsIgnoreCase(user.getUsername())) {
            errors.rejectValue("cardNumber", "card.fraud.exception");
        }


    }

    private void validateCardNumber(String cardNumber, Errors errors, String fieldName, String regex) {
        validateString(cardNumber, errors, fieldName, NOT_CHAR_WHITE_SPACE_REGEX);
        if (cardNumber.trim().length() != CARD_LENGTH) {
            errors.rejectValue(fieldName, "card.length.mismatch");
        }
        if (!validatedByLuhnAlgorithm(cardNumber.toCharArray())) {
            errors.rejectValue(fieldName, "malformed.card.number");
        }

    }

    private void validateString(String string, Errors errors, String fieldName, String regex) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty");
        if (string.matches(regex)) {
            errors.rejectValue(fieldName, "malformed.string.exception");
        }
    }


}
