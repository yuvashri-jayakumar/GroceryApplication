package com.demo.groceryapplication.service;

import com.demo.groceryapplication.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {


    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        Query query = Query.query(Criteria.where("_id").is(seqName));
        UpdateDefinition update = new Update().inc("sequence", 1);
        DatabaseSequence counter = mongoOperations.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return counter != null ? counter.sequence() : 1;
    }


}
