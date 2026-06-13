package view;

import controller.EventoPresencialController;
import controller.EventoOnlineController;
import controller.OrganizadorController;
import controller.ParticipanteController;
import exceptions.EntidadeNaoEncontradaException;
import model.EventoOnline;
import model.EventoPresencial;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;
    
    // Controladores
    private EventoPresencialController eventoPresencialController;
    private EventoOnlineController eventoOnlineController;
    private OrganizadorController organizadorController;
    private ParticipanteController participanteController;

    // Views
    private EventoPresencialView eventoPresencialView;
    private EventoOnlineView eventoOnlineView;
    private OrganizadorView organizadorView;
    private ParticipanteView participanteView;

    public MenuPrincipal(OrganizadorController organizadorController, ParticipanteController participanteController, EventoOnlineController eventoOnlineController, EventoPresencialController eventoPresencialController) {
        this.scanner = new Scanner(System.in);

        this.eventoPresencialController = eventoPresencialController;
        this.eventoOnlineController = eventoOnlineController;
        this.organizadorController = organizadorController;
        this.participanteController = participanteController;

        // Inicializando as views e passando os controllers
        this.eventoPresencialView = new EventoPresencialView(this.eventoPresencialController);
        this.eventoOnlineView = new EventoOnlineView(this.eventoOnlineController);
        this.organizadorView = new OrganizadorView(this.organizadorController);
        this.participanteView = new ParticipanteView(this.participanteController);
    }

    public void iniciar() {
        int opcao = -1;
        do {
            System.out.println("\n===================================================");
            System.out.println("      SISTEMA DE GESTÃO DE EVENTOS - MENU");
            System.out.println("===================================================");
            System.out.println("1. Gerenciar Pessoas (Organizadores e Participantes)");
            System.out.println("2. Gerenciar Eventos e Estrutura (Locais, Categorias, Sessões)");
            System.out.println("3. Financeiro e Inscrições (Inscrições e Pagamentos)");
            System.out.println("4. Execução do Evento (Check-in, Certificados, Relatórios)");
            System.out.println("0. Salvar e Sair");
            System.out.println("===================================================");
            System.out.print("Escolha uma opção: ");

            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        menuGerenciarPessoas();
                        break;
                    case 2:
                        menuGerenciarEventos();
                        break;
                    case 3:
                        System.out.println("Módulo Financeiro em desenvolvimento...");
                        break;
                    case 4:
                        System.out.println("Módulo de Execução em desenvolvimento...");
                        break;
                    case 0:
                        System.out.println("Salvando dados...");
                        organizadorController.salvarDadosArquivo();
                        participanteController.salvarDadosArquivo();
                        eventoOnlineController.salvarDadosArquivo();
                        eventoPresencialController.salvarDadosArquivo();
                        System.out.println("Sistema encerrado.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
            }
        } while (opcao != 0);
    }

    private void menuGerenciarPessoas() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR PESSOAS ---");
            System.out.println("1. Cadastrar Novo Organizador");
            System.out.println("2. Cadastrar Novo Participante");
            System.out.println("3. Listar Todos os Usuários");
            System.out.println("4. Atualizar Dados de Usuário");
            System.out.println("5. Excluir Usuário");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        organizadorView.cadastrar();
                        break;
                    case 2:
                        participanteView.cadastrar();
                        break;
                    case 3:
                        System.out.println("\n--- LISTA DE USUÁRIOS ---");
                        organizadorView.listar();
                        participanteView.listar();
                        break;
                    case 4:
                        System.out.print("Digite o ID do Usuário a atualizar: ");
                        String idUpdate = scanner.nextLine();
                        if (atualizarSeExistir(idUpdate)) {
                            System.out.println("Processo de atualização finalizado.");
                        } else {
                            System.out.println("Erro: Usuário não encontrado.");
                        }
                        break;
                    case 5:
                        System.out.print("Digite o ID do Usuário a excluir: ");
                        String idDel = scanner.nextLine();
                        if (deletarSeExistir(idDel)) {
                            System.out.println("Usuário excluído com sucesso.");
                        } else {
                            System.out.println("Erro: Usuário não encontrado.");
                        }
                        break;
                    case 0:
                        System.out.println("Retornando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
            }
        } while (opcao != 0);
    }

    private void menuGerenciarEventos() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR EVENTOS ---");
            System.out.println("1. Menu de Evento Online");
            System.out.println("2. Menu de Evento Presencial");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        eventoOnlineView.exibirMenu();
                        break;
                    case 2:
                        eventoPresencialView.exibirMenu();
                        break;
                    case 0:
                        System.out.println("Retornando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
            }
        } while (opcao != 0);
    }

    private boolean atualizarSeExistir(String id) {
        for (model.Organizador o : organizadorController.listarOrganizadores()) {
            if (o.getId().equals(id)) {
                organizadorView.atualizar(id);
                return true;
            }
        }
        for (model.Participante p : participanteController.listarParticipantes()) {
            if (p.getId().equals(id)) {
                participanteView.atualizar(id);
                return true;
            }
        }
        return false;
    }

    private boolean deletarSeExistir(String id) {
        try {
            organizadorController.deletarOrganizador(id);
            return true;
        } catch (EntidadeNaoEncontradaException e1) {
            try {
                participanteController.deletarParticipante(id);
                return true;
            } catch (EntidadeNaoEncontradaException e2) {
                return false;
            }
        }
    }
}
