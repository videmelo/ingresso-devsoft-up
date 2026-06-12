package view;

import controller.OrganizadorController;
import exceptions.EntidadeNaoEncontradaException;
import model.Organizador;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizadorView {
    private OrganizadorController controller;
    private Scanner scanner;

    public OrganizadorView(OrganizadorController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- MENU ORGANIZADOR ---");
            System.out.println("1. Cadastrar Organizador");
            System.out.println("2. Listar Organizadores");
            System.out.println("3. Atualizar Organizador");
            System.out.println("4. Excluir Organizador");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpar buffer

                switch (opcao) {
                    case 1:
                        cadastrar();
                        break;
                    case 2:
                        listar();
                        break;
                    case 3:
                        atualizar();
                        break;
                    case 4:
                        excluir();
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
                scanner.nextLine(); // limpar buffer
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Setor: ");
        String setor = scanner.nextLine();
        System.out.print("Nível de Acesso: ");
        String nivelAcesso = scanner.nextLine();

        Organizador org = new Organizador(id, nome, cpf, email, setor, nivelAcesso);
        controller.cadastrarOrganizador(org);
        System.out.println("Organizador cadastrado com sucesso!");
    }

    private void listar() {
        System.out.println("\nLista de Organizadores:");
        for (Organizador org : controller.listarOrganizadores()) {
            org.exibirDadosPessoais();
        }
    }

    private void atualizar() {
        System.out.print("Digite o ID do Organizador a atualizar: ");
        String id = scanner.nextLine();

        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Novo E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Novo Setor: ");
        String setor = scanner.nextLine();
        System.out.print("Novo Nível de Acesso: ");
        String nivelAcesso = scanner.nextLine();

        Organizador org = new Organizador(id, nome, cpf, email, setor, nivelAcesso);
        try {
            controller.atualizarOrganizador(id, org);
            System.out.println("Organizador atualizado com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.print("Digite o ID do Organizador a excluir: ");
        String id = scanner.nextLine();

        try {
            controller.deletarOrganizador(id);
            System.out.println("Organizador excluído com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
