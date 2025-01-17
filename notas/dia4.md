
Estamos montando un sistema con componentes desacoplados.

Eso significa que tenemos muchos componentes que entre si dependen de abstracciones (interfaces).

Imaginad esto mismo en una bicicleta.

Defino el concepto de manillar (interfaz... especificación)
Defino el concepto de rueda ( no se cuantas pulgadas de radio y tal ancho de rueda)
Defino el concepto de cuadro

Con eso establecido, puedo empezar a montar las IMPLEMENTACIONES:
- Creo / construyo un cuadro FISICO
- Creo / construyo un manillar FISICO
- Creo / construyo una rueda FISICA

La bicicleta es el SISTEMA completo que quiero montar... y es un sistema FISICO... concreto.. con componentes de verdad .

INTEGRO COMPONENTES REALES... 
Voy a tomar:
- El cuadro AB-1938T de color negro que he montado
- La rueda 284762/8 que he montado
- El manillar skdhf983 que he montado y los pongo juntos.

---

# Sealed Interfaces 

En muchas ocasiones necesitamos definir una interfaz para que una función pueda devolver objetos de distintos tipos

public sealed class Animal permits Gato, Perro{
}

public class XXXX {
    public Animal getAnimal(){}
}


Mis animales son Perro y Gato, clases que implementan Animal

El problema es que me puedo poner a crear código:

Alguien que usa getAnimal, podría hacer:
Si lo que me devuelve es un perro: Hago algo
Si lo queme devuelve es un Gato hago otra cosa.

Nada impide que el día de mañana alguien cree un Cocodrilo, que extienda de Animal...
Y se me jode el sistema.

En Software Orientado a Objetos: SOLID
- L: Principio de sustitución de Barbara Liskov