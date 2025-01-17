package api;

import com.curso.dni.api.Dni;
import com.curso.dni.api.DniUtils;

public class DniUtilsImpl implements DniUtils {

    @Override
    public Dni of(int numero, char letra) {
        return this.of(numero+ String.valueOf(letra));
    }

    @Override
    public Dni of(int numero, String letra) {
        return this.of(numero+ letra);
    }

    @Override
    public Dni of(String dni) {



        return null;
    }
}
