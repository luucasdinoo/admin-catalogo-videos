package com.dino.admin.catalogo.domain.category;

import com.dino.admin.catalogo.domain.Identifier;
import com.dino.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class CategoryId extends Identifier {

    private final String value;

    private CategoryId(final String value) {
        this.value = Objects.requireNonNull(value);;
    }

    public static CategoryId unique(){
        return new CategoryId(IdUtils.uuid());
    }

    public static CategoryId from(final String anId) {
        return new CategoryId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryId that = (CategoryId) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
