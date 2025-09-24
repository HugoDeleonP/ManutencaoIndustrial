package br.com.manutencao.model;

public class Peca {
    private Integer id;
    private String nome;
    private double estoque;

    public Peca(Integer id, String nome, double estoque) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
    }

    public Peca(String nome, double estoque) {
        this.nome = nome;
        this.estoque = estoque;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getEstoque() {
        return estoque;
    }

    public void setEstoque(double estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "=========================| Peça |=========================\n" +
                "ID: " + this.id +
                "\nNome: " + this.nome +
                "\nEstoque: " + this.estoque + "\n";
    }
}
