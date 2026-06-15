package controller;

import model.Inscricao;
import model.Evento;
import model.Participante;
import util.ArquivoUtil;
import util.LogUtil;
import exceptions.EntidadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

    public Map<Evento, List<Participante>> agruparParticipantesPorEvento() {
        Map<Evento, List<Participante>> mapa = new HashMap<>();
        for (Inscricao insc : listaInscricoes) {
            if (insc.getSessao() != null && insc.getSessao().getEventoVinculado() != null && insc.getParticipante() != null) {
                Evento evento = insc.getSessao().getEventoVinculado();
                Participante participante = insc.getParticipante();
                
                if (!mapa.containsKey(evento)) {
                    mapa.put(evento, new ArrayList<>());
                }
                
                // Evitar duplicidade do mesmo participante no evento
                List<Participante> participantesDoEvento = mapa.get(evento);
                boolean jaExiste = false;
                for(Participante p : participantesDoEvento) {
                    if(p.getId().equals(participante.getId())) {
                        jaExiste = true;
                        break;
                    }
                }
                if(!jaExiste) {
                    participantesDoEvento.add(participante);
                }
            }
        }
        return mapa;
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
