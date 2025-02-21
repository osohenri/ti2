package Teste.teste;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DAO pokemonDAO = new DAO();
        int opcao;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Listar");
            System.out.println("2. Inserir");
            System.out.println("3. Excluir");
            System.out.println("4. Atualizar");
            System.out.println("5. Sair");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: 
                    System.out.println("Lista de Pokémons:");
                    Pokemon[] pokemons = pokemonDAO.getPokemons();
                    if (pokemons != null) {
                        for (Pokemon p : pokemons) {
                            System.out.println(p);
                        }
                    } else {
                        System.out.println("Nenhum Pokémon encontrado.");
                    }
                    break;

                case 2: 
                    System.out.print("Digite o nome do Pokémon: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o tipo do Pokémon: ");
                    String tipo = scanner.nextLine();
                    System.out.print("Digite o nível do Pokémon: ");
                    int nivel = scanner.nextInt();
                    scanner.nextLine();
                    Pokemon novoPokemon = new Pokemon(nome, tipo, nivel);
                    if (pokemonDAO.inserirPokemon(novoPokemon)) {
                        System.out.println("Pokémon inserido com sucesso!");
                    } else {
                        System.out.println("Erro ao inserir Pokémon.");
                    }
                    break;

                case 3:
                    System.out.print("Digite o código do Pokémon para excluir: ");
                    int codigoExcluir = scanner.nextInt();
                    if (pokemonDAO.excluirPokemon(codigoExcluir)) {
                        System.out.println("Pokémon excluído com sucesso!");
                    } else {
                        System.out.println("Erro ao excluir Pokémon.");
                    }
                    break;

                case 4: 
                    System.out.print("Digite o código do Pokémon para atualizar: ");
                    int codigoAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Digite o novo tipo: ");
                    String novoTipo = scanner.nextLine();
                    System.out.print("Digite o novo nível: ");
                    int novoNivel = scanner.nextInt();
                    scanner.nextLine();
                    Pokemon pokemonAtualizado = new Pokemon(novoNome, novoTipo, novoNivel);
                    pokemonAtualizado.setCodigo(codigoAtualizar);
                    if (pokemonDAO.atualizarPokemon(pokemonAtualizado)) {
                        System.out.println("Pokémon atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar Pokémon.");
                    }
                    break;

                case 5: 
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 5);

        scanner.close();
    }
}