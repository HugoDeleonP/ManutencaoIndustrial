package br.com.manutencao.model;

public enum StatusMaquina {

    //("OPERACIONAL", "EM_MANUTENCAO")

    OPERACIONAL("OPERACIONAL"),
    EM_MANUTENCAO("EM_MANUTENCAO");

    private final String ENUM_CHOOSE;

    StatusMaquina(String ENUM_CHOOSE){
        this.ENUM_CHOOSE = ENUM_CHOOSE;
    }

    @Override
    public String toString() {
        return ENUM_CHOOSE;
    }
}
