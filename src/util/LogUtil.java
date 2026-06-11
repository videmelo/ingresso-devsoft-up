package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {
    private static final String CAMINHO_LOG = "logs/log.txt";

    public static void registrarLog(String nivel, String mensagem) {
        try (PrintWriter out = new PrintWriter(new FileWriter(CAMINHO_LOG, true))) {
            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            out.println("[" + dataHora + "] - [" + nivel.toUpperCase() + "] - " + mensagem);
        } catch (IOException e) {
            System.err.println("Erro crítico ao gravar log: " + e.getMessage());
        }
    }

    public static void info(String mensagem) {
        registrarLog("INFO", mensagem);
    }

    public static void warn(String mensagem) {
        registrarLog("WARN", mensagem);
    }

    public static void error(String mensagem) {
        registrarLog("ERROR", mensagem);
    }
}
