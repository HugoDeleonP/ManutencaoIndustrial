package br.com.manutencao.service;

import br.com.manutencao.dao.*;
import br.com.manutencao.model.*;
import br.com.manutencao.view.Validacao;
import br.com.manutencao.view.Viewer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                cadastrarTecnico();
            }
            case 3->{
                //Cadastrar peça
                cadastrarPeca();
            }
            case 4->{
                //Criar ordem de manutenção
                cadastrarOrdemManutencao();
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

    private void cadastrarTecnico(){
                /*
                Solicitar:
                ○ Nome do técnico
                ○ Especialidade
                3. Validações:
                ○ Nome obrigatório
                ○ Evitar duplicidade, validar informações no banco de dados.
                */
        String operacao = "Cadastrar técnico";
        String entidade = "o técnico";

        String nome = uiView.stringInput(operacao,"o nome", entidade);
        boolean isNullNome = Validacao.verifyNull(nome);

        String especialidade = uiView.stringInput(operacao, "a especialidade", entidade);

        Tecnico tecnico = new Tecnico(nome, especialidade);

        boolean isUniqueNome = tecnicoData.verifyDuplicataByTecnico(tecnico);

        if(isNullNome){
            uiView.warnEmptyInput("O nome");
        }
        else if(!isUniqueNome){
            uiView.warnNonUnique(" funcionário", "o");
        }else {
            tecnicoData.insert(tecnico);
        }
    }
    private void cadastrarPeca(){
        /*
        Validações:
        ○ Nome obrigatório
        ○ Estoque ≥ 0
        ○ Evitar duplicidade, validar se a peça já existe no banco de dados
        */
        String operacao = "Cadastrar peça";
        String entidade = "a peça";

        String nome = uiView.stringInput(operacao,"o nome", entidade);
        boolean isNullNome = Validacao.verifyNull(nome);

        double estoque = uiView.intInput(operacao, "a quantidade de estoque", entidade);
        boolean isGreaterThanZero = Validacao.verifyNullDouble(estoque);

        boolean isUnique = pecaData.verifyUniqueByNome(nome);

        Peca peca = new Peca(nome, estoque);

        if(isNullNome){
            uiView.warnEmptyInput("O nome");
        }
        else if(!isGreaterThanZero){
            uiView.warnLessThanZero("O estoque", "o");
        }

        //analisar a validade da leitura de um double e int
        else if(!isUnique){
            uiView.warnNonUnique("a peça", "a");
        }
        else{
            pecaData.insert(peca);
        }
    }
    private void cadastrarOrdemManutencao(){

        /*
            Selecionar máquina:
            ○ Listar máquinas somente com status: “OPERACIONAL”
            ○ Usuário escolhe
            ○ Validar escolha, se é uma das opções disponíveis.
            3. Selecionar técnico:
            ○ Listar todos os técnicos
            ○ Usuário escolhe
            ○ Validar escolha, se é uma das opções disponíveis.

            4. Data de solicitação = data atual
            5. Status inicial = PENDENTE
            6. Inserção no banco:
            ○ Inserir informações obtidas na sua respectiva tabela.
            7. Atualizar status da máquina para EM_MANUTENCAO
        */
        String operacao = "Cadastrar ordem de manutenção";

        maquinas = listaMaquina_Pendente();
        Integer maquina_id = uiView.intInput(operacao,"o ID", "a máquina desejada");
        Maquina maquina = buscaMaquina(maquina_id);
        if(maquina == null){
            uiView.warnOptionInexistent("a máquina");
            return;
        }

        tecnicos = listaTecnico();
        Integer tecnico_id = uiView.intInput(operacao, "o ID","o técnico desejado");
        Tecnico tecnico = buscaTecnico(tecnico_id);
        if(tecnico == null){
            uiView.warnOptionInexistent("o técnico");
            return;
        }

        LocalDate data = LocalDate.now();
        String status = StatusManutencao.PENDENTE.toString();
        
        OrdemManutencao manutencao = new OrdemManutencao(maquina, tecnico, data, status);


        manutencaoData.insert(manutencao);
        maquinaData.updateStatus(maquina);

    }

    private List<Maquina> listaMaquina_Pendente(){
        maquinas = maquinaData.select_statusPendente();

        for(Maquina maquina: maquinas){
            System.out.println(maquina);
        }

        return maquinas;
    }

    private Maquina buscaMaquina(Integer id){
        maquinas = maquinaData.select();
        for(Maquina maquina: maquinas){
            if(Objects.equals(maquina.getId(), id)){
                return maquina;
            }
        }

        return null;
    }

    private List<Tecnico> listaTecnico(){
        tecnicos = tecnicoData.select();

        for(Tecnico tecnico: tecnicos){
            System.out.println(tecnico);
        }

        return tecnicos;
    }

    private Tecnico buscaTecnico(Integer id){
        tecnicos = tecnicoData.select();
        for(Tecnico tecnico: tecnicos){
            if(Objects.equals(tecnico.getId(), id)){
                return tecnico;
            }
        }

        return null;
    }

    private void associaPeca_ordem(){
    }
    private void executaManutencao(){
    }
}
