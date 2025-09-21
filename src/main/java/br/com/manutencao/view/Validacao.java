package br.com.manutencao.view;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.service.Industria;

import java.sql.Wrapper;
import java.util.*;
import java.util.function.Function;

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

    public static boolean verifyNullDouble(Double numero){
        return numero >= 0;
    }

    public static boolean verifyNullInt(int numero){
        return numero >= 0;
    }
}
