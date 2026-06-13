package model;

public class EventoOnline extends Evento {

    private String linkAcesso;
    private String plataforma;

    public EventoOnline(String id, String titulo, String data, String linkAcesso, String plataforma) {
        super(id, titulo, data, "Agendado");
        this.linkAcesso = linkAcesso;
        this.plataforma = plataforma;
    }

    // Construtor completo para carregar do arquivo (inclui status)
    public EventoOnline(String id, String titulo, String data, String status, String linkAcesso, String plataforma) {
        super(id, titulo, data, status);
        this.linkAcesso = linkAcesso;
        this.plataforma = plataforma;
    }

    public String getLinkAcesso() {
        return linkAcesso;
    }

    public void setLinkAcesso(String linkAcesso) {
        this.linkAcesso = linkAcesso;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    // Polimorfismo de sobrescrita: define o que "iniciar" significa para evento online
    @Override
    public void iniciarEvento() {
        setStatus("Em andamento");
        System.out.println("===========================================");
        System.out.println("  EVENTO ONLINE INICIADO!");
        System.out.println("  Título    : " + getTitulo());
        System.out.println("  Plataforma: " + plataforma);
        System.out.println("  Data      : " + getData());
        System.out.println("===========================================");
        enviarLinkAcesso();
    }

    // Simula o envio do link de acesso a todos os participantes
    public void enviarLinkAcesso() {
        System.out.println("------------------------------------------");
        System.out.println("  Enviando link de acesso...");
        System.out.println("  Plataforma : " + plataforma);
        System.out.println("  Link       : " + linkAcesso);
        System.out.println("  Link enviado com sucesso!");
        System.out.println("------------------------------------------");
    }

    @Override
    public void gerarRelatorio() {
        super.gerarRelatorio();
        System.out.println("  Tipo       : Online");
        System.out.println("  Plataforma : " + plataforma);
        System.out.println("  Link       : " + linkAcesso);
        System.out.println("-------------------------------------------");
    }

    @Override
    public String toString() {
        return "[ONLINE] ID: " + getId() +
               " | Título: " + getTitulo() +
               " | Data: " + getData() +
               " | Status: " + getStatus() +
               " | Plataforma: " + plataforma +
               " | Link: " + linkAcesso;
    }
}
