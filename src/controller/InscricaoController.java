package controller;

import model.Inscricao;
import util.ArquivoUtil;
import util.LogUtil;
import exceptions.EntidadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class InscricaoController {
    private List<Inscricao> listaInscricoes = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/inscricoes.dat";

    public void cadastrarInscricao(Inscricao obj) {
        listaInscricoes.add(obj);
        LogUtil.info("Nova Inscrição cadastrada: ID " + obj.getId());
    }

    public List<Inscricao> listarInscricoes() {
        return listaInscricoes;
    }

    public boolean atualizarInscricao(String id, Inscricao objAtualizado) {
        for (int i = 0; i < listaInscricoes.size(); i++) {
            if (listaInscricoes.get(i).getId().equals(id)) {
                listaInscricoes.set(i, objAtualizado);
                LogUtil.info("Inscrição atualizada: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Inscrição não encontrada para o ID: " + id);
    }

    public boolean deletarInscricao(String id) {
        for (int i = 0; i < listaInscricoes.size(); i++) {
            if (listaInscricoes.get(i).getId().equals(id)) {
                listaInscricoes.remove(i);
                LogUtil.info("Inscrição deletada: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Inscrição não encontrada para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaInscricoes, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Inscricao> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaInscricoes = dadosCarregados;
        }
    }
}
