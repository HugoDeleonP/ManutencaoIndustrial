package br.com.manutencao.dao;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.model.Tecnico;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TecnicoDao {

    Viewer uiView = new Viewer();

    public void insert(Tecnico tecnico){
        String sql = """
                INSERT INTO Tecnico (nome, especialidade)
                VALUES (?, ?);
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt= conn.prepareStatement(sql)){

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());

            uiView.sucessoDao("Técnico", "cadastrado");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
