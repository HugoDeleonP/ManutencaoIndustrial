package br.com.manutencao.service;

import br.com.manutencao.dao.*;
import br.com.manutencao.model.*;
import br.com.manutencao.view.Validacao;
import br.com.manutencao.view.Viewer;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

public class Industria {

    Viewer uiView = new Viewer();

    List<Maquina> maquinas;
    List<OrdemManutencao> manutencoes;
    List<OrdemPeca> ordemPecas;
    List<Peca> pecas;
    List<Tecnico> tecnicos;

    MaquinaDao maquinaData;
    OrdemManutencaoDao manutencaoData;
    OrdemPecaDao ordemPecaData;
    PecaDao pecaData;
    TecnicoDao tecnicoData;

    public Industria(){
        uiView = new Viewer();

        maquinas = new ArrayList<>();
        manutencoes = new ArrayList<>();
        ordemPecas = new ArrayList<>();
        pecas = new ArrayList<>();
        tecnicos = new ArrayList<>();

        maquinaData = new MaquinaDao();
        manutencaoData = new OrdemManutencaoDao();
        ordemPecaData = new OrdemPecaDao();
        pecaData = new PecaDao();
        tecnicoData = new TecnicoDao();

    }

    public void mainRouter(int mainMenu){
        switch (mainMenu){
            case 1->{
                //Cadastrar máquina
                cadastrarMaquina();
            }
            case 2->{
                //Cadastrar técnico
            }
            case 3->{
                //Cadastrar peça
            }
            case 4->{
                //Criar ordem de manutenção
            }
            case 5->{
                //Associar peças à ordem
            }
            case 6->{
                //Executar manutenção
            }

        }
    }

    private void cadastrarMaquina(){
        String operacao = "Cadastrar máquina";
        String entidade = "a máquina";

        /*
        Nome e setor obrigatórios

        Não cadastrar máquina duplicada no mesmo setor, validar a existência no banco
        de dados.
        */

        String nome = uiView.stringInput(operacao, "o nome", entidade);
        boolean isNullNome = Validacao.verifyNull(nome);


        String setor = uiView.stringInput(operacao, "o setor", entidade);
        boolean isNullSetor = Validacao.verifyNull(setor);

        Maquina maquinaVerify = new Maquina(nome, setor);

        boolean isUniqueNome = maquinaData.verifyDuplicataBySetor(maquinaVerify);

        if(isNullNome || isNullSetor){
            uiView.warnEmptyInput("O nome");
            uiView.warnEmptyInput("O setor");
        }
        else if(!isUniqueNome){
            uiView.warnNonUnique("a máquina", "a");
        }
        else{
            Maquina maquina = new Maquina(nome, setor, StatusMaquina.OPERACIONAL.toString());
            maquinaData.insert(maquina);
        }

    }

    public void cadastrarTecnico(){
    }
    public void cadastrarPeca(){
    }
    public void cadastrarOrdemManutencao(){
    }
    public void associaPeca_ordem(){
    }
    public void executaManutencao(){
    }
}
