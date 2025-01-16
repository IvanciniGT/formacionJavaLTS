module diccionarios.ficheros.impl {
    // En cuanto tengo archivo module, es necesario definir todas las dependencias como requires
    // si no pongo archivo de module-info.java, se hereda el comportamiento de JAVA v8,
    // Donde no es necesario definir las dependencias
    requires diccionarios.api;
    requires lombok;

    //exports com.curso.diccionarios.impl;
    // Esto haría que alguien al usar nuestra librería (módulo) pudiera hacer un new SuministradorDeDiccionariosImpl()
    // o cual no es admisible.
    provides com.curso.diccionarios.api.SuministradorDeDiccionarios
            with com.curso.diccionarios.impl.SuministradorDeDiccionariosImpl;
    // Nuestro módulo provee una implementación de esa interfaz... Mediante una determinada CLASE
    // Esto permite SOLO al ServiceLoader instanciar esa clase. NADIE MAS PUE HACERLO.

}
