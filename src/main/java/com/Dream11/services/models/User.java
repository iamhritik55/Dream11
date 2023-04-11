package com.Dream11.services.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    private String id;
    private String name;
    private int credits;

}
