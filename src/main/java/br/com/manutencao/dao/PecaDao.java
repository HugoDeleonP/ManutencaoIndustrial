package br.com.manutencao.dao;

import br.com.manutencao.model.Maquina;
import br.com.manutencao.model.Peca;
import br.com.manutencao.model.Tecnico;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDao {

    public void insert(Peca peca) throws SQLException{
        String sql = """
                INSERT INTO Peca (nome, estoque)
                VALUES (?, ?);
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt= conn.prepareStatement(sql)){

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getEstoque());

            stmt.executeUpdate();

        }
    }

    public List<Peca> select_saldo() throws SQLException{
        String sql = """
                SELECT id, nome, estoque
                FROM Peca;
                """;
        List<Peca> pecas = new ArrayList<>();

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");

                Peca peca = new Peca(id, nome, estoque);
                pecas.add(peca);
            }
        }

        return pecas;
    }

    public boolean verifyUniqueByNome(String nome) throws SQLException{
        String sql = """
                select count(nome) as quantidade_nome from Peca
                where nome = ?;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int quantidade_nome = rs.getInt("quantidade_nome");
                return quantidade_nome == 0;
            }
        }
        return false;
    }

    public void updateEstoque(Peca peca) throws SQLException{
        String sql = """
                UPDATE Peca SET estoque = ?
                WHERE id = ?;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setDouble(1, peca.getEstoque());
            stmt.setInt(2, peca.getId());
            stmt.executeUpdate();

        }
    }
}
