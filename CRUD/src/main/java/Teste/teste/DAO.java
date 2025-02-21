package Teste.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    private Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "teste";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;
        
        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean inserirPokemon(Pokemon pokemon) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO pokemon (codigo, nome, tipo, nivel) "
                        + "VALUES (" + pokemon.getCodigo() + ", '" + pokemon.getNome() + "', '"  
                        + pokemon.getTipo() + "', " + pokemon.getNivel() + ");");
            st.close();
            status = true;
        } catch (SQLException e) {  
            throw new RuntimeException(e);
        }
        return status;
    }

    public boolean atualizarPokemon(Pokemon pokemon) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE pokemon SET nome = '" + pokemon.getNome() + "', tipo = '"  
                       + pokemon.getTipo() + "', nivel = " + pokemon.getNivel()
                       + " WHERE codigo = " + pokemon.getCodigo();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException e) {  
            throw new RuntimeException(e);
        }
        return status;
    }

    public boolean excluirPokemon(int codigo) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM pokemon WHERE codigo = " + codigo);
            st.close();
            status = true;
        } catch (SQLException e) {  
            throw new RuntimeException(e);
        }
        return status;
    }

    public Pokemon[] getPokemons() {
        Pokemon[] pokemons = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM pokemon");        
            if (rs.next()) {
                rs.last();
                pokemons = new Pokemon[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    pokemons[i] = new Pokemon(rs.getInt("codigo"), rs.getString("nome"), 
                                              rs.getString("tipo"), rs.getInt("nivel"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return pokemons;
    }

    public Pokemon[] getPokemonsFogo() {
        Pokemon[] pokemons = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM pokemon WHERE tipo LIKE 'Fogo'");        
            if (rs.next()) {
                rs.last();
                pokemons = new Pokemon[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    pokemons[i] = new Pokemon(rs.getInt("codigo"), rs.getString("nome"), 
                                              rs.getString("tipo"), rs.getInt("nivel"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return pokemons;
    }
}