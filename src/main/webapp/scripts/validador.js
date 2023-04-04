

function validar() {
    let nome = frmContato.nome.value;
    let fone = frmContato.fone.value;

    if (nome === "") {
        alert("Preencha o campo Nome");
        frmContato.nome.focus()
        return false;
    }

    if (fone === "") {
        alert("Preencha o campo Fone");
        frmContato.fone.focus()
        return false;
    }

    document.forms["frmContato"].submit()

}