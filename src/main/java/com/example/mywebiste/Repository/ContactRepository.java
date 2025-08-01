package com.example.mywebiste.Repository;


import com.example.mywebiste.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {


    //Contact findContactByEmail(String email);

    Boolean existsByEmail(String Email);




}
