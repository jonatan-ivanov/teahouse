package org.example.teahouse.reporting;

import lombok.Data;

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


}
