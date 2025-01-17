
Java LTS (Long Term Support):
Son versiones con soporte a largo plazo.
- 1.8
- 11
  - 9
- 17
  - 15
- 21 <-

---

# JAVA

Es un lenguaje de programación que:

## Tipado dinámico (débil) vs Tipado estático (fuerte)? 

Cuando trabajamos con lenguajes de programación manejamos datos. Y esos datos tienen un determinado tipo:
    - String
    - int
    - List<String>
    - Date
Eso ocurre en todos los lenguajes de programación sin excepción.

Pero también al escribir código trabajamos con variables.

### ¿Qué es una variable?

x Un almacén donde guardamos datos. Esa definición de variable: Una variable es como una cajita donde meto cosas, para luego recuperarlas es válida para otros lenguajes de programación.
En función del lenguaje de programación que estemos utilizando, el concepto de variable cambia.
- En C, C++, C#, Fortán, Ada, una variable la podemos definir de esa forma. Está bien.
- En Java, Javascript, TypeScript, Python... no. En estos lenguajes el concepto de variable tiene que ver más con el concepto de PUNTERO en C.

```java
    ...
    String texto = "hola";      // Asigna el valor "hola" a la variable "texto"             ES LO CONTRARIO
                                // Inicializa la variable "texto" con el valor "hola"       ES LO CONTRARIO
    ...
```

Esa linea tiene tres partes diferentes, que por orden de ejecución serían:
- "hola"            Crear un objeto de tipo String en memoria RAM... con valor "hola".
- "String texto"    Crea una variable de "tipo String"... Eso significa que esa variable puede APUNTAR / REFERENCIAR a objetos de tipo String... o de sus subtipos.
- "="               Asigna la variable "texto" al objeto "hola".

En los lenguajes de tipado ESTATICO, las variables tienen tipo, igual que los datos... y solo pueden apuntar a datos de su mismo tipo o de sus subtipos.

Eso por ejemplo no ocurre en js

```js
var texto = "hola";
```

"hola" es de tipo texto... es un dato... y en JS los datos TAMBIÉN TIENEN TIPO
Pero la variable "texto" no tiene tipo... puede apuntar a cualquier dato, independientemente de su tipo.

Volviendo a JAVA...

```java
    texto = "adios";
```

- "adios"           Crear un objeto de tipo String en memoria RAM... con valor "adios".
                    Dónde se crea ese objeto en RAM? En el mismo sitio dónde se creó el objeto "hola" o en uno nuevo?
                    EN UNO NUEVO.... y en este momento en JAVA, en la RAM hay dos objetos de tipo String... uno con valor "hola" y otro con valor "adios".
- "texto ="         Asigna la variable "texto" al nuevo objeto "adios".
                    (Muevo/Vario) el postit de una ubicación a otra.
                    Por cierto... el objeto "hola" sigue en la RAM... pero desde JAVA ya no lo puedo acceder... porque no tengo ninguna variable que apunte a él. Se acaba de convertir en BASURA (Garbage).
                    Y quizás o no... en algún momento el recolector de basura de JAVA (Garbage Collector) lo elimine de la RAM.

```java
var texto = "hola";         // Desde JAVA 11 en adelante esto es válido.
```
PERO OJO !!!!
El `var`de JS no es el mismo `var` de JAVA.
JAVA, aunque ahora admita la palabra `var` para definir variables, sigue siendo un lenguaje de tipado estático.
Lo que ocurre es que JAVA infiere el tipo de la variable a partir del tipo del primer dato que le asignamos (con el que la inicializamos).

```js
var texto = "hola";
texto = 33; // Esto es perfectamente válido en JS
```

```java
var texto = "hola";
texto = 33;             // Error de compilación... ya que la variable "texto" es de tipo String... y no puede apuntar a un objeto de tipo int.
```

Lo cierto es que esta palabrita la pusieron en JAVA para intentar atraer a la gente de JS y PYTHON... y veremos más de una cosa así en el curso.

Las buenas prácticas es no usar la palabra var.. SOLO EN CASOS MUY EXCEPCIONALES, donde realmente APORTE ALGO DE VALOR la usaremos.

REGLA DE ORO! El objetivo número 1 a día de hoy en el mundo del desarrollo de software es la LEGIBILIDAD del código. Que sea fácil de leer, de entender, de mantener, de modificar, de depurar, de testear... y de todo lo que se te ocurra.

Una de las grandes ventajas del tipado estático es que un desarrollador rápidamente entienda cómo comunicarse con el código hay escrito.
Y eso lo perdemos con la palabra var. De hecho, los lenguajes de tipado dinámico se usan solo para "programitas" (scripts)... es más... cuando se ha querido hacer un proyecto MAS SERIO con este tipo de lenguajes, ha sido necesario dotar al lenguaje de elementos (sintaxis) que nos permitan identificar los tipos.

```py
def generar_informe(titulo:str , datos:list(int)): 
    # Aquí iría el código de la función
```

```java
Map<String,List<Map<String, Date>>> dato = unaFuncion();
otraFuncion(dato);
```
En un caso como este es donde puede aportar algo la palabra var... pero en general, no la usaremos.

```java
var dato = unaFuncion();    // Esto puede ser más legible
otraFuncion(dato);
```

---

# Qué tal le va a JAVA? como lenguaje...

No muy bien... ha vivido tiempos mejores.

Allá por el año 2000, JAVA era el lenguaje de programación más popular (y usado) del mundo... y lo fue durante unos cuantos años.
Todo el mundo quería aprender JAVA...
Y todo nuevo proyecto se hacía en JAVA.

- App web : JAVA
- App mobile (Android) : JAVA
- App de escritorio : JAVA (swing)
- Software embebido : JAVA (J2ME)

Hoy en día:
- App web :
    - Frontal: JS (con React, Angular, Vue...)
    - Backend: JAVA ( el único resquicio que le ha quedado a JAVA... y gracias a un framework que existe en JAVA: Spring)
      - El año pasado, se acabo de migrar Spring a Kotlin...
- App mobile (Android) : Kotlin

        .java -> compila (javac) -> .class -> JVM (Java Virtual Machine)
                                    (bytecode)

        .kt -> compila (kotlinc) -> .class -> JVM (Java Virtual Machine)
                                    (bytecode)
        .scala -> compila (scalac) -> .class -> JVM (Java Virtual Machine)
                                    (bytecode)
            (Kotlin o Scala ofrecen una sintaxis alternativa a JAVA... para generar bytecode, que corra en la JVM)
- App de escritorio : Vb, C#, Objective-C, Swift
- Software embebido : C, C++, Python

Qué pasó? Por qué JAVA ha perdido tanto terreno?

## Versiones de JAVA

Versión 1.1 -> Año 1997
Versión 1.2 -> Año 1998
Versión 1.3 -> Año 2000
Versión 1.4 -> Año 2002
Versión 1.5 -> Año 2004
Versión 1.6 -> Año 2006
                            Aquí vino la primera "muerte de JAVA": La compra de SUN por parte de ORACLE.
                            A Oracle JAVA le valía MIERDA... lo que quería era el hardware de SUN.
                                    MySQL                       -> MariaDB
                                    OpenOffice (StarOffice)     -> LibreOffice
                                    Hudson                      -> Jenkins
Versión 1.7 -> Año 2011     ¿Qué ha pasado?

Versión 1.8 -> Año 2014     ¿Qué ha pasado?
                            Aquí vino la puntilla... Oracle anuncia que va a empezar a cobrar por JVM:
                            - para particulares: 25$ al año
                            - para empresas: 50$/core al año

                            En esta situación, UNA EMPRESA que plantó cara a Oracle... y que tenía grandes intereses en JAVA: GOOGLE 

                            - Se acordó liberar la máquina virtual JAVA. Hoy en día tenemos MUCHAS implementaciones de la JVM:
                                - OracleJDK
                                - OpenJDK
                                - Eclipse Temurin
                                - Amazon Corretto
                                - ...
                            - Se forzó a que salieran 2 versiones de JAVA al año
                            - Se forzó a Oracle a donar JEE a una fundación (Jakarta)
                              JEE? J2EE -> JEE (Java Enterprise Edition) -> JEE (Jakarta Enterprise Edition)
                                Es una colección de especificaciones que definen cómo se deben hacer las cosas en JAVA en el mundo empresaria:
                                - JDBC
                                - JPA
                                - JMS
                                - ...
                            Pero google no perdonó. ni olvidó.
                            - Encargó a JetBrains (la empresa que desarrolla Kotlin) que hiciera un lenguaje de programación que corriera en la JVM... y que fuera más moderno que JAVA.
                            - En paralelo, tomaron el interprete JS de su proyecto CRHOMIUM (el que se usa como base para montar Chrome, Edge, Safari, Opera...) y lo convirtieron en un proyecto independiente: NODEJS
                                Node es el JVM de JS.
                                Me permite correr código JS fuera de un navegador. 
Versión   9 -> Año 2017     ¿Qué ha pasado?     En 11 años, salen 3 versiones de JAVA.
Versión  10 -> Año 2018     
Versión  11 -> Año 2018     ¿Qué ha pasado?     En 1 año, salen 3 versiones de JAVA.                LTS
Versión  12 -> Año 2019
Versión  13 -> Año 2019
Versión  14 -> Año 2020
Versión  15 -> Año 2020
Versión  16 -> Año 2021
Versión  17 -> Año 2021                                                                             LTS
Versión  18 -> Año 2022
Versión  19 -> Año 2022
Versión  20 -> Año 2023
Versión  21 -> Año 2023                                                                             LTS
Versión  22 -> Año 2024
Versión  23 -> Año 2024
Versión  24 -> Año 2025


Interesante entender que desde Java 1.9 se ha intentado recuperar a parte de la gente que se fué a otros lenguajes ... y atraer a gente nueva... pero no ha funcionado.

Hoy en día los 2 lenguajes de programación más populares del mundo son: PYTHON y JS.
Una de las grandes quejas ha sido siempre en JAVA que es un lenguaje MUY VERBOSO... En este sentido se han metido "chorraditas" en JAVA como por ejemplo 'var' paa intentar que sea más sencillo inicialmente para gente nueva.

Por otro lado, hay cosas que aunque SI tienen sentido, en la práctica tenemos mejores alternativas:
- records de JAVA 17
    ```java
    record Persona(String nombre, int edad) {}

    Persona p1 = new Persona("Pepe", 33);
    System.out.println(p1.nombre());
    ```
    Es un tipo de clase INMUTABLE... que tiene un constructor, un método equals, un método hashCode, un método toString... y un método para acceder a los campos.
    En la práctica tenemos librerías como LOMBOK que nos permiten hacer esto mismo... y mucho más.
    ```java
    @Value
    public class Persona{
        String nombre;
        int edad;
    }

    Persona p1 = new Persona("Pepe", 33);
    System.out.println(p1.getNombre());
    ```

    ```java
    public class Persona {

        private final String nombre;
        private final int edad;

        public Persona(String nombre, int edad) {
            this.nombre = nombre;
            this.edad = edad;
        }

        public String getNombre() {
            return nombre;
        }

        public int getEdad() {
            return edad;
        }
    }
    ```
---

# JAVA 1.8

- **Programación funcional**
    - Nuevo paquete java.util.function
    - Lambda
    - Stream
- Optional
- Paquete "nuevo" para la gestión de fechas y horas 
  Digo "nuevo" porque no es sino la antigua librería JODA-TIME... que se ha integrado en JAVA.
    java.util.Date
    java.sql.Date
    java.util.Calendar -> java.util.GregorianCalendar
- ...

---


# Paradigmas de programación

Son formas de usar un lenguajes para expresar conceptos.

## Programación imperativa

Cuando damos órdenes a la computadora que debe procesar de forma secuencial.
A veces queremos romper esa secuencialidad... y para ello usamos estructuras de control: IF, WHILE, FOR, SWITCH...

## Programación procedural

Cuando el lenguaje me permite agrupar sentencias de órdenes en funciones/métodos/procedimientos/subrutinas.
Y posteriormente invocar esas funciones/métodos/procedimientos/subrutinas.
Aporta/Beneficios:
- Reutilizar código
- Mejora la estructura/mantenimiento del código (lectura)
- Poder pasarle una función a otra que requiera una función como parámetro.

## Programación orientada a objetos

Hemos dicho que cualquier lenguaje maneja datos... y esos datos tienen un determinado TIPO de datos.
Todos los lenguajes traen algunos tipos de datos PREDEFINIDOS:
- String
- Numbers
- Arrays / Listas
Hay lenguajes que me permiten definir mis propios tipos de datos... y a eso se le llama PROGRAMACIÓN ORIENTADA A OBJETOS.
Esos tipos de datos es lo que llamamos una CLASE. Los datos que creamos de esos tipos es lo que llamamos OBJETOS.
Al definir un nuevo tipo de datos (CLASE) podemos darle sus características (ATRIBUTOS) y sus comportamientos (MÉTODOS).
                        Caracteristicas (ATRIBUTOS)                                     Comportamientos (MÉTODOS)
    String              Secuencia de caracteres                                         .toUpperCase() .startsWith()
    GregorianCalendar   Fecha y hora
                        Fecha: Año, Mes, Día
                        Hora: Hora, Minutos, Segundos + Milisegundos, Zona horaria      .add() .set() .get()

Hay un tipo de clases, que es lo que denominamos MODELOS... que simulan comportamientos/objetos del mundo real.

## Programación funcional

Esto es muy antiguo... la cosa es que en java, hasta 1.8 no lo teníamos disponible. Y ESTABAMOS MUY LIMITADOS.
JS, PYTHON, RUBY, SCALA, KOTLIN... lo tienen desde el principio. En C desde sus orígenes.
Esto cambia la forma de programar....
El API de JAVA entero esta migrando a programación funcional.

Decimos que un lenguaje soporta un paradigma funcional cuando:
- Me permite que una variable apunte a una función. (Se tratan las funciones como cualquier otro tipo de datos)
- Posteriormente me permite invocar esa función a través de esa variable.

Lo interesante no es lo qué es la programación funcional.
Lo importante (y lo que nos vuela la cabeza) es lo que podemos comenzar a hacer cuando el lenguaje soporta esto:
- Crear funciones que reciben funciones como parámetros.
- Crear funciones que devuelven funciones.

Esto me permite suministrar parte de la lógica de una función como un argumento.