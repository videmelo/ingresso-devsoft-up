import view.MenuPrincipal;
import util.LogUtil;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        new File("dados").mkdirs();
        new File("logs").mkdirs();

        LogUtil.info("Sistema iniciado com sucesso.");

        // TODO: Inicializar os Controllers e carregarDadosArquivo() aqui

        MenuPrincipal menu = new MenuPrincipal();
        menu.iniciar();

        LogUtil.info("Sistema encerrado.");
    }
}
