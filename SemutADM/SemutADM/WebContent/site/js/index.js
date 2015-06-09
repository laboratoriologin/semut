var map = null;
var bounds = new google.maps.LatLngBounds();
var infoWindow = new google.maps.InfoWindow();
var listaMarkers = new Array();
var listaPolygon = new Array();
var listaMarkersCenterPolygon = new Array();
var iconBase = "../site/imagens/";
var dataInicial = "";
var dataFinal = "";
var lastCallFunction = null;
var lastArg1 = null;
var dayName = new Array ("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado");
var monName = new Array ("janeiro", "fevereiro", "março", "abril", "maio", "junho", "agosto", "outubro", "novembro", "dezembro");
var grupo = 1;
var tiposOcorrencias = new Array();
var firstTime = false;

google.maps.event.addDomListener(window, 'load', initialize);

google.maps.Map.prototype.clearMarkers = function() {
	
	infoWindow.close();
	
	for ( var i = 0; i < listaMarkers.length; i++) {
		listaMarkers[i].setMap(null);
	}
	listaMarkers = new Array();
	bounds = new google.maps.LatLngBounds();
};

google.maps.Map.prototype.clearPolygon = function() {

	infoWindow.close();
	
	for ( var i = 0; i < listaPolygon.length; i++) {
		listaPolygon[i].setMap(null);
	}
	listaPolygon = new Array();	
};

google.maps.Map.prototype.clearMarkersCenterPolygon = function() {
	
	infoWindow.close();

	for ( var i = 0; i < listaMarkersCenterPolygon.length; i++) {
		listaMarkersCenterPolygon[i].setMap(null);
	}
	listaMarkersCenterPolygon = new Array();
};

function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
 }

$(document).ready(function() {	
	
	$("#usuario").html("Olá, "+$.cookie("usuario")+"!");
	displayLoad(false);	
	time();
	$.pnotify.defaults.styling = "jqueryui";
	$.pnotify.defaults.history = false;
	$.pnotify.defaults.delay = 500;
	loadSelectRegiao();
	loadTiposOcorrencias();

	$('#btnGrafico').click(function(){
		
		window.location.href="grafico.html?data_inicial="+$("#txtStartDate").val()+"&data_final="+$("#txtEndDate").val();
	});
	
	$('#botaoPendenteA').addClass('botaoPendenteAActive');
	
	$('#sair').click(function(){
		if (confirm("Deseja sair?")){
			$.removeCookie("usuario");
			$.removeCookie("JSESSIONID");
			$.get("sair" ,function(data){
				  window.location.href="login.xhtml";
			});
		}
	});
	
	$("#reload").click(function(){
		window.location.href="index.html?data_inicial="+$("#txtStartDate").val()+"&data_final="+$("#txtEndDate").val();
	});
	
	
		
	effectButton('botaoPendenteA');
	effectButton('botaoAceitaA');
	effectButton('botaoResolvidaA');
	effectButton('botaoRecusadaA');
	effectButton('botaoTodasCadUsuarioA');
	effectButton('botaoTodaA');
	effectButton('botaoStatusBairroA');
	
	eventButton("botaoPendenteA", getOcorrencias, 3, null, null);
	eventButton("botaoAceitaA", getOcorrencias, 1, null, null);
	eventButton("botaoResolvidaA", getOcorrencias, 4, null, null);
	eventButton("botaoRecusadaA", getOcorrencias, 2, null, null);
	eventButton("botaoTodasCadUsuarioA", getOcorrenciasPorUsuario, null, true, null);
	eventButton("botaoTodaA", getOcorrencias, null, null, null);
	eventButton("botaoStatusBairroA", getQtdOcorrenciaByRegiao, null, null, true);
	
	$("#menuDrawingPolygon").click(function(){
		if($(this).attr("drawing") == "true"){
			$(this).attr("drawing", false);
			if(getFnName(lastCallFunction) == "getQtdOcorrenciaByRegiao"){
				map.clearPolygon();
				map.clearMarkers();
			}else{
				map.clearPolygon();
				map.clearMarkersCenterPolygon();				
			}
			$("#menuDrawingPolygon img").attr("src", "imagens/off_polygon.png");
		}else{
			$(this).attr("drawing", true);
			displayLoad(true);
			if(getFnName(lastCallFunction) == "getQtdOcorrenciaByRegiao"){
				callFunction(getQtdOcorrenciaByRegiao,null);
			}else{
				drawingPolygons();				
			}
			$("#menuDrawingPolygon img").attr("src", "imagens/on_polygon.png");
		}
		
		displayLoad(false);
	});
	
	/*	
	$("#botaoTransalvadorA").click(function(){
		displayLoad(true);
		clearButtonGrupo();
		$('#botaoTransalvadorA').attr('select', "true");
		grupo = 1;	
		loadTiposOcorrencias();
		callFunction(lastCallFunction, lastArg1);		
	});	
	
	$("#botaoSucomA").click(function(){
		displayLoad(true);
		clearButtonGrupo();
		$('#botaoSucomA').attr('select', "true");
		grupo = 2;		
		loadTiposOcorrencias();
		callFunction(lastCallFunction, lastArg1);
	});	
	*/
	
	$("#selecaoBairro").change(function(){
		displayLoad(true);
		callFunction(lastCallFunction, lastArg1);
		if(getFnName(lastCallFunction) == "getOcorrenciasPorUsuario"){
			changeLegend(null, true, null);
		}
		else if (getFnName(lastCallFunction) == "getQtdOcorrenciaByRegiao"){
			changeLegend(null, null, true);
		}
		else{
			changeLegend(lastArg1, null, null);
		}
		
	});	
	
	$("#selecaoTipoOcorrencia").change(function(){
		displayLoad(true);
		callFunction(lastCallFunction, lastArg1);
		if(getFnName(lastCallFunction) == "getOcorrenciasPorUsuario"){
			changeLegend(null, true, null);
		}
		else if (getFnName(lastCallFunction) == "getQtdOcorrenciaByRegiao"){
			changeLegend(null, null, true);
		}
		else{
			changeLegend(lastArg1, null, null);
		}
		
	});	

	$("#txtStartDate").keyup(function(event) {		
		if (event.keyCode == 13) {
			displayLoad(true);
			dataInicial = $(this).val();
			if (dataInicial == null || dataInicial == "") {
				dataInicial = "01/01/2013";
			}
			callFunction(lastCallFunction, lastArg1);
			
			$("#txtStartDate").datepicker("hide");
		}
	});

	$("#txtEndDate").keyup(function(event) {		
		if (event.keyCode == 13) {
			displayLoad(true);
			dataFinal = $(this).val();	
			callFunction(lastCallFunction, lastArg1);
			
			$("#txtEndDate").datepicker("hide");
		}
	});
	
	var $datepickerInicial = $("#txtStartDate").datepicker({
		showOtherMonths: true,
		selectOtherMonths: true,
		numberOfMonths: 2,
		dateFormat : 'dd/mm/yy',
		onSelect : function() {
			displayLoad(true);
			dataInicial = $(this).val();
			if (dataInicial == null || dataInicial == "") {
				dataInicial = "01/01/2013";
			}
			callFunction(lastCallFunction, lastArg1);
			
			$("#txtStartDate").datepicker("hide");
		},
		onClose: function( selectedDate ) {
			$( "#txtEndDate" ).datepicker( "option", "minDate", selectedDate );
        }
	});
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	
	$datepickerInicial.datepicker('setDate', firstDay);

	var $datepickerFinal = $("#txtEndDate").datepicker({
		showOtherMonths: true,
		selectOtherMonths: true,
		numberOfMonths: 2,
		dateFormat : 'dd/mm/yy',
		onSelect : function() {
			displayLoad(true);
			dataFinal = $(this).val();
			callFunction(lastCallFunction, lastArg1);
			
			$("#txtEndDate").datepicker("hide");
		},
		onClose: function( selectedDate ) {
			$( "#txtStartDate" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	
	$datepickerFinal.datepicker('setDate', new Date());
	
	if (GetURLParameter("data_inicial") != null){
	$("#txtStartDate").val(GetURLParameter("data_inicial"));
	dataInicial = GetURLParameter("data_inicial");
	}
	if (GetURLParameter("data_final") != null){
	$("#txtEndDate").val(GetURLParameter("data_final"));
	dataFinal = GetURLParameter("data_final");
	callFunction(getOcorrencias,3);
	}

});

function shorError(status, msg){
	
	var stack_bar_top = {"dir1": "down", "dir2": "right", "push": "top", "spacing1": 0, "spacing2": 0};
	
	if(status == 500)
		msg = "Erro nos servidores do sistema, tente mais tarde ou entre em contato com o Administrador do sistema.";
	else if(status == 400)
		msg = "Erro na URL do sistema, entre em contato com o adminstrador do sistema.";
	else if(status == 0)
		msg = "Servidores fora do ar, entre em contato com o adminstrador do sistema.";
	else if(status==600) {
		alert('Sua sessão expirou, faça o login novamente.');
		window.location.href='login.xhtml';
	}
    $.pnotify({
        title: 'Erro no sistema',
        text: msg,
        addclass: "stack-bar-top",
        cornerclass: "",
        width: "95%",
        stack: stack_bar_top,
        type: "error"
    });
}

function openInfoWindow(marker, ocorrencia){

	
	ocorrencia = ocorrencia.Ocorrencia;
	
	if(!ocorrencia.descricao)
		ocorrencia.descricao="";
	if(!ocorrencia.id)
		ocorrencia.id="";
	if(!ocorrencia.nomeTipoOcorrencia)
		ocorrencia.nomeTipoOcorrencia="";
	if(!ocorrencia.nomeUsuario)
		ocorrencia.nomeUsuario="";
	if(!ocorrencia.situacao)
		ocorrencia.situacao="";

	var info = '';
	var color = '#6D6E70';
	
	info += '<div style="background : ' + color + '; text-align: left; width: 310px; min-width:310px;">';
	info += '<div style="color : #FFFFFF; font-size: 21px; padding-top: 10px; padding-left: 10px;">Código: '  + ocorrencia.id + ' <a href="#" onclick="fullWindow(\'../pages/dashboard.xhtml?ocorrencia='+ocorrencia.id+'\',\'ocorrencia\',1);"><img style="border:none;" src="imagens/icone_adm_ocorrencias.png"/></a></div>';
	info += '<div style="color : #FFFFFF; font-size: 13px; padding-left: 10px;">Descrição: '  + ocorrencia.descricao + '</div>';
	info += '<div style="color : #FFFFFF; font-size: 13px; padding-left: 10px;">Tipo: '  + ocorrencia.nomeTipoOcorrencia + '</div>';
	info += '<div style="color : #FFFFFF; font-size: 13px; padding-left: 10px;">Usuário: '  + ocorrencia.nomeUsuario + '</div>';
	info += '<div style="color : #FFFFFF; font-size: 13px; padding-left: 10px;">Data cadastro: '  + ocorrencia.dataFormatada + '</div>';
	info += '<div style="color : #FFFFFF; font-size: 13px; padding-bottom: 10px; padding-left: 10px;">Status: '  + ocorrencia.situacao + '</div>';
	
	if(ocorrencia.imagem){
		
		var img = new Image();
		img.onload = function() {
			  
			var width;
			var heigh; 
			if(this.width >= this.height){
				width = "305px";
				heigh = "auto";
			}else{
				width = "auto";
				heigh = "250px";				
			}
			  
			info += '<div style="text-align: center; padding-bottom: 10px;"><img id="imgOcorrenciaInfoWindows" style="width:' + width + '; height: ' + heigh + ';" src="http://177.1.212.50:9004/arquivos_semut/' + ocorrencia.imagem + '" alt="'  + ocorrencia.situacao + '"/></div>';
			  
			info += '</div>';
				
			infoWindow.setContent(info);
			infoWindow.open(map, marker);
			
			if (firstTime == false)
			{
				setTimeout( function () {infoWindow.close(); infoWindow.open(map, marker);}, 1000);
				firstTime = true;
			}  
		};		
		img.src = "http://177.1.212.50:9004/arquivos_semut/" + ocorrencia.imagem;
		
	}else{
		info += '</div>';
		
		infoWindow.setContent(info);
		infoWindow.open(map, marker);
		
		if (firstTime == false)
		{
			setTimeout( function () {infoWindow.close(); infoWindow.open(map, marker);}, 1000);
			firstTime = true;
		}
	}	
}

function openInfoWindowStatus(obj, regiao, latLng){
	
	var info = '';
	var color = '';
	
	info += '<div style="text-align: left; width: 230px;">';
	info += '<div style="color : #FFFFFF; background: #6D6E70; font-size: 20px; font-weight: bold; padding-bottom: 10px; padding-left: 10px; padding-top: 10px;">' + regiao.nome + '</div>';
	info += '<div style="color : #FFFFFF; background: #A53736; overflow: hidden; "><div style="float: left; padding-left: 10px; padding-bottom: 2px; padding-top: 2px;"><img style="width: 21px; height: 21px;" src="' + iconBase + 'map_pendentesStatusBairro.png" title="' + regiao.nome  + '" atl="' + regiao.nome  + '"></div> <div style="font-size: 16px; font-weight: bold; float: right; padding-right: 10px; padding-bottom: 2px; padding-top: 2px;">' + regiao.QTD_PENDENTE + '</div> </div>';
	info += '<div style="color : #FFFFFF; background: #00A14B; overflow: hidden; "><div style="float: left; padding-left: 10px; padding-bottom: 2px; padding-top: 2px;"><img style="width: 21px; height: 21px;" src="' + iconBase + 'map_aceitasStatusBairro.png" title="' + regiao.nome  + '" atl="' + regiao.nome  + '"></div> <div style="font-size: 16px; font-weight: bold; float: right; padding-right: 10px; padding-bottom: 2px; padding-top: 2px;">' + regiao.QTD_ACEITA + '</div> </div>';
	info += '<div style="color : #FFFFFF; background: #33558C; overflow: hidden; "><div style="float: left; padding-left: 10px; padding-bottom: 2px; padding-top: 2px;"><img style="width: 21px; height: 21px;" src="' + iconBase + 'map_resolvidasStatusBairro.png" title="' + regiao.nome  + '" atl="' + regiao.nome  + '"></div> <div style="font-size: 16px; font-weight: bold; float: right; padding-right: 10px; padding-bottom: 2px; padding-top: 2px;">' + regiao.QTD_RESOLVIDA + '</div> </div>';
	info += '<div style="color : #FFFFFF; background: #6D6E70; overflow: hidden; "><div style="float: left; padding-left: 10px; padding-bottom: 2px; padding-top: 2px;"><img style="width: 21px; height: 21px;" src="' + iconBase + 'map_recusadasStatusBairro.png" title="' + regiao.nome  + '" atl="' + regiao.nome  + '"></div> <div style="font-size: 16px; font-weight: bold; float: right; padding-right: 10px; padding-bottom: 2px; padding-top: 2px;">' + regiao.QTD_RECUSADA + '</div> </div>';
	info += '</div>';
	
    infoWindow.setContent(info);
    
    if(latLng != null){
    	infoWindow.setPosition(latLng);
    	infoWindow.open(map);
    	
    	if (firstTime == false)
		{
    		setTimeout( function () {infoWindow.close(); infoWindow.open(map);}, 1000);
			firstTime = true;
		}
    	
    }else{
    	infoWindow.open(map, obj);
    	
    	if (firstTime == false)
		{
    		setTimeout( function () {infoWindow.close(); infoWindow.open(map, obj)}, 1000);
			firstTime = true;
		}
    }    	
    
}

function makePolygon(polyCoords, polyLabel) {

	var poly = new google.maps.Polygon({
       paths: polyCoords,
       strokeColor: "#FF0000",
       strokeOpacity: 0.30,
       strokeWeight: 2,
       fillColor: "#FF0000",
       fillOpacity: 0.10,
       map: map,
       tooltip: polyLabel
   });
	
	var tooltip = new Tooltip({map: map}, poly);
    tooltip.bindTo("text", poly, "tooltip");
    
    google.maps.event.addListener(poly, 'mousemove', function(event) {
        tooltip.addTip();
        tooltip.getPos2(event.latLng);
    });
	
    google.maps.event.addListener(poly, 'mouseout', function(event) {
        tooltip.removeTip();
    });
    
    listaPolygon.push(poly);
    
    return poly;
}

function drawingPolygons(){
	if (map != null){
	map.clearPolygon();	
	map.clearMarkersCenterPolygon();
	
	if($("#menuDrawingPolygon").attr("drawing") == "true"){	
		$.getJSON("RegioesServlet?id=" + $("#selecaoBairro").val(), function( data ) {
			for(var i = 0; i < data.regioes.length ; i++)
			{
				var regiao = data.regioes[i];
				var polgynCoord = new Array();
				                      
				for(var j = 0; j < regiao.coordenadas.length ; j++)
				{	
					var coordenadas = regiao.coordenadas[j];
					polgynCoord[j] =  new google.maps.LatLng(coordenadas.latitude, coordenadas.longitude);						
				}		
				makePolygon(polgynCoord, regiao.nome);
			}			
		}).fail(function( jqxhr, textStatus, error ) {
		    shorError(jqxhr.status, jqxhr.statusText);
		    displayLoad(false);
		  });
	}
}
}

function loadSelectRegiao(){
	$.getJSON("RegioesServlet", function( data ) {
		var $select = $('#selecaoBairro');  
		$select.append('<option selected value="0">Todas</option>');
		
		$.each(data.regioes,function(key, value) 
		{
			$select.append('<option value=' + value.id + '>' + value.nome + '</option>');
		});
		
	}).fail(function( jqxhr, textStatus, error ) {
	    shorError(jqxhr.status, jqxhr.statusText);
	  });
}

function loadTiposOcorrencias() {
	
	$.ajax({
		  url: "tipos_ocorrencias",
		  dataType: 'json',
		  async: false,
		  success: function(data) {
			  tiposOcorrencias = new Array();

				for(linha in data.tipoOcorrencias) {
					if(data.tipoOcorrencias[linha].grupoOcorrencia==1 && grupo == 1) {
						tiposOcorrencias.push(data.tipoOcorrencias[linha]);
					} else if (data.tipoOcorrencias[linha].grupoOcorrencia==2 && grupo == 2) {
						tiposOcorrencias.push(data.tipoOcorrencias[linha]);
					}
				}
				
				var $select = $('#selecaoTipoOcorrencia');  
				
				$select.empty();
				
				$select.append('<option selected value="0">Todas as ocorrências</option>');
				
				for(linha in tiposOcorrencias)  
				{
					$select.append('<option value=' + tiposOcorrencias[linha].id + '>' + tiposOcorrencias[linha].nome + '</option>');
				}
		  }
		}).fail(function( jqxhr, textStatus, error ) {
		    shorError(jqxhr.status, jqxhr.statusText);
		  });
}

function pad(n) { return ("0" + n).slice(-2); }

function time()
{
	now = new Date();
	$("#localData").html("" + dayName[now.getDay() ] + ", " + now.getDate () + " de " + monName [now.getMonth() ]   +  " de "  +     now.getFullYear () + ". " + pad(now.getHours()) + ":" + pad(now.getMinutes()) + ":" + pad(now.getSeconds()));
	setTimeout('time()',500);
}

function clearButton(){
	
	$('#botaoPendenteA').attr('class', "botaoPendenteA");
	$('#botaoAceitaA').attr('class', "botaoAceitaA");
	$('#botaoResolvidaA').attr('class', "botaoResolvidaA");
	$('#botaoRecusadaA').attr('class', "botaoRecusadaA");
	$('#botaoTodasCadUsuarioA').attr('class', "botaoTodasCadUsuarioA");
	$('#botaoTodaA').attr('class', "botaoTodaA");
	$('#botaoStatusBairroA').attr('class', "botaoStatusBairroA");
	
	$('#botaoPendenteA').attr('select', "false");
	$('#botaoAceitaA').attr('select', "false");
	$('#botaoResolvidaA').attr('select', "false");
	$('#botaoRecusadaA').attr('select', "false");
	$('#botaoTodasCadUsuarioA').attr('select', "false");
	$('#botaoTodaA').attr('select', "false");
	$('#botaoStatusBairroA').attr('select', "false");
}

function clearButtonGrupo(){
	$('#botaoSucomA').attr('select', "false");
	$('#botaoTransalvadorA').attr('select', "true");
}

function changeLegend(situacao, usuario, bairro) {
	$('#legendaConteudo').empty();
	$('#numeroPorRegiaoAceita').remove();
	$('#numeroPorRegiaoRecusada').remove();
	$('#numeroPorRegiaoPendente').remove();
	$('#numeroPorRegiaoResolvida').remove();

	if (bairro != null) {
		$('#legendaConteudo').append(' <div id="imagemLegendaOpcaoPendentes"><img src="imagens/map_pendentesStatusBairro.png" alt="Ocorências Pendentes" width="30" height="30" /></div>');
		$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoPendentes">Pendentes</div>');
		$('#legendaConteudo').append('<div id="imagemLegendaOpcaoAceitas"><img src="imagens/map_aceitasStatusBairro.png" alt="Ocorências Aceitas" width="30" height="30" /></div>');
		$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoAceitas">Aceitas</div>');
		$('#legendaConteudo').append(' <div id="imagemLegendaOpcaoResolvidas"><img src="imagens/map_resolvidasStatusBairro.png" alt="Ocorências Resolvidas" width="30" height="30" /></div>');
		$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoResolvidas">Resolvidas</div>');
		$('#legendaConteudo').append(' <div id="imagemLegendaOpcaoRecusadas"><img src="imagens/map_recusadasStatusBairro.png" alt="Ocorências Recusadas" width="30" height="30" /></div>');
		$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoRecusadas">Recusadas</div>');

	} else if (usuario != null) {

		$('#legendaConteudo').append('<div id="imagemLegendaUsuario"><img src="imagens/map_usuario.png" width="30" height="30" /></div>');
		$('#legendaConteudo').append('<div class="textoLegendaUsuario" id="textoLegendaUsuario">Usuário Cidadão</div>');
		$('#legendaConteudo').append('<div class="numeroPorRegiaoUsuario" id="numeroPorRegiaoUsuario"></div>');
		$('#legendaConteudo').append('<div id="imagemLegendaSuper"><img src="imagens/map_usuario_semut.png" width="30" height="30" /></div>');
		$('#legendaConteudo').append('<div class="textoLegendaSuper" id="textoLOpcao2">Superintendência</div>');
		$('#legendaConteudo').append('<div class="numeroPorRegiaoSuper" id="numeroPorRegiaoSuper"></div>');

	} else if (situacao != null) {
		switch (situacao) {
		case 1:
			$('#legendaConteudo').append('<div id="imagemLegendaOpcaoPendentes"><img src="imagens/map_aceitas.png" alt="Ocorências Aceitas" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoPendentes">Aceitas</div><div class="aceita" id="numeroPorRegiaoAceita"></div>');
			break;
		case 2:
			$('#legendaConteudo').append('<div id="imagemLegendaOpcaoPendentes"><img src="imagens/map_recusadas.png" alt="Ocorências Recusadas" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoPendentes">Recusadas</div><div class="recusada" id="numeroPorRegiaoRecusada" ></div>');
			break;
		case 3:
			$('#legendaConteudo').append('<div id="imagemLegendaOpcaoPendentes"><img src="imagens/map_pendentes.png" alt="Ocorências Pendentes" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoPendentes">Pendentes</div><div class="pendente" id="numeroPorRegiaoPendente"></div>');
			break;
		case 4:
			$('#legendaConteudo').append('<div id="imagemLegendaOpcaoPendentes"><img src="imagens/map_resolvidas.png" alt="Ocorências Resolvidas" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoPendentes">Resolvidas</div><div class="resolvida" id="numeroPorRegiaoResolvida"></div>');
			break;
		default:			
			break;
		}
	}
	else {
			$('#legendaConteudo').append(' <div id="imagemLegendaOpcaoPendentes"><img src="imagens/map_pendentes.png" alt="Ocorências Pendentes" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoPendentes">Pendentes</div>');
			$('#legendaConteudo').append('<div class="numeroPorRegiaoPendente" id="numeroPorRegiaoPendente"></div>');
			
			$('#legendaConteudo').append('<div id="imagemLegendaOpcaoAceitas"><img src="imagens/map_aceitas.png" alt="Ocorências Aceitas" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoAceitas">Aceitas</div>');
			$('#legendaConteudo').append('<div class="numeroPorRegiaoAceita" id="numeroPorRegiaoAceita"></div>');
			
			$('#legendaConteudo').append('<div id="imagemLegendaOpcaoResolvidas"><img src="imagens/map_resolvidas.png" alt="Ocorências Resolvidas" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoResolvidas">Resolvidas</div>');
			$('#legendaConteudo').append('<div class="numeroPorRegiaoResolvida" id="numeroPorRegiaoResolvida"></div>');
			
			$('#legendaConteudo').append(' <div id="imagemLegendaOpcaoRecusadas"><img src="imagens/map_recusadas.png" alt="Ocorências Recusadas" width="30" height="30" /></div>');
			$('#legendaConteudo').append('<div class="textoLegendaMapa" id="textoLegendaOpcaoRecusadas">Recusadas</div>');
			$('#legendaConteudo').append('<div class="numeroPorRegiaoRecusada" id="numeroPorRegiaoRecusada" ></div>');
	}
}

function initialize() {
	
	var mapOptions = {
		center : new google.maps.LatLng(-12.97102012429756,-38.500943183898926),
		zoom : 14,
		streetViewControl: false		
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions); 
	drawingPolygons();
	callFunction(getOcorrencias,3);
}

function getOcorrencias (situacao) {
	
	var urlSituacao = "";
	var urlRegiao = "";
	var urlTipoOcorrencia = "";
	
	if(situacao != null)
		urlSituacao = "&situacao="+situacao;
	
	if($("#selecaoBairro").val() != null)
		urlRegiao = "&regiao="+$("#selecaoBairro").val();
	
	if($("#selecaoTipoOcorrencia").val() != null)
		urlTipoOcorrencia = "&categoria="+$("#selecaoTipoOcorrencia").val();
	
	$.getJSON("OcorrenciasCruasServlet?data_inicial="+dataInicial+"&data_final="+dataFinal+urlSituacao+"&grupo="+grupo+urlRegiao+urlTipoOcorrencia, function( data ) {
		var latLng;
		var marker;
		var ocorrencia;
		
		map.clearMarkers();	
		
		if(data.OcorrenciasCruas != null){
			for(var i = 0; i < data.OcorrenciasCruas.length ; i++)
			{
				ocorrencia = data.OcorrenciasCruas[i];
				
				latLng = new google.maps.LatLng(ocorrencia.latitude, ocorrencia.longitude);
				
				bounds.extend(latLng);
				
				marker  = new google.maps.Marker({
					position: latLng,
					map: map,
					title: ocorrencia.tipoOcorrencia,
					icon: iconBase + ocorrencia.icone
				});
				
				listaMarkers[i] = marker;
				
		        google.maps.event.addListener(marker, 'click', (function(marker, ocorrencia) {
		            return function() {
		            	$.getJSON("OcorrenciaIndividualServlet?id=" + ocorrencia.id, function( ocorrenciaCompleta ) {
		            		openInfoWindow(marker, ocorrenciaCompleta);
		            	}).fail(function( jqxhr, textStatus, error ) {
		            	    shorError(jqxhr.status, jqxhr.statusText);
		            	  });	            	
		            };
		        })(marker, ocorrencia));
		        map.fitBounds(bounds);
			}
		}
		
		displayLoad(false);
	}).fail(function( jqxhr, textStatus, error ) {
	    shorError(jqxhr.status, jqxhr.statusText);
	    displayLoad(false);
	  });
	
	atualizar();
	atualizarPorRegiao();
}
	
function getOcorrenciasPorUsuario () {
	
	var urlRegiao = "";
	var urlTipoOcorrencia = "";
	
	if($("#selecaoBairro").val() != null)
		urlRegiao = "&regiao="+$("#selecaoBairro").val();
	
	if($("#selecaoTipoOcorrencia").val() != null)
		urlTipoOcorrencia = "&categoria="+$("#selecaoTipoOcorrencia").val();
	
	$.getJSON("OcorrenciasCruasServlet?data_inicial="+dataInicial+"&data_final="+dataFinal+"&porUsuario=true"+"&grupo="+grupo+urlRegiao+urlTipoOcorrencia, function( data ) {
		
		var latLng;
		var marker;
		var ocorrencia;
		
		map.clearMarkers();	
		if(data.OcorrenciasCruas != null){
			for(var i = 0; i < data.OcorrenciasCruas.length ; i++)
			{
				ocorrencia = data.OcorrenciasCruas[i];
				
				latLng = new google.maps.LatLng(ocorrencia.latitude, ocorrencia.longitude);
				
				bounds.extend(latLng);
				
				marker  = new google.maps.Marker({
					position: latLng,
					map: map,
					title: ocorrencia.tipoOcorrencia,
					icon: iconBase + ocorrencia.icone
				});
				
				listaMarkers[i] = marker;
				
		        google.maps.event.addListener(marker, 'click', (function(marker, ocorrencia) {
		            return function() {
		            	$.getJSON("OcorrenciaIndividualServlet?id=" + ocorrencia.id, function( ocorrenciaCompleta ) {
		            		openInfoWindow(marker, ocorrenciaCompleta);
		            	}).fail(function( jqxhr, textStatus, error ) {
		            	    shorError(jqxhr.status, jqxhr.statusText);
		            	  });	            	
		            };
		        })(marker, ocorrencia));
		        
		        map.fitBounds(bounds);
			}	
		}
		displayLoad(false);
		
		if(data.Quantidades[0].Superintendencia){
			$("#numeroPorRegiaoSuper").html("("+data.Quantidades[0].Superintendencia+")");
		}
		else{
			$("#numeroPorRegiaoSuper").html("(0)");
		}
		
		if(data.Quantidades[0].Usuario){
			$("#numeroPorRegiaoUsuario").html("("+data.Quantidades[0].Usuario+")");
		}
		else{
			$("#numeroPorRegiaoUsuario").html("(0)");
		}
		
		atualizar();
		atualizarPorRegiao();
		
		
	}).fail(function( jqxhr, textStatus, error ) {
	    shorError(jqxhr.status, jqxhr.statusText);
	    displayLoad(false);
	  });
}

function getQtdOcorrenciaByRegiao(){
	var urlRegiao = "";
	var urlTipoOcorrencia = "";
	
	map.clearMarkers();
	map.clearPolygon();
	map.clearMarkersCenterPolygon();
	
	if($("#selecaoBairro").val() != null)
		urlRegiao = "&regiao="+$("#selecaoBairro").val();
	
	if($("#selecaoTipoOcorrencia").val() != null)
		urlTipoOcorrencia = "&categoria="+$("#selecaoTipoOcorrencia").val();
	
	$.getJSON("OcorrenciasPorRegiaoServlet?data_inicial="+dataInicial+"&data_final="+dataFinal+"&grupo="+grupo+urlRegiao+urlTipoOcorrencia, function( retorno ) {

		data = retorno.regioes;
		for(var i = 0; i < data.length ; i++)
		{
			var regiao = data[i];
			var polgynCoord = new Array();
			var boundsPolygon = new google.maps.LatLngBounds();
			var marker = null;
			                      
			for(var j = 0; j < regiao.pontos.length ; j++)
			{	
				var coordenadas = regiao.pontos[j];
				polgynCoord[j] =  new google.maps.LatLng(coordenadas.lat, coordenadas.lng);
				
				boundsPolygon.extend(polgynCoord[j]);
			}
			
			marker  = new google.maps.Marker({
				position: boundsPolygon.getCenter(),
				icon: iconBase + regiao.icone
			});
			
			marker.setMap(map);
			
			listaMarkersCenterPolygon.push(marker);
			
			if($("#menuDrawingPolygon").attr("drawing") == "true"){	
				var drawingPolygonQtdRegiao = makePolygon(polgynCoord, regiao.nome);
				
		        google.maps.event.addListener(drawingPolygonQtdRegiao, 'click', (function(drawingPolygonQtdRegiao, regiao) {
		            return function(event) {
		            	openInfoWindowStatus(drawingPolygonQtdRegiao, regiao, event.latLng);      	
		            };
		        })(drawingPolygonQtdRegiao, regiao));
			}
			
	        google.maps.event.addListener(marker, 'click', (function(marker, regiao) {
	            return function() {
	            	openInfoWindowStatus(marker, regiao, null);    	
	            };
	        })(marker, regiao));
	        
	        google.maps.event.addListener(drawingPolygonQtdRegiao, 'click', (function(drawingPolygonQtdRegiao, regiao) {
	            return function(event) {
	            	openInfoWindowStatus(drawingPolygonQtdRegiao, regiao, event.latLng);      	
	            };
	        })(drawingPolygonQtdRegiao, regiao));
		}
		displayLoad(false);
	}).fail(function( jqxhr, textStatus, error ) {
	    shorError(jqxhr.status, jqxhr.statusText);
	    displayLoad(false);
	  });
	
	atualizar();
	 atualizarPorRegiao();
}

function callFunction(_function, arg1)
{
	lastCallFunction = _function;
	lastArg1 = arg1;
	
	if(getFnName(lastCallFunction) != "getQtdOcorrenciaByRegiao")
		drawingPolygons();
	
	lastCallFunction(lastArg1);
}

function atualizar (){
	
	var urlRegiao = "";
	
	if($("#selecaoBairro").val() != null)
		urlRegiao = "&regiao="+$("#selecaoBairro").val();
	
	$.getJSON("QtdOcorrenciasPorStatusServlet?data_inicial="+dataInicial+"&data_final="+dataFinal+"&grupo="+grupo+urlRegiao, function( data ) {
		if(data.QTD_ACEITA){
			$("#numeroAceitasMap").html(data.QTD_ACEITA);
		}
		else{
			$("#numeroAceitasMap").html("0");
		}
		if(data.QTD_PENDENTE){
			$("#numeroPendentesMap").html(data.QTD_PENDENTE);
		}
		else{
			$("#numeroPendentesMap").html("0");
		}
		if(data.QTD_RESOLVIDA){
			$("#numeroResolvidasMap").html(data.QTD_RESOLVIDA);
		}
		else{
			$("#numeroResolvidasMap").html("0");
		}
		if(data.QTD_RECUSADA){
			$("#numeroRecusadasMap").html(data.QTD_RECUSADA);
		}
		else{
			$("#numeroRecusadasMap").html("0");
		}
	}).fail(function( jqxhr, textStatus, error ) {
	    shorError(jqxhr.status, jqxhr.statusText);
	  });
}

function atualizarPorRegiao (){
	
	var urlRegiao = "";
	urlTipoOcorrencia = "";
	
	if($("#selecaoBairro").val() != null)
		urlRegiao = "&regiao="+$("#selecaoBairro").val();
	
	if($("#selecaoTipoOcorrencia").val() != null)
		urlTipoOcorrencia = "&categoria="+$("#selecaoTipoOcorrencia").val();	
	
	$.getJSON("QtdOcorrenciasPorStatusServlet?data_inicial="+dataInicial+"&data_final="+dataFinal+"&grupo="+grupo+urlRegiao+urlTipoOcorrencia, function( data ) {
		
		if (($("#selecaoBairro").val() != null && $("#selecaoBairro").val() != 0 ) 
				|| ($("#selecaoTipoOcorrencia").val() != null && $("#selecaoTipoOcorrencia").val() != 0 )){
			if(data.QTD_REGIAO_ACEITA){
			$("#numeroPorRegiaoAceita").html("("+data.QTD_REGIAO_ACEITA+")");}
			else{
			$("#numeroPorRegiaoAceita").html("(0)");}
			
			if(data.QTD_REGIAO_RECUSADA){
			$("#numeroPorRegiaoRecusada").html("("+data.QTD_REGIAO_RECUSADA+")");}
			else{
			$("#numeroPorRegiaoRecusada").html("(0)");
			}
			if(data.QTD_REGIAO_RESOLVIDA){
			$("#numeroPorRegiaoResolvida").html("("+data.QTD_REGIAO_RESOLVIDA+")");}
			else{
			$("#numeroPorRegiaoResolvida").html("(0)");
			}
			if(data.QTD_REGIAO_PENDENTE){
			$("#numeroPorRegiaoPendente").html("("+data.QTD_REGIAO_PENDENTE+")");}
			else{
			$("#numeroPorRegiaoPendente").html("(0)");
			}
		}
	}).fail(function( jqxhr, textStatus, error ) {
	    shorError(jqxhr.status, jqxhr.statusText);
	  });
}

function getFnName(fn) {
    return (fn.toString().match(/function (.+?)\(/)||[,''])[1];
}

function displayLoad(mode){
	if(mode)
		$("#load").show("slow");
	else
		$("#load").hide("slow");
}

function effectButton(id){
	$('#'+id).hover(function(){
		if($('#'+id).attr('select') == "false")
			$('#'+id).addClass(id+'Hover');
	},function(){
		if($('#'+id).attr('select') == "false")
			$('#'+id).removeClass(id+'Hover');
	});
}

function eventButton(id, funcao, situacao, usuario, bairro){
	$("#"+id).click(function(){
		displayLoad(true);
		clearButton();
		$('#'+id).addClass(id+'Active');
		$('#'+id).attr('select', "true");
		callFunction(funcao,situacao);
		
		changeLegend(situacao,usuario,bairro);
	});
}

function fullWindow(mypage,myname,scroll){
	settings ='height='+screen.height+',width='+screen.width+',top=0,left=0,scrollbars='+scroll+',resizable=yes,status=no';
	win = window.open(mypage,myname,settings);
	win.focus();
}

