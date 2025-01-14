
def saluda(nombre):
    print("Hola " + nombre)

saluda("Iván")

miVariable = saluda  # Asignamos la función a una variable  
miVariable("Iván")   # Ejecutamos la función a través de la variable

def generar_saludo_formal(nombre):
    return "Buenos días " + nombre

def generar_saludo_informal(nombre):
    return "Hola " + nombre

def imprimir_saludo(funcion_generadora_de_saludos, nombre):
    print(funcion_generadora_de_saludos(nombre))

imprimir_saludo(generar_saludo_formal, "Iván")
#imprimir_saludo(generar_saludo_formal("Iván"))
imprimir_saludo(generar_saludo_informal, "Iván")


def doblar_valor(numero):
    return numero * 2

def sumar_cuatro(numero):
    return numero + 4

def aplicar_operacion(funcion, numero):
    return funcion(numero)
# Esto lo puedo escribir así en JAVA?? Directamente una función en un fichero a PRIMER NIVEL? NO... lo tengo que hacer dentro de una clase
# Para que sirven las clases? Para definir tipos de datos nuevos de los que generar una instancia.