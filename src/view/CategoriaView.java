package view;

import controller.CategoriaController;
import exceptions.EntidadeNaoEncontradaException;
import model.Categoria;

import java.util.Scanner;

public class CategoriaView {
    private CategoriaController controller;
    private Scanner scanner;

    public CategoriaView(CategoriaController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR CATEGORIAS ---");
            System.out.println("1. Cadastrar Categoria");
            System.out.println("2. Listar Categorias");
            System.out.println("3. Atualizar Descrição");
            System.out.println("4. Excluir Categoria");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrar(); break;
                    case 2: listar(); break;
                    case 3: atualizar(); break;
                    case 4:
                        System.out.print("ID da Categoria: ");
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
        System.out.println("\n--- CADASTRAR CATEGORIA ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String desc = scanner.nextLine();

        Categoria cat = new Categoria(id, nome, desc);
        controller.cadastrarCategoria(cat);
        System.out.println("Categoria cadastrada com sucesso!");
    }

    public void listar() {
        System.out.println("\n--- LISTA DE CATEGORIAS ---");
        for (Categoria cat : controller.listarCategorias()) {
            System.out.println(cat);
        }
    }

    public void atualizar() {
        System.out.print("ID da Categoria: ");
        String id = scanner.nextLine();
        System.out.print("Nova Descrição: ");
        String desc = scanner.nextLine();

        try {
            for (Categoria cat : controller.listarCategorias()) {
                if (cat.getId().equals(id)) {
                    cat.atualizarDescricao(desc);
                    System.out.println("Descrição atualizada com sucesso!");
                    return;
                }
            }
            throw new EntidadeNaoEncontradaException("Categoria não encontrada.");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletar(String id) {
        try {
            controller.deletarCategoria(id);
            System.out.println("Categoria deletada com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
}
