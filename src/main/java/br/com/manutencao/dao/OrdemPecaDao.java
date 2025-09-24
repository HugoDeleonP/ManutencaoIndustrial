package br.com.manutencao.dao;

import br.com.manutencao.model.OrdemManutencao;
import br.com.manutencao.model.OrdemPeca;
import br.com.manutencao.utils.Connect;
import br.com.manutencao.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdemPecaDao {


    public void insert(OrdemPeca ordemPeca) throws SQLException{
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

        }
    }
    public boolean verifyEstoque_quantidade() throws SQLException{
        String sql = """
                select Peca.estoque as peca_estoque, OrdemPeca.quantidade as ordemPeca_quantidade
                FROM OrdemPeca
                LEFT JOIN Peca ON OrdemPeca.idPeca = Peca.id
                WHERE ;
                """;

        try(Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                double peca_estoque = rs.getDouble("peca_estoque");
                double ordemPeca_quantidade = rs.getDouble("ordemPeca_quantidade");

                return peca_estoque >= ordemPeca_quantidade;
            }
        }
        return false;
    }
}
