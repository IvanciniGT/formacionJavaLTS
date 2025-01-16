package com.curso.diccionarios.app;

import com.curso.diccionarios.api.Diccionario;
import com.curso.diccionarios.api.SuministradorDeDiccionarios;

import java.util.Optional;
import java.util.ServiceLoader;

public class Aplicacion {

    // java com.curso.diccionario.app.Aplicacion ES manzana
    public static void main(String[] args) {
        System.out.println("Bienvenido a la Aplicación de diccionarios");

        if (args.length != 2) {
            System.err.println("Uso: java com.curso.diccionario.app.Aplicacion <idioma> <palabra>");
            System.exit(1);
        }

        String idioma = args[0];
        String palabra = args[1];

        // Debemos conseguir un diccionario del idioma que nos indican.
        // Se lo pediremos a un SuministradorDeDiccionarios.
        // De dónde saco el suministrador? A mi me da igual. El que haya en el classpath.
        // A eso nos ayuda el ServiceLoader.

        // Nuestro ServiceLoader va a buscar en el classpath una implementación de la interfaz SuminsitradorDeDiccioanrios
        // Y de ella, genera una instancia (hace un new())... eso lo que me entrega.
        // Eso si, dentro de un Optional... porque puede ser que no lo encuentre.
        Optional<SuministradorDeDiccionarios> potencialSuministrador = ServiceLoader.load(SuministradorDeDiccionarios.class).findFirst();

        if (potencialSuministrador.isPresent()) {
            SuministradorDeDiccionarios miSuministrador = potencialSuministrador.get();
            if (miSuministrador.tienesDiccionariosDe(idioma)) {
                // El .orElseThrow() es para que si al hacer el .get No hubiera nada, se lance una Exception en automático.
                // En nuestro caso, si no me devolviera nada después de haber dicho que SI TENIA DICCIONARIO, sería un BUG y querriamos una excepción.
                Diccionario miDiccionario = miSuministrador.getDiccionario(idioma).orElseThrow(); //()-> new IllegalArgumentException() );
                if (miDiccionario.existe(palabra)) {
                    System.out.println("La palabra " + palabra + " SI existe en el diccionario");
                    System.out.println("Significados:");
                    //var potencialesSignificados = miDiccionario.getSignificados(palabra);
                    //if(potencialesSignificados.isPresent()){
                    //List<String> significados = potencialesSignificados.get();
                    //for(String significado : significados){
                    //    System.out.println(" - " + significado);
                    //}
                    //
                    //    potencialesSignificados.get().forEach(significado -> System.out.println(" - " + significado));
                    //}
                    miDiccionario.getSignificados(palabra).ifPresent(
                            significados -> significados.forEach(
                                    significado -> System.out.println(" - " + significado)
                            )
                    );
                } else {
                    System.out.println("La palabra " + palabra + " NO existe en el diccionario");
                    System.out.println("Palabras similares:");
                    miDiccionario.getSimilares(palabra).forEach(
                            palabraSimilar -> System.out.println(" - " + palabraSimilar)
                    );
                }
            } else {
                System.err.println("No tengo el diccionario de " + idioma);
                System.exit(1);
            }
        } else {
            System.err.println("No se ha encontrado un suministrador de diccionarios válido en el classpath.");
            System.exit(1);
        }

    }

}
