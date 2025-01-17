package com.curso.dni.api;

public sealed interface Dni permits DniInvalido, DniValido {}
