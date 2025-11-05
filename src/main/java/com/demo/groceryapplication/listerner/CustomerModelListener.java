package com.demo.groceryapplication.listerner;

import com.demo.groceryapplication.model.Customer;
import com.demo.groceryapplication.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerModelListener extends AbstractMongoEventListener<Customer> {


    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public CustomerModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Customer> event) {
        if (event.getSource().getId() == 0) {
            event.getSource().setId(sequenceGeneratorService.generateSequence("customer_sequence"));
            event.getSource().setCreatedAt(new Date());
        }
    }
}
