package com.Dream11.services.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @NonNull
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Long)
    private int credits;

}
