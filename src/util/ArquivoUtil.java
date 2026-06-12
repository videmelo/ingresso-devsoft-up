package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    public static <T> void salvarDadosDat(List<T> lista, String caminhoArquivo) {
        File file = new File(caminhoArquivo);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
            LogUtil.info("Dados salvos com sucesso em " + caminhoArquivo);
        } catch (IOException e) {
            LogUtil.error("Erro ao salvar arquivo " + caminhoArquivo + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregarDadosDat(String caminhoArquivo) {
        List<T> lista = new ArrayList<>();
        File file = new File(caminhoArquivo);
        if (!file.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<T>) ois.readObject();
            LogUtil.info("Dados carregados com sucesso de " + caminhoArquivo);
        } catch (IOException | ClassNotFoundException e) {
            LogUtil.error("Erro ao carregar arquivo " + caminhoArquivo + ": " + e.getMessage());
        }
        return lista;
    }
}
