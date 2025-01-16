package com.curso.diccionarios.api;

import java.util.Optional;

public interface SuministradorDeDiccionarios {

    boolean tienesDiccionariosDe(String idioma);

    Optional<Diccionario> getDiccionario(String idioma);

}
