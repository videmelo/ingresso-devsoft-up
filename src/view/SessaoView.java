package view;

import controller.SessaoController;
import controller.EventoPresencialController;
import controller.EventoOnlineController;
import exceptions.EntidadeNaoEncontradaException;
import model.Sessao;
import model.Evento;

import java.util.Scanner;

public class SessaoView {
    private SessaoController controller;
    private EventoPresencialController epController;
    private EventoOnlineController eoController;
    private Scanner scanner;

    public SessaoView(SessaoController controller, EventoPresencialController epController, EventoOnlineController eoController) {
        this.controller = controller;
        this.epController = epController;
        this.eoController = eoController;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR SESSÕES ---");
            System.out.println("1. Cadastrar Sessão");
            System.out.println("2. Listar Sessões");
            System.out.println("3. Reagendar Sessão");
            System.out.println("4. Excluir Sessão");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrar(); break;
                    case 2: listar(); break;
                    case 3: reagendar(); break;
                    case 4:
                        System.out.print("ID da Sessão: ");
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
        System.out.println("\n--- CADASTRAR SESSÃO ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Tema: ");
        String tema = scanner.nextLine();
        System.out.print("Horário de Início: ");
        String horario = scanner.nextLine();
        System.out.print("Palestrante: ");
        String palestrante = scanner.nextLine();

        System.out.println("Vincular a um Evento:");
        System.out.println("1. Evento Online");
        System.out.println("2. Evento Presencial");
        int tipo = Integer.parseInt(scanner.nextLine());
        
        Evento evento = null;
        if (tipo == 1) {
            for (model.EventoOnline eo : eoController.listarEventos()) {
                System.out.println(eo);
            }
            System.out.print("ID do Evento Online: ");
            String idEvento = scanner.nextLine();
            for (model.EventoOnline eo : eoController.listarEventos()) {
                if (eo.getId().equals(idEvento)) { evento = eo; break; }
            }
        } else {
            for (model.EventoPresencial ep : epController.listarEventos()) {
                System.out.println(ep);
            }
            System.out.print("ID do Evento Presencial: ");
            String idEvento = scanner.nextLine();
            for (model.EventoPresencial ep : epController.listarEventos()) {
                if (ep.getId().equals(idEvento)) { evento = ep; break; }
            }
        }

        if (evento != null) {
            Sessao sessao = new Sessao(id, tema, horario, palestrante, evento);
            controller.cadastrarSessao(sessao);
            System.out.println("Sessão cadastrada com sucesso!");
        } else {
            System.out.println("Evento não encontrado. Sessão não cadastrada.");
        }
    }

    public void listar() {
        System.out.println("\n--- LISTA DE SESSÕES ---");
        for (Sessao s : controller.listarSessoes()) {
            System.out.println(s);
        }
    }

    public void reagendar() {
        System.out.print("ID da Sessão: ");
        String id = scanner.nextLine();
        System.out.print("Novo Horário: ");
        String novoHorario = scanner.nextLine();

        for (Sessao s : controller.listarSessoes()) {
            if (s.getId().equals(id)) {
                s.reagendarSessao(novoHorario);
                return;
            }
        }
        System.out.println("Sessão não encontrada.");
    }

    public void deletar(String id) {
        try {
            controller.deletarSessao(id);
            System.out.println("Sessão deletada com sucesso!");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
}
