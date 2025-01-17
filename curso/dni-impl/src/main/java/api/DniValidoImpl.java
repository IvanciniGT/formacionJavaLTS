package api;

import com.curso.dni.api.DniValido;
import com.curso.dni.api.FormatoDeDni;
import lombok.Value;

@Value
public class DniValidoImpl implements DniValido {

    private int numero;
    public char letra;

    @Override
    public String formatear(FormatoDeDni formato) {
        return "";
    }
}
