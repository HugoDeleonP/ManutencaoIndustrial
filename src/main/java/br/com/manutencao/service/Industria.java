package br.com.manutencao.service;

import br.com.manutencao.dao.*;
import br.com.manutencao.model.*;
import br.com.manutencao.view.Validacao;
import br.com.manutencao.view.Viewer;

import java.sql.SQLException;
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
                associaPeca_ordem();
            }
            case 6->{
                //Executar manutenção
            }

        }
    }

    private void cadastrarMaquina(){
        String operacao = "Cadastrar máquina";
        String entidade = "a máquina";
        boolean isUniqueNome = false;

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
        try{
             isUniqueNome = maquinaData.verifyDuplicataBySetor(maquinaVerify);
        } catch (SQLException e){
            e.printStackTrace();
        }


        if(isNullNome || isNullSetor){
            uiView.warnEmptyInput("O nome");
            uiView.warnEmptyInput("O setor");
        }
        else if(!isUniqueNome){
            uiView.warnNonUnique("a máquina", "a");
        }
        else{
            Maquina maquina = new Maquina(nome, setor, StatusMaquina.OPERACIONAL.toString());

            try{
                maquinaData.insert(maquina);
                uiView.sucessoDao("Máquina", "cadastrada");
            } catch (SQLException e){
                e.printStackTrace();
            }

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
        boolean isUniqueNome = false;

        String nome = uiView.stringInput(operacao,"o nome", entidade);
        boolean isNullNome = Validacao.verifyNull(nome);

        String especialidade = uiView.stringInput(operacao, "a especialidade", entidade);

        Tecnico tecnico = new Tecnico(nome, especialidade);

        try{
            isUniqueNome = tecnicoData.verifyDuplicataByTecnico(tecnico);
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(isNullNome){
            uiView.warnEmptyInput("O nome");
        }
        else if(!isUniqueNome){
            uiView.warnNonUnique(" funcionário", "o");
        }else {
            try{
                tecnicoData.insert(tecnico);
                uiView.sucessoDao("Técnico", "cadastrado");
            } catch (SQLException e){
                e.printStackTrace();
            }
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
        boolean isUnique = false;

        String nome = uiView.stringInput(operacao,"o nome", entidade);
        boolean isNullNome = Validacao.verifyNull(nome);

        double estoque = uiView.intInput(operacao, "a quantidade de estoque", entidade);
        boolean isGreaterThanZero = Validacao.verifyNullDouble(estoque);

        try {
            pecaData.verifyUniqueByNome(nome);
        }catch (SQLException e){
            e.printStackTrace();
        }

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
            try{
                pecaData.insert(peca);
                uiView.sucessoDao("Peça", "cadastrada");
            } catch (SQLException e){
                e.printStackTrace();
            }

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

        try{
            manutencaoData.insert(manutencao);
            uiView.sucessoDao("Ordem de manutenção", "cadastrada");
            maquinaData.updateStatus(maquina);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private List<Maquina> listaMaquina_Pendente(){
        try{
            maquinas = maquinaData.select_statusPendente();
        } catch (SQLException e){
            e.printStackTrace();
        }

        for(Maquina maquina: maquinas){
            System.out.println(maquina);
        }

        return maquinas;
    }

    private Maquina buscaMaquina(Integer id){
        try{
            maquinas = maquinaData.select();
        } catch (SQLException e){
            e.printStackTrace();
        }

        for(Maquina maquina: maquinas){
            if(Objects.equals(maquina.getId(), id)){
                return maquina;
            }
        }

        return null;
    }

    private List<Tecnico> listaTecnico(){
        try{
            tecnicos = tecnicoData.select();
        } catch (SQLException e){
            e.printStackTrace();
        }


        for(Tecnico tecnico: tecnicos){
            System.out.println(tecnico);
        }

        return tecnicos;
    }

    private Tecnico buscaTecnico(Integer id){
        try{
            tecnicos = tecnicoData.select();
        } catch (SQLException e){
            e.printStackTrace();
        }
        for(Tecnico tecnico: tecnicos){
            if(Objects.equals(tecnico.getId(), id)){
                return tecnico;
            }
        }

        return null;
    }

    private void associaPeca_ordem(){
        /*
        2. Selecionar ordem:
            ○ Listar todas as ordens com status: “PENDENTE”
            ○ Usuário escolhe
            ○ Valida se é uma opção válida.

        3. Para cada peça:
            ○ Listar peças e seus respectivos saldos
            ○ Selecionar peça do estoque
            ○ Validar se é uma opção válida
            ○ Informar quantidade necessária
            ○ Validar quantidade ≥ 0
        4. Inserir na tabela “OrdemPeca” as informações obtidas
        5. Permitir adicionar várias peças, faça uma validação após inserir solicitando se o usuário
        deseja adicionar mais uma peça a ordem.
         */
        String operacao = "Cadastrar ordem de peça";

        boolean continuar = false;

        do{
            manutencoes = listaManutencao();
            Integer manutencao_id = uiView.intInput(operacao, "o ID", "a ordem de manutenção");
            OrdemManutencao manutencao = buscaManutencao(manutencao_id);
            if(manutencao == null){
                uiView.warnOptionInexistent("a manutenção");
                return;
            }

            pecas = listaPeca();
            Integer id = uiView.intInput(operacao, "o ID", "a peça");
            Peca peca = buscaPeca(id);
            if(peca == null){
                uiView.warnOptionInexistent("a peça");
                return;
            }
            double quantidade_estoque = uiView.doubleInput(operacao, "a quantidade", "a peça");

            if(quantidade_estoque < 0){
                uiView.warnLessThanZero("A quantidade", "a");
                return;
            }
            OrdemPeca ordemPeca = new OrdemPeca(manutencao, peca, quantidade_estoque);

            try{
                ordemPecaData.insert(ordemPeca);
                uiView.sucessoDao("Ordem de peça", "cadastrada");

            } catch (SQLException e) {
                e.printStackTrace();
            }

            int escolha = uiView.adicionarMaisPeca();

            switch (escolha){
                case 1 -> {
                    continuar = true;
                }
                case 2 ->{
                    return;
                }
                default -> {
                    uiView.warnOptionInexistent("a opção");
                }
            }
        }while(continuar);


    }

    private List<OrdemManutencao> listaManutencao(){
        try{
            manutencoes = manutencaoData.select_statusPendente();
        } catch (SQLException e){
            e.printStackTrace();
        }


        for(OrdemManutencao manutencao: manutencoes){
            System.out.println(manutencao);
        }

        return manutencoes;
    }

    private OrdemManutencao buscaManutencao(Integer id){
        for(OrdemManutencao manutencao: manutencoes){
            if(Objects.equals(manutencao.getId(), id)){
                return manutencao;
            }
        }

        return null;
    }

    private List<Peca> listaPeca(){
        try{
            pecas = pecaData.select_saldo();
        } catch (SQLException e){
            e.printStackTrace();
        }


        for(Peca peca: pecas){
            System.out.println(peca);
        }

        return pecas;
    }

    private Peca buscaPeca(Integer id){
        for(Peca peca: pecas){
            if(Objects.equals(peca.getId(), id)){
                return peca;
            }
        }

        return null;
    }

    private void executaManutencao(){

        /*

        2. Selecionar ordem somente com status: “PENDENTE”.
        3. Verificar estoque de peças:
            ○ Para cada peça da ordem:
                ■ Verificar o estoque atual da peça no banco de dados.
                ■ Comparar com quantidade necessária

        4. Se estoque insuficiente:
            ○ Mensagem de erro e abortar execução

        5. Se estoque suficiente:
            ○ Para cada peça:
                ■ Atualize o estoque diminuindo a quantidade que foi
                utilizada na ordem.
            ○ Atualizar ordem:
                ■ Atualize o status da ordem para “Executada”
            ○ Atualizar máquina:
                ■ Atualize o status da maquina para “OPERACIONAL”.
        */
        String operacao = "Executar manutenção";
        boolean comparaEstoque_quantidade = false;

        manutencoes = listaManutencao();
        Integer manutencao_id = uiView.intInput(operacao, "o ID", "a ordem de manutenção");
        OrdemManutencao manutencao = buscaManutencao(manutencao_id);
        try{
            comparaEstoque_quantidade = ordemPecaData.verifyEstoque_quantidade();
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(manutencao == null){
            uiView.warnOptionInexistent("a manutenção");
            return;
        }
        else if(comparaEstoque_quantidade == false){
            System.out.println("A quantidade de estoque é menor que a quantidade necessária");
            return;
        }
        else{

        }


    }
}
