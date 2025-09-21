package br.com.manutencao.dao;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDao {

    Viewer uiView = new Viewer();

    public void insert(Maquina maquina){
        String sql = """
                INSERT INTO Maquina (nome, setor, status)
                VALUES (?, ?, ?);
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt= conn.prepareStatement(sql)){

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, maquina.getStatus_maquina());

            stmt.executeUpdate();

            uiView.sucessoDao("Máquina", "cadastrada");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Maquina> select(){
        String sql = """
                SELECT id, nome, setor, status_maquina
                FROM Maquina;
                """;

        List<Maquina> maquinas = new ArrayList<>();

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");
                String setor = rs.getString("setor");
                String status_maquina = rs.getString("status_maquina");

                Maquina maquina = new Maquina(id, nome, setor, status_maquina);
                maquinas.add(maquina);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return maquinas;
    }

    public boolean verifyDuplicataBySetor(Maquina maquina){
        String sql = """
                select count(nome) as quantidade_nome from Maquina
                where setor = ?
                and nome = ?;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, maquina.getSetor());
            stmt.setString(2, maquina.getNome());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int quantidade = rs.getInt("quantidade_nome");
                return quantidade == 0;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

}
