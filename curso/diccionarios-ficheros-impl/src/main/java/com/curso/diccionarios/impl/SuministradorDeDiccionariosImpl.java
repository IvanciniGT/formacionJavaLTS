package com.curso.diccionarios.impl;

import com.curso.diccionarios.api.Diccionario;
import com.curso.diccionarios.api.SuministradorDeDiccionarios;

import java.util.Optional;
import java.util.WeakHashMap;

public class SuministradorDeDiccionariosImpl implements SuministradorDeDiccionarios {

    private final WeakHashMap<String, Diccionario> CACHE_DE_DICCIONARIOS;

    public SuministradorDeDiccionariosImpl(){
        CACHE_DE_DICCIONARIOS = new WeakHashMap<>();
    }

    @Override
    public boolean tienesDiccionariosDe(String idioma) {
        // Lo primero miro si esta en cache. Si esta guay
        // si no está en cache, lo que miro es si existe un fichero de diccionario en el classpath para el idioma.
        return CACHE_DE_DICCIONARIOS.containsKey(idioma) || Utilidades.existeFicheroPara(idioma);
    }

    @Override
    public Optional<Diccionario> getDiccionario(String idioma) {
        if(!tienesDiccionariosDe(idioma)){
            return Optional.empty();
        } else {
            // Si no lo tengo en cache.. a la cache.. lo cargo...
            if(!CACHE_DE_DICCIONARIOS.containsKey(idioma)){
                CACHE_DE_DICCIONARIOS.put(idioma, Utilidades.cargarDiccionario(idioma).orElseThrow());
            }
            // Lo devuelvo siempre de cache
            return Optional.ofNullable(CACHE_DE_DICCIONARIOS.get(idioma));
        }
    }

}
