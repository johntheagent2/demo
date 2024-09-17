package com.sparkminds.elasticSearch.entity;

import com.sparkminds.elasticSearch.entity.attributes.Brand;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Data
@SuperBuilder
public class AutomotiveEntity {

    @Id
    private String id;

    private String name;

    private Brand brand;

    private String year;
}
