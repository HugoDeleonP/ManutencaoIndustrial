package br.com.manutencao.model;

import java.time.LocalDate;

public class OrdemManutencao {

    private Integer id;
    private Maquina maquina;
    private Tecnico tecnico;
    private LocalDate dataSolicitacao;
    private String status_manutencao;

    public OrdemManutencao(Integer id, Maquina maquina, Tecnico tecnico, LocalDate dataSolicitacao, String status_manutencao) {
        this.id = id;
        this.maquina = maquina;
        this.tecnico = tecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status_manutencao = status_manutencao;
    }

    public OrdemManutencao(Maquina maquina, Tecnico tecnico, LocalDate dataSolicitacao, String status_manutencao) {
        this.maquina = maquina;
        this.tecnico = tecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status_manutencao = status_manutencao;
    }

    public OrdemManutencao(Integer id, Maquina maquina, Tecnico tecnico, LocalDate dataSolicitacao) {
        this.id = id;
        this.maquina = maquina;
        this.tecnico = tecnico;
        this.dataSolicitacao = dataSolicitacao;
    }

    public OrdemManutencao(Maquina maquina, Tecnico tecnico, LocalDate dataSolicitacao) {
        this.maquina = maquina;
        this.tecnico = tecnico;
        this.dataSolicitacao = dataSolicitacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus_manutencao() {
        return status_manutencao;
    }

    public void setStatus_manutencao(String status_manutencao) {
        this.status_manutencao = status_manutencao;
    }

    @Override
    public String toString() {
        return "=========================| Ordem de manutenção |=========================\n" +
                "ID: " + this.id +
                "\nMáquina: " + this.maquina.getId() +
                "\nTécnico: " + this.tecnico.getNome() +
                "\nData de solicitação: " + this.dataSolicitacao +
                "\nStatus: " + this.status_manutencao + "\n";
    }
}
