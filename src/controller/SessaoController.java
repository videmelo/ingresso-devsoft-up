package controller;

import model.Sessao;
import util.ArquivoUtil;
import util.LogUtil;
import exceptions.EntidadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class SessaoController {
    private List<Sessao> listaSessoes = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/sessoes.dat";

    public void cadastrarSessao(Sessao obj) {
        listaSessoes.add(obj);
        LogUtil.info("Nova Sessão cadastrada: " + obj.getTema() + " (ID: " + obj.getId() + ")");
    }

    public List<Sessao> listarSessoes() {
        return listaSessoes;
    }

    public boolean atualizarSessao(String id, Sessao objAtualizado) {
        for (int i = 0; i < listaSessoes.size(); i++) {
            if (listaSessoes.get(i).getId().equals(id)) {
                listaSessoes.set(i, objAtualizado);
                LogUtil.info("Sessão atualizada: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Sessão não encontrada para o ID: " + id);
    }

    public boolean deletarSessao(String id) {
        for (int i = 0; i < listaSessoes.size(); i++) {
            if (listaSessoes.get(i).getId().equals(id)) {
                listaSessoes.remove(i);
                LogUtil.info("Sessão deletada: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Sessão não encontrada para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaSessoes, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Sessao> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaSessoes = dadosCarregados;
        }
    }
}
