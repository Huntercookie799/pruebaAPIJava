let estados = [];
let ciudades = [];

function cargarEstados() {
    let select = document.getElementById("selectorEstado");
    let opciones = "<option value=''>Seleccione un estado</option>";

    estados.forEach(estado => {
        opciones += `<option value="${estado.idEstado}">${estado.nombre}</option>`;
    });

    select.innerHTML = opciones;
}

function cargarCiudades() {
    let idEstado = document.getElementById("selectorEstado").value;
    if (!idEstado) {
        document.getElementById("selectorCiudad").innerHTML = "<option value=''>Seleccione un estado primero</option>";
        return;
    }

    fetch(`http://localhost:8080/ZarapeWeb/api/ciudades/${idEstado}`)
        .then(response => response.json())
        .then(datos => {
            ciudades = datos;
            let select = document.getElementById("selectorCiudad");
            let opciones = "<option value=''>Seleccione una ciudad</option>";

            ciudades.forEach(ciudad => {
                opciones += `<option value="${ciudad.idCiudad}">${ciudad.nombre}</option>`;
            });

            select.innerHTML = opciones;
        })
        .catch(error => console.error('Error:', error));
}

fetch('http://localhost:8080/ZarapeWeb/api/estados')
    .then(response => response.json())
    .then(datos => {
        estados = datos;
        cargarEstados();
        document.getElementById("selectorEstado").addEventListener("change", cargarCiudades);
    })
    .catch(error => console.error('Error:', error));
