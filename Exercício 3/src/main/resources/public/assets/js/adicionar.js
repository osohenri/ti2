document.addEventListener('DOMContentLoaded', function() {
    const formAdicionar = document.getElementById("formAdicionar");
    if (!formAdicionar) {
        console.error("Elemento 'formAdicionar' não encontrado!");
        return;
    }
    
    formAdicionar.addEventListener("submit", (e) => {
        e.preventDefault();
        let nome = document.getElementById("nomeAdd").value;
        let ano = document.getElementById("anoAdd").value;

        let formData = new URLSearchParams();
        formData.append("nome", nome);
        formData.append("ano", ano);

        fetch("/jogos", {
            method: "POST",
            body: formData
        })
        .then(response => response.text())
        .then(msg => {
            document.getElementById("msgAdd").innerText = msg;
            // Limpa os campos
            document.getElementById("nomeAdd").value = "";
            document.getElementById("anoAdd").value = "";
        })
        .catch(err => {
            document.getElementById("msgAdd").innerHTML = `<p class="erro">${err.message}</p>`;
        });
    });
});
