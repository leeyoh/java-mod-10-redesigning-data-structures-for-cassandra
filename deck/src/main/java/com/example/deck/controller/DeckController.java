package com.example.deck.controller;

import com.example.deck.models.Card;
import com.example.deck.repository.CardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static java.util.UUID.randomUUID;

@RestController
public class DeckController {
    // Initialize values table
    Map<String,Long> faceValue = new HashMap<String,Long>() {{
        put("Two",2L);
        put("Three",3L);
        put("Four",4L);
        put("Five",5L);
        put("Six",6L);
        put("Seven",7L);
        put("Eight",8L);
        put("Nine",9L);
        put("Ten",10L);
        put("Jack",10L);
        put("Queen",10L);
        put("King",10L);
        put("Ace",11L);
    }};

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/new")
    public String newDeck(@RequestParam(value = "decks", defaultValue = "1") Long decks) {
        // Drop tables and start new
        cardRepository.deleteAll();

        // Initialize card table
        List<String> suits = Arrays.asList("Clubs", "Hearts", "Spades", "Diamonds");
        List<String> names = Arrays.asList("Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                "Ten", "Jack", "Queen", "King", "Ace");
        for (Long deck = 1L; deck <= decks; deck++) {
            Long position = 1L;
            for (String suit : suits) {
                for (String name : names) {
                    UUID uuid = randomUUID();

                    cardRepository.save(
                            new Card(
                                    uuid,
                                    name,
                                    suit,
                                    this.faceValue.get(name),
                                    position,
                                    deck)
                    );
                    position++;
                }
            }
        }
        return String.format("New Deck using %s decks.", decks);
   }

    @GetMapping("/shuffle")
    public String shuffleDeck() {

        // Read order of cards
        Iterable<Card> cards = cardRepository.findAll();
        List<Card> order = new ArrayList<Card>();
        Queue<Card> ll = new LinkedList<Card>();

        for (Card card : cards) {
            order.add(card);
        }

        // Shuffle order
        Collections.shuffle(order);

        // Write new order of cards
        Long pos = 1L;
        for (Card card: order) {
            card.setPosition(pos);
            cardRepository.save(card);
            pos++;
        }
        System.out.println(order);
        return "Deck shuffled.";
    }

    @GetMapping("/deal")
    public String dealCard() {
        Iterable<Card> cards = cardRepository.findAll();
        Card lowCard = cards.iterator().next();
        for(Card card: cards){
            if(card.getPosition() < lowCard.getPosition()){
                lowCard = card;
            }
        }
        cardRepository.delete(lowCard);
        return String.format("Dealt %s of %s: Worth %s points.", lowCard.getName(), lowCard.getSuit(),
                lowCard.getPoints());
    }

}
