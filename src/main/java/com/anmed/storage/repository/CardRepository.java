package com.anmed.storage.repository;

import com.anmed.storage.model.Card;
import com.anmed.storage.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findCardsByCardNumberContainingAndUser(String lookup, User user);

    Card findCardByCardNumber(String cardNumber);
}
