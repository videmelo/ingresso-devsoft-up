package model;

import java.io.Serializable;

public class Inscricao implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Participante participante;
    // Utilizando Object ou criando um mock de Sessao. Vamos criar mock.
    private Sessao sessao;
    private String dataInscricao;
    private String status;

    public Inscricao(String id, Participante participante, Sessao sessao, String dataInscricao, String status) {
        this.id = id;
        this.participante = participante;
        this.sessao = sessao;
        this.dataInscricao = dataInscricao;
        this.status = status;
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

    public void confirmar() {
        this.status = "Confirmada";
    }

    public void cancelar() {
        this.status = "Cancelada";
    }
}
