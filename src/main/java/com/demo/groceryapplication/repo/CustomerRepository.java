package com.demo.groceryapplication.repo;

import com.demo.groceryapplication.model.Address;
import com.demo.groceryapplication.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {


    @Query("{'_id':?0}")
    @Update("{'$addToSet':{'addresses':?1}}")
    void addAddress(Long id, Address newAddress);

    @Query("{'_id':?0}")
    @Update("{'$set':{'addresses':{'type':?1}}}")
    void updateAddress(Long id, String type, Address newAddress);

    @Query("{'_id':?0}")
    @Update("{'$pull':{'addresses':{'type':?1}}}")
    void deleteAddress(Long id, String type);

    @Query(value = "{'_id':?0, 'addresses.type':?1}", exists = true)
    boolean addressExistsByType(Long id, String type);

    @Query("{'$or':[{'_id':{$eq:?0}},{'phone':{$eq:?1}},{'email':{$eq:?2}}]}")
    Customer findByIdPhoneEmail(Long id, String phone, String email);
}
