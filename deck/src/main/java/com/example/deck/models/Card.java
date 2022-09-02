package com.example.deck.models;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Table
public class Card implements Serializable {
    @PrimaryKeyColumn(name = "position", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Long position;
    @PrimaryKeyColumn(name = "uuid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID uuid;

    // Card Info
    private String name;
    private String suit;
    private Long points;



    // Desk Info
    private Long deckNumber;

    public Card( UUID uuid, String name, String suit, Long points, Long position, Long deckNumber){

        this.uuid = uuid;
        this.name = name;
        this.suit = suit;
        this.points = points;
        this.position = position;
        this.deckNumber = deckNumber;
    }
}