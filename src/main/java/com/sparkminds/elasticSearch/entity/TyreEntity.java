package com.sparkminds.elasticSearch.entity;

import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@SuperBuilder
@Document(indexName = "tyre") // Name of the Elasticsearch index
public class TyreEntity extends AutomotiveEntity{

    private Integer width;

    private Integer height;

    private Integer rim;

    private TyrePattern pattern;
}
