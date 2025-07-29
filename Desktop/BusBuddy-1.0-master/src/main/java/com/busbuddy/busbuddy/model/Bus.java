package com.busbuddy.busbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    private String busId;
    private String busNumber;
    private List<Route> routes;
    private String companyId;
    private String driverId;
    @DBRef
    private Driver driver;
    private String status; // Example: "Running" or "Stopped"
    private Location location; // Updated to Location class
}