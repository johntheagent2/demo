package com.sparkminds.elasticSearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparkminds.elasticSearch.enumeration.BatteryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "battery")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatteryEntity extends AutomotiveEntity{

    private Integer capacity;

    private Integer Voltage;

    @Field(type = FieldType.Nested)
    private BatteryType batteryType;
}
