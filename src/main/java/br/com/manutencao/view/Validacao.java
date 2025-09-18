package br.com.manutencao.view;

import br.com.manutencao.service.Industria;

public class Validacao {
    Industria industria;
    public static Viewer uiView;

    public Validacao(){
        industria = new Industria();
        uiView = new Viewer();
    }



    public static boolean verifyNull(String variavel){
        return variavel.trim().isEmpty();
    }
}
