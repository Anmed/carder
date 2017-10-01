package com.anmed.storage.service.impl;

import com.anmed.storage.model.Card;
import com.anmed.storage.model.User;
import com.anmed.storage.repository.CardRepository;
import com.anmed.storage.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> listAll() {
        List<Card> cards = new ArrayList<>();
        cardRepository.findAll().forEach(cards::add);
        return cards;
    }

    @Override
    public Card getById(Long id) {
        return cardRepository.findOne(id);
    }

    @Override
    public Card saveOrUpdate(Card domainObject) {
        //to make sure that cards have the same format
        domainObject.setCardNumber(domainObject.getCardNumber().trim());
        Card card = cardRepository.findCardByCardNumber(domainObject.getCardNumber());
        if (card != null) {
            domainObject.setCardId(card.getCardId());
        }
        return cardRepository.save(domainObject);
    }

    @Override
    public void delete(Long id) {
        cardRepository.delete(id);
    }

    @Override
    public List<Card> findCardsByContainingStringAndUser(String lookup, User user) {
        return cardRepository.findCardsByCardNumberContainingAndUser(lookup, user);
    }

    @Override
    public Card findCardByNumber(String cardNumber) {
        return cardRepository.findCardByCardNumber(cardNumber);
    }
}
