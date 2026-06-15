package model;

import interfaces.Checkinavel;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Inscricao implements Serializable, Checkinavel {
    private static final long serialVersionUID = 1L;
    private String id;
    private Participante participante;
    private Sessao sessao;
    private String dataInscricao;
    private String status;
    private boolean compareceu;

    public Inscricao(String id, Participante participante, Sessao sessao, String dataInscricao, String status) {
        this.id = id;
        this.participante = participante;
        this.sessao = sessao;
        this.dataInscricao = dataInscricao;
        this.status = status;
        this.compareceu = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }
    public Sessao getSessao() { return sessao; }
    public void setSessao(Sessao sessao) { this.sessao = sessao; }
    public String getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(String dataInscricao) { this.dataInscricao = dataInscricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isCompareceu() { return compareceu; }
    public void setCompareceu(boolean compareceu) { this.compareceu = compareceu; }

    public void confirmar() {
        this.status = "Confirmada";
    }

    public void cancelar() {
        this.status = "Cancelada";
    }

    @Override
    public boolean realizarCheckin() {
        String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if ("Confirmada".equalsIgnoreCase(this.status) && this.sessao != null && this.sessao.getEventoVinculado() != null) {
            if (dataAtual.equals(this.sessao.getEventoVinculado().getData())) {
                this.compareceu = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[INSCRIÇÃO] ID: " + id + " | Participante: " + participante.getNome() + " | Sessão: " + sessao.getTema() + " | Status: " + status + " | Compareceu: " + (compareceu ? "Sim" : "Não");
    }
}
