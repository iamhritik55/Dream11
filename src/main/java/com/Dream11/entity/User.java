package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    private int id;
    private String name;
    private int credits;
    private ArrayList<Integer> chosenPlayerIdList;
    private int teamPoints;
}
