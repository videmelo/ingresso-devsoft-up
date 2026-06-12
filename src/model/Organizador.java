package model;

public class Organizador extends Pessoa {
    private String setor;
    private String nivelAcesso;

    public Organizador(String id, String nome, String cpf, String email, String setor, String nivelAcesso) {
        super(id, nome, cpf, email);
        this.setor = setor;
        this.nivelAcesso = nivelAcesso;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    @Override
    public void exibirDadosPessoais() {
        System.out.println("[ORGANIZADOR] Nome: " + getNome() + " | CPF: " + getCpf() + " | Setor: " + this.setor);
    }

    public boolean aprovarEvento(Evento evento) {
        if (this.nivelAcesso != null && this.nivelAcesso.equalsIgnoreCase("Admin")) {
            evento.setStatus("Aprovado");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Organizador{" +
                "id='" + getId() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", setor='" + setor + '\'' +
                ", nivelAcesso='" + nivelAcesso + '\'' +
                '}';
    }
}
