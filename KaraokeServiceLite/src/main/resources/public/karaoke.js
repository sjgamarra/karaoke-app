var dirCanciones;
var currentPedido;

$(document).ready(function() {
	ejecutarSiguientePedido();
});

//ESTADOS
//EN_COLA = 1 ;
//EN_REPRODUCCION = 2 ;
//REPRODUCIDO = 3 ;
//CANCELADO = 4 ;
function ejecutarSiguientePedido(){
//	$.ajax({
//        url: "http://localhost:8080/parametro/DIRECTORIO_CANCIONES",
//        type: 'get'
//    }).then(function(parametro) {
//    	dirCanciones = parametro.valor;
    	
	    	 //Obtener pedido de BD
    	 $.ajax({
    	        url: "http://localhost:8080/pedido",
    	        async: false,
    	        type: 'get'
    	    }).then(function(pedido) {
    	    	currentPedido = pedido;
    	    	var cancion = pedido.cancion;
    	    	var rutaArchivo = "biblioteca/"+cancion.nombreArchivo.replace(".mp3","");

    	    	//llamar al método para reproducir 
    	    	set_file_prefix(rutaArchivo);
    	    	
    	    	//TODO: cambiar estado de pedido actual a EN_REPRODUCCION
    	    	currentPedido.estado = 2;
    	    	
    	    	//var test = JSON.stringify(currentPedido);
    	    	
    	    	//actualizar Pedido
    	    	actualizarPedido(currentPedido);
    	// });
    });
}

function actualizarPedido(actualizarPedido){
	$.ajax({
	    url: "http://localhost:8080/pedido",
	    type: 'put',
	    contentType : 'application/json',
	    dataType : 'json',
	    data : JSON.stringify(actualizarPedido),
	    async: false,
	    processData: false,
	    cache: false
	}).then(function() {
	});
}