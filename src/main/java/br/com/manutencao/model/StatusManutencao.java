package br.com.manutencao.model;

public enum StatusManutencao {

    //("PENDENTE", "EXECUTADA", "CANCELADA")

    PENDENTE("PENDENTE"),
    EXECUTADA("EXECUTADA"),
    CANCELADA("CANCELADA");

    private final String ENUM_CHOOSE;

    StatusManutencao(String ENUM_CHOOSE){
        this.ENUM_CHOOSE = ENUM_CHOOSE;
    }

    @Override
    public String toString() {
        return ENUM_CHOOSE;
    }
}
