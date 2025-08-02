package com.example.mywebiste.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.DataAmount;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import lombok.Data;


@Entity
@Data
@Table(name ="contact")
public class Contact {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name" ,nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "phone_number") // Correct column name
    private String phoneNumber;


    @Column(name = "comments")
    private String comments;

    public Contact() {
        // Default constructor required by JPA
    }






}
