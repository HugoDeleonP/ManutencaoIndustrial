package br.com.manutencao.dao;

import br.com.manutencao.model.OrdemManutencao;
import br.com.manutencao.model.Peca;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdemManutencaoDao {

    Viewer uiView = new Viewer();

    public void insert(OrdemManutencao ordemManutencao) {
        String sql = """
                INSERT INTO OrdemManutencao (idMaquina, idTecnico, dataSolicitacao, status_manutencao)
                VALUES (?, ?, ?, ?);
                """;

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordemManutencao.getMaquina().getId());
            stmt.setInt(2, ordemManutencao.getTecnico().getId());
            stmt.setObject(3, ordemManutencao.getDataSolicitacao());
            stmt.setString(4, ordemManutencao.getStatus_manutencao());

            stmt.executeUpdate();
            uiView.sucessoDao("Ordem de manutenção", "cadastrada");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
