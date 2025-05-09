// Script responsavel por listar os jogos
document.getElementById("btnListar").addEventListener("click", () => {
    fetch("/jogos")
        .then(response => {
            if (!response.ok) {
                throw new Error("Falha ao obter lista de jogos");
            }
            return response.json();
        })
        .then(data => {
            let html = "<ul>";
            data.forEach(jogo => {
                html += `<li>ID: ${jogo.id} | Nome: ${jogo.nome} | Ano: ${jogo.ano}</li>`;
            });
            html += "</ul>";
            document.getElementById("listaJogos").innerHTML = html;
        })
        .catch(err => {
            document.getElementById("listaJogos").innerHTML = `<p class="erro">${err.message}</p>`;
        });
});
