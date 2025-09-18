package br.com.manutencao.dao;

import br.com.manutencao.model.Peca;
import br.com.manutencao.model.Tecnico;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PecaDao {

    Viewer uiView = new Viewer();

    public void insert(Peca peca){
        String sql = """
                INSERT INTO Peca (nome, estoque)
                VALUES (?, ?);
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt= conn.prepareStatement(sql)){

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getEstoque());

            uiView.sucessoDao("Peça", "cadastrada");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
