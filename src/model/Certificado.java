package model;

import java.io.Serializable;
import exceptions.PagamentoPendenteException;

public class Certificado implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Participante participante;
    private Evento evento;
    private String dataEmissao;
    private int cargaHoraria;

    public Certificado(String id, Participante participante, Evento evento, String dataEmissao, int cargaHoraria) {
        this.id = id;
        this.participante = participante;
        this.evento = evento;
        this.dataEmissao = dataEmissao;
        this.cargaHoraria = cargaHoraria;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public String getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(String dataEmissao) { this.dataEmissao = dataEmissao; }
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public void emitir() throws PagamentoPendenteException {
        System.out.println("=========================================");
        System.out.println("            CERTIFICADO");
        System.out.println("=========================================");
        System.out.println("Certificamos que " + participante.getNome());
        System.out.println("participou do evento " + evento.getTitulo());
        System.out.println("com carga horária de " + cargaHoraria + " horas.");
        System.out.println("Data de Emissão: " + dataEmissao);
        System.out.println("Código de Autenticidade: " + gerarCodigoAutenticidade());
        System.out.println("=========================================");
    }

    public String gerarCodigoAutenticidade() {
        return evento.getId() + "-" + participante.getId() + "-" + id;
    }

    @Override
    public String toString() {
        return "[CERTIFICADO] ID: " + id + " | Participante: " + participante.getNome() + " | Evento: " + evento.getTitulo();
    }
}
