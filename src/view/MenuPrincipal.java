package view;

import controller.OrganizadorController;
import controller.ParticipanteController;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;
    
    // Controladores
    private OrganizadorController organizadorController;
    private ParticipanteController participanteController;

    // Views
    private OrganizadorView organizadorView;
    private ParticipanteView participanteView;

    public MenuPrincipal(OrganizadorController organizadorController, ParticipanteController participanteController) {
        this.scanner = new Scanner(System.in);
        
        this.organizadorController = organizadorController;
        this.participanteController = participanteController;

        // Inicializando as views e passando os controllers
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
                        System.out.println("Módulo de Eventos em desenvolvimento...");
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
            System.out.println("1. Menu de Organizadores");
            System.out.println("2. Menu de Participantes");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        organizadorView.exibirMenu();
                        break;
                    case 2:
                        participanteView.exibirMenu();
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
}
