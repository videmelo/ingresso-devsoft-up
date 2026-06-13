package view;

import controller.ParticipanteController;
import exceptions.EntidadeNaoEncontradaException;
import model.Participante;

import java.util.Scanner;

public class ParticipanteView {
    private ParticipanteController controller;
    private Scanner scanner;

    public ParticipanteView(ParticipanteController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrar() {
        System.out.println("\n--- CADASTRAR PARTICIPANTE ---");
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

    public void listar() {
        for (Participante part : controller.listarParticipantes()) {
            part.exibirDadosPessoais();
        }
    }

    public void atualizar(String id) {
        System.out.println("\n--- ATUALIZAR PARTICIPANTE (" + id + ") ---");
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
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
