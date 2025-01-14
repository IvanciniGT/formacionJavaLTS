
public interface Operaciones {

    public static double doblar(double numero) {
        return numero * 2;
    }

    public static double sumar(double numero1, double numero2) {
        return numero1 + numero2;
    }

    public static double restar(double numero1, double numero2) {
        return numero1 - auxiliar(numero2);
    }

    public static double multiplicar(double numero1, double numero2) { // Java 8
        return numero1 * auxiliar(numero2);
    }

    private static double auxiliar(double numero) { // Java 9
        return Math.abs(numero) + 5;
    }

}