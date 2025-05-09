package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Pokemon;

public class PokemonDAO {

    private Connection connection;

    public PokemonDAO() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Pokemons", 
                "ti2cc", 
                "ti@cc"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int inserir(Pokemon pokemon) {
        int codigoGerado = -1;
        String sql = "INSERT INTO \"Pokemon\" (\"Nome\", \"Tipo\", \"Nivel\") VALUES (?, ?, ?) RETURNING \"Codigo\"";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pokemon.getNome());
            ps.setString(2, pokemon.getTipo());
            ps.setInt(3, pokemon.getNivel());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                codigoGerado = rs.getInt("Codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoGerado;
    }

    public Pokemon buscarPorCodigo(int codigo) {
        Pokemon pokemon = null;
        String sql = "SELECT * FROM pokemon WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pokemon = new Pokemon(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getInt("nivel")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemon;
    }

    public List<Pokemon> listarTodos() {
        List<Pokemon> pokemons = new ArrayList<>();
        String sql = "SELECT \"Codigo\", \"Nome\", \"Tipo\", \"Nivel\" FROM \"Pokemon\" ORDER BY \"Codigo\"";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int codigo = rs.getInt("Codigo");
                String nome = rs.getString("Nome");
                String tipo = rs.getString("Tipo");
                int nivel = rs.getInt("Nivel");

                Pokemon pokemon = new Pokemon(codigo, nome, tipo, nivel);
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    public boolean atualizar(Pokemon pokemon) {
        String sql = "UPDATE \"Pokemon\" SET \"Nome\" = ?, \"Tipo\" = ?, \"Nivel\" = ? WHERE \"Codigo\" = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pokemon.getNome());
            stmt.setString(2, pokemon.getTipo());
            stmt.setInt(3, pokemon.getNivel());
            stmt.setInt(4, pokemon.getCodigo());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletar(int codigo) {
        String sql = "DELETE FROM \"Pokemon\" WHERE \"Codigo\" = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}