package com.curso.diccionarios.impl;

import com.curso.diccionarios.api.Diccionario;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Utilidades {

    int DISTANCIA_MAXIMA_ADMISIBLE = 2;
    int NUMERO_MAXIMO_SIMILITUDES = 5;

    // Java 1.8. Podemos poner código en interfaces dentro de funciones estáticas públicas
    // A partir de Java 9, podemos poner métodos privados static en interfaces
    static String normalizar(String palabra){
        return palabra.toLowerCase().trim();
    }

    static int distancia(String str1, String str2) {
        return computeLevenshteinDistance(str1.toCharArray(), str2.toCharArray());
    }

    private static int minimum(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    private static int computeLevenshteinDistance(char [] str1, char [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];

        for(int i=0;i<=str1.length;i++){
            distance[i][0]=i;
        }
        for(int j=0;j<=str2.length;j++){
            distance[0][j]=j;
        }
        for(int i=1;i<=str1.length;i++){
            for(int j=1;j<=str2.length;j++){
                distance[i][j]= minimum(distance[i-1][j]+1,
                        distance[i][j-1]+1,
                        distance[i-1][j-1]+
                                ((str1[i-1]==str2[j-1])?0:1));
            }
        }
        return distance[str1.length][str2.length];
    }

    static boolean existeFicheroPara(String idioma){
        // El fichero lo buscamos en el classpath... Podemos tirar del ClassLoader
        return getRutaDelDiccionario(idioma).isPresent();
    }

    static Optional<Diccionario> cargarDiccionario(String idioma)  {
        Optional<URL> potencialRutaDelDiccionario = getRutaDelDiccionario(idioma);
        Diccionario aDevolver = null;
        if(potencialRutaDelDiccionario.isPresent()) {
            // Tengo fichero... me toca leerlo
            // El fichero va a tener la siguiente estructura:
            //  Palabra=Significado1|Significado2|Significado3
            //  palabra2=Significado1
            //  Palabra2=Significado1
            //
            //  Palabra3=Significado1|Significado2
            // Para leer el fichero, podemos tirar de una funcion genial que nos han puesto en Java 11
            //Path pathDelFichero = Path.of(potencialRutaDelDiccionario.get().getPath());
            try {
                Path pathDelFichero = Path.of(potencialRutaDelDiccionario.get().toURI());
                // String contenido = Files.readString(pathDelFichero); // Java 11
                // Stream<String> lineas = contenido.lines();// JAVA 11... Me devuelve un Stream<String> con cada linea del fichero.. partiditas por \n \r \n\r
                Map<String, List<String>> palabras = Files.readString(pathDelFichero).lines()
                        .filter( linea -> !linea.isBlank() )      // JAVA 11. isBlank en String.  Si la linea no está en blanco
                        .filter( linea -> linea.contains("=") )      // JAVA 11. isBlank en String.  Si la linea no está en blanco
                        .map( linea ->  linea.split("=") )  // Separar la palabra de los significados, por el carácter =
                                                                        // Llegados a este punto, tendremos que tipo de objeto? Stream<String[]>.. Ese array, en la primera posición tiene la palabra... en la segunda los significados
                                                                        //  "Palabra=Significado1|Significado2|Significado3"
                                                                        //  ["Palabra", "Significado1|Significado2|Significado3"]
                        .collect( Collectors.toMap( // Convierte el stream en un map... Pero necesita 2 funciones:
                                array -> normalizar(array[0]), // La función para determinar qué se usa como clave
                                array -> List.of(array[1].split("\\|")), // La función para determinar qué se asigna como valor a esa clave.
                                (listaSignificados1, listaSignificados2) ->  // Si hay dos palabras iguales despues de normalizar, se juntan las 2 listas de significados en 1
                                    Stream.concat(listaSignificados1.stream(), listaSignificados2.stream()).toList()
                            )
                        );
                aDevolver = new DiccionarioImpl(idioma, palabras);
            } catch (IOException e) {
                // Logueo por ahí que no he podido leer el fichero
            }catch (URISyntaxException e){
                // Logueo por ahí que no he podido leer el fichero
            }
        }
        return Optional.ofNullable(aDevolver);
    }

    private static Optional<URL> getRutaDelDiccionario(String idioma) {
        return Optional.ofNullable(Utilidades.class.getClassLoader().getResource("diccionario." + idioma + ".txt"));
    }

}
