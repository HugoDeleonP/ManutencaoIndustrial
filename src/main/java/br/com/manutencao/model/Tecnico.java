package br.com.manutencao.model;

public class Tecnico {
    private Integer id;
    private String nome;
    private String especialidade;

    public Tecnico(Integer id, String nome, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Tecnico(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Tecnico(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "=========================| Técnico |=========================\n" +
                "ID: " + this.id +
                "\nNome: " + this.nome +
                "\nEspecialidade: " + this.especialidade + "\n";
    }
}
