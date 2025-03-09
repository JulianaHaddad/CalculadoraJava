
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Memoria {
    private enum TipoComando {
        ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA, SINAL;
    }

    private static final Memoria instancia = new Memoria();
    private final List<MemoriaObservador> observadores = new ArrayList<>();

    private TipoComando ultimaOperacao = null;
    private boolean substituir = false;
    private String textoAtual = "";
    private String textoBuffer = "";

    private Memoria() {}

    public static Memoria getInstancia() {
        return instancia;
    }

    public void adicionarObservador(MemoriaObservador observador) {
        observadores.add(observador);
    }

    public String getTextoAtual() {
        return textoAtual.isEmpty() ? "0" : textoAtual;
    }

    public void processarComando(String texto) {
        TipoComando tipoComando = detectarTipoComando(texto);

        if (tipoComando == null) {
            return;
        } else if (tipoComando == TipoComando.ZERAR) {
            textoAtual = "";
            textoBuffer = "";
            ultimaOperacao = null;
            substituir = false;
        } else if (tipoComando == TipoComando.NUMERO || tipoComando == TipoComando.VIRGULA) {
            if (substituir) {
                textoAtual = texto;
                substituir = false;
            } else {
                textoAtual += texto;
            }
        } else if (tipoComando == TipoComando.SINAL && !textoAtual.isEmpty()) {
            textoAtual = textoAtual.startsWith("-") ? textoAtual.substring(1) : "-" + textoAtual;
        } else {
            if (ultimaOperacao != null) {
                textoAtual = obterResultadoOperacao();
                textoBuffer = "";
            }
            substituir = true;
            ultimaOperacao = tipoComando;
            textoBuffer = textoAtual;
        }

        observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
    }

    private TipoComando detectarTipoComando(String texto) {
        if (textoAtual.isEmpty() && texto.equals("0")) {
            return null;
        }

        Map<String, TipoComando> comandos = Map.of(
            "AC", TipoComando.ZERAR,
            "/", TipoComando.DIV,
            "*", TipoComando.MULT,
            "+", TipoComando.SOMA,
            "-", TipoComando.SUB,
            "=", TipoComando.IGUAL,
            "±", TipoComando.SINAL,
            ",", TipoComando.VIRGULA
        );

        if (comandos.containsKey(texto)) {
            if (comandos.get(texto) == TipoComando.VIRGULA && textoAtual.contains(",")) {
                return null;
            }
            return comandos.get(texto);
        }

        return texto.matches("\\d") ? TipoComando.NUMERO : null;
    }

    private String obterResultadoOperacao() {
        double numeroBuffer;
        double numeroAtual;

        try {
            numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
            numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
        } catch (NumberFormatException e) {
            return "Erro";
        }

        double resultado;
        switch (ultimaOperacao) {
            case SOMA:
                resultado = numeroBuffer + numeroAtual;
                break;
            case SUB:
                resultado = numeroBuffer - numeroAtual;
                break;
            case MULT:
                resultado = numeroBuffer * numeroAtual;
                break;
            case DIV:
                if (numeroAtual == 0) {
                    return "Erro";
                }
                resultado = numeroBuffer / numeroAtual;
                break;
            default:
                return textoAtual;
        }

        String resultadoTexto = Double.toString(resultado).replace(".", ",");
        return resultadoTexto.endsWith(",0") ? resultadoTexto.substring(0, resultadoTexto.length() - 2) : resultadoTexto;
    }
}
