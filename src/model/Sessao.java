package model;

import java.io.Serializable;

public class Sessao implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String tema;
    private String horarioInicio;
    private String palestrante;
    private Evento eventoVinculado;

    public Sessao(String id, String tema, String horarioInicio, String palestrante, Evento eventoVinculado) {
        this.id = id;
        this.tema = tema;
        this.horarioInicio = horarioInicio;
        this.palestrante = palestrante;
        this.eventoVinculado = eventoVinculado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(String horarioInicio) { this.horarioInicio = horarioInicio; }
    public String getPalestrante() { return palestrante; }
    public void setPalestrante(String palestrante) { this.palestrante = palestrante; }
    public Evento getEventoVinculado() { return eventoVinculado; }
    public void setEventoVinculado(Evento eventoVinculado) { this.eventoVinculado = eventoVinculado; }

    public void reagendarSessao(String novoHorario) {
        this.horarioInicio = novoHorario;
        System.out.println("Sessão " + this.id + " reagendada para " + novoHorario);
    }

    @Override
    public String toString() {
        return "[SESSÃO] ID: " + id + " | Tema: " + tema + " | Horário: " + horarioInicio + " | Palestrante: " + palestrante + (eventoVinculado != null ? " | Evento: " + eventoVinculado.getTitulo() : "");
    }
}
