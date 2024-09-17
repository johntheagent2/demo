package com.sparkminds.elasticSearch.dto;

import com.sparkminds.elasticSearch.entity.attributes.Brand;
import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TyreRequestDto {

    private String name;

    private Integer width;

    private Integer height;

    private Integer rim;

    private String year;

    private String patternId;

    private String brandId;
}
