google.load("visualization", "1", {
	packages : [ "corechart" ]
});

function drawChartOcorrencia(json, chart_id, _title) {

	var data = new google.visualization.DataTable();

	// Add columns
	data.addColumn('string', 'Status');
	data.addColumn('number', 'Quantidade');
	data.addRow([ 'Resolvidas', json.QTD_RESOLVIDA ]);
	data.addRow([ 'Recusadas', json.QTD_RECUSADA ]);
	data.addRow([ 'Pendentes', json.QTD_PENDENTE ]);
	data.addRow([ 'Aceitas', json.QTD_ACEITA ]);

	var options = {
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

		'width' : 490,
		'height' : 500

	};

	var chart = new google.visualization.PieChart(document
			.getElementById(chart_id));

	chart.draw(data, options);

}

$(document).ready(function() {

	$(".dialogGrafico").dialog({
		autoOpen : false,
		closable : false,
		modal : true
	});

});

function loadOcorrenciaGraph(dataInicial, dataFinal) {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;
	
	$("#dialogOcorrenciaGeral").dialog("open");

	$
			.getJSON(
					"../site/QtdOcorrenciasPorStatusServlet?data_inicial="+inicio+"&data_final="+ fim,
					function() {
						console.log("success");
					}).done(
					function(data) {
						drawChartOcorrencia(data, 'chart_geral',
								'Quantidade de ocorência por status');
					}).fail(function() {
				console.log("error");
			}).always(function() {
				console.log("complete");
				$("#dialogOcorrenciaGeral").dialog("close");
			});

}

function loadOcorrenciaSucomGraph(dataInicial, dataFinal) {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;

	$("#dialogOcorrenciaSucom").dialog("open");

	$.getJSON("../site/QtdOcorrenciasPorStatusServlet?data_inicial="+inicio+"&data_final="+ fim + "&grupo=2",
			function() {
				console.log("success");
			}).done(function(data) {
				drawChartOcorrencia(data, 'chart_sucom','Quantidade de ocorência por status - SUCOM');
			}).fail(function() {
				console.log("error");
			}).always(function() {
				console.log("complete");
				$("#dialogOcorrenciaSucom").dialog("close");
	});

}

function loadOcorrenciaTransalvadorGraph(dataInicial, dataFinal) {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;

	$("#dialogOcorrenciaTransalvador").dialog("open");

	$
			.getJSON("../site/QtdOcorrenciasPorStatusServlet?data_inicial="+inicio+"&data_final="+ fim + "&grupo=1",
					function() {
						console.log("success");
					})
			.done(
					function(data) {
						drawChartOcorrencia(data, 'chart_transalvador',
								'Quantidade de ocorência por status - TRANSALVADOR');
					}).fail(function() {
				console.log("error");
			}).always(function() {
				console.log("complete");
				$("#dialogOcorrenciaTransalvador").dialog("close");
			});

}

function loadAreaGraph(dataInicial, dataFinal) {
	
	var inicio = document.getElementById('data_inicial').value;
	var fim    = document.getElementById('data_final').value;

	$("#dialogArea").dialog("open");

	$.getJSON("../site/ocorrencias_regiao?data_inicial="+inicio+"&data_final="+fim, function() {
		console.log("success");
	}).done(function(data) {
		drawChartRegiao(data, 'chart_area', 'Ocorrências por área');
	}).fail(function() {
		console.log("error");
	}).always(function() {
		console.log("complete");
		$("#dialogArea").dialog("close");
	});

}

function drawChartRegiao(json, chart_id, _title) {

	var data = new google.visualization.DataTable();

	// Add columns
	data.addColumn('string', 'Área');
	data.addColumn('number', 'Resolvidas');
	data.addColumn('number', 'Recusadas');
	data.addColumn('number', 'Pendentes');
	data.addColumn('number', 'Aceitas');
	for (linha in json) {
		data.addRow([json[linha].nome, json[linha].QTD_RESOLVIDA, json[linha].QTD_RECUSADA, json[linha].QTD_PENDENTE,json[linha].QTD_ACEITA]);		
	}
	
	var options = {
		title : _title,
		hAxis : {
			title : 'Áreas',
			titleTextStyle : {
				color : '#333'
			}
		},
		vAxis : {
			title : 'Quantidade',
			minValue : 0
		},

		'width' : 1024,
		'height' : 500

	};

	var chart = new google.visualization.ColumnChart(document
			.getElementById(chart_id));

	chart.draw(data, options);

}

function loadLineGraph(dataInicial, dataFinal) {
	
	var inicio  = document.getElementById('data_inicial').value;
	var fim     = document.getElementById('data_final').value;
	var grupo   = document.getElementById('grupo_line').value;
	var periodo = document.getElementById('periodo_line').value;

	$("#dialogArea").dialog("open");
	$.getJSON("../site/ocorrencias_tempo?periodo="+periodo+"&data_inicial="+inicio+"&data_final="+fim+"&grupo="+grupo, function() {
		console.log("success");
	}).done(function(data) {
		drawChartLine(data, 'chart_line', 'Ocorrências por tempo');
	}).fail(function() {
		console.log("error");
	}).always(function() {
		console.log("complete");
		$("#dialogArea").dialog("close");
	});

}

function drawChartLine(json, chart_id, _title) {

	var data = new google.visualization.DataTable();

	// Add columns
	data.addColumn('string', 'Período');
	data.addColumn('number', 'Qtd');
	for (linha in json) {
		data.addRow([json[linha].agrupador, json[linha].qtd]);
	}
	
	var options = {
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

		'width' : 490,
		'height' : 600,
		pointSize: 5

	};

	var chart = new google.visualization.LineChart(document.getElementById(chart_id));

	chart.draw(data, options);

}