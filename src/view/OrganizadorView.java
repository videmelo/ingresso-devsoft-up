package view;

import controller.OrganizadorController;
import exceptions.EntidadeNaoEncontradaException;
import model.Organizador;

import java.util.Scanner;

public class OrganizadorView {
    private OrganizadorController controller;
    private Scanner scanner;

    public OrganizadorView(OrganizadorController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrar() {
        System.out.println("\n--- CADASTRAR ORGANIZADOR ---");
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

    public void listar() {
        for (Organizador org : controller.listarOrganizadores()) {
            org.exibirDadosPessoais();
        }
    }

    public void atualizar(String id) {
        System.out.println("\n--- ATUALIZAR ORGANIZADOR (" + id + ") ---");
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
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
