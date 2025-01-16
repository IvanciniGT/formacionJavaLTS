module diccionarios.app {

    requires diccionarios.api; // Este módulo necesita que la JVM tenga este otro módulo.
    // Si en el classpath no encuentra el módulo diccionarios.api, no podrá ejecutar mi app... y ni arrancará.

    uses com.curso.diccionarios.api.SuministradorDeDiccionarios; // Necesito una implementación de SuministradorDeDiccionarios
    // Esa también debe estar en el classpath

}