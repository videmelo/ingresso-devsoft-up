package view;

import controller.PagamentoController;
import controller.InscricaoController;
import model.Pagamento;
import model.Inscricao;

import java.util.Scanner;

public class PagamentoView {
    private PagamentoController controller;
    private InscricaoController iController;
    private Scanner scanner;

    public PagamentoView(PagamentoController controller, InscricaoController iController) {
        this.controller = controller;
        this.iController = iController;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- GERENCIAR PAGAMENTOS ---");
            System.out.println("1. Processar Novo Pagamento");
            System.out.println("2. Listar Pagamentos");
            System.out.println("3. Atualizar Pagamento");
            System.out.println("4. Excluir Pagamento");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrar(); break;
                    case 2: listar(); break;
                    case 3:
                        System.out.print("ID do Pagamento: ");
                        atualizar(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("ID do Pagamento: ");
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
        System.out.println("\n--- PROCESSAR PAGAMENTO ---");
        System.out.print("ID do Pagamento: ");
        String id = scanner.nextLine();

        System.out.println("Selecione a Inscrição PENDENTE:");
        for (Inscricao insc : iController.listarInscricoes()) {
            if (insc.getStatus().equals("Pendente")) {
                System.out.println(insc);
            }
        }
        System.out.print("ID da Inscrição: ");
        String idI = scanner.nextLine();
        Inscricao inscricao = null;
        for (Inscricao insc : iController.listarInscricoes()) {
            if (insc.getId().equals(idI) && insc.getStatus().equals("Pendente")) { inscricao = insc; break; }
        }

        if (inscricao != null) {
            double valorCobrado = inscricao.getSessao().getPreco();
            System.out.println("Valor a ser pago: R$ " + valorCobrado);
            System.out.print("Método de Pagamento: ");
            String metodo = scanner.nextLine();
            System.out.print("Cupom de Desconto (Pressione ENTER se não houver): ");
            String cupom = scanner.nextLine();

            Pagamento pag = new Pagamento(id, inscricao, valorCobrado, metodo);
            boolean sucesso;
            if (cupom.isEmpty()) {
                sucesso = pag.processarPagamento();
            } else {
                sucesso = pag.processarPagamento(cupom);
            }

            if (sucesso) {
                controller.cadastrarPagamento(pag);
                System.out.println("Pagamento processado com sucesso! Valor final com descontos: R$ " + pag.getValor());
            } else {
                System.out.println("Erro ao processar pagamento. Verifique o valor.");
            }
        } else {
            System.out.println("Inscrição não encontrada ou não está pendente.");
        }
    }

    public void listar() {
        System.out.println("\n--- LISTA DE PAGAMENTOS ---");
        for (Pagamento pag : controller.listarPagamentos()) {
            System.out.println(pag);
        }
    }

    public void atualizar(String id) {
        System.out.println("\n--- ATUALIZAR PAGAMENTO ---");
        System.out.print("Novo Método de Pagamento: ");
        String metodo = scanner.nextLine();
        System.out.print("Está pago? (S/N): ");
        boolean pago = scanner.nextLine().equalsIgnoreCase("S");

        try {
            for (Pagamento pag : controller.listarPagamentos()) {
                if (pag.getId().equals(id)) {
                    Pagamento pagAtualizado = new Pagamento(id, pag.getInscricao(), pag.getValor(), metodo);
                    pagAtualizado.setPago(pago);
                    if (controller.atualizarPagamento(id, pagAtualizado)) {
                        System.out.println("Pagamento atualizado com sucesso!");
                    } else {
                        System.out.println("Falha ao atualizar pagamento.");
                    }
                    return;
                }
            }
            System.out.println("Pagamento não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void deletar(String id) {
        try {
            if (controller.deletarPagamento(id)) {
                System.out.println("Pagamento deletado com sucesso!");
            } else {
                System.out.println("Pagamento não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
    }
}
