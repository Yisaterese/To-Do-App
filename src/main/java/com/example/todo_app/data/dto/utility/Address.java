package com.example.todo_app.data.dto.utility;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Address {
    private String streetName;
    @Id
    private String houseNumber;
    private String cityName;
    private String State;
    private String country;



}
