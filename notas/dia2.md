# App de consola 

Recibe como argumentos:

- IDIOMA
- PALABRA

El programa busca esa palabra en el diccionario del idioma:
- Si la palabra no existe: Muestra sugerencias
    MALON -> Melón
- Si la palabra existe, debe mostrar los significados.

---

## Cuantos proyectos independientes monto?

Cuántos proyecto maven (pom.xml) quiero para este programita?
Cuántos repos de git quiero tener?

- Frontal - En nuestro caso es un frontal de consola... con una funcionalidad.
- API de comunicación entre ellos. (Conjunto de interfaces... modelos... excepciones, enum.)
- Backend - Gestionar diccionarios, con sus palabras, significados... <--- Esto lo podríamos reusar para montar un corrector ortográfico.

---

# API

```java

package com.curso.diccionarios.api;
import java.util.Optional; // Java 1.8

public interface Diccionario {

    String getIdioma();
    boolean existe(@NonNull String palabra);
    Optional<List<String>> getSignificados(@NonNull String palabra); //Lombok
        // Qué me devuelve la función si le paso el dato: "manzana"?
        //   List<String> ---> ["Fruta del manzano"]
        // Qué me devuelve la función si le paso el dato: "archilococo"?
        //   - List<String> ---> []           NO SON EXPLICITA = AMBIGUO
        //   - null                           NO SON EXPLICITA = AMBIGUO
        //   - throw new NoSuchWordException. EXPLICITA.
        //          El problema es que es una MUY MALA PRACTICA usar excpciones para controlar LOGICA.
    List<String> getSimilares(String palabraNoExistente); 
}
/*
    Optional<List<String>> potencialesSignificados = miDiccionario.getSignificados(palabra);
    if(potencialesSignificados.isEmpty()){ // .isPresent()

    } else {
        List<String> significados = potencialesSignificados.get(); // Si no hay nada... Lanza exception
    }
*/

public interface SuministradorDeDiccionarios {
    boolean tienesDiccionarioDe(@NonNull String idioma);
    Optional<Diccionario> getDiccionario(@NonNull String idioma);
    int numeroDeIdiomasSoportados();
}


```

# Frontal de Consola

```java
package com.curso.app;

//import com.curso.diccionarios.ficheros.SuministradorDeDiccionariosDesdeFicheros; // Y AQUI LA HEMOS CAGAO !!!!
// ESA LINEA ES LA MUERTE DEL PROYECTO ESTO ES UN MONOLITO !
// Nos hemos cagado en uno de los grandes principios de desarrollo de software SOLID
// D = Principio de inversión de la dependencia.
// Un componente de alto nivel del sistema NO DEBE DEPENDER de una IMPLEMENTACION de un componente de más bajo nievl.
// En su lugar ambos deben depender de ABSTRACCIONES (interfaces)... De un API
import com.curso.diccionarios.api.SuministradorDeDiccionarios;
import com.curso.diccionarios.api.Diccionario;
import java.util.ServciceLoader; // Si trabajamos conSpring NO SE USA.. Spring tiene su propia forma (Más potente) de resolver estos escenarios.

public class App{

    public static void main(String[] args){
        ....
    }

    private void procesarPeticion(String idioma, String palabra){
        //SuministradorDeDiccionarios miSuministrador = new SuministradorDeDiccionariosDesdeFicheros();
        Iterator<SuministradorDeDiccionarios> suministradores = ServiceLoader.load(SuministradorDeDiccionarios.class).iterator();
        // En automático esto busca TODAS LAS CLASES QUE IMPLEMENTEN LA INTERFAZ EN EL CLASSPATH
        // GEnera una instancia de cada una... y me las mete en un iterador.
        if(! suministradores.hasNext()){
            System.out.println("No hay suministradores de diccionarios");
        }else {
            SuministradorDeDiccionarios miSuministrador = suministradores.next(); // Cogería el primero..
            if(miSuministrador.tienesDiccionarioDe(idioma)) {
                Diccionario miDiccionario = miSuministrador.getDiccionario(idioma).get();
                if(miDiccionario.existe(palabra)){
                    miDiccionario.getSignificados(palabra).get().forEach( significado -> System.out.println("- " + significado));
                }else{
                    System.out.println("La palabra no existe en el diccionario. Quizás quisiste decir:");
                    miDiccionario.getSimilares(palabra).forEach( palabra -> System.out.println("- " + palabra));
                }
            }else{
                System.out.println("El diccionario de ese idioma no lo tenemos");
            }
        }
    }

}
```

# Implementación del API

```java

package com.curso.diccionarios.ficheros;
import java.util.Optional; // Java 1.8
import com.curso.diccionarios.api.SuministradorDeDiccionarios;
import com.curso.diccionarios.api.Diccionario;

public class DiccionarioEnFichero implements Diccionario{

    public String getIdioma(){...}
    public boolean existe(@NonNull String palabra){...}
    public Optional<List<String>> getSignificados(@NonNull String palabra){...}
    public List<String> getSimilares(String palabraNoExistente){...}
}

public class SuministradorDeDiccionariosDesdeFicheros implements SuministradorDeDiccionarios {
    public boolean tienesDiccionarioDe(@NonNull String idioma){...}
    public Optional<Diccionario> getDiccionario(@NonNull String idioma){...}
}

```

Desde java 1.8, está considerado una muy mala práctica que una función devuelva null. De hecho SONARQUBE no lo acepta.

    App -(1)-> SuministradorDeDiccionariosDesdeFicheros -> SuministradorDeDiccionarios
    App -> SuministradorDeDiccionarios <-(1)- SuministradorDeDiccionariosDesdeFicheros QUIERO INVERTIR LA DEPENDENCIA (1)

Cómo resolver in problema de ruptura del ppio de Inversión de la dependencia.
Hay varias formas... muy estandarizadas: PATRONES
- Patrón Factoria
- Patrón Inyección de dependencias:
    Una clase NO DEBE CREAR INSTANCIAS DE LOS OBJETOS QUE NECESITA, en su lugar, le debe ser suministrado.

```java
package com.curso.diccionarios.api;

public interface Diccionario {}
public interface SuministradorDeDiccionarios {}
/*public interface SuministradorFactory{ 
    static SuministradorDeDiccionarios getSuministrador(){ // JAVA 1.8
        return new SuministradorDeDiccionariosImpl(); // Quizás podríamos usarf aquí un patrón singleton.
    }
}*/ // En lugar de esto... los módulos me permiten hacer este tipo de cosas en AUTOMATICO.

package com.curso.diccionarios.impl;

public class DiccionarioImpl implements Diccionario{}
public class SuministradorDeDiccionariosImpl implements SuministradorDeDiccionarios{}

// En otro proyecto podría:


import com.curso.diccionarios.api.Diccionario ;
import com.curso.diccionarios.api.SuministradorDeDiccionarios ;
// import com.curso.diccionarios.impl.SuministradorDeDiccionariosImpl ; Daria error... aunque sea pública... porque no se exporta por el modulo
import com.curso.diccionarios.api.SuministradorFactory;

public class App{

    private void procesarPeticion(String idioma, String palabra){
        SuministradorDeDiccionarios miSuministrador = SuministradorFactory.getSuministrador();
        //... Resto igual!
    }

}
```


# Java 9

Java 9 incorpora el mayor cambio en JAVA desde la incorporación de la programación funcional en J.1.8: EL PROYECTO JIGSAW=
Aparece el concepto de MODULO en JAVA y se MODULARIZA la JVM:

Qué es eso de un módulo?

Hasta java 1.8, en java podíamos crear:

- module
    - package
        - interface
        - class

```java
module libreriadiccionarios {
    exports com.curso.diccionario.api;
    // Y todo lo que sea public en ese paquete podrá ser accedido por otros módulos que usen este módulo
    provides com.curso.diccionarios.impl.SuministradorDeDiccionariosImpl; // Esto en automático es como si me generase un FACTORY
                                                                          // Realmente lo que hace es habilitar esa clase en el ServiceLoader
}
// En otro proyecto
module appconsola {
    requires libreriadiccionarios;
    // Y todo lo que sea public en ese paquete podrá ser accedido por otros módulos que usen este módulo
    uses com.curso.diccionarios.api.SuministradorDeDiccionarios;           // Permitir que el ServiceLoader pida instancias de este tipo.
}
```

Ese código (`module`) se define en un archivo llamado module-info.java que debe estar a primer nivel en el proyecto (.jar)

El api de java contiene muchos paquetes.
En java 1.9 ... y superiores... esos paquetes se agrupan en módulos.
Y podemos levantar máquinas virtuales de java donde solo algunos módulos estén disponibles.
Eso me da máquinas virtuales más ligeras... Y SEGURAS.
En ciertas JVM por ejemplo por defecto el módulo java.reflection. VIENE DESACTIVADO DE SERIE... por considerarse inseguro y una mala práctica a la hora de acceder a la información de las clases.

MAVEN hoy en día soporta el concepto de MODULO... y de proyecto multi módulo.

LO CIERTO es que cuando usamos SPRING (que es en la mayoría de proyectos.. Spring ya nos gestiona estos follones de otra forma.. y no lo usamos.)

---

Los lenguajes, las metodologías, las herramientas, los frameworks, las arquitecturas evolucionan 
en paralelo para resolver los nuevos problemas que vamos teniendo en cada momento del tiempo.

Y evolucionan en PARALELO.

En los 2000-2003 pensábamos que lo mejor para montar un proyecto era:
- Un monolito
- Seguir una metodología en cascada (o una variante: espiral, v...)
- Y tiraba de Structs
- Y usaba java 1.6 que tenía todo lo que yo necesitaba para montar eso.

En 2025 pensamos otra cosa:
- Los monolitos no hay quien los mantenga. YA NO LOS QUEREMOS. queremos sistemas con componentes desacoplados
- Nos hemos dado cuenta de las carencias de las met. tradicionales... y optamos por metodologías ágiles.
- Quiero un framework que me ayude a montar un sistema con COMPONENTES DESACOPLADOS
- Quiero una arquitectura de microservicios
- Y necesito una versión de java que me permita hacer esas cosas.

En 2005 montábamos apps web en JAVA... donde el HTML se generaba en servidor.
En 2025 no vale eso. Por qué? porque vamos a tener 10 frontales diferentes:
- De hecho un navegador web ya no es la forma más usada para acceder a unos datos.
  Hoy en día tiramos más de app nativa.
    - Frontal iOS           y éste no consume HTML
    - Frontal Android       y éste no consume HTML
    - Frontal Web
    - Frontal ALEXA, OK GOOGLE, SIRI    y éste no consume HTML
    - Frontal IVR

Quiero que todos accedan a los mismos DATOS (pero necesito un lenguaje de proposito general para marcar los datos: JSON/XML...)
no ve vale ya un lenguaje de propósito específico (HTML)

Y el frontal WEB (la generación del HTML), la delegamos a programas JS que corren dentro de un navegador (en cliente)

---

- App web de Animalitos Fermín. En 2004 sería un monolito.
- En 2025 hablamos del sistema de Animalitos Fermín

        Frontal iOS                 Programas que proveen los datos a esos otros (backend)
        Frontal Android
        Frontal IVR
        Frontal WEB

En un momento dado, Dia 1 del mes... Netflix cobra a 10Millones de personas. 
Y quizás hay poca gente descargando peliculas

El día 12 de Octubre... festivo... tengo poco gente a la que cobrar... pero mucha gente que se descarga películas.

Necesito más potencia de cálculo y de procesamiento en distintos programas (ESCALAR)... cluster


---

# Maven

Es una herramienta para automatizar tareas típicas en proyectos de desarrollo de software (especialmente java):
- Compilar
- Formatearlo
- Generar los javadoc
- Empaquetar
- Gestionar las dependencias
- Mandar el código a Sonar
- ...

El archivo pom.xml es el archivo de configuración de maven para mi proyecto.
En ese archivo metemos metadatos:
- Nombre
- Descripcion
- Licencia
También los datos identificativos del proyecto:
- GRUPO (groupID)
- ARTEFACTO (módulo/librería)
- Versión
Además metemos:
- módulos
- dependencias
- otras configuraciones adicionales.

---

# Nuevo paquete en j1.8 para fechas

Antiguo YODATIME incorporada al api de java.

- LocalDateTime
- ZonedDateTime
- LocalTime
- LocalDate

- Instant ---> Esto viene a ser el punto de enganche con el DATE

---

```java
public class DiccionarioEnFichero implements Diccionario{
    
    private Map<String, List<String>> palabras;
    private String idioma;

    DiccionarioEnFichero (String idioma, Map<String, List<String>> palabras){
        this.palabras=palabras;
    }

    public String getIdioma(){
        return this.idioma;
    }

    public boolean existe(@NonNull String palabra){...}
    public Optional<List<String>> getSignificados(@NonNull String palabra){...}
    public List<String> getSimilares(String palabraNoExistente){...}
}

public class SuministradorDeDiccionariosDesdeFicheros implements SuministradorDeDiccionarios {
    public boolean tienesDiccionarioDe(@NonNull String idioma){...}
    public Optional<Diccionario> getDiccionario(@NonNull String idioma){...}
}
```

Cuando me pidan un diccionario de un determinado idioma, lo cargaré desde su fichero... caso que exista...
Y la siguiente vez? otra vez?
Queremos implementar una especie de cache:

WeakHashMap<String, Diccionario>
    IDIOMA -> Diccionario

Los hashmap no son buenos candidatos a ser usados para CACHES... Por aquñi nos vienen un huevo de OutOfMEmoryException

Imaginad que lleno la RAM... cargando diccionarios... el de chino... el de español.. el japonés...
OUT OF MEMORY AL CANTO !
Me podría interesar liberar RAM quitando DICCIONARIOS DE CACHE?

---

LOMBOK... Hoy en día no usarla es absurdo!
Lombok ofrece anotaciones que nos ayudan a generar un huevo de código en automático.
Entre otras cosas... una de las grandes cagadas que sigue teniendo JAVA a día de hoy: getter y setter
Tenemos que tener claro que los getter y los setter son una CAGADA DE DISEÑO DE JAVA!

Para qué sirven los getter y setter?
- ESTO NO:  Para establecer o recuperar los valores de los atributos de un objeto.
- ESTO SI: Los getter y los setter sirven para poder establecer comportamientos adicionales al establecer o recuperar un valor de un atributo.

```java
public class Persona {
    private String nombre;
    private int edad;

    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setEdad(int edad){
        this.edad=edad;
    }
    public String getNombre(){
        return this.nombre;
    }
    public int getEdad(){
        this.edad=edad;
    }
}

```

Eso si.. si creo esto me dirían que es una muy mala práctica en JAVA.. Y ES VERDAD!


```java
// DIA 1
public class Persona {
    public String nombre;
    private int edad;
    
    public void setEdad(int edad){
        if(edad <0)
            throw new IllegalArgumentExecption();

        this.edad=edad;
    }
    public int getEdad(){
        this.edad=edad;
    }
}

// DIA 2...100 tengo genete usándola
Persona p1 = new Persona();
p1.nombre  = "Ivan";
p1.edad    = 47;
System.out.println(p1.nombre);

// DIA 101... se me ilumina la bombilla y quiero ahora que una edad no pueda ser negativa. CAMBIO DE REQUISITOS
```



```java
@Data
@AllArgumentsConstructor
@RequiredArgumentsConstructor // Constructor con variables final
public class Persona {
    private final String nombre;
    private int edad;
}
```
@Data = @Getter @Setter @ToString



```java
@RequiredArgumentsConstructor
public class DiccionarioEnFichero implements Diccionario{
    
    private final Map<String, List<String>> palabras;
    @Getter
    private final String idioma;

    public boolean existe(@NonNull String palabra){
        return palabras.containsKey(palabra);
    }
    
    public Optional<List<String>> getSignificados(@NonNull String palabra){
        //List<String> significados = palabras.get(palabra);
        //if(significados == null)
        //    return Optional.empty();
        //return Optional.of(significados); // Me los envuelves para regalo!
        //return significados == null ? Optional.empty() : Optional.of(significados);
        return Optional.ofNullable(palabras.get(palabra));
    }
/*
    public Optional<List<String>> getSignificados(@NonNull String palabra){
        List<String> significados = palabras.get(palabra);
        Optional<List<String>> aDevolver;
        if(significados == null)
            aDevolver= Optional.empty();
        else
            aDevolver= Optional.of(significados); // Me los envuelves para regalo!
        return aDevolver;
    }*/

    public List<String> getSimilares(String palabraNoExistente){
        
    }

}

public class SuministradorDeDiccionariosDesdeFicheros implements SuministradorDeDiccionarios {
    public boolean tienesDiccionarioDe(@NonNull String idioma){...}
    public Optional<Diccionario> getDiccionario(@NonNull String idioma){...}
}
```



Tenemos un algoritmo muy probado y aceptado en la industria llamado DISTANCIA DE LEVENSTHEIN:
Cuenta el número de cracteres que hay que quitar, poner o cambiar de una palabra para llegar a otra:

                        DISTANCIA
- manana -> manzana         1
- manana -> manzano         2

Básicamente tenemos que comparar la palabra que me dan con TODAS las palabras del diccionario.
Calcular su distancia de levenshtein
Ordenar de menor a mayor
Devolver las N primeras.

Stream<String> palabrasStream = palabras.keySet().parallelStream();

Palabra objetivo "manana"

Stream INICIAL                                  Stream<PalabraPuntuada>             Stream<PalabraPuntuada>           Stream<PalabraPuntuada>
                    .map()                                                  .sort( pp -> pp.distancia )
-----------------                                                                                           ----> truncado ---> 
manzana     ----> distancia de Levenshtein --->    [manzana, 1]       ------> ordenar ascendente   [manzana, 1]             [manzana, 1]
manzano                                            [manzano, 2]                por distancia       [mañana, 1]              [mañana, 1]   
mañana                                             [mañana, 1]                                     [manzano, 2]             [manzano, 2] 
ananá                                              [ananá, 2 ]                                     [ananá, 2 ] 
federico                                           [federico, 8]                                   [federico, 8]         
....
                                                                    Antes del sort FILTRAREMOS. 
                                                                        Si la palabra tiene una distancia de más de 2.

distancia de levenshtein entre:
mar
mermelada
   ______
Si las palabras en longitud difieren en más de 2... Pasa.... OTRO FILTRO

@RequiredArgumentsConstructor;
class PalabraPuntuada {
    public final String palabra;
    public final int distancia;
}

public PalabraPuntuada puntuarPalabra(String palabra1, String palabra2){
    return new PalabraPuntuada(palabra1, distance( palabra1,  palabra2));
}

public int distance(String palabra1, String palabra2);









---
@Log4j
@slf4j

log.info("");

Patrones builder
@Builder

Diccionario midict = Diccionario.builder().idioma("ES").build();