package controller;

import model.Organizador;
import util.ArquivoUtil;
import util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class OrganizadorController {
    private List<Organizador> listaOrganizadores = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/organizadores.txt";

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
        List<String> linhas = new ArrayList<>();
        for (Organizador org : listaOrganizadores) {
            linhas.add(org.getId() + ";" + org.getNome() + ";" + org.getCpf() + ";" + org.getEmail() + ";" + org.getSetor() + ";" + org.getNivelAcesso());
        }
        ArquivoUtil.salvarDadosTxt(linhas, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        listaOrganizadores.clear();
        List<String> linhas = ArquivoUtil.carregarDadosTxt(ARQUIVO_DADOS);
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            if (dados.length == 6) {
                Organizador org = new Organizador(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                listaOrganizadores.add(org);
            }
        }
    }
}
