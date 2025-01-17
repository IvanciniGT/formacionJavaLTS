
Librería para la gestión/trabajo con DNIs (España)

- validar un dni (texto)
  - si está mal, que me diga los problemas 
- formatear dni


DNI: Número entre 1 y 8 dígitos + letra al final
Eso sí, la letra no es al azar.

 23.000.001 | 23
            +--------
          1   1.000.000
          ^
         resto -> [0-22]
 
Básicamente lo que aplica es un algoritmo de HUELLA (HASH)

- 23000000T VALIDO? SI
- 23000000t VALIDO? SI
- 23.000.000-T VALIDO? SI
- "23.000.000 t" VALIDO? SI
- "230.00.000(t)" VALIDO? NO
- "23.000.000At" VALIDO? NO
- 23.000t VALIDO? SI -> 00.023.000-T
- "....230T"
- federico

---

Formateo:
- Si quiero puntos o no
- Si quiero ceros al inicio o no
- Si quiero espacios al inicio o no
- letra en mayúscula o minúscula
- un determinado separador o no: " "             "-"            ""
- padding a la izquierda o a la derecha

Dni miDni = Dni.of(23000000,"T");

Dni dniValidado = DniUtils.validaDni("23.000.00t");

dniValidado.getNumero();
dniValidado.getLetra();
miDni.formatear(con puntos, con ceros...)


23000000TIvan                                       Osuna                                Ayuste
2300T    Miguel Angel                               García                               Pérez


FormatoDeDni formado = FormatoDeDni.builder()
                                    .letraMayuscula(false)
                                    .cerosDelante(false)
                                    .build();



@Builder.Default
Character separador = null;
@Builder.Default
boolean cerosDelante = true;
@Builder.Default
boolean puntosDecimales = false;
}



    ErrorEnDni validarDni(String dni);

    Optional<Dni> of(int numero, char letra);

    Optional<Dni> of(int numero, String letra);

Optional<Dni> potencialDNI = DniUtils.of(23000000,"T");
if(potencialDNI.isPresent()) {
    Dni dni = potencialDNI.get();
    dni.formatear(FormatoDeDni.builder().build());
}else{
    ErrorEnDni error = DniUtils.validaDni("23.000.00t");
    System.out.println("Error: " + error);
}

Dni dni = DniUtils.of("23000000T");
if( dni instanceof DniInvalido dniInvalido ){
    System.out.println("Error: " + dniInvalido.getError());
}else if( dni instanceof DniValido dniValido ){
    dniValido.formatear(FormatoDeDni.builder().build());
}


    ** Este sabe que va a venir...
    Si no no lo sabe!
    VVV
    APP  --->    API   <----   IMPLEMENTACION
            Quiero que el API
            Cierre el uso de DNI a (DNIValido y DNIInvalido)



// Java 8
- Programación funcional *************
  - Paquete java.util.function
  - Operadores :: ->
  - Streams
- Optional
- Paquete de fecha y hora: java.time ***************
- Meter funciones con código en interfaces (public static / default)

// java 9
- Projecto jigsaw: module-info **************
- Meter funciones con código en interfaces (private static)
- Crear colecciones inmutables List.of(), Set.of(), Map.of()
- ServiceLoader                                     Es también plug... PERO POR SPRING

// java 10
- var

// java 11
- String.isBlank()
- String.strip()
- String.lines()
- Paquete java.http

// java 17
- Patrones de instanceof            *******
  - Switch expresion                *******
- sealed class/interface                            Están genial para completar JAVA, pero con un uso MUY RESTRINGIDO
- Text blocks                       *******
- Record                                            Es un poco pluf (lombok)
- Pattern matching                  *******
