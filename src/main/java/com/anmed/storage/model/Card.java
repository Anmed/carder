package com.anmed.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"cardNumber"})
)
public class Card {
    @Id
    @GeneratedValue
    private Long cardId;
    private String cardNumber;
    private String cardHolder;
    private int expiryYear;
    private int expiryMonth;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Override
    public String toString() {
        return String.format("card number - %s, holder - %s, expiry %s/%s",
                cardNumber, cardHolder, expiryMonth, expiryYear);
    }
}
