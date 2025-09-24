package br.com.manutencao.dao;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.model.OrdemManutencao;
import br.com.manutencao.model.Peca;
import br.com.manutencao.model.Tecnico;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemManutencaoDao {

    public void insert(OrdemManutencao ordemManutencao) throws SQLException {
        String sql = """
                INSERT INTO OrdemManutencao (idMaquina, idTecnico, dataSolicitacao, status)
                VALUES (?, ?, ?, ?);
                """;

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordemManutencao.getMaquina().getId());
            stmt.setInt(2, ordemManutencao.getTecnico().getId());
            stmt.setObject(3, ordemManutencao.getDataSolicitacao());
            stmt.setString(4, ordemManutencao.getStatus_manutencao());

            stmt.executeUpdate();

        }
    }

    public List<OrdemManutencao> select_statusPendente() throws SQLException{
        String sql = """
                select OrdemManutencao.id as manutencao_id,
                idMaquina as maquina_id,
                Maquina.nome as maquina_nome,
                idTecnico as tecnico_id,
                Tecnico.nome as tecnico_nome,
                dataSolicitacao,
                OrdemManutencao.status
                from OrdemManutencao
                LEFT JOIN Maquina ON OrdemManutencao.idMaquina = Maquina.id
                LEFT JOIN Tecnico ON OrdemManutencao.idTecnico = Tecnico.id
                WHERE OrdemManutencao.status = "PENDENTE";
                """;

        List<OrdemManutencao> manutencoes = new ArrayList<>();

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer manutencao_id = rs.getInt("manutencao_id");

                Integer maquina_id = rs.getInt("maquina_id");
                String maquina_nome = rs.getString("maquina_nome");

                Integer tecnico_id = rs.getInt("tecnico_id");
                String tecnico_nome = rs.getString("tecnico_nome");

                LocalDate dataSolicitacao = rs.getObject("dataSolicitacao", LocalDate.class);
                String status = rs.getString("status");

                Maquina maquina = new Maquina(maquina_id, maquina_nome);
                Tecnico tecnico = new Tecnico(tecnico_id, tecnico_nome);

                OrdemManutencao manutencao = new OrdemManutencao(manutencao_id, maquina, tecnico, dataSolicitacao, status);
                manutencoes.add(manutencao);
            }
        }
        return manutencoes;
    }

    public void updateExecutada(Integer id) throws SQLException{
        String sql = """
                UPDATE OrdemManutencao SET status = "EXECUTADA"
                WHERE id = ?;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);
            stmt.executeUpdate();

        }
    }
}
