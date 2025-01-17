package com.curso.dni.impl;

import com.curso.dni.api.DniValido;
import com.curso.dni.api.FormatoDeDni;
import lombok.Value;

@Value
public class DniValidoImpl implements DniValido {

    int numero;
    char letra;

    @Override
    public String formatear(FormatoDeDni formato) {
        String dniFormateado = ""+(formato.getSeparador().isPresent()?formato.getSeparador().get():"")+getLetra();
        String parteNumerica = String.valueOf(getNumero());
        // Ceros delante
        if(formato.isCerosDelante())
            parteNumerica = ("00000000"+parteNumerica).substring(parteNumerica.length());
        // Separador de miles
        if(formato.isPuntosDecimales()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parteNumerica.length(); i++) {
                if(i>0 && i%3==0) sb.append('.');
                sb.append(parteNumerica.charAt(i));
            }
            parteNumerica = sb.toString();
        }
        return parteNumerica+dniFormateado;
    }
}
