package br.com.manutencao.model;

public class Maquina {
    private Integer id;
    private String nome;
    private String setor;
    private String status_maquina;

    public Maquina(Integer id, String nome, String setor, String status_maquina) {
        this.id = id;
        this.nome = nome;
        this.setor = setor;
        this.status_maquina = status_maquina;
    }

    public Maquina(String nome, String setor, String status_maquina) {
        this.nome = nome;
        this.setor = setor;
        this.status_maquina = status_maquina;
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

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getStatus_maquina() {
        return status_maquina;
    }

    public void setStatus_maquina(String status_maquina) {
        this.status_maquina = status_maquina;
    }

    @Override
    public String toString(){
        return "=========================| Máquina |=========================\n" +
                "ID: " + this.id +
                "\nNome: " + this.nome +
                "\nSetor: " + this.setor +
                "\nStatus: " + this.status_maquina +"\n";
    }
}
