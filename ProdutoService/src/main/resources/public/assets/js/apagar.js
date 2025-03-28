// Script responsavel por apagar os jogos
document.getElementById("formExcluir").addEventListener("submit", (e) => {
    e.preventDefault();
    let id = document.getElementById("idDel").value;
    fetch(`/jogos/${id}`, {
        method: "DELETE"
    })
    .then(response => response.text())
    .then(msg => {
        document.getElementById("msgDel").innerText = msg;
        document.getElementById("idDel").value = "";
    })
    .catch(err => {
        document.getElementById("msgDel").innerHTML = `<p class="erro">${err.message}</p>`;
    });
});
