package br.com.manutencao.model;

public class OrdemPeca {

    private OrdemManutencao ordemManutencao;
    private Peca peca;
    private double quantidade;

    public OrdemPeca(OrdemManutencao ordemManutencao, Peca peca, double quantidade) {
        this.ordemManutencao = ordemManutencao;
        this.peca = peca;
        this.quantidade = quantidade;
    }

    public OrdemPeca(double quantidade) {
        this.quantidade = quantidade;
    }

    public OrdemManutencao getOrdemManutencao() {
        return ordemManutencao;
    }

    public void setOrdemManutencao(OrdemManutencao ordemManutencao) {
        this.ordemManutencao = ordemManutencao;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "=========================| Ordem de peça |=========================\n" +
                "Ordem de manutenção: " + this.ordemManutencao.getId() +
                "\nPeça: " + this.peca.getId() +
                "\nQuantidade: " + this.quantidade + "\n";
    }
}
