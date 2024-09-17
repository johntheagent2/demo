package com.sparkminds.elasticSearch.entity;

import com.sparkminds.elasticSearch.enumeration.BatteryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@SuperBuilder
@Document(indexName = "battery")
public class BatteryEntity extends AutomotiveEntity{

    private Integer capacity;

    private Integer Voltage;

    private BatteryType batteryType;
}
