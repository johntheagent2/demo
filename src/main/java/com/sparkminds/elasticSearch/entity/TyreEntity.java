package com.sparkminds.elasticSearch.entity;

import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "tyre") // Name of the Elasticsearch index
public class TyreEntity extends AutomotiveEntity{

    private Long id;

    private Integer width;

    private Integer height;

    private Integer rim;

    private TyrePattern pattern;
}
