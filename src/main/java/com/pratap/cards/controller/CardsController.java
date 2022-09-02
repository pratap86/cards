package com.pratap.cards.controller;

import com.pratap.cards.model.Cards;
import com.pratap.cards.model.Customer;
import com.pratap.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {
    @Autowired
    private CardsRepository cardsRepository;

    @GetMapping("/myCards")
    public ResponseEntity<List<Cards>> getCardDetails(@RequestBody Customer customer) {
        List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
        if (cards != null && !cards.isEmpty())
            return new ResponseEntity<>(cards, HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
