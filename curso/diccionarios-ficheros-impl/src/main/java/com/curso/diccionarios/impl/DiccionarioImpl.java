package com.curso.diccionarios.impl;

import com.curso.diccionarios.api.Diccionario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RequiredArgsConstructor
// Genera en automático un constrctor con los atributos final
public class DiccionarioImpl implements Diccionario {

    @Getter // Genera un automático un getter para el atributo
    private final String idioma;
    private final Map<String, List<String>> palabras;

    @Override
    public boolean existe(String palabra) { // "manzana".  "Manzana".  "MANZANA"
        return palabras.containsKey(Utilidades.normalizar(palabra));
    }

    @Override
    public Optional<List<String>> getSignificados(String palabra) {
        /*if(existe(Utilidades.normalizar(palabra))) {
            return Optional.of(palabras.get(Utilidades.normalizar(palabra)));
        } else {
            return Optional.empty();
        }*/
        return Optional.ofNullable(palabras.get(Utilidades.normalizar(palabra)));
    }

    @Override
    public List<String> getSimilares(String palabraObjetivo) {
        String palabraObjetivoNormalizada = Utilidades.normalizar(palabraObjetivo);
        // Utilidades.normalizar(palabra)
        return palabras.keySet().parallelStream()                                                                                  // Para cada palabra
                .filter( palabra -> Math.abs( palabra.length() - palabraObjetivoNormalizada.length())<= Utilidades.DISTANCIA_MAXIMA_ADMISIBLE ) // Filtra las que tengan una longitud similar
                .map( palabra -> new PalabraPuntuada(palabra,Utilidades.distancia(palabraObjetivoNormalizada, palabra)))     // Añádele la distancia de Levenshtein
                .filter( palabraPuntuada -> palabraPuntuada.distancia <= Utilidades.DISTANCIA_MAXIMA_ADMISIBLE)     // Me quedo con las que están a una distancia admisible
                .sorted(Comparator.comparing(( palabraPuntuada -> palabraPuntuada.distancia)))                      // Ordena por distancia
                .limit(Utilidades.NUMERO_MAXIMO_SIMILITUDES)                                                                       // Limita a las N mejores
                .map( palabraPuntuada -> palabraPuntuada.palabra)                                                   // Descarta la puntación
                .toList();                                                                                                        // Mete en una lista
    }

    @RequiredArgsConstructor
    private static class PalabraPuntuada {
        public final String palabra;
        public final int distancia;
    }
}
