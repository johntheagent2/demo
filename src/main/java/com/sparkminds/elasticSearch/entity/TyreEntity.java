package com.sparkminds.elasticSearch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tyre")
public class TyreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tyre_generator")
    @SequenceGenerator(name = "tyre_generator", sequenceName = "tyre_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "rim")
    private Integer rim;

    @Column(name = "loadIndex")
    private String loadIndex;

    @Column(name = "year")
    private String year;

    @Column(name = "brand")
    private String brand;

    @Column(name = "pattern")
    private String pattern;
}
