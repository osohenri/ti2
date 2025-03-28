// Script responsavel por atualizar os jogos
document.getElementById("formAtualizar").addEventListener("submit", (e) => {
    e.preventDefault();
    let id = document.getElementById("idUp").value;
    let nome = document.getElementById("nomeUp").value;
    let ano = document.getElementById("anoUp").value;

    let formData = new URLSearchParams();
    formData.append("nome", nome);
    formData.append("ano", ano);

    fetch(`/jogos/${id}`, {
        method: "PUT",
        body: formData
    })
    .then(response => response.text())
    .then(msg => {
        document.getElementById("msgUp").innerText = msg;
        document.getElementById("idUp").value = "";
        document.getElementById("nomeUp").value = "";
        document.getElementById("anoUp").value = "";
    })
    .catch(err => {
        document.getElementById("msgUp").innerHTML = `<p class="erro">${err.message}</p>`;
    });
});
