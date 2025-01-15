package com.nagarro.Docker.Assignment.Basic.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Customer implements Serializable {

    @Id
    private String customerId;
    private String customerName;
    private String contactNumber;
    private String emailAddress;
    private String password;


}
