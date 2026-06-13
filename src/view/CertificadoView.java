package view;

import controller.*;
import exceptions.EntidadeNaoEncontradaException;
import model.Certificado;
import model.Participante;
import model.Evento;
import model.Inscricao;

import java.util.Scanner;

public class CertificadoView {
    private CertificadoController controller;
    private ParticipanteController pController;
    private EventoOnlineController eoController;
    private EventoPresencialController epController;
    private InscricaoController iController;
    private Scanner scanner;

    public CertificadoView(CertificadoController controller, ParticipanteController pController, 
                          EventoOnlineController eoController, EventoPresencialController epController,
                          InscricaoController iController) {
        this.controller = controller;
        this.pController = pController;
        this.eoController = eoController;
        this.epController = epController;
        this.iController = iController;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR CERTIFICADOS ---");
            System.out.println("1. Emitir Novo Certificado");
            System.out.println("2. Listar Certificados");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: emitir(); break;
                    case 2: listar(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    public void emitir() {
        System.out.println("\n--- EMITIR CERTIFICADO ---");
        System.out.print("ID do Certificado: ");
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

        System.out.println("Selecione o Evento:");
        System.out.println("1. Evento Online");
        System.out.println("2. Evento Presencial");
        int tipo = Integer.parseInt(scanner.nextLine());
        Evento evento = null;
        if (tipo == 1) {
            for (model.EventoOnline eo : eoController.listarEventos()) { System.out.println(eo); }
            System.out.print("ID do Evento Online: ");
            String idEv = scanner.nextLine();
            for (model.EventoOnline eo : eoController.listarEventos()) { if (eo.getId().equals(idEv)) { evento = eo; break; } }
        } else {
            for (model.EventoPresencial ep : epController.listarEventos()) { System.out.println(ep); }
            System.out.print("ID do Evento Presencial: ");
            String idEv = scanner.nextLine();
            for (model.EventoPresencial ep : epController.listarEventos()) { if (ep.getId().equals(idEv)) { evento = ep; break; } }
        }

        if (participante != null && evento != null) {
            // Validação de Pagamento
            boolean pago = false;
            for (Inscricao insc : iController.listarInscricoes()) {
                if (insc.getParticipante().getId().equals(participante.getId()) && 
                    insc.getSessao().getEventoVinculado().getId().equals(evento.getId())) {
                    if (insc.getStatus().equals("Confirmada")) {
                        pago = true;
                        break;
                    }
                }
            }

            if (!pago) {
                System.out.println("Erro: Pagamento pendente para este evento.");
                return;
            }

            System.out.print("Data de Emissão: ");
            String data = scanner.nextLine();
            System.out.print("Carga Horária: ");
            int carga = Integer.parseInt(scanner.nextLine());

            Certificado cert = new Certificado(id, participante, evento, data, carga);
            try {
                cert.emitir();
                controller.cadastrarCertificado(cert);
                System.out.println("Certificado registrado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao emitir certificado: " + e.getMessage());
            }
        } else {
            System.out.println("Participante ou Evento não encontrados.");
        }
    }

    public void listar() {
        System.out.println("\n--- LISTA DE CERTIFICADOS ---");
        for (Certificado cert : controller.listarCertificados()) {
            System.out.println(cert);
        }
    }
}
