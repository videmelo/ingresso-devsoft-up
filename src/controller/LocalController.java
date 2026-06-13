package controller;

import model.Local;
import util.ArquivoUtil;
import util.LogUtil;
import exceptions.EntidadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class LocalController {
    private List<Local> listaLocais = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/locais.dat";

    public void cadastrarLocal(Local obj) {
        listaLocais.add(obj);
        LogUtil.info("Novo Local cadastrado: " + obj.getNome() + " (ID: " + obj.getId() + ")");
    }

    public List<Local> listarLocais() {
        return listaLocais;
    }

    public boolean atualizarLocal(String id, Local objAtualizado) {
        for (int i = 0; i < listaLocais.size(); i++) {
            if (listaLocais.get(i).getId().equals(id)) {
                listaLocais.set(i, objAtualizado);
                LogUtil.info("Local atualizado: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Local não encontrado para o ID: " + id);
    }

    public boolean deletarLocal(String id) {
        for (int i = 0; i < listaLocais.size(); i++) {
            if (listaLocais.get(i).getId().equals(id)) {
                listaLocais.remove(i);
                LogUtil.info("Local deletado: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Local não encontrado para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaLocais, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Local> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaLocais = dadosCarregados;
        }
    }
}
