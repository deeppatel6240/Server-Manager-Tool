package com.sterlite.smt.servicemanagerapi.servicemanagerapi.model;

/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 21/02/2022
 */

import com.sterlite.smt.servicemanagerapi.servicemanagerapi.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "IP Address of Server can not be empty or null")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;

}
