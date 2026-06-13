package controller;

import exceptions.EntidadeNaoEncontradaException;
import model.EventoOnline;
import util.ArquivoUtil;
import util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class EventoOnlineController {

    private static final String ARQUIVO = "dados/eventos_online.json";
    private List<EventoOnline> listaEventos = new ArrayList<>();

    // ─── CRUD ────────────────────────────────────────────────────────────────

    public void cadastrarEvento(EventoOnline evento) {
        listaEventos.add(evento);
        LogUtil.info("Evento online cadastrado: " + evento.getTitulo() + " (ID: " + evento.getId() + ")");
        System.out.println("Evento online '" + evento.getTitulo() + "' cadastrado com sucesso!");
    }

    public List<EventoOnline> listarEventos() {
        return new ArrayList<>(listaEventos);
    }

    public EventoOnline buscarPorId(String id) throws EntidadeNaoEncontradaException {
        return listaEventos.stream()
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Evento online com ID '" + id + "' não encontrado."));
    }

    public boolean atualizarEvento(String id, EventoOnline dadosAtualizados) {
        try {
            EventoOnline existente = buscarPorId(id);
            existente.setTitulo(dadosAtualizados.getTitulo());
            existente.setData(dadosAtualizados.getData());
            existente.setLinkAcesso(dadosAtualizados.getLinkAcesso());
            existente.setPlataforma(dadosAtualizados.getPlataforma());
            existente.setStatus(dadosAtualizados.getStatus());
            LogUtil.info("Evento online atualizado: ID " + id);
            return true;
        } catch (EntidadeNaoEncontradaException e) {
            LogUtil.warn("Tentativa de atualizar evento online inexistente: ID " + id);
            return false;
        }
    }

    public boolean deletarEvento(String id) {
        boolean removido = listaEventos.removeIf(e -> e.getId().equalsIgnoreCase(id));
        if (removido) {
            LogUtil.info("Evento online removido: ID " + id);
        } else {
            LogUtil.warn("Tentativa de remover evento online inexistente: ID " + id);
        }
        return removido;
    }

    // ─── Ações de negócio ────────────────────────────────────────────────────

    public void iniciarEvento(String id) throws EntidadeNaoEncontradaException {
        EventoOnline evento = buscarPorId(id);
        evento.iniciarEvento(); // já chama enviarLinkAcesso() internamente
        LogUtil.info("Evento online iniciado: " + evento.getTitulo() + " (ID: " + id + ")");
    }

    public void enviarLink(String id) throws EntidadeNaoEncontradaException {
        EventoOnline evento = buscarPorId(id);
        evento.enviarLinkAcesso();
        LogUtil.info("Link reenviado para evento ID: " + id);
    }

    public void gerarRelatorio(String id) throws EntidadeNaoEncontradaException {
        EventoOnline evento = buscarPorId(id);
        evento.gerarRelatorio();
    }

    // ─── Persistência ────────────────────────────────────────────────────────

    public void salvarDadosArquivo() {
        ArquivoUtil.salvarDados(listaEventos, ARQUIVO);
    }

    public void carregarDadosArquivo() {
        List<EventoOnline> carregados = ArquivoUtil.carregarEventosOnline(ARQUIVO);
        listaEventos.addAll(carregados);
        LogUtil.info("Eventos online carregados: " + listaEventos.size() + " registro(s).");
    }
}
