package view;

import controller.ParticipanteController;
import exceptions.EntidadeNaoEncontradaException;
import model.Participante;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ParticipanteView {
    private ParticipanteController controller;
    private Scanner scanner;

    public ParticipanteView(ParticipanteController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- MENU PARTICIPANTE ---");
            System.out.println("1. Cadastrar Participante");
            System.out.println("2. Listar Participantes");
            System.out.println("3. Atualizar Participante");
            System.out.println("4. Excluir Participante");
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
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        Participante part = new Participante(id, nome, cpf, email, matricula);
        controller.cadastrarParticipante(part);
        System.out.println("Participante cadastrado com sucesso!");
    }

    private void listar() {
        System.out.println("\nLista de Participantes:");
        for (Participante part : controller.listarParticipantes()) {
            part.exibirDadosPessoais();
        }
    }

    private void atualizar() {
        System.out.print("Digite o ID do Participante a atualizar: ");
        String id = scanner.nextLine();

        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Novo E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Nova Matrícula: ");
        String matricula = scanner.nextLine();

        Participante part = new Participante(id, nome, cpf, email, matricula);
        try {
            controller.atualizarParticipante(id, part);
            System.out.println("Participante atualizado com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.print("Digite o ID do Participante a excluir: ");
        String id = scanner.nextLine();

        try {
            controller.deletarParticipante(id);
            System.out.println("Participante excluído com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
