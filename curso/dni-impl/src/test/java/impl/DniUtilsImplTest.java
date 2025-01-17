package impl;

import com.curso.dni.api.*;
import com.curso.dni.impl.DniUtilsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class DniUtilsImplTest {

    @Test
    void validateDnisBuenos() {
        List<String> dnis = List.of("230000T", "23.000.000-t", "23.000 T", "00023000t");
        DniUtils dniUtils = new DniUtilsImpl();
        Assertions.assertEquals(0,
                dnis.stream()
                        .map(dniUtils::of)
                        .filter(DniInvalido.class::isInstance)
                        .count()
        );

        FormatoDeDni formato= FormatoDeDni.builder()
                .letraMayuscula(true)
                .separador('-')
                .cerosDelante(true)
                .puntosDecimales(false)
                .build();

        dnis.stream()
            .map(dniUtils::of)
            .filter(DniValido.class::isInstance)
            .forEach(dni -> System.out.println(((DniValido) dni).formatear(formato)));

    }
    @Test
    void validateDnisMalos() {
        List<String> dnis = List.of("2300000000T", "230.00.000-t", "23.000 a", "00023000$t");
        DniUtils dniUtils = new DniUtilsImpl();
        Assertions.assertEquals(4,
                dnis.stream()
                        .map(dniUtils::of)
                        .filter(DniInvalido.class::isInstance)
                        .count()
        );
    }

    @Test
    void pintarDNIs() {
        List<String> dnis = List.of("230000T", "23.000.000-t", "23.000 T", "00023000t", "2300000000T", "230.00.000-t", "23.000 a", "00023000$t");
        DniUtils dniUtils = new DniUtilsImpl();

        FormatoDeDni formato= FormatoDeDni.builder()
                .letraMayuscula(true)
                .separador('-')
                .cerosDelante(true)
                .puntosDecimales(false)
                .build();

        List<String> formateados = dnis.parallelStream()
                .map(dniUtils::of)
                .map(dni -> switch (dni) {
                                    case DniValido valido     -> valido.formatear(formato);
                                    case DniInvalido invalido -> invalido.error().name();
                                }).toList();
        formateados.forEach(System.out::println);
    }
}


                        /*
                        dni -> {
                            if (dni instanceof DniValido valido) { // pattern expression de instanceof
                                return valido.formatear(formato);
                            } else if (dni instanceof DniInvalido(ErrorEnDni error)) {
                                return error.name();
                            } else {
                                return "Error desconocido";
                            }
                        }
                        */