let pruebaBase = [];
function loadTable(){
    let table = document.getElementById("renglones");
    let renglon = "";
    
    pruebaBase.forEach(registro =>{
        renglon += "<tr><td>" + registro.idBase + "</td><td>" 
                              + registro.nombre + "</td><td>"
                              + registro.edad + "</td></tr>"; 
    });
    
    table.innerHTML = renglon;
}

fetch('http://localhost:8080/app_base/api/prueba/getAll')
        .then(response => response.json())
        .then(
            datos =>{
                console.log(datos);
                pruebaBase = datos;
                loadTable();
            }
        );