package service;

import dao.PokemonDAO;
import model.Pokemon;
import java.util.List;

public class PokemonService {

    private PokemonDAO pokemonDAO;

    public PokemonService() {
        pokemonDAO = new PokemonDAO();
    }

    public int inserirPokemon(String nome, String tipo, int nivel) {
        Pokemon pokemon = new Pokemon(nome, tipo, nivel);
        return pokemonDAO.inserir(pokemon);
    }

    public Pokemon buscarPokemonPorCodigo(int codigo) {
        return pokemonDAO.buscarPorCodigo(codigo);
    }

    public List<Pokemon> listarTodosPokemons() {
        return pokemonDAO.listarTodos();
    }

    public boolean atualizarPokemon(int codigo, String nome, String tipo, int nivel) {
        Pokemon pokemon = new Pokemon(codigo, nome, tipo, nivel);
        return pokemonDAO.atualizar(pokemon);
    }

    public boolean deletarPokemon(int codigo) {
        return pokemonDAO.deletar(codigo);
    }
}