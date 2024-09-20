package com.sparkminds.elasticSearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparkminds.elasticSearch.entity.attributes.Brand;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutomotiveEntity {

    @Id
    private String id;

    private String name;

    @Field(type = FieldType.Nested)
    private Brand brand;

    private String year;
}
