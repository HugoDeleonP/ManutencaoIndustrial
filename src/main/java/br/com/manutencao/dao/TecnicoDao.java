package br.com.manutencao.dao;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.model.Tecnico;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

            stmt.executeUpdate();
            uiView.sucessoDao("Técnico", "cadastrado");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean verifyDuplicataByTecnico(Tecnico tecnico){
        String sql = """
                select count(nome) as quantidade_nome from Tecnico
                where especialidade = ?
                and nome = ?;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, tecnico.getEspecialidade());
            stmt.setString(2, tecnico.getNome());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int quantidade_nome = rs.getInt("quantidade_nome");
                return quantidade_nome == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
