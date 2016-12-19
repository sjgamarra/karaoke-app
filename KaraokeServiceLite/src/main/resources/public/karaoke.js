var dirCanciones;
var currentPedido;
var zoom_mode = 0;
//screen variable used to make zoom
var screenCdgPlayer;

//Tiempo que se demora en mostrar el mensaje de notificación
var duracionNotificacion = 5000;

$(document).ready(function() {
	ejecutarSiguientePedido();
	//screenCdgPlayer = document.getElementById('cdg_canvas');
	screenCdgPlayer = document.getElementById('cdg_border');
	
	//al cancelar un pedido se detiene la reproduccion actual y se reproduce la siguiente
	$("#btnCancelar").click(function(){
		cdgPlayer_detenerReproduccion();
		cdgPlayer_reproducirSiguiente();
    }); 
	
	$("#cdg_border").dblclick(function(){
		cdgPlayer_fullScreen();
    });
	
	$( "#dialog-notificacionMesa" ).dialog({
//		position: { my: "center", at: "center", of: "#cdg_border"},
		autoOpen: false,
		 zIndex: 2147483647,
		 appendTo: "#cdg_border",
		 //appendTo: "#cdg_canvas",
		 modal: true,
	      show: {
	        effect: "fadeIn",
	        duration: 1000
	      },
	      hide: {
	        effect: "fadeOut",
	        duration: 1000
	      }
	 });
	
	if (document.addEventListener)
	{
	    document.addEventListener('webkitfullscreenchange', exitFullScreenHandler, false);
	    document.addEventListener('mozfullscreenchange', exitFullScreenHandler, false);
	    document.addEventListener('fullscreenchange', exitFullScreenHandler, false);
	    document.addEventListener('MSFullscreenChange', exitFullScreenHandler, false);
	}
	
});

//ESTADOS
//EN_COLA = 1 ;
//EN_REPRODUCCION = 2 ;
//REPRODUCIDO = 3 ;
//CANCELADO = 4 ;
function ejecutarSiguientePedido(){
    	
    	 //Obtener pedido de BD
    	 $.ajax({
    	        //url: "http://localhost:8080/pedido",
    		 	url: "/pedido",
    	        async: false,
    	        type: 'get'
    	    }).then(function(pedido) {
    	    	currentPedido = pedido;
    	    	var cancion = pedido.cancion;
    	    	var rutaArchivo = "biblioteca/"+cancion.nombreArchivo.replace(".mp3","");
    	    	//llamar al método para reproducir 
    	    	set_file_prefix(rutaArchivo);
     	    	currentPedido.estado = 2;
    	    	//actualizar Pedido
    	    	actualizarPedido(currentPedido);
    	    	if(pedido.dispositivoId)
    	    	{
//    	    		$( "#notificacionMesa" ).text( pedido.dispositivoId );
//    	    		$( "#notificacionMesa" ).show().delay(5000).fadeOut();
    	    		$( "#notificacionMesa" ).text( pedido.dispositivoId );
    	    		//$( "#dialog-notificacionMesa" ).dialog( "open" );
    	    		//$( "#dialog-notificacionMesa" ).dialog("open").delay(9000).dialog( "close" );
    	    		$( "#dialog-notificacionMesa" ).dialog("open");
    	    		setTimeout(function(){
    	    			$( "#dialog-notificacionMesa" ).dialog("close");
    	    		}, duracionNotificacion);
    	    	}
    	    	
    	// });
    });
}

function actualizarPedido(actualizarPedido){
	$.ajax({
	    //url: "http://localhost:8080/pedido",
		url: "/pedido",
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

//detener cancion actualmente reproducida
function cdgPlayer_detenerReproduccion(){
	$.each($('audio'), function () {
	    this.pause();
	    this.currentTime = 0;
	});
}

function enableFullScreen(){
	 if(screenCdgPlayer.requestFullscreen) {
 		screenCdgPlayer.requestFullscreen();
 	  } else if(screenCdgPlayer.mozRequestFullScreen) {
 		  screenCdgPlayer.mozRequestFullScreen();
 	  } else if(screenCdgPlayer.webkitRequestFullscreen) {
 		  screenCdgPlayer.webkitRequestFullscreen();
 	  } else if(screenCdgPlayer.msRequestFullscreen) {
 		  screenCdgPlayer.msRequestFullscreen();
 	  }
}

function disableFullScreen(){
	if(document.exitFullscreen) {
	    document.exitFullscreen();
	  } else if(document.mozCancelFullScreen) {
	    document.mozCancelFullScreen();
	  } else if(document.webkitExitFullscreen) {
	    document.webkitExitFullscreen();
	  } else if (document.msExitFullscreen) {
		  document.msExitFullscreen();
	  }
}

function isFullScreenEnabled(){
	var isInFullScreen = document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement;
	return isInFullScreen;
}

function cdgPlayer_fullScreen(){
	if(!isFullScreenEnabled())
	{
//		$("#cdg_audio").hide();
//    	$("#btnCancelar").hide();
//    	$("#cdg_status").hide();
//    	$("#cdg_audio").hide();
		enableFullScreen();
	}else{
//		$("#cdg_audio").show();
//    	$("#btnCancelar").show();
//    	$("#cdg_status").show();
//    	$("#cdg_audio").show();
    	disableFullScreen();
	}
}

function exitFullScreenHandler()
{
    if (screenCdgPlayer.webkitIsFullScreen || screenCdgPlayer.mozFullScreen || screenCdgPlayer.msFullscreenElement !== null)
    {
//	    $("#cdg_audio").show();
//	   	$("#btnCancelar").show();
//	   	$("#cdg_status").show();
//	   	$("#cdg_audio").show();
	   	toggle_4x_mode();
    }else{
    	toggle_4x_mode();
    }
    	
}


//var zoom_mode = 0;
function toggle_4x_mode()
{
	var cnvs = document.getElementById("cdg_canvas");
	var brdr = document.getElementById("cdg_border");
	brdr.style.width  = (isFullScreenEnabled()) ? 1296 + "px" : 324 + "px";
	cnvs.style.width  = (isFullScreenEnabled()) ? 1152 + "px" : 288 + "px";
	brdr.style.height = (isFullScreenEnabled()) ? 864 + "px" : 216 + "px";
	cnvs.style.height = (isFullScreenEnabled()) ? 768 + "px" : 192 + "px";
};