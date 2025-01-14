import java.util.ArrayList;
import java.util.function.*; // Nuevo en java 1.8.
// Define lo que llamamos interfaces Funcionales.
// Es decir, interfaces que nos permiten referenciar a FUNCIONES.
// Cada una de esas interfaces funcionales define un método para invocar a una función.
// - Function<T,R>      Representa una función que acepta un argumento de tipo T y devuelve un resultado de tipo R.
        // .apply(T t) -> R
// - Consumer<T>        Representa una operación que acepta un solo argumento de entrada de tipo T y no devuelve ningún resultado (void)
        // .accept(T t)
// - Supplier<R>        Representa una operación que no acepta argumentos de entrada y devuelve un resultado de tipo R.
        // .get() -> R
// - Predicate<T>       Representa una operación que acepta un argumento de tipo T y devuelve un valor booleano.
        // .test(T t) -> boolean

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class ProgramacionFuncional {
    
    public static void saluda(String nombre){
        System.out.println("Hola " + nombre);
    }

    public static String generaSaludoFormal(String nombre){
        return "Buenos días " + nombre;
    }

    public static String generarSaludoInformal(String nombre){
        return "Hola " + nombre;
    }
                                                /////
    public static void imprimirSaludo(Function<String, String> funcionGeneradoraDeSaludos, String nombre){
        System.out.println(funcionGeneradoraDeSaludos.apply(nombre));
    }

    public static void main(String[] args) {
        saluda("Iván");

        Consumer<String> miVariable = ProgramacionFuncional::saluda; // En java 1.8 aparece un nuevo operador: El operador DOBLE DOS PUNTO ::
        miVariable.accept("Iván");

        imprimirSaludo(ProgramacionFuncional::generaSaludoFormal, "Iván");
        imprimirSaludo(ProgramacionFuncional::generarSaludoInformal, "Iván");

        // Qué pasa si necesito pasar una función a otra, pero:
        // - el definirla en otro sitio (de forma independiente) no aporta/mejora legibilidad.... al contrario.. la empeora.
        // - Si no quiero reutilizarla en otro sitio...
        // Solo la defino porque necesito definirla para pasarla a otra función.

        // Qué son las Expresiones LAMBDA (las de la flecha)?
        // Ante todo son EXPRESIONES. En programación:
        String unNombre = "Iván"; // Statement, declaración, orden, sentencia.
        int numero = 5 + 6 ;    // Otro statement
                     /////         dentro del statement, hay una expresión.
                     ///           Una expresión es un trozo de código que devuelve un valor.
        // Una lambda por tanto es un trozo de código que devuelve un valor... qué valor?
        // Una función ANONIMA declarada dentro de la propia expresión.
        // Y en Java se hace mediante otro operator:            ->

        Function<String,String> unaFuncion = ProgramacionFuncional::generaSaludoFormal;
        Function<String,String> otraFuncion = (String nombre) -> {
                                                                        return "Buenos días amigo " + nombre;
                                                                 }; // Declarar una función que recibe un String y devuelve un String (Se infiere) delo código..
                                                    
        imprimirSaludo(otraFuncion, "Iván");
        imprimirSaludo((String nombre) -> {
                                                    return "Buenos días amigo " + nombre;
                                          }, "Iván"
                      );

        imprimirSaludo((nombre) -> { // El tipo de los argumentos se puede también inferir de la variable que lo recibe.
                        return "Buenos días amigo " + nombre;
              }, "Iván"
        );


        imprimirSaludo(nombre -> { // si solo hay un argumento, se pueden quitar los paréntesis.
            return "Buenos días amigo " + nombre;
            }, "Iván"
        );

        imprimirSaludo(nombre -> "Buenos días amigo " + nombre  // si mi función solo tiene una linea, puedo eliminar las llaves y el return.
          , "Iván"
        );

        imprimirSaludo(nombre -> "Buenos días amigo " + nombre  , "Iván"  );

        // Un ejemplo de uso de programación funcional lo podemos ver en la nueva forma de iterar sobre colecciones.
        /*
        List<Integer> numeros = new ArrayList<>();
        numeros.add(1);
        numeros.add(2);
        numeros.add(5);
        */
        //List<Integer> numeros = Arrays.asList(1,2,5); 
        // Java 1.9 En todas las INTERFACES collection se ha añadido un método llamado .of() que permite crear una colección INMUTABLE a partir de argumentos .
        List<Integer> numeros = List.of(1,2,5); // Java 1.9
            // Esto mismo está para el resto de interfaces de colecciones: Set, Map, List, etc.
            // Por qué no se añadió la función .of(...) hasta la versión 1.9?
            // Pregunta... qué tipo de "cosa" es java.util.List? Interfaz...
            // Y en java las interfaces pueden definir funciones estáticas con CODIGO ?  Desde Java 1.8 SI... YA ERA HORA !
            // Por cierto... en JAVA 1.8 solo se permiten funciones estáticas PUBLICAS! CAGADA!
            // Y las privadas? Tuvimos que esperar hasta JAVA 1.9 para que se permitieran funciones estáticas privadas en interfaces.
            // Y aún asi es una guarrada.. pero un poco menos.

        // Pre java 1.5
        for (int i = 0; i < numeros.size(); i++) {
            System.out.println(numeros.get(i)); // BUCLE TRADICIONAL
        }
        // Entre Java 1,.5 y Java 1.8   
        for (Integer numero2 : numeros) {
            System.out.println(numero2); // BUCLE FOR EACH BASADO EN ITERADORES
        }

        // Desde Java 1.8, todas las interfaces de colecciones tienen un método llamado forEach que recibe un Consumer.
        numeros.forEach(System.out::println); // lo que hace es llamar a esa función para cada uno de los elementos de la colección.
        numeros.forEach(numero4 -> System.out.println( numero4 + 5)); // lo que hace es llamar a esa función para cada uno de los elementos de la colección.

        // Un ejemplo de uso de programación funcional lo podemos ver en los Streams.
        // Qué es un Stream? La forma en la que se ha incorporado en JAVA el modelo de programación MAP-REDUCE
        // Al añadirse soporte a programación funcional en JAVA 1.8, se pudo implementar el modelo MAP-REDUCE.
        // Es impresionante.
        // Tenemos la interfaz Stream<T>... es como una lista.. pero con funciones diferentes.
        // En lugar de tener funciones para añadir elementos, recuperar un elemento, borrar un elemento, etc.
        // Tenemos funciones para aplicar transformaciones sobre los elementos de la colección usando programación funcional.
        // Cualquier collection lo podéis convertir a un Stream mediante el método .stream()
        // Cualquier Stream lo podéis convertir a una collection mediante el método .collect( Collectors.toList() ), .collect( Collectors.toSet() ), etc.
        // De hecho en Java 17, teneís disponible directamente la función .toList(), .toSet(), etc.
        Stream<Integer> numerosStream = numeros.stream(); // Convertimos la lista de números en un Stream de números.
        List<Integer> otraListaDeNumeros = numerosStream.collect(Collectors.toList()); // Convertimos el Stream de números en una lista de números.
        // Desde java 17:
        List<Integer> otraListaDeNumeros2 = numeros.stream().toList(); // Convertimos el Stream de números en una lista de números.

        numeros = List.of(1,2,5); 
        List<Integer> modificados = numeros.stream()
                //.parallel()
                // .parallelStream() es igual a .stream().parallel()
                .map( elNumero -> elNumero * 2)  // Permite generar otro stream (como otra lista) con los valores resultantes 
                                                // de aplicar una función a cada uno de los elementos del stream.
                .map( elNumero -> elNumero + 6)
                .filter( elNumero -> elNumero % 2 == 0) // Permite filtrar los elementos del stream que cumplan una condición.
                .filter( elNumero -> elNumero > 10)
                .sorted() // Permite ordenar los elementos del stream.
                //.forEach(System.out::println); // Permite recorrer los elementos del stream y aplicar una función a cada uno de ellos.
                .toList();
        System.out.println(modificados);
        // Esto va infinitamente más rápido que los for tradicionales.
        // Cuantos elementos tengo en esa lista ahora? 3... y si tuviera 300.000.000?
        // A cuánto vería la CPU de mi máquina durante un ratito? Pero esto no pondría la CPU al 100%... Lo CUAL ESTA MAL !!!!
        // por qué? Cuantos hilos están ejecutando ese código a la vez? SOLO 1...
        // Por tanto si tuviera una máquina con 8 cores... la vería al 12.5%... y eso no me gusta = CAGADA !
        // Lo quiero ver al 100%... si no llega al 100% es que no estoy aprovechando la CPU de la máquina.
        // Qué tal es abrir hilos en JAVA??? Y sincronizar resultados.. y todo ese rollo? SE NOS ATRAGANTA !
        // QUE MARAVILLA LOS STREAMS !
        // Simplemente poniendo en medio del flujo del stream la función parallel(), java solito se encarga de abrir hilos y sincronizar resultados, cerrar hilos, etc.
    }

}

