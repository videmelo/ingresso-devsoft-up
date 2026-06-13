import controller.OrganizadorController;
import controller.ParticipanteController;
import controller.EventoPresencialController;
import controller.EventoOnlineController;
import model.EventoPresencial;
import view.EventoOnlineView;
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
        EventoOnlineController eventoOnlineController = new EventoOnlineController();
        EventoPresencialController eventoPresencialController = new EventoPresencialController();

        organizadorController.carregarDadosArquivo();
        participanteController.carregarDadosArquivo();
        eventoOnlineController.carregarDadosArquivo();
        eventoPresencialController.carregarDadosArquivo();


        MenuPrincipal menu = new MenuPrincipal(organizadorController, participanteController, eventoOnlineController, eventoPresencialController);
        menu.iniciar();

        LogUtil.info("Sistema encerrado.");
    }
}
