package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    public static <T> void salvarDados(List<T> lista, String caminhoArquivo) {
        LogUtil.info("Iniciando rotina de salvamento provisória para: " + caminhoArquivo);
    }

    public static <T> List<T> carregarDados(String caminhoArquivo) {
        LogUtil.info("Iniciando rotina de leitura provisória para: " + caminhoArquivo);
        return new ArrayList<>();
    }

    public static void salvarDadosTxt(List<String> linhas, String caminhoArquivo) {
        File file = new File(caminhoArquivo);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
            LogUtil.info("Dados salvos em " + caminhoArquivo);
        } catch (IOException e) {
            LogUtil.error("Erro ao salvar arquivo " + caminhoArquivo + ": " + e.getMessage());
        }
    }

    public static List<String> carregarDadosTxt(String caminhoArquivo) {
        List<String> linhas = new ArrayList<>();
        File file = new File(caminhoArquivo);
        if (!file.exists()) return linhas;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
            LogUtil.info("Dados carregados de " + caminhoArquivo);
        } catch (IOException e) {
            LogUtil.error("Erro ao carregar arquivo " + caminhoArquivo + ": " + e.getMessage());
        }
        return linhas;
    }
}
