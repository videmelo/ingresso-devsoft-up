package view;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
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
                        // 
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 0:
                        // TODO: Chamar o salvarDadosArquivo() de todos os controllers aqui
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
            }
        } while (opcao != 0);
    }
}
