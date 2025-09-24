package br.com.manutencao.dao;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.model.OrdemManutencao;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDao {


    public void insert(Maquina maquina) throws SQLException{
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


        }
    }

    public List<Maquina> select() throws SQLException{
        String sql = """
                SELECT id, nome, setor, status
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
                String status = rs.getString("status");

                Maquina maquina = new Maquina(id, nome, setor, status);
                maquinas.add(maquina);
            }

        }

        return maquinas;
    }


    public List<Maquina> select_statusPendente() throws SQLException{
        String sql = """
                SELECT id, nome, setor, status
                FROM Maquina
                where status = "OPERACIONAL";
                """;

        List<Maquina> maquinas = new ArrayList<>();

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");
                String setor = rs.getString("setor");
                String status = rs.getString("status");

                Maquina maquina = new Maquina(id, nome, setor, status);
                maquinas.add(maquina);
            }

        }

        return maquinas;
    }

    public boolean verifyDuplicataBySetor(Maquina maquina) throws SQLException{
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

        }

        return false;
    }

    public void updateStatus(Maquina maquina) throws SQLException{
        String sql = """
                UPDATE Maquina SET status = ?
                WHERE id = ?;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, maquina.getId());
            stmt.executeUpdate();

        }
    }

    public void updateExecutada(Integer id) throws SQLException{
        String sql = """
                UPDATE OrdemManutencao SET status = "OPERACIONAL"
                WHERE id = ?;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);
            stmt.executeUpdate();

        }
    }

}
