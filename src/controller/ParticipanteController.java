package controller;

import model.Participante;
import util.ArquivoUtil;
import util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteController {
    private List<Participante> listaParticipantes = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/participantes.dat";

    public void cadastrarParticipante(Participante obj) {
        listaParticipantes.add(obj);
        LogUtil.info("Novo Participante cadastrado: " + obj.getNome() + " (Matricula: " + obj.getMatricula() + ")");
    }

    public List<Participante> listarParticipantes() {
        return listaParticipantes;
    }

    public boolean atualizarParticipante(String id, Participante objAtualizado) {
        for (int i = 0; i < listaParticipantes.size(); i++) {
            if (listaParticipantes.get(i).getId().equals(id)) {
                listaParticipantes.set(i, objAtualizado);
                LogUtil.info("Participante atualizado: " + id);
                return true;
            }
        }
        throw new exceptions.EntidadeNaoEncontradaException("Participante não encontrado para o ID: " + id);
    }

    public boolean deletarParticipante(String id) {
        for (int i = 0; i < listaParticipantes.size(); i++) {
            if (listaParticipantes.get(i).getId().equals(id)) {
                listaParticipantes.remove(i);
                LogUtil.info("Participante deletado: " + id);
                return true;
            }
        }
        throw new exceptions.EntidadeNaoEncontradaException("Participante não encontrado para o ID: " + id);
    }

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDadosDat(listaParticipantes, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        List<Participante> dadosCarregados = ArquivoUtil.carregarDadosDat(ARQUIVO_DADOS);
        if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
            this.listaParticipantes = dadosCarregados;
        }
    }
}
