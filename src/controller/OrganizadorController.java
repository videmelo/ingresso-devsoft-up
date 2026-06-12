package controller;

import model.Organizador;
import util.ArquivoUtil;
import util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class OrganizadorController {
    private List<Organizador> listaOrganizadores = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/organizadores.dat";

    public void cadastrarOrganizador(Organizador obj) {
        listaOrganizadores.add(obj);
        LogUtil.info("Novo Organizador cadastrado: " + obj.getNome() + " (CPF: " + obj.getCpf() + ")");
    }

    public List<Organizador> listarOrganizadores() {
        return listaOrganizadores;
    }

    public boolean atualizarOrganizador(String id, Organizador objAtualizado) {
        for (int i = 0; i < listaOrganizadores.size(); i++) {
            if (listaOrganizadores.get(i).getId().equals(id)) {
                listaOrganizadores.set(i, objAtualizado);
                LogUtil.info("Organizador atualizado: " + id);
                return true;
            }
        }
        throw new exceptions.EntidadeNaoEncontradaException("Organizador não encontrado para o ID: " + id);
    }

    public boolean deletarOrganizador(String id) {
        for (int i = 0; i < listaOrganizadores.size(); i++) {
            if (listaOrganizadores.get(i).getId().equals(id)) {
                listaOrganizadores.remove(i);
                LogUtil.info("Organizador deletado: " + id);
                return true;
            }
        }
        throw new exceptions.EntidadeNaoEncontradaException("Organizador não encontrado para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaOrganizadores, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Organizador> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaOrganizadores = dadosCarregados;
        }
    }
}
