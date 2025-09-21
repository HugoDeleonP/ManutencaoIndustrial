package br.com.manutencao.utils;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        try(Connection conn = Connect.connect()){
            if(conn != null){
                System.out.println("Banco de dados conectado com sucesso!");
            }
            else{
                System.out.println("500 - Internal server error");
            }
        } catch (Exception e){
            System.out.println("Banco de dados não criado!");
            e.printStackTrace();
        }
    }
}
