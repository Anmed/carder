package com.anmed.storage.service;

import com.anmed.storage.model.Card;
import com.anmed.storage.model.User;

import java.util.List;

public interface CardService extends GeneralCRUDservice<Card> {
    List<Card> findCardsByContainingStringAndUser(String lookup, User user);

    Card findCardByNumber(String cardNumber);
}
