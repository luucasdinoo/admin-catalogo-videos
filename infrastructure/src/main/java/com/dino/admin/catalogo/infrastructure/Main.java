package com.dino.admin.catalogo.infrastructure;

import com.dino.admin.catalogo.application.UseCase;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println(new UseCase().execute());
    }
}