package com.pratap.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pratap.cards.config.CardsServiceConfig;
import com.pratap.cards.model.Cards;
import com.pratap.cards.model.Properties;
import com.pratap.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {
    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsServiceConfig cardsServiceConfig;

    @GetMapping("/myCards")
    public ResponseEntity<List<Cards>> getCardDetails(@RequestHeader("narayanbank-correlation-id") String correlationId,
                                                      @RequestParam int customerId) {
        List<Cards> cards = cardsRepository.findByCustomerId(customerId);
        if (cards != null && !cards.isEmpty())
            return new ResponseEntity<>(cards, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/properties")
    public String getPropertiesDetails() throws JsonProcessingException {

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(cardsServiceConfig.getMsg(), cardsServiceConfig.getBuildVersion(),
                cardsServiceConfig.getMailDetails(), cardsServiceConfig.getActiveBranches());
        return objectWriter.writeValueAsString(properties);
    }
}
