package com.curso.fechas;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        // En Java 1.8 se añade la librería JODA-TIME bajo el api de java en java.time
        // Clases importantes

        LocalDate hoy = LocalDate.now();
        System.out.println("Ahora: " + hoy);

        LocalDate descubrimientoAmerica = LocalDate.of(1492, 10, 12);
        System.out.println("Descubrimiento de América: " + descubrimientoAmerica);

        // Podemos hacer operaciones fácil sobre estos datos.
        var ayer = hoy.minusDays(1);
        var hoyDeHaceUnMes = hoy.minusMonths(1);
        var hoyDeHaceUnAno = hoy.minusYears(1); // Los mismos también con plus

        // Restar dos fechas nos da un Period
        Period periodo = descubrimientoAmerica.until(hoy);

        // Sumar ese pediodo a hoy
        var fechaFutura = hoy.plus(periodo);
        // Definir el periodo de tiempo, 2 meses:
        Period periodo2Meses = Period.ofMonths(2); // Años, meses, semanas y dias
        Period.of(2, 0, 0); // Otra forma de hacerlo

        // Formatear
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Fecha formateada: " + hoy.format(formateador));

        // Compatibilidad con lo antiguo: java.util.Date.
        // Se hace mediante un tipo adicional: Instant
        LocalDateTime ahora = LocalDateTime.now(); // Añade a la fecha, hora
        System.out.println("Ahora: " + ahora);
        LocalDateTime horaYFechaDelDescubrimiento = LocalDateTime.of(1492, 10, 12, 0, 0);
        LocalDateTime comienzoDeHoy = hoy.atStartOfDay();

        // ZoneOffset.UTC
        // Convertir comienzo de hoy a instante en zona del sistema

        Instant instante = comienzoDeHoy.toInstant((ZoneId.systemDefault().getRules().getOffset(comienzoDeHoy)));
        Date fecha = Date.from(instante);

        LocalDate fechaDesdeDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime fechaYHoraDesdeDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Otra que usamos mucho:

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZonedDateTime.of(2021, 10, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        ZoneId.systemDefault();
        // La fecha de ahora en zona madrid
        ZoneId.of("Europe/Madrid");
        var holaEnMadrid= ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
        var horaEnLosAngeles = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));

        System.out.println("Hora en Madrid: " + holaEnMadrid);
        System.out.println("Hora en Los Angeles: " + horaEnLosAngeles);

        Duration dosHoras = Duration.ofHours(2); // horas, minutos, segundos, milisegundos, nanosegundos
        Duration.of(2, ChronoUnit.HOURS); // Otra forma de hacerlo
        //La fecha de madrid en 2 horas
        var enDosHoras = holaEnMadrid.plus(dosHoras);

        ZonedDateTime horaActual = new Date().toInstant().atZone(ZoneId.systemDefault());
        Date fechaOtra=Date.from(horaActual.toInstant());

    }

    // Fecha: LocalDate
    // Hora: LocalTime
    // Fecha/Hora: LocalDateTime / ZonedDateTime

    // Duración: Duration (horas hacia abajo )
    // Periodo: Period (años, meses, días)

    // Podeis hacer plus / minus de Periods y Durations a fechas y horas

    // Formatear: DateTimeFormatter

    // Para convertir a Date y viceversa, siempre pasando por Instant




}