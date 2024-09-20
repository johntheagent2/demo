package com.sparkminds.elasticSearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "tyre")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TyreEntity extends AutomotiveEntity{

    private Integer width;

    private Integer height;

    private Integer rim;

    @Field(type = FieldType.Nested)
    private TyrePattern pattern;
}
