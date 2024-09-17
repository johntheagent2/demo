package com.sparkminds.elasticSearch.entity.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "tyre_pattern")
public class TyrePattern {

    private Long id;

    private String name;
}
