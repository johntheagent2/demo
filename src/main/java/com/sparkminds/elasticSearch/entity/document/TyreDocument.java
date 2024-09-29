package com.sparkminds.elasticSearch.entity.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "tyre", createIndex = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TyreDocument{

    @Id
    private Long id;

    private String name;

    private Integer width;

    private Integer height;

    private Integer rim;

    private String loadIndex;

    private String pattern;

    private String brand;

    private String year;
}
