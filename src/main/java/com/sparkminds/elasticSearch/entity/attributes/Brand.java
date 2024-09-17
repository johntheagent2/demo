package com.sparkminds.elasticSearch.entity.attributes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "brand")
public class Brand {

    @Id
    private String id;

    private String name;
}
