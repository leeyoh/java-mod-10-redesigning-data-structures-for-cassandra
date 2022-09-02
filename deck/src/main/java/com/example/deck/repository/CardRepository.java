package com.example.deck.repository;

import com.example.deck.models.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Long> {
   // Optional<Card> findFirstByOrderByPositionDesc();

}
