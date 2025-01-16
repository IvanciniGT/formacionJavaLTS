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
class DiccionarioImpl implements Diccionario {

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
/*
    @RequiredArgsConstructor
    private static class PalabraPuntuada {
        public final String palabra;
        public final int distancia;
    }
 */
                                    // Esto serían los argumentos del constructor de la clase / record
    private record PalabraPuntuada(String palabra, int distancia) {}
    // ^^^^FINAL: No permite extender el record.. Lo cual ocurre por defecto.. por ser un record

    /*


        Este código de arriba sería equivalente a:
        private final class PalabraPuntuada {
            public final String palabra;
            public final int distancia;

            public PalabraPuntuada(String palabra, int distancia){
                this.palabra = palabra;
                this.distancia = distancia;
            }

            public String toString(){
                return "PalabraPuntuada[palabra=" + palabra + ", distancia=" + distancia + "]";
            }

            public boolean equals(){
                if(this == o) return true;
                if(o == null || getClass() != o.getClass()) return false;
                PalabraPuntuada that = (PalabraPuntuada) o;
                return distancia == that.distancia && Objects.equals(palabra, that.palabra);
            }
        }


        En la práctica los record no los veréis mucho.... ya que LOMBOK llego antes.. y hace MUCHO MAS ...
        Y se ha impuesto.


    @Value // @Getter @AllArgumentsConstructor, @EqualsAndHashCode, @ToString, "private final"
    private final class PalabraPuntuada {
        String palabra;
        int distancia;
    }
    // En este caso, lombok crea getters de las props. Lo cual nos da una sintaxis MAS HOMOGENEA con el resto de proyecto.

    Pero es que lombok me da ademas:
    @Data (Sirve para montar DTOs) // IGUAL @Getter @Setter @AllArgumentsConstructor, @EqualsAndHashCode, @ToString
    private final class PalabraPuntuada {
        private String palabra;
        private int distancia;
    }


    */
}
