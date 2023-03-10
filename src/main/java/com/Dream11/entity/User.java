package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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
