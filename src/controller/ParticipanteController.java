package controller;

import model.Participante;
import util.ArquivoUtil;
import util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteController {
    private List<Participante> listaParticipantes = new ArrayList<>();
    private final String ARQUIVO_DADOS = "dados/participantes.txt";

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
        List<String> linhas = new ArrayList<>();
        for (Participante part : listaParticipantes) {
            linhas.add(part.getId() + ";" + part.getNome() + ";" + part.getCpf() + ";" + part.getEmail() + ";" + part.getMatricula() + ";" + part.isPresente());
        }
        ArquivoUtil.salvarDadosTxt(linhas, ARQUIVO_DADOS);
    }

    public void carregarDadosArquivo() {
        listaParticipantes.clear();
        List<String> linhas = ArquivoUtil.carregarDadosTxt(ARQUIVO_DADOS);
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            if (dados.length == 6) {
                Participante part = new Participante(dados[0], dados[1], dados[2], dados[3], dados[4]);
                part.setPresente(Boolean.parseBoolean(dados[5]));
                listaParticipantes.add(part);
            }
        }
    }
}
