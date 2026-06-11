package model;

import interfaces.RelatorioGeravel;

public abstract class Evento implements RelatorioGeravel {
    private String id;
    private String titulo;
    private String data;
    private String status;

    public Evento(String id, String titulo, String data, String status) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract void iniciarEvento();

    @Override
    public void gerarRelatorio() {
        System.out.println("--- Resumo do Evento ---");
        System.out.println("Título: " + titulo);
        System.out.println("Data: " + data);
        System.out.println("Status atual: " + status);
        System.out.println("------------------------");
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", data='" + data + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
