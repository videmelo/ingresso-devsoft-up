package model;

import interfaces.Checkinavel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Participante extends Pessoa implements Checkinavel {
    private String matricula;
    private List<Inscricao> historicoInscricoes;
    private boolean presente;

    public Participante(String id, String nome, String cpf, String email, String matricula) {
        super(id, nome, cpf, email);
        this.matricula = matricula;
        this.historicoInscricoes = new ArrayList<>();
        this.presente = false;
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

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    @Override
    public void exibirDadosPessoais() {
        System.out.println("[PARTICIPANTE] Nome: " + getNome() + " | Matrícula: " + this.matricula + " | Total Inscrições: " + this.historicoInscricoes.size());
    }

    @Override
    public boolean realizarCheckin() {
        String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        for (Inscricao inscricao : historicoInscricoes) {
            if ("Confirmada".equalsIgnoreCase(inscricao.getStatus()) && inscricao.getSessao() != null && inscricao.getSessao().getEventoVinculado() != null) {
                if (dataAtual.equals(inscricao.getSessao().getEventoVinculado().getData())) {
                    this.presente = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "id='" + getId() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", matricula='" + matricula + '\'' +
                ", presente=" + presente +
                '}';
    }
}
