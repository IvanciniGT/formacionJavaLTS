package com.curso.diccionarios.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilidadesTest {

    @Test
    void normalizarTest(){
        String valorDePrueba = "Hola ";
        String valorEsperado = "hola";
        String valorObtenido = Utilidades.normalizar(valorDePrueba);
        Assertions.assertEquals(valorEsperado, valorObtenido);
    }

    @Test
    void distanciaTest(){
        String palabraDestino = "Manzana";
        String palabraOrigen = "Manana";
        int valorEsperado = 1;
        int valorObtenido = Utilidades.distancia(palabraOrigen, palabraDestino);
        Assertions.assertEquals(valorEsperado, valorObtenido);
    }

    @Test
    void distancia2Test(){
        String palabraDestino = "Manzano";
        String palabraOrigen = "Manana";
        int valorEsperado = 2;
        int valorObtenido = Utilidades.distancia(palabraOrigen, palabraDestino);
        Assertions.assertEquals(valorEsperado, valorObtenido);
    }

    @Test
    void distancia3Test(){
        String palabraDestino = "Manzano";
        String palabraOrigen = "Federico";
        int valorEsperado = 7;
        int valorObtenido = Utilidades.distancia(palabraOrigen, palabraDestino);
        Assertions.assertEquals(valorEsperado, valorObtenido);
    }

}
