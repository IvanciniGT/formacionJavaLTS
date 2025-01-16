package com.curso.diccionarios.impl;

import com.curso.diccionarios.api.Diccionario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("Cargar un diccionario existente de fichero")
    void cargarDiccionarioExistenteTest(){
        Optional<Diccionario> potencialDiccionario = Utilidades.cargarDiccionario("es");
        Assertions.assertTrue(potencialDiccionario.isPresent());
        Assertions.assertEquals("es", potencialDiccionario.get().getIdioma());
        List.of("manzana", "melón", "mañana", "murciélago", "manzano", "ángel", "Ángel").forEach(
                palabra -> Assertions.assertTrue(potencialDiccionario.get().existe(palabra))
        );
        Assertions.assertFalse(potencialDiccionario.get().existe("federico"));
        Assertions.assertEquals(1, potencialDiccionario.get().getSignificados("manzana").orElseThrow().size());
        Assertions.assertEquals(2, potencialDiccionario.get().getSignificados("melón").orElseThrow().size());
        Assertions.assertEquals(2, potencialDiccionario.get().getSignificados("ángel").orElseThrow().size());
    }

    @Test
    @DisplayName("Cargar un diccionario no existente de fichero")
    void cargarDiccionarioNoExistenteTest(){
        Optional<Diccionario> potencialDiccionario = Utilidades.cargarDiccionario("italiano");
        Assertions.assertTrue(potencialDiccionario.isEmpty());
    }

}
