package com.dino.admin.catalogo.infrastructure.utils;

public final class SqlUtils {

    public static String like(final String term){
        if (term == null) return null;
        return "%" + term + "%";
    }
}
