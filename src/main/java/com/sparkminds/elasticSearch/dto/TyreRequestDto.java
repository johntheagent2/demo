package com.sparkminds.elasticSearch.dto;

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

    private String loadIndex;

    private String year;

    private String pattern;

    private String brand;
}
