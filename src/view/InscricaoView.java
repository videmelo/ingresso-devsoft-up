package view;

import controller.InscricaoController;
import controller.ParticipanteController;
import controller.SessaoController;
import exceptions.EntidadeNaoEncontradaException;
import model.Inscricao;
import model.Participante;
import model.Sessao;

import java.util.Scanner;

public class InscricaoView {
    private InscricaoController controller;
    private ParticipanteController pController;
    private SessaoController sController;
    private Scanner scanner;

    public InscricaoView(InscricaoController controller, ParticipanteController pController, SessaoController sController) {
        this.controller = controller;
        this.pController = pController;
        this.sController = sController;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR INSCRIÇÕES ---");
            System.out.println("1. Realizar Inscrição");
            System.out.println("2. Listar Inscrições");
            System.out.println("3. Atualizar Inscrição");
            System.out.println("4. Cancelar Inscrição");
            System.out.println("5. Excluir Inscrição");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrar(); break;
                    case 2: listar(); break;
                    case 3:
                        System.out.print("ID da Inscrição: ");
                        atualizar(scanner.nextLine());
                        break;
                    case 4: 
                        System.out.print("ID da Inscrição: ");
                        cancelar(scanner.nextLine());
                        break;
                    case 5:
                        System.out.print("ID da Inscrição: ");
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
        System.out.println("\n--- REALIZAR INSCRIÇÃO ---");
        System.out.print("ID da Inscrição: ");
        String id = scanner.nextLine();
        
        System.out.println("Selecione o Participante:");
        for (Participante p : pController.listarParticipantes()) {
            System.out.println(p);
        }
        System.out.print("ID do Participante: ");
        String idP = scanner.nextLine();
        Participante participante = null;
        for (Participante p : pController.listarParticipantes()) {
            if (p.getId().equals(idP)) { participante = p; break; }
        }

        System.out.println("Selecione a Sessão:");
        for (Sessao s : sController.listarSessoes()) {
            System.out.println(s);
        }
        System.out.print("ID da Sessão: ");
        String idS = scanner.nextLine();
        Sessao sessao = null;
        for (Sessao s : sController.listarSessoes()) {
            if (s.getId().equals(idS)) { sessao = s; break; }
        }

        if (participante != null && sessao != null) {
            if (sessao.getEventoVinculado() instanceof model.EventoPresencial) {
                model.EventoPresencial ep = (model.EventoPresencial) sessao.getEventoVinculado();
                try {
                    ep.incrementarInscritos();
                } catch (exceptions.EventoLotadoException e) {
                    System.out.println("Erro: " + e.getMessage());
                    return;
                }
            }

            System.out.print("Data da Inscrição: ");
            String data = scanner.nextLine();
            Inscricao insc = new Inscricao(id, participante, sessao, data, "Pendente");
            controller.cadastrarInscricao(insc);
            participante.getHistoricoInscricoes().add(insc);
            System.out.println("Inscrição realizada com sucesso! Status: Pendente.");
        } else {
            System.out.println("Participante ou Sessão não encontrados.");
        }
    }

    public void listar() {
        System.out.println("\n--- LISTA DE INSCRIÇÕES ---");
        for (Inscricao insc : controller.listarInscricoes()) {
            System.out.println(insc);
        }
    }

    public void cancelar(String id) {
        try {
            for (Inscricao insc : controller.listarInscricoes()) {
                if (insc.getId().equals(id)) {
                    insc.cancelar();
                    if (insc.getSessao() != null && insc.getSessao().getEventoVinculado() instanceof model.EventoPresencial) {
                        model.EventoPresencial ep = (model.EventoPresencial) insc.getSessao().getEventoVinculado();
                        ep.decrementarInscritos();
                    }
                    System.out.println("Inscrição cancelada com sucesso!");
                    return;
                }
            }
            throw new EntidadeNaoEncontradaException("Inscrição não encontrada.");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizar(String id) {
        System.out.println("\n--- ATUALIZAR INSCRIÇÃO ---");
        System.out.print("Nova Data: ");
        String data = scanner.nextLine();
        System.out.print("Novo Status (Pendente/Confirmada/Cancelada): ");
        String status = scanner.nextLine();

        try {
            for (Inscricao insc : controller.listarInscricoes()) {
                if (insc.getId().equals(id)) {
                    Inscricao inscAtualizada = new Inscricao(id, insc.getParticipante(), insc.getSessao(), data, status);
                    inscAtualizada.setCompareceu(insc.isCompareceu());
                    if (controller.atualizarInscricao(id, inscAtualizada)) {
                        System.out.println("Inscrição atualizada com sucesso!");
                    } else {
                        System.out.println("Inscrição não encontrada no controller.");
                    }
                    return;
                }
            }
            throw new EntidadeNaoEncontradaException("Inscrição não encontrada.");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletar(String id) {
        try {
            if (controller.deletarInscricao(id)) {
                System.out.println("Inscrição deletada com sucesso!");
            } else {
                System.out.println("Inscrição não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
    }
}
