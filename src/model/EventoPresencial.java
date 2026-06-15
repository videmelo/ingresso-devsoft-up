package model;

import exceptions.EventoLotadoException;

public class EventoPresencial extends Evento {

    private Local local;
    private int capacidadeMaxima;
    private int totalInscritos;

    public EventoPresencial(String id, String titulo, String data, Local local, int capacidadeMaxima) {
        super(id, titulo, data, "Agendado");
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.totalInscritos = 0;
    }

    public EventoPresencial(String id, String titulo, String data, String status, Local local, int capacidadeMaxima, int totalInscritos) {
        super(id, titulo, data, status);
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.totalInscritos = totalInscritos;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getTotalInscritos() {
        return totalInscritos;
    }

    public void setTotalInscritos(int totalInscritos) {
        this.totalInscritos = totalInscritos;
    }

    @Override
    public void iniciarEvento() {
        setStatus("Em andamento");
        System.out.println("===========================================");
        System.out.println("  EVENTO PRESENCIAL INICIADO!");
        System.out.println("  Título : " + getTitulo());
        System.out.println("  Local  : " + (local != null ? local.getNome() : "Não definido"));
        System.out.println("  Data   : " + getData());
        System.out.println("  Abrindo as portas para o público...");
        System.out.println("===========================================");
    }

    public boolean verificarLotacao() throws EventoLotadoException {
        if (totalInscritos >= capacidadeMaxima) {
            throw new EventoLotadoException(
                "Evento '" + getTitulo() + "' está lotado! Capacidade máxima de " + capacidadeMaxima + " atingida."
            );
        }
        return false; // ainda há vagas
    }

    public void incrementarInscritos() throws EventoLotadoException {
        verificarLotacao();
        totalInscritos++;
    }

    public void decrementarInscritos() {
        if (totalInscritos > 0) {
            totalInscritos--;
        }
    }

    public int getVagasRestantes() {
        return capacidadeMaxima - totalInscritos;
    }

    @Override
    public void gerarRelatorio() {
        super.gerarRelatorio();
        System.out.println("  Tipo         : Presencial");
        System.out.println("  Local        : " + (local != null ? local.getNome() : "Não definido"));
        System.out.println("  Capacidade   : " + capacidadeMaxima);
        System.out.println("  Inscritos    : " + totalInscritos);
        System.out.println("  Vagas livres : " + getVagasRestantes());
        System.out.println("-------------------------------------------");
    }

    @Override
    public String toString() {
        return "[PRESENCIAL] ID: " + getId() +
               " | Título: " + getTitulo() +
               " | Data: " + getData() +
               " | Status: " + getStatus() +
               " | Local: " + (local != null ? local.getNome() : "Não definido") +
               " | Vagas: " + getVagasRestantes() + "/" + capacidadeMaxima;
    }
}
