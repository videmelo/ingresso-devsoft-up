package controller;

import model.Certificado;
import util.ArquivoUtil;
import util.LogUtil;
import exceptions.EntidadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class CertificadoController {
    private List<Certificado> listaCertificados = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/certificados.dat";

    public void cadastrarCertificado(Certificado obj) {
        listaCertificados.add(obj);
        LogUtil.info("Novo Certificado cadastrado: ID " + obj.getId());
    }

    public List<Certificado> listarCertificados() {
        return listaCertificados;
    }

    public boolean atualizarCertificado(String id, Certificado objAtualizado) {
        for (int i = 0; i < listaCertificados.size(); i++) {
            if (listaCertificados.get(i).getId().equals(id)) {
                listaCertificados.set(i, objAtualizado);
                LogUtil.info("Certificado atualizado: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Certificado não encontrado para o ID: " + id);
    }

    public boolean deletarCertificado(String id) {
        for (int i = 0; i < listaCertificados.size(); i++) {
            if (listaCertificados.get(i).getId().equals(id)) {
                listaCertificados.remove(i);
                LogUtil.info("Certificado deletado: " + id);
                return true;
            }
        }
        throw new EntidadeNaoEncontradaException("Certificado não encontrado para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaCertificados, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Certificado> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaCertificados = dadosCarregados;
        }
    }
}
