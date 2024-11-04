let controladorGral;

function cargarServicioPrueba(){
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
