package com.curso.diccionarios.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DiccionarioImplTest {

    @Test
    @DisplayName("Comprobar la función de palabras similares")
    void palabrasSimilares1Test(){
        Map<String, List<String>> palabras = new HashMap<>();
        // Cargar palabras de ejemplo
        palabras.put("manzana", List.of("fruta"));
        palabras.put("manzanas", List.of("fruta"));
        palabras.put("manzano", List.of("árbol"));
        palabras.put("mañana", List.of("tiempo"));
        palabras.put("federico", List.of("nombre"));
        DiccionarioImpl miDict = new DiccionarioImpl("es", palabras);
        List<String> resultado = miDict.getSimilares("MANANA");
        Assertions.assertEquals( 4, resultado.size());
        Assertions.assertFalse(resultado.contains("federico"));
        Assertions.assertTrue(resultado.indexOf("manzana") <= 1);
        Assertions.assertTrue(resultado.indexOf("mañana") <= 1);
        Assertions.assertTrue(resultado.indexOf("manzanas") >= 2);
        Assertions.assertTrue(resultado.indexOf("manzano") >= 2);
    }

}
