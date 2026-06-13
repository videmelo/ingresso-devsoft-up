package model;

import java.io.Serializable;

public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Inscricao inscricao;
    private double valor;
    private String metodoPagamento;
    private boolean pago;

    public Pagamento(String id, Inscricao inscricao, double valor, String metodoPagamento) {
        this.id = id;
        this.inscricao = inscricao;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
        this.pago = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Inscricao getInscricao() { return inscricao; }
    public void setInscricao(Inscricao inscricao) { this.inscricao = inscricao; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public boolean isPago() { return pago; }
    public void setPago(boolean pago) { this.pago = pago; }

    public boolean processarPagamento() {
        if (this.valor > 0) {
            this.pago = true;
            if (this.inscricao != null) {
                this.inscricao.confirmar();
            }
            return true;
        }
        return false;
    }

    public boolean processarPagamento(String cupomDesconto) {
        if (cupomDesconto != null && cupomDesconto.equals("DESCONTO10")) {
            this.valor = this.valor * 0.9;
        }
        return processarPagamento();
    }

    @Override
    public String toString() {
        return "[PAGAMENTO] ID: " + id + " | Valor: " + valor + " | Pago: " + (pago ? "Sim" : "Não") + (inscricao != null ? " | Inscrição: " + inscricao.getId() : "");
    }
}
