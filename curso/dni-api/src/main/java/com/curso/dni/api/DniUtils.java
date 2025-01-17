package com.curso.dni.api;

public interface DniUtils {

    Dni of(int numero, char letra);
    Dni of(int numero, String letra);
    Dni of(String dni);

}
