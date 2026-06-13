package controller;

import model.Categoria;
import util.ArquivoUtil;
import util.LogUtil;
import exceptions.EntidadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {
    private List<Categoria> listaCategorias = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/categorias.dat";

    public void cadastrarCategoria(Categoria obj) {
        listaCategorias.add(obj);
        LogUtil.info("Nova Categoria cadastrada: " + obj.getNome() + " (ID: " + obj.getId() + ")");
    }

    public List<Categoria> listarCategorias() {
        return listaCategorias;
    }

    public boolean atualizarCategoria(String id, Categoria objAtualizado) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getId().equals(id)) {
                listaCategorias.set(i, objAtualizado);
                LogUtil.info("Categoria atualizada: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Categoria não encontrada para o ID: " + id);
    }

    public boolean deletarCategoria(String id) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getId().equals(id)) {
                listaCategorias.remove(i);
                LogUtil.info("Categoria deletada: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Categoria não encontrada para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaCategorias, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Categoria> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaCategorias = dadosCarregados;
        }
    }
}
