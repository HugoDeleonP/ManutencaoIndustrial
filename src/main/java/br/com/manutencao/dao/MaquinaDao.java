package br.com.manutencao.dao;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaquinaDao {

    Viewer uiView = new Viewer();

    public void insert(Maquina maquina){
        String sql = """
                INSERT INTO Maquina (nome, setor, status_maquina)
                VALUES (?, ?, ?);
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt= conn.prepareStatement(sql)){

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, maquina.getStatus_maquina());

            uiView.sucessoDao("Máquina", "cadastrada");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
