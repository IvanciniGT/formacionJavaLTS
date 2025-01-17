package com.curso.dni.api;

public non-sealed interface DniValido extends Dni {

    int getNumero();
    char getLetra();
    String formatear(FormatoDeDni formato);

}
