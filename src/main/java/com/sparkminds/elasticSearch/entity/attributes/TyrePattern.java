package com.sparkminds.elasticSearch.entity.attributes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "tyre_pattern")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TyrePattern {

    @Id
    private String id;

    private String name;
}
