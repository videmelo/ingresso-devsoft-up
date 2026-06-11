package util;

import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    public static <T> void salvarDados(List<T> lista, String caminhoArquivo) {
        // TODO: Implementar lógica real de gravação em TXT ou JSON
        LogUtil.info("Iniciando rotina de salvamento para: " + caminhoArquivo);
    }

    public static <T> List<T> carregarDados(String caminhoArquivo) {
        // TODO: Implementar lógica real de leitura de TXT ou JSON
        LogUtil.info("Iniciando rotina de leitura para: " + caminhoArquivo);
        return new ArrayList<>();
    }
}
