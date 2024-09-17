package com.sparkminds.elasticSearch.entity.attributes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "tyre_pattern")
public class TyrePattern {

    @Id
    private String id;

    private String name;
}
