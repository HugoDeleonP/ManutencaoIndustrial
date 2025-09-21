package br.com.manutencao.dao;

import br.com.manutencao.model.OrdemManutencao;
import br.com.manutencao.model.OrdemPeca;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdemPecaDao {

    Viewer uiView = new Viewer();

    public void insert(OrdemPeca ordemPeca) {
        String sql = """
                INSERT INTO OrdemPeca (idOrdem, idPeca, quantidade)
                VALUES (?, ?, ?);
                """;

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordemPeca.getOrdemManutencao().getId());
            stmt.setInt(2, ordemPeca.getPeca().getId());
            stmt.setDouble(3, ordemPeca.getQuantidade());

            stmt.executeUpdate();
            uiView.sucessoDao("Ordem de peça", "cadastrada");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
