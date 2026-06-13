package model;

import java.io.Serializable;

public class Local implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String endereco;
    private boolean possuiAcessibilidade;

    public Local(String id, String nome, String endereco, boolean possuiAcessibilidade) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.possuiAcessibilidade = possuiAcessibilidade;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public boolean isPossuiAcessibilidade() { return possuiAcessibilidade; }
    public void setPossuiAcessibilidade(boolean possuiAcessibilidade) { this.possuiAcessibilidade = possuiAcessibilidade; }

    public boolean verificarDisponibilidadeData(String data) {
        return true;
    }

    @Override
    public String toString() {
        return "[LOCAL] ID: " + id + " | Nome: " + nome + " | Endereço: " + endereco + " | Acessibilidade: " + (possuiAcessibilidade ? "Sim" : "Não");
    }
}
