package controller;

import model.Pagamento;
import util.ArquivoUtil;
import util.LogUtil;
import exceptions.EntidadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class PagamentoController {
    private List<Pagamento> listaPagamentos = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/pagamentos.dat";

    public void cadastrarPagamento(Pagamento obj) {
        listaPagamentos.add(obj);
        LogUtil.info("Novo Pagamento cadastrado: ID " + obj.getId());
    }

    public List<Pagamento> listarPagamentos() {
        return listaPagamentos;
    }

    public boolean atualizarPagamento(String id, Pagamento objAtualizado) {
        for (int i = 0; i < listaPagamentos.size(); i++) {
            if (listaPagamentos.get(i).getId().equals(id)) {
                listaPagamentos.set(i, objAtualizado);
                LogUtil.info("Pagamento atualizado: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Pagamento não encontrado para o ID: " + id);
    }

    public boolean deletarPagamento(String id) {
        for (int i = 0; i < listaPagamentos.size(); i++) {
            if (listaPagamentos.get(i).getId().equals(id)) {
                listaPagamentos.remove(i);
                LogUtil.info("Pagamento deletado: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Pagamento não encontrado para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaPagamentos, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Pagamento> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaPagamentos = dadosCarregados;
        }
    }
}
