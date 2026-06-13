import controller.*;
import view.MenuPrincipal;
import util.LogUtil;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        new File("dados").mkdirs();
        new File("logs").mkdirs();

        LogUtil.info("Sistema iniciado com sucesso.");
        
        OrganizadorController organizadorController = new OrganizadorController();
        ParticipanteController participanteController = new ParticipanteController();
        EventoOnlineController eventoOnlineController = new EventoOnlineController();
        EventoPresencialController eventoPresencialController = new EventoPresencialController();
        LocalController localController = new LocalController();
        SessaoController sessaoController = new SessaoController();
        CategoriaController categoriaController = new CategoriaController();
        InscricaoController inscricaoController = new InscricaoController();
        PagamentoController pagamentoController = new PagamentoController();
        CertificadoController certificadoController = new CertificadoController();

        organizadorController.carregarDadosArquivo();
        participanteController.carregarDadosArquivo();
        eventoOnlineController.carregarDadosArquivo();
        eventoPresencialController.carregarDadosArquivo();
        localController.carregarDadosArquivo();
        sessaoController.carregarDadosArquivo();
        categoriaController.carregarDadosArquivo();
        inscricaoController.carregarDadosArquivo();
        pagamentoController.carregarDadosArquivo();
        certificadoController.carregarDadosArquivo();

        MenuPrincipal menu = new MenuPrincipal(
            organizadorController, participanteController, 
            eventoOnlineController, eventoPresencialController,
            localController, sessaoController,
            categoriaController, inscricaoController,
            pagamentoController, certificadoController
        );
        menu.iniciar();

        LogUtil.info("Sistema encerrado.");
    }
}
