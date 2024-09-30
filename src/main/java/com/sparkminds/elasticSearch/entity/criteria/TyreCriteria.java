package com.sparkminds.elasticSearch.entity.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TyreCriteria implements Criteria {

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter width;

    private IntegerFilter height;

    private IntegerFilter rim;

    private StringFilter loadIndex;

    private StringFilter pattern;

    private StringFilter brand;

    private StringFilter year;

    public TyreCriteria(TyreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.rim = other.rim == null ? null : other.rim.copy();
        this.loadIndex = other.loadIndex == null ? null : other.loadIndex.copy();
        this.pattern = other.pattern == null ? null : other.pattern.copy();
        this.brand = other.brand == null ? null : other.brand.copy();
        this.year = other.year == null ? null : other.year.copy();
    }

    @Override
    public Criteria copy() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TyreCriteria that = (TyreCriteria) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(width, that.width)
                && Objects.equals(height, that.height)
                && Objects.equals(rim, that.rim)
                && Objects.equals(loadIndex, that.loadIndex)
                && Objects.equals(pattern, that.pattern)
                && Objects.equals(brand, that.brand)
                && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, width, height, rim, loadIndex, pattern, brand, year);
    }
}
