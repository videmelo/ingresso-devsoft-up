package view;

import controller.EventoOnlineController;
import controller.OrganizadorController;
import exceptions.EntidadeNaoEncontradaException;
import model.EventoOnline;
import model.Organizador;

import java.util.List;
import java.util.Scanner;

public class EventoOnlineView {

    private final EventoOnlineController controller;
    private final OrganizadorController organizadorController;
    private final Scanner scanner;

    public EventoOnlineView(EventoOnlineController controller, OrganizadorController organizadorController) {
        this.controller = controller;
        this.organizadorController = organizadorController;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- Gerenciar Eventos Online ---");
            System.out.println("1. Cadastrar evento online");
            System.out.println("2. Listar todos os eventos online");
            System.out.println("3. Atualizar evento online");
            System.out.println("4. Excluir evento online");
            System.out.println("5. Iniciar evento online (envia link)");
            System.out.println("6. Reenviar link de acesso");
            System.out.println("7. Gerar relatório de evento");
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
                    case 6 -> reenviarLink();
                    case 7 -> relatorio();
                    case 0 -> System.out.println("Voltando...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um número válido.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n-- Cadastro de Evento Online --");
        System.out.print("Digite o seu ID de Organizador: ");
        String idOrg = scanner.nextLine().trim();
        
        Organizador org = null;
        for (Organizador o : organizadorController.listarOrganizadores()) {
            if (o.getId().equals(idOrg)) {
                org = o;
                break;
            }
        }
        
        if (org == null) {
            System.out.println("Erro: Organizador não encontrado. Apenas organizadores podem criar eventos.");
            return;
        }

        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Data (dd/MM/yyyy): ");
        String data = scanner.nextLine().trim();
        System.out.print("Plataforma (ex: Zoom, Teams, YouTube): ");
        String plataforma = scanner.nextLine().trim();
        System.out.print("Link de acesso: ");
        String link = scanner.nextLine().trim();

        EventoOnline evento = new EventoOnline(id, titulo, data, link, plataforma);
        
        if (org.aprovarEvento(evento)) {
            System.out.println("Evento aprovado automaticamente pelo Administrador.");
        } else {
            System.out.println("Evento criado com status Agendado. Aguardando aprovação de um Administrador para ir ao ar.");
        }
        
        controller.cadastrarEvento(evento);
    }

    private void listar() {
        List<EventoOnline> eventos = controller.listarEventos();
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento online cadastrado.");
            return;
        }
        System.out.println("\n-- Eventos Online --");
        eventos.forEach(System.out::println);
    }

    private void atualizar() {
        System.out.print("\nID do evento a atualizar: ");
        String id = scanner.nextLine().trim();
        System.out.print("Novo título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Nova data (dd/MM/yyyy): ");
        String data = scanner.nextLine().trim();
        System.out.print("Nova plataforma: ");
        String plataforma = scanner.nextLine().trim();
        System.out.print("Novo link de acesso: ");
        String link = scanner.nextLine().trim();
        System.out.print("Novo status (Agendado/Em andamento/Finalizado): ");
        String status = scanner.nextLine().trim();

        EventoOnline atualizado = new EventoOnline(id, titulo, data, status, link, plataforma);
        boolean ok = controller.atualizarEvento(id, atualizado);
        System.out.println(ok ? "Evento atualizado com sucesso!" : "Evento não encontrado.");
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

    private void reenviarLink() {
        System.out.print("\nID do evento: ");
        String id = scanner.nextLine().trim();
        try {
            controller.enviarLink(id);
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
