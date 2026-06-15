package model;

import java.util.ArrayList;
import java.util.List;

public class Participante extends Pessoa {
    private String matricula;
    private List<Inscricao> historicoInscricoes;

    public Participante(String id, String nome, String cpf, String email, String matricula) {
        super(id, nome, cpf, email);
        this.matricula = matricula;
        this.historicoInscricoes = new ArrayList<>();
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Inscricao> getHistoricoInscricoes() {
        return historicoInscricoes;
    }

    public void setHistoricoInscricoes(List<Inscricao> historicoInscricoes) {
        this.historicoInscricoes = historicoInscricoes;
    }

    @Override
    public void exibirDadosPessoais() {
        System.out.println("[PARTICIPANTE] Nome: " + getNome() + " | Matrícula: " + this.matricula + " | Total Inscrições: " + this.historicoInscricoes.size());
    }

    @Override
    public String toString() {
        return "Participante{" +
                "id='" + getId() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", matricula='" + matricula + '\'' +
                '}';
    }
}
