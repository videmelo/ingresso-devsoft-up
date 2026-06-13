package model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String descricao;

    public Categoria(String id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public void atualizarDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    @Override
    public String toString() {
        return "[CATEGORIA] ID: " + id + " | Nome: " + nome + " | Descrição: " + descricao;
    }
}
