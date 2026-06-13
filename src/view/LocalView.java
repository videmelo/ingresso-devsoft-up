package view;

import controller.LocalController;
import exceptions.EntidadeNaoEncontradaException;
import model.Local;

import java.util.Scanner;

public class LocalView {
    private LocalController controller;
    private Scanner scanner;

    public LocalView(LocalController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR LOCAIS ---");
            System.out.println("1. Cadastrar Local");
            System.out.println("2. Listar Locais");
            System.out.println("3. Atualizar Local");
            System.out.println("4. Excluir Local");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrar(); break;
                    case 2: listar(); break;
                    case 3: 
                        System.out.print("ID do Local: ");
                        atualizar(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("ID do Local: ");
                        deletar(scanner.nextLine());
                        break;
                    case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    public void cadastrar() {
        System.out.println("\n--- CADASTRAR LOCAL ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Possui Acessibilidade (S/N): ");
        boolean acess = scanner.nextLine().equalsIgnoreCase("S");

        Local local = new Local(id, nome, endereco, acess);
        controller.cadastrarLocal(local);
        System.out.println("Local cadastrado com sucesso!");
    }

    public void listar() {
        System.out.println("\n--- LISTA DE LOCAIS ---");
        for (Local local : controller.listarLocais()) {
            System.out.println(local);
        }
    }

    public void atualizar(String id) {
        System.out.println("\n--- ATUALIZAR LOCAL ---");
        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Possui Acessibilidade (S/N): ");
        boolean acess = scanner.nextLine().equalsIgnoreCase("S");

        Local local = new Local(id, nome, endereco, acess);
        try {
            controller.atualizarLocal(id, local);
            System.out.println("Local atualizado com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletar(String id) {
        try {
            controller.deletarLocal(id);
            System.out.println("Local deletado com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
}
