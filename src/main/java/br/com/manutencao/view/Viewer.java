package br.com.manutencao.view;

import java.util.Scanner;

public class Viewer {

    public Scanner input;

    public Viewer(){
        input = new Scanner(System.in);
    }

    public int mainMenu(){
        System.out.println("Sistema de manutenção industrial\n");
        System.out.println("1- Cadastrar máquina");
        System.out.println("2- Cadastrar técnico");
        System.out.println("3- Cadastrar peça");
        System.out.println("4- Criar ordem de manutenção");
        System.out.println("5- Associar peças à ordem");
        System.out.println("6- Executar manutenção\n");
        System.out.println("0- Sair");
        int escolha = input.nextInt();
        input.nextLine();

        return escolha;
    }

    public String stringInput(String operacao, String atributo, String entidade){
        System.out.printf("%s\n\n", operacao);
        System.out.printf("Digite %s d%s\n", atributo, entidade);
        return input.nextLine();
    }

    public int intInput(String operacao, String atributo, String entidade){
        System.out.printf("%s\n\n", operacao);
        System.out.printf("Digite %s d%s\n", atributo, entidade);
        int valor = input.nextInt();
        input.nextLine();

        return valor;
    }

    public double doubleInput(String operacao, String atributo, String entidade){
        System.out.printf("%s\n\n", operacao);
        System.out.printf("Digite %s d%s\n", atributo, entidade);
        double valor = input.nextDouble();
        input.nextLine();

        return valor;
    }

    public void sucessoDao(String entidade, String operacao){
        System.out.printf("%s %s com sucesso!\n", entidade, operacao);
    }

    public void erroDao(String entidade, String operacao){
        System.out.printf("%s não foi %s, devido a um erro referente ao banco de dados\n", entidade, operacao);
    }

    public void warnEmptyInput(String atributo){
        System.out.printf("%s é obrigatório\n", atributo);
    }

}
