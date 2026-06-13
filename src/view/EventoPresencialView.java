package view;

import controller.EventoPresencialController;
import exceptions.EntidadeNaoEncontradaException;
import exceptions.EventoLotadoException;
import model.EventoPresencial;

import java.util.List;
import java.util.Scanner;

public class EventoPresencialView {

    private final EventoPresencialController controller;
    private final Scanner scanner;

    public EventoPresencialView(EventoPresencialController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- Gerenciar Eventos Presenciais ---");
            System.out.println("1. Cadastrar evento presencial");
            System.out.println("2. Listar todos os eventos presenciais");
            System.out.println("3. Atualizar evento presencial");
            System.out.println("4. Excluir evento presencial");
            System.out.println("5. Iniciar evento presencial");
            System.out.println("6. Gerar relatório de evento");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> listar();
                    case 3 -> atualizar();
                    case 4 -> deletar();
                    case 5 -> iniciar();
                    case 6 -> relatorio();
                    case 0 -> System.out.println("Voltando...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um número válido.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n-- Cadastro de Evento Presencial --");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Data (dd/MM/yyyy): ");
        String data = scanner.nextLine().trim();
        System.out.print("Local: ");
        String local = scanner.nextLine().trim();
        System.out.print("Capacidade máxima: ");

        try {
            int capacidade = Integer.parseInt(scanner.nextLine().trim());
            EventoPresencial evento = new EventoPresencial(id, titulo, data, local, capacidade);
            controller.cadastrarEvento(evento);
        } catch (NumberFormatException e) {
            System.out.println("Erro: capacidade inválida.");
        }
    }

    private void listar() {
        List<EventoPresencial> eventos = controller.listarEventos();
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento presencial cadastrado.");
            return;
        }
        System.out.println("\n-- Eventos Presenciais --");
        eventos.forEach(System.out::println);
    }

    private void atualizar() {
        System.out.print("\nID do evento a atualizar: ");
        String id = scanner.nextLine().trim();
        System.out.print("Novo título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Nova data (dd/MM/yyyy): ");
        String data = scanner.nextLine().trim();
        System.out.print("Novo local: ");
        String local = scanner.nextLine().trim();
        System.out.print("Nova capacidade máxima: ");

        try {
            int capacidade = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Novo status (Agendado/Em andamento/Finalizado): ");
            String status = scanner.nextLine().trim();

            EventoPresencial atualizado = new EventoPresencial(id, titulo, data, status, local, capacidade, 0);
            boolean ok = controller.atualizarEvento(id, atualizado);
            System.out.println(ok ? "Evento atualizado com sucesso!" : "Evento não encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: capacidade inválida.");
        }
    }

    private void deletar() {
        System.out.print("\nID do evento a excluir: ");
        String id = scanner.nextLine().trim();
        boolean ok = controller.deletarEvento(id);
        System.out.println(ok ? "Evento excluído com sucesso!" : "Evento não encontrado.");
    }

    private void iniciar() {
        System.out.print("\nID do evento a iniciar: ");
        String id = scanner.nextLine().trim();
        try {
            controller.iniciarEvento(id);
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void relatorio() {
        System.out.print("\nID do evento: ");
        String id = scanner.nextLine().trim();
        try {
            controller.gerarRelatorio(id);
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
