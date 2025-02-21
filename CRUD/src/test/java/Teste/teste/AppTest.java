package Teste.teste;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

    private DAO dao;

    public AppTest(String testName) {
        super(testName);
        dao = new DAO();
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testConectar() {
        assertTrue("Não foi possível conectar ao banco!", dao.conectar());
    }

    public void testInserirPokemon() {
        dao.conectar();
        Pokemon novoPokemon = new Pokemon("Pikachu", "Elétrico", 5);
        assertTrue("Erro ao inserir Pokémon", dao.inserirPokemon(novoPokemon));
        dao.close();
    }

    public void testAtualizarPokemon() {
        dao.conectar();
        Pokemon pokemonParaAtualizar = new Pokemon("Pikachu", "Elétrico", 5);
        dao.inserirPokemon(pokemonParaAtualizar);
        pokemonParaAtualizar.setNivel(10);
        assertTrue("Erro ao atualizar Pokémon", dao.atualizarPokemon(pokemonParaAtualizar));
        dao.close();
    }

    public void testExcluirPokemon() {
        dao.conectar();
        Pokemon pokemonParaExcluir = new Pokemon("Charmander", "Fogo", 8);
        dao.inserirPokemon(pokemonParaExcluir);
        int codigoPokemon = pokemonParaExcluir.getCodigo();
        assertTrue("Erro ao excluir Pokémon", dao.excluirPokemon(codigoPokemon));
        dao.close();
    }

    public void testListarPokemons() {
        dao.conectar();
        dao.inserirPokemon(new Pokemon("Bulbasaur", "Planta", 7));
        dao.inserirPokemon(new Pokemon("Squirtle", "Água", 6));
        Pokemon[] pokemons = dao.getPokemons();
        assertNotNull("Erro ao listar Pokémons", pokemons);
        assertTrue("A lista de Pokémons está vazia", pokemons.length > 0);
        dao.close();
    }

    public void testListarPokemonsFogo() {
        dao.conectar();
        dao.inserirPokemon(new Pokemon("Charizard", "Fogo", 36));
        Pokemon[] pokemonsFogo = dao.getPokemonsFogo();
        assertNotNull("Erro ao listar Pokémons do tipo Fogo", pokemonsFogo);
        assertTrue("Nenhum Pokémon do tipo Fogo encontrado", pokemonsFogo.length > 0);
        dao.close();
    }

    @Override
    protected void tearDown() throws Exception {
        dao.close();
        super.tearDown();
    }
}