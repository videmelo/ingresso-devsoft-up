package controller;

import exceptions.EntidadeNaoEncontradaException;
import exceptions.EventoLotadoException;
import model.EventoPresencial;
import util.ArquivoUtil;
import util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class EventoPresencialController {

    private static final String ARQUIVO = "dados/eventos_presenciais.json";
    private List<EventoPresencial> listaEventos = new ArrayList<>();

    // ─── CRUD ────────────────────────────────────────────────────────────────

    public void cadastrarEvento(EventoPresencial evento) {
        listaEventos.add(evento);
        LogUtil.info("Evento presencial cadastrado: " + evento.getTitulo() + " (ID: " + evento.getId() + ")");
        System.out.println("Evento presencial '" + evento.getTitulo() + "' cadastrado com sucesso!");
    }

    public List<EventoPresencial> listarEventos() {
        return new ArrayList<>(listaEventos);
    }

    public EventoPresencial buscarPorId(String id) throws EntidadeNaoEncontradaException {
        return listaEventos.stream()
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Evento presencial com ID '" + id + "' não encontrado."));
    }

    public boolean atualizarEvento(String id, EventoPresencial dadosAtualizados) {
        try {
            EventoPresencial existente = buscarPorId(id);
            existente.setTitulo(dadosAtualizados.getTitulo());
            existente.setData(dadosAtualizados.getData());
            existente.setLocal(dadosAtualizados.getLocal());
            existente.setCapacidadeMaxima(dadosAtualizados.getCapacidadeMaxima());
            existente.setStatus(dadosAtualizados.getStatus());
            LogUtil.info("Evento presencial atualizado: ID " + id);
            return true;
        } catch (EntidadeNaoEncontradaException e) {
            LogUtil.warn("Tentativa de atualizar evento inexistente: ID " + id);
            return false;
        }
    }

    public boolean deletarEvento(String id) {
        boolean removido = listaEventos.removeIf(e -> e.getId().equalsIgnoreCase(id));
        if (removido) {
            LogUtil.info("Evento presencial removido: ID " + id);
        } else {
            LogUtil.warn("Tentativa de remover evento presencial inexistente: ID " + id);
        }
        return removido;
    }

    // ─── Ações de negócio ────────────────────────────────────────────────────

    public void iniciarEvento(String id) throws EntidadeNaoEncontradaException {
        EventoPresencial evento = buscarPorId(id);
        evento.iniciarEvento();
        LogUtil.info("Evento presencial iniciado: " + evento.getTitulo() + " (ID: " + id + ")");
    }

    public void inscreverParticipante(String idEvento) throws EntidadeNaoEncontradaException, EventoLotadoException {
        EventoPresencial evento = buscarPorId(idEvento);
        evento.incrementarInscritos(); // já lança EventoLotadoException se necessário
        LogUtil.info("Inscrição registrada no evento presencial ID: " + idEvento +
                     " | Vagas restantes: " + evento.getVagasRestantes());
    }

    public void gerarRelatorio(String id) throws EntidadeNaoEncontradaException {
        EventoPresencial evento = buscarPorId(id);
        evento.gerarRelatorio();
    }

    // ─── Persistência ────────────────────────────────────────────────────────

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDados(listaEventos, ARQUIVO);
    }

    public void carregarDadosArquivo() {
        List<EventoPresencial> carregados = ArquivoUtil.carregarEventosPresenciais(ARQUIVO);
        listaEventos.addAll(carregados);
        LogUtil.info("Eventos presenciais carregados: " + listaEventos.size() + " registro(s).");
    }
}
