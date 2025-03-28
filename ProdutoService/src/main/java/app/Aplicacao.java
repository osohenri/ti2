package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.PokemonService;
import model.Pokemon;
import java.util.List;

public class Aplicacao {
    public static void main(String[] args) {
        port(4567);

        staticFiles.location("/public");

        PokemonService pokemonService = new PokemonService();
        Gson gson = new Gson();

        get("/pokemons", (req, res) -> {
            List<Pokemon> pokemons = pokemonService.listarTodosPokemons();
            res.type("application/json");
            return pokemons;
        }, gson::toJson);

        get("/pokemons/:codigo", (req, res) -> {
            int codigo = Integer.parseInt(req.params("codigo"));
            Pokemon pokemon = pokemonService.buscarPokemonPorCodigo(codigo);
            if(pokemon == null) {
                res.status(404);
                return "Pokemon não encontrado";
            }
            res.type("application/json");
            return pokemon;
        }, gson::toJson);

        post("/pokemons", (req, res) -> {
            String nome = req.queryParams("nome");
            String tipo = req.queryParams("tipo");
            int nivel = Integer.parseInt(req.queryParams("nivel"));
            int codigoGerado = pokemonService.inserirPokemon(nome, tipo, nivel);
            res.status(201);
            return "Pokemon inserido com código " + codigoGerado;
        });

        put("/pokemons/:codigo", (req, res) -> {
            int codigo = Integer.parseInt(req.params("codigo"));
            String nome = req.queryParams("nome");
            String tipo = req.queryParams("tipo");
            int nivel = Integer.parseInt(req.queryParams("nivel"));
            boolean atualizado = pokemonService.atualizarPokemon(codigo, nome, tipo, nivel);
            if(atualizado) {
                return "Pokemon atualizado com sucesso!";
            } else {
                res.status(404);
                return "Pokemon não encontrado!";
            }
        });

        delete("/pokemons/:codigo", (req, res) -> {
            int codigo = Integer.parseInt(req.params("codigo"));
            boolean deletado = pokemonService.deletarPokemon(codigo);
            if(deletado) {
                return "Pokemon removido com sucesso!";
            } else {
                res.status(404);
                return "Pokemon não encontrado!";
            }
        });
    }
}