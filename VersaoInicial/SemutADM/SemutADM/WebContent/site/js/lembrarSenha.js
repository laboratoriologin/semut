$(document).ready(function() {	
	
	$("#btnLembrarSenha").click(function(){
		$("#btnLembrarSenha").html("Enviando...");
		
		if($("#email").val() != ""){		
			$.getJSON("LembrarSenhaSiteServlet?email="+$("#email").val(), function( data ) {
				if(data.status == "1"){ 
					alert('Foi enviado para seu e-mail uma nova senha!');
					window.location.href = "login.xhtml";
				}
				else
					alert('Não foi possível enviar uma nova senha para seu e-mail!');
				
				$("#btnLembrarSenha").html("Lembrar");
			}).fail(function( jqxhr, textStatus, error ) {
			    $("#btnLembrarSenha").html("Enviar");
			  });
		}else{
			alert('Digite seu e-mail!');
			$("#btnLembrarSenha").html("Lembrar");
		}		
	});
});