package view;

import controller.*;
import exceptions.EntidadeNaoEncontradaException;
import model.*;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;

    private EventoPresencialController eventoPresencialController;
    private EventoOnlineController eventoOnlineController;
    private OrganizadorController organizadorController;
    private ParticipanteController participanteController;
    private LocalController localController;
    private SessaoController sessaoController;
    private CategoriaController categoriaController;
    private InscricaoController inscricaoController;
    private PagamentoController pagamentoController;
    private CertificadoController certificadoController;

    private EventoPresencialView eventoPresencialView;
    private EventoOnlineView eventoOnlineView;
    private OrganizadorView organizadorView;
    private ParticipanteView participanteView;
    private LocalView localView;
    private SessaoView sessaoView;
    private CategoriaView categoriaView;
    private InscricaoView inscricaoView;
    private PagamentoView pagamentoView;
    private CertificadoView certificadoView;

    public MenuPrincipal(OrganizadorController organizadorController, ParticipanteController participanteController, 
                         EventoOnlineController eventoOnlineController, EventoPresencialController eventoPresencialController,
                         LocalController localController, SessaoController sessaoController,
                         CategoriaController categoriaController, InscricaoController inscricaoController,
                         PagamentoController pagamentoController, CertificadoController certificadoController) {
        this.scanner = new Scanner(System.in);

        this.eventoPresencialController = eventoPresencialController;
        this.eventoOnlineController = eventoOnlineController;
        this.organizadorController = organizadorController;
        this.participanteController = participanteController;
        this.localController = localController;
        this.sessaoController = sessaoController;
        this.categoriaController = categoriaController;
        this.inscricaoController = inscricaoController;
        this.pagamentoController = pagamentoController;
        this.certificadoController = certificadoController;

        this.eventoPresencialView = new EventoPresencialView(this.eventoPresencialController);
        this.eventoOnlineView = new EventoOnlineView(this.eventoOnlineController);
        this.organizadorView = new OrganizadorView(this.organizadorController);
        this.participanteView = new ParticipanteView(this.participanteController);
        this.localView = new LocalView(this.localController);
        this.sessaoView = new SessaoView(this.sessaoController, this.eventoPresencialController, this.eventoOnlineController);
        this.categoriaView = new CategoriaView(this.categoriaController);
        this.inscricaoView = new InscricaoView(this.inscricaoController, this.participanteController, this.sessaoController);
        this.pagamentoView = new PagamentoView(this.pagamentoController, this.inscricaoController);
        this.certificadoView = new CertificadoView(this.certificadoController, this.participanteController, this.eventoOnlineController, this.eventoPresencialController, this.inscricaoController);
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
                        menuFinanceiro();
                        break;
                    case 4:
                        menuExecucao();
                        break;
                    case 0:
                        System.out.println("Salvando dados...");
                        organizadorController.salvarDadosArquivo();
                        participanteController.salvarDadosArquivo();
                        eventoOnlineController.salvarDadosArquivo();
                        eventoPresencialController.salvarDadosArquivo();
                        localController.salvarDadosArquivo();
                        sessaoController.salvarDadosArquivo();
                        categoriaController.salvarDadosArquivo();
                        inscricaoController.salvarDadosArquivo();
                        pagamentoController.salvarDadosArquivo();
                        certificadoController.salvarDadosArquivo();
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
            System.out.println("\n--- GERENCIAR EVENTOS E ESTRUTURA ---");
            System.out.println("1. Menu de Evento Online");
            System.out.println("2. Menu de Evento Presencial");
            System.out.println("3. Gerenciar Locais");
            System.out.println("4. Gerenciar Categorias");
            System.out.println("5. Gerenciar Sessões");
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
                    case 3:
                        localView.exibirMenu();
                        break;
                    case 4:
                        categoriaView.exibirMenu();
                        break;
                    case 5:
                        sessaoView.exibirMenu();
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

    private void menuFinanceiro() {
        int opcao = -1;
        do {
            System.out.println("\n--- FINANCEIRO E INSCRIÇÕES ---");
            System.out.println("1. Gerenciar Inscrições");
            System.out.println("2. Gerenciar Pagamentos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: inscricaoView.exibirMenu(); break;
                    case 2: pagamentoView.exibirMenu(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void menuExecucao() {
        int opcao = -1;
        do {
            System.out.println("\n--- EXECUÇÃO DO EVENTO ---");
            System.out.println("1. Realizar Check-in");
            System.out.println("2. Gerenciar Certificados");
            System.out.println("3. Gerar Relatórios");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: realizarCheckin(); break;
                    case 2: certificadoView.exibirMenu(); break;
                    case 3: gerarRelatorios(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void realizarCheckin() {
        System.out.print("ID do Participante para Check-in: ");
        String idP = scanner.nextLine();
        for (model.Participante p : participanteController.listarParticipantes()) {
            if (p.getId().equals(idP)) {
                if (p.realizarCheckin()) {
                    System.out.println("Check-in realizado com sucesso!");
                } else {
                    System.out.println("Falha no Check-in. Verifique as inscrições.");
                }
                return;
            }
        }
        System.out.println("Participante não encontrado.");
    }

    private void gerarRelatorios() {
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("Relatórios de Eventos Online:");
        for (model.EventoOnline eo : eventoOnlineController.listarEventos()) {
            eo.gerarRelatorio();
        }
        System.out.println("Relatórios de Eventos Presenciais:");
        for (model.EventoPresencial ep : eventoPresencialController.listarEventos()) {
            ep.gerarRelatorio();
        }
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
