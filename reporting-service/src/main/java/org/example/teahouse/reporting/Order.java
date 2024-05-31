package org.example.teahouse.reporting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@Data
public class Order {

    @Id
    private String id;

    private long timestamp;

    private String tealeaf;

    private String water;

    public Order(long timestamp, String tealeaf, String water) {
        this.timestamp = timestamp;
        this.tealeaf = tealeaf;
        this.water = water;
    }

    public Order() {
    }
}
