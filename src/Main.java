import controller.OrganizadorController;
import controller.ParticipanteController;
import view.MenuPrincipal;
import util.LogUtil;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        new File("dados").mkdirs();
        new File("logs").mkdirs();

        LogUtil.info("Sistema iniciado com sucesso.");

        // Inicializando os Controllers e carregarDadosArquivo()
        OrganizadorController organizadorController = new OrganizadorController();
        ParticipanteController participanteController = new ParticipanteController();

        organizadorController.carregarDadosArquivo();
        participanteController.carregarDadosArquivo();

        MenuPrincipal menu = new MenuPrincipal(organizadorController, participanteController);
        menu.iniciar();

        LogUtil.info("Sistema encerrado.");
    }
}
