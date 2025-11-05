package com.demo.groceryapplication.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "database_sequences")
public record DatabaseSequence(@Id String id, long sequence) {
}
