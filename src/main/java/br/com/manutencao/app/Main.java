package br.com.manutencao.app;

import br.com.manutencao.service.Industria;
import br.com.manutencao.view.Viewer;

import java.security.Provider;

public class Main {

    public static void main(String[] args) {
        Viewer uiView = new Viewer();
        Industria industria = new Industria();

        int escolhaUsuario;
        do{
            escolhaUsuario = uiView.mainMenu();
            industria.mainRouter(escolhaUsuario);
        } while (escolhaUsuario != 0);
    }
}
