package com.curso.dni.impl;

import com.curso.dni.api.Dni;
import com.curso.dni.api.DniInvalido;
import com.curso.dni.api.DniUtils;
import com.curso.dni.api.ErrorEnDni;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class DniUtilsImpl implements DniUtils {

    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    private static final String PATRON_DNI = "^ *(([0-9]{1,8})|([0-9]{1,2}[.][0-9]{3}[.][0-9]{3})|([0-9]{1,3}[.][0-9]{3}))[ -]?[a-zA-Z] *$";
    private static final Predicate<String> ACABA_EN_LETRA = Pattern.compile(".*[a-zA-Z]$").asPredicate();
    private static final Predicate<String> SEPARADOR = Pattern.compile("[0-9 -][a-zA-Z]$").asPredicate();

    @Override
    public Dni of(int numero, String letra) {
        if(letra == null) return new DniInvalido(ErrorEnDni.FALTA_LETRA);
        if(letra.isBlank() ) return new DniInvalido(ErrorEnDni.FALTA_LETRA);
        if(letra.length() > 1) return new DniInvalido(ErrorEnDni.LETRA_INCORRECTA);
        return this.of(numero,letra.charAt(0));
    }

    @Override
    public Dni of(String dni) {
        if(dni == null) return new DniInvalido(ErrorEnDni.DNI_NULO);
        if(dni.isBlank() ) return new DniInvalido(ErrorEnDni.DNI_VACIO);

        dni = dni.trim();

        // Validar el patrón del dni
        if (!ACABA_EN_LETRA.test(dni)) return new DniInvalido(ErrorEnDni.FALTA_LETRA);
        if (!SEPARADOR.test(dni)) return new DniInvalido(ErrorEnDni.SEPARADOR_INCORRECTO);
        if(!dni.matches(PATRON_DNI)) return new DniInvalido(ErrorEnDni.NUMERO_INVALIDO);

        char letra = dni.charAt(dni.length()-1);
        int numero = Integer.parseInt(dni.substring(0, dni.length()-1).replaceAll("[. -]", ""));

        return this.of(numero, letra);
    }

    @Override
    public Dni of(int numero, char letra) {
        if(numero < 0 || numero > 99999999) return new DniInvalido(ErrorEnDni.NUMERO_INVALIDO);
        // La letra a mayuscula
        letra = Character.toUpperCase(letra);
        if(letra != calcularLetra(numero)) return new DniInvalido(ErrorEnDni.LETRA_INCORRECTA);
        return new DniValidoImpl(numero, letra);
    }

    private static char calcularLetra(int numero) {
        return LETRAS_DNI.charAt(numero % 23);
    }

}
