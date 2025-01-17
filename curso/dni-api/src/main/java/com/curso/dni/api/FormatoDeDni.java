package com.curso.dni.api;

import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class FormatoDeDni {
    @Builder.Default
    boolean letraMayuscula = true;
    @Builder.Default
    Character separador = null;
    @Builder.Default
    boolean cerosDelante = true;
    @Builder.Default
    boolean puntosDecimales = false;

    public Optional<Character> getSeparador() {
        return Optional.ofNullable(separador);
    }
}
