import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProgramacionFuncional {

    public static void main(String[] args) {
        List<Integer> numeros = List.of(1,2,4,5,8);

        numeros.forEach(System.out::println);

        Stream<Integer> numerosStream = numeros.stream();   // Cualquier Collection a List
        List<Integer> numerosLista = numerosStream.collect(Collectors.toList());
                                                    // Cualquier Stream a Collection
                                                            // Dentro de los paretesis indico el Collector...
                                                            // básicamente a que tipo de Collectipon quiero convertirlo
        //List<Integer> numerosLista2 = numerosStream.toList(); // Java 16

        // MAP: aplica una funcion sobre cada elemento del stream. CAPTURA su resultado...
        // Y todos los resultados los empaqueta en un nuevo Stream
        // BASICAMENTE TRANSFORMAR UNA COLECCION DE DATOS MEDIANTE UNA FUNCION...
        Stream<Integer> dobles = numeros.stream().map( numero -> numero * 2);

        Stream<String> letras = numeros.stream().map( numero -> "Federicoooooo ".charAt(numero)+"");
        // IMPORTANTISIMO: Sobre los Streams
        // Los Streams siguen un modelo de programación MAP-REDUCE
        // Básicamente consiste en aplicar sobre un objeto colección que soporta programación MAP-REDUCE (En JAVA Stream)
        // muchas funciones de tipo MAP... y acabar con una función de tipo REDUCE
        // Funciones tipo MAP... Hay DECENAS! map, filter, flatMap, distinct, sorted, limit, skip, peek, takeWhile, dropWhile, concat, etc...
        //     Son funciones que partiendo de un Stream devuelven un Stream
        //     Son funciones que se ejecutan en modo PEREZOSO: LAZY <<<<< ESTA ES LA MAGIA DE LOS STREAMS.. Y lo que hay que conocer
        //         Hasta que el valor resultado de la operación de tipo map no es necesario, no se calcula
        // Funciones de tipo REDUCE... Hay DECENAS! forEach, reduce, collect, count, anyMatch, allMatch, noneMatch, findFirst, findAny, min, max, etc...
        //     Son funciones que partiendo de un Stream devuelven un valor QUE NO ES UN STREAM
        //     Son funciones que se ejecutan en modo ANSIOSO: EAGER
        //     Se ejecutan en cuanto se invocan
        numeros.stream().forEach(System.out::println);


        System.out.println("Añadiendo números");
        long tin = System.nanoTime();
        List<Integer> muchosNumeros = new ArrayList<>();
        for(int i = 0; i < 10000000; i++) {
            muchosNumeros.add(i);
        }
        long tout = System.nanoTime();
        System.out.println("Tiempo en añadir: " + (tout - tin));
        System.out.println("Numeros añadidos");

        tin = System.nanoTime();
        Stream<Double> resultado = muchosNumeros.parallelStream()
                .map( numero -> Math.random())                                  // Calculo un número aleatorio
                .map( numeroAleatorio -> numeroAleatorio * 100)                 // Lo multiplico por 100
                .map( numeroAleatorio -> numeroAleatorio * numeroAleatorio)     // Lo elevo al cuadrado
                .map( numeroAleatorio -> numeroAleatorio / 23)                  // Lo divido por 23
                .sorted()                                                              // Lo ordeno
                .map( numeroAleatorio -> Math.sqrt(numeroAleatorio));           // Le saco la raiz cuadrada

        tout = System.nanoTime();
        System.out.println("Tiempo en transformar: " + (tout - tin));
        // Cuanto tiempo tarda en ejecutarse ese código? NADA
        // Realmente no ejecuta ninguna operación... Solo APUNTA que esas operaciones deben ejecutarse.

        tin = System.nanoTime();
        System.out.println(resultado);
        tout = System.nanoTime();
        System.out.println("Tiempo en mostrar: " + (tout - tin));

        // Es al realizar una operación reduce, que se dispara tod o el proceso de cálculo (los map).
        // Hay un efecto dominó.
        tin = System.nanoTime();
        System.out.println("Resultado: " + resultado.toList());
        tout = System.nanoTime();
        System.out.println("Tiempo en mostrar: " + (tout - tin));

        // Al ejecutarse esos claculos, El Stream puede decidir COMO IRLOS EJECUTANDO.

        // Otra cosa importante es que los Streams SE CONSUMEN al hacer un reduce

        System.out.println("Resultado: " + resultado.count());


    }

}
