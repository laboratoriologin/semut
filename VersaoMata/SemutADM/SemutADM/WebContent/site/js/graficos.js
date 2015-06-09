var dayName = new Array ("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado");
var monName = new Array ("janeiro", "fevereiro", "março", "abril", "maio", "junho", "agosto", "outubro", "novembro", "dezembro");
var grupoTransalvador = new Array();
var grupoSucom   = new Array();
var corRecusada  = "#6D6E70";
var corResolvida = "#33558C";
var corAceita    = "#00A14B";
var corPendente  = "#A53736";
var graficoVazio = '<div class="caixaMensagemSemRegistro"><p><span class="ui-icon-alerta1"></span>Nenhum registro encontrado.</p></div>';
var expired=false;

google.load("visualization", "1", {
	packages : [ "corechart" ]
});


function pad(n) { return ("0" + n).slice(-2); }

function time()
{
	now = new Date();
	$("#localData").html("" + dayName[now.getDay() ] + ", " + now.getDate () + " de " + monName [now.getMonth() ]   +  " de "  +     now.getFullYear () + ". " + pad(now.getHours()) + ":" + pad(now.getMinutes()) + ":" + pad(now.getSeconds()));
	setTimeout('time()',500);
}

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

function fullWindow(mypage,myname,scroll){
	settings ='height='+screen.height+',width='+screen.width+',top=0,left=0,scrollbars='+scroll+',resizable=yes,status=no';
	win = window.open(mypage,myname,settings);
	win.focus();
}

function showError(status, msg){
	
	var stack_bar_top = {"dir1": "down", "dir2": "right", "push": "top", "spacing1": 0, "spacing2": 0};
	
	if(status == 500)
		msg = "Erro nos servidores do sistema, tente mais tarde ou entre em contato com o Administrador do sistema.";
	else if(status == 400)
		msg = "Erro na URL do sistema, entre em contato com o adminstrador do sistema.";
	else if(status == 0)
		msg = "Servidores fora do ar, entre em contato com o adminstrador do sistema.";
	else if(status==600) {
		if(!expired) {
			expired=true;
			alert('Sua sessão expirou, faça o login novamente.');
			window.location.href='login.xhtml';
		}
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

function loadTiposOcorrencias() {
	
	$.getJSON("tipos_ocorrencias",function(data) {
		for(linha in data.tipoOcorrencias) {
			if(data.tipoOcorrencias[linha].grupoOcorrencia==1) {
				grupoTransalvador.push(data.tipoOcorrencias[linha]);
			} else {
				grupoSucom.push(data.tipoOcorrencias[linha]);
			}
		}
	
		var $boxTransalvador = $('.boxTransalvador');
		
		for(linha in grupoTransalvador) {
			$boxTransalvador.append(new Option(grupoTransalvador[linha].nome, grupoTransalvador[linha].id));
		}
		
		var $boxSucom = $('.boxSucom');
		
		for(linha in grupoSucom) {
			$boxSucom.append(new Option(grupoSucom[linha].nome, grupoSucom[linha].id));
		}
	}).fail(function( jqxhr, textStatus, error ) {
		showError(jqxhr.status, jqxhr.statusText);
	});
	
}

function reloadComboOcorrencia(grupo,combo_id) {
	
	var id = '#'+combo_id;
	
	var $component = $(id);
	
	$component.empty();
    
	$component.append(new Option('Todas as ocorrências',''));
	
	if(grupo==1) {
	
		for(linha in grupoTransalvador) {
			$component.append(new Option(grupoTransalvador[linha].nome, grupoTransalvador[linha].id));
		}
		
	} else {
		
		for(linha in grupoSucom) {
			$component.append(new Option(grupoSucom[linha].nome, grupoSucom[linha].id));
		}
		
	}
	
}

/* Request charts */

function loadOcorrenciasGeral() {
	
	$.getJSON("qtd_ocorrencias_geral",function() {}).done(
	function(data) {
		
		if(data.geral) {
			$('#numeroOcorrenciasPendentesGraf').html(data.geral);
		} else {
			$('#numeroOcorrenciasPendentesGraf').html('0');
		}
		
		if(data.sucom) {
			$('#numeroOcorrenciasResSucomGraf').html(data.sucom);
		} else {
			$('#numeroOcorrenciasResSucomGraf').html(0);
		}
		
		if(data.transalvador) {
			$('#numeroOcorrenciasResTransalGraf').html(data.transalvador);
		} else {
			$('#numeroOcorrenciasResTransalGraf').html('0');
		}
		
	}).fail(function() {
		$('#numeroOcorrenciasPendentesGraf').html('?');
		$('#numeroOcorrenciasResSucomGraf').html('?');
		$('#numeroOcorrenciasResTransalGraf').html('?');
	});
}
/*
function loadOcorrenciaGraph() {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;
	
	$('#chart_geral').html('<div align="left" style="padding:10px;"><img src="imagens/icone_carregando.gif"/></div>');

	$.getJSON("QtdOcorrenciasPorStatusServlet?data_inicial="+inicio+"&data_final="+ fim,
		function(data) {
			$('#chart_geral').html('');
			drawChartOcorrencia(data, 'chart_geral','Quantidade de ocorência por status');
		}).fail(function( jqxhr, textStatus, error ) {
			$('#chart_geral').html('');
			showError(jqxhr.status, jqxhr.statusText);
		});

}
*/
/*
function loadOcorrenciaSucomGraph() {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;
	var categoria = document.getElementById('categoria_sucom').value;

	$('#chart_sucom').html('<div align="left" style="padding:10px;"><img src="imagens/icone_carregando.gif"/></div>');

	$.getJSON("QtdOcorrenciasPorStatusServlet?data_inicial="+inicio+"&data_final="+ fim + "&grupo=2" + "&categoria=" + categoria,function(data) {
		$('#chart_sucom').html('');
		drawChartOcorrencia(data, 'chart_sucom','Quantidade de ocorência por status - SUCOM');	
	}).fail(function( jqxhr, textStatus, error ) {
		showError(jqxhr.status, jqxhr.statusText);
	});

}*/

function loadOcorrenciaTransalvadorGraph() {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;
	var categoria = document.getElementById('categoria_transalvador').value;

	$('#chart_transalvador').html('<div align="left" style="padding:10px;"><img src="imagens/icone_carregando.gif"/></div>');

	$.getJSON("QtdOcorrenciasPorStatusServlet?data_inicial="+inicio+"&data_final="+ fim + "&grupo=1&categoria="+categoria,function(data) {
		$('#chart_transalvador').html('');
		drawChartOcorrencia(data, 'chart_transalvador','Quantidade de ocorência por status');				
	}).fail(function( jqxhr, textStatus, error ) {
		showError(jqxhr.status, jqxhr.statusText);
	});

}

function loadAreaGraph() {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;

	$('#chart_area').html('<div align="left" style="padding:10px;"><img src="imagens/icone_carregando.gif"/></div>');

	$.getJSON("OcorrenciasPorRegiaoServlet?data_inicial="+inicio+"&data_final="+fim, function(data) {
		$('#chart_area').html('');
		drawChartRegiao(data, 'chart_area', 'Ocorrências por área');
	}).fail(function( jqxhr, textStatus, error ) {
		showError(jqxhr.status, jqxhr.statusText);
	});

}

function loadTempoGraph() {
	
	var inicio    = document.getElementById('data_inicial').value;
	var fim       = document.getElementById('data_final').value;
	//var grupo     = document.getElementById('grupo_tempo').value;
	var grupo     = 1;
	var periodo   = document.getElementById('periodo_tempo').value;
	var categoria = document.getElementById('combo_tipo_tempo').value;
	
	$('#chart_tempo').html('<div align="left" style="padding:10px;"><img src="imagens/icone_carregando.gif"/></div>');
	
	$.getJSON("ocorrencias_tempo?periodo="+periodo+"&data_inicial="+inicio+"&data_final="+fim+"&grupo="+grupo + "&categoria=" + categoria, function(data) {
		$('#chart_tempo').html('');
		drawChartTempo(data, 'chart_tempo', 'Ocorrências por tempo');
	}).fail(function( jqxhr, textStatus, error ) {
		showError(jqxhr.status, jqxhr.statusText);
	});

}

function loadTempoResolucaoGraph() {
	
	//var grupo     = document.getElementById('grupo_tempo_resolucao').value;
	var grupo     = 1;
	var categoria = document.getElementById('combo_tipo_tempo_resolucao').value;
	
	$('#chart_tempo_resolucao').html('<div align="left" style="padding:10px;"><img src="imagens/icone_carregando.gif"/></div>');
	
	$.getJSON("ocorrencias_tempo_resolucao?grupo="+grupo + "&categoria=" + categoria, function(data) {
		$('#chart_tempo_resolucao').html('');
		drawChartTempoResolucao(data, 'chart_tempo_resolucao', 'Ocorrências por tempo de resolução');
	}).fail(function( jqxhr, textStatus, error ) {
		showError(jqxhr.status, jqxhr.statusText);
	});

}


/* Draw charts */

function drawNenhumRegistro(chart_id) {
	$('#' +chart_id).html(graficoVazio);
}

function drawChartOcorrencia(json, chart_id, _title) {
	
	if(json.QTD_REGIAO_RESOLVIDA || json.QTD_REGIAO_RECUSADA || json.QTD_REGIAO_PENDENTE || json.QTD_REGIAO_ACEITA) {
	

		var data = google.visualization.arrayToDataTable([
				[ 'Status', 'Quantidade'],
				[ 'Resolvidas', json.QTD_REGIAO_RESOLVIDA], 
				[ 'Recusadas',  json.QTD_REGIAO_RECUSADA], 
				[ 'Pendentes',  json.QTD_REGIAO_PENDENTE],
				[ 'Aceitas',    json.QTD_REGIAO_ACEITA], 
															
		]);

		var options = {
			colors: [corResolvida, corRecusada, corPendente, corAceita],
			title : _title,
			hAxis : {
				title : 'Status',
				titleTextStyle : {
					color : '#333'
				}
			},
			vAxis : {
				minValue : 0
			},
	
			'width' : 550,
			'height' : 520
	
		};
	
		var chart = new google.visualization.PieChart(document.getElementById(chart_id));
	
		chart.draw(data, options);
	
	} else {
		drawNenhumRegistro(chart_id);
	}

}


function drawChartRegiao(json, chart_id, _title) {
	
	if(json.existe) {
	
		var data = new google.visualization.DataTable();
	
		// Add columns
		data.addColumn('string', 'Área');
		data.addColumn('number', 'Resolvidas');
		data.addColumn('number', 'Recusadas');
		data.addColumn('number', 'Pendentes');
		data.addColumn('number', 'Aceitas');
		for (linha in json.regioes) {
			data.addRow([json.regioes[linha].nome, json.regioes[linha].QTD_RESOLVIDA, json.regioes[linha].QTD_RECUSADA, json.regioes[linha].QTD_PENDENTE,json.regioes[linha].QTD_ACEITA]);		
		}
		
		var options = {
			colors: [corResolvida, corRecusada, corPendente, corAceita],
			title : _title,
			hAxis : {
				title : 'Áreas',
				titleTextStyle : {
					color : '#333'
				}
			},
			vAxis : {
				title : 'Quantidade',
				minValue : 0,
				showTextEvery:1
			},
	
			'width' : 550,
			'height' : 520
	
		};
	
		var chart = new google.visualization.ColumnChart(document
				.getElementById(chart_id));
	
		chart.draw(data, options);
	
	} else {
		drawNenhumRegistro(chart_id);
	}

}

function drawChartTempo(json, chart_id, _title) {
	
	if(json && json[0]) {

	var data = new google.visualization.DataTable();

	// Add columns
	
	
	data.addColumn('string', 'Período');
	data.addColumn('number', 'Resolvidas');
	data.addColumn('number', 'Recusadas');
	data.addColumn('number', 'Pendentes');
	data.addColumn('number', 'Aceitas');
	
	for (linha in json) {
		data.addRow([json[linha].agrupador,json[linha].QTD_RESOLVIDA,json[linha].QTD_RECUSADA,json[linha].QTD_PENDENTE,json[linha].QTD_ACEITA]);
	}
	
	var options = {
		colors: [corResolvida, corRecusada, corPendente, corAceita],
		title : _title,
		hAxis : {
			title : 'Período',
			titleTextStyle : {
				color : '#333'
			}
		},
		vAxis : {
			title : 'Quantidade',
			minValue : 0
		},

		'width' : 550,
		'height' : 520,
		pointSize: 5

	};

	var chart = new google.visualization.LineChart(document.getElementById(chart_id));

	chart.draw(data, options);
	
	} else {
		drawNenhumRegistro(chart_id);
	}

}

function drawChartTempoResolucao(json, chart_id, _title) {

	if(json && json[0]) {
	
	var data = new google.visualization.DataTable();

	// Add columns
	data.addColumn('string', 'Mês/Ano');
	data.addColumn('number', 'Horas');
	for (linha in json) {
		data.addRow([json[linha].agrupador, json[linha].qtd]);
	}
	
	var options = {
		title : _title,
		hAxis : {
			title : 'Mês/Ano',
			titleTextStyle : {
				color : '#333'
			}
		},
		vAxis : {
			title : 'Horas',
			minValue : 0
		},

		'width' : 550,
		'height' : 520,
		pointSize: 5

	};

	var chart = new google.visualization.LineChart(document.getElementById(chart_id));

	chart.draw(data, options);
	
	} else {
		drawNenhumRegistro(chart_id);
	}

}

$(document).ready(function() {
	
	//time();
	
	$("#usuario").html("Olá, "+$.cookie("usuario")+"!");
	
	$.pnotify.defaults.styling = "jqueryui";
	$.pnotify.defaults.history = false;
	$.pnotify.defaults.delay = 500;
	
	$('#btnMapa').click(function(){
		
		window.location.href="index.html?data_inicial="+$("#data_inicial").val()+"&data_final="+$("#data_final").val();
	});
	
	$('#sair').click(function(){
		if (confirm("Deseja sair?")){
			$.removeCookie("usuario");
			window.location.href="sair";
		}
	});
	
	$("#reload").click(function(){
		window.location.href="grafico.html?data_inicial="+$("#data_inicial").val()+"&data_final="+$("#data_final").val();
	});
	
	$( "#dialog" ).dialog({
      autoOpen: false,
      modal:true,
      width:1024,
      height:768,
      show: {
        effect: "drop",
        duration: 500
      }
    });
	
	$( "#dialogImagem" ).dialog({
	      autoOpen: false,
	      modal:true,
	      width:1024,
	      height:768
	    });
 
    $( "#dialogOpener" ).click(function() {
      $( "#dialog" ).dialog( "open" );
      loadListOcorrencia();
    });
    
    loadTiposOcorrencias();
    
	var $datepickerInicial = $("#data_inicial").datepicker({
		dateFormat : 'dd/mm/yy',
		showOtherMonths: true,
		selectOtherMonths: true,
		numberOfMonths: 2,
		onClose: function( selectedDate ) {
			$( "#data_final" ).datepicker( "option", "minDate", selectedDate );
        }
	});
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	
	$datepickerInicial.datepicker('setDate', firstDay);

	var $datepickerFinal = $("#data_final").datepicker({
		dateFormat : 'dd/mm/yy',
		showOtherMonths: true,
		selectOtherMonths: true,
		numberOfMonths: 2,
		onClose: function( selectedDate ) {
			$( "#data_inicial" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	
	$datepickerFinal.datepicker('setDate', new Date());
	
	if (GetURLParameter("data_inicial") != null)
	$("#data_inicial").val(GetURLParameter("data_inicial"));
	if (GetURLParameter("data_final") != null)
	$("#data_final").val(GetURLParameter("data_final"));

    atualizarGraficos();

	$("#secaoGraficos2").hide();

});

function loadListOcorrencia() {
	
	$('#datatable').html('<div align="center"><img src="imagens/icone_carregando.gif"/></div>');
	
	$.getJSON("ocorrencias_pendentes", function() {
		
	}).done(function(data) {
		
		var html = '<table cellpadding="0" cellspacing="0" border="0" class="display dataTable" id="example" style="width: 980px" aria-describedby="Ocorrências Pendentes">'+
		'<thead>' //+
		//'<th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="Grupo" style="width: 180px;"> <div class="DataTables_sort_wrapper">Grupo<span class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span></div></th>'
		+'<th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="Ocorrência" style="width: 180px;"> <div class="DataTables_sort_wrapper">Ocorrência<span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span></div></th>'
		+'<th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="Descrição" style="width: 240px;"> <div class="DataTables_sort_wrapper">Descrição<span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span></div></th>'
		+'<th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="Data" style="width: 200px;"> <div class="DataTables_sort_wrapper">Data<span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span></div></th>'
		+'<th class="ui-state-default" style="width: 90px;">Imagem</th>'
		+'<th class="ui-state-default" style="width: 90px;">Alterar</th>'
		+'</tr>'
		+
	    '</thead>' +
		'<tbody role="alert" aria-live="polite" aria-relevant="all"> ';
		
		for(linha in data) {
			html = html + '<tr>' 			
			//+ '<td>' + data[linha].GrupoOcorrencia + '</td>' 
			+ '<td>' + data[linha].nomeTipoOcorrencia+ '</td>' 
			+ '<td>';
			if(data[linha].descricao) {
				html = html + data[linha].descricao; 
			}
			html = html + '</td>' 
			+ '<td>' + data[linha].data + '</td><td>';
			
			if(data[linha].imagem ) {
				html = html + '<div align="center"><a href="#" onclick="exibirImagem(\'http://177.1.212.50:9004/arquivos_semut/' + data[linha].imagem+'\');"><img style="border:none;" src="imagens/icone_botao_ampliar_dark.png"/></a></div>';
			}
			
			html = html + '</td><td><div align="center"><a href="#" onclick="fullWindow(\'../pages/dashboard.xhtml?ocorrencia='+data[linha].id+'\',\'ocorrencia\',1);"><img style="border:none;" src="imagens/icone_adm_ocorrencias_dark.png"/></a></div></td></tr>';
			
		}
		
		html = html + '</tbody></table>';
		
		$('#datatable').html(html);
		
		var table = $('#example').dataTable();
		
		table.fnSort( [[0,'desc']] );
		
	}).fail(function() {
		$('#datatable').html('Não foi possível carregar ocorrências, tente novamente mais tarde');
	});
	
}


function exibirImagem(imagem) {
	
	var html = '<div align="center"><img src="'+imagem+'" /></div>';
	
	$( "#dialogImagem" ).html(html);
	
	$( "#dialogImagem" ).dialog( "open" );
}

function atualizarGraficos() {

	loadAreaGraph();
	//loadOcorrenciaGraph();
	//loadOcorrenciaSucomGraph();
	loadOcorrenciaTransalvadorGraph();
	loadTempoGraph();
	loadTempoResolucaoGraph();
	loadOcorrenciasGeral();
}

function showAba1() {
	var options = {};
	$("#secaoGraficos2").hide('drop', options, 500, callback('#secaoGraficos'));
	$("#aba2").removeClass('abaSelecionada');
	$("#aba1").addClass('abaSelecionada');
}

function showAba2() {
	var options = {};
	$("#secaoGraficos").hide('drop', options, 500,callback('#secaoGraficos2'));
	$("#aba1").removeClass('abaSelecionada');
	$("#aba2").addClass('abaSelecionada');
}

function callback(show) {
	setTimeout(function() {
		$("#effect").removeAttr("style").hide().fadeIn();
		$(show).show();
		if(show == '#secaoGraficos2') {
			loadTempoGraph();
			loadTempoResolucaoGraph();
		} else {
			loadAreaGraph();
			//loadOcorrenciaGraph();
			//loadOcorrenciaSucomGraph();
			loadOcorrenciaTransalvadorGraph();
			loadTempoGraph();
		}
	}, 500);
};
