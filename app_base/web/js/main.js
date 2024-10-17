let controladorGral;

function cargarServicioPrueba(){
    alert(11);
    fetch('modulos/moduloPruebas/pruebas.html')
            .then(response => response.text())
            .then(html=>{
                document.getElementById('maincontent').innerHTML = html;
                import("../modulos/moduloPruebas/pruebas.js")
                        .then(
                            function(controller){
                                controladorGral = controller;
                            }
                        );
            });
}
