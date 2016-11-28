$(document).ready(function(){
	
	//################ INIT ################
	
	$('#consumos-periodo-chart').css("display","block");
	$('#consumos-dia-chart').css("display","none");
	$('#consumos-semana-chart').css("display","none");
	$('#consumos-otros-chart').css("display","none");
	
	$("#consumos-periodo-button").prop("disabled",true);
	$("#consumos-dia-button").prop("disabled",true);
	$("#consumos-semana-button").prop("disabled",true);
	$("#consumos-otros-button").prop("disabled",true);
	
	$('#tramos-button').css("display","none");
	$('#laborables-button').css("display","none");
	
	dibujaChartConsumoDiasPeriodoFacturacion(null, null, "consumos-periodo-chart");

	var userId = $('#userId').val();
	var meses = ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];
	
	//#################################################################################################################
	//Cuando entramos en la vista, si previamente había cargado un archivo CSV, se mantiene y se vuelve a dibujar todo
	//#################################################################################################################
	
	var loadedCSV = sessionStorage["loadedCSV"];
	
	if(loadedCSV){
		
		$("#consumos-periodo-button").prop("disabled",false);
		$("#consumos-dia-button").prop("disabled",false);
		$("#consumos-semana-button").prop("disabled",false);
		$("#consumos-otros-button").prop("disabled",false);
		
		var dataArray = [];
		dataArray[0] = JSON.parse(sessionStorage["result"]);
		
		//Dibuja gráfica consumo medio de cada día de la semana de los datos del CSV
		mediasConsumoDiaSemanaArray = calculaMediasDiasSemana(dataArray);
		dibujaChartMediasDiasSemana(mediasConsumoDiaSemanaArray,"consumos-semana-chart");
		
		//Dibuja gráfica consumo por cada día del periodo contenido en los datos del CSV
		consumosPorDiaMesArray = calculaConsumoDiasPeriodoFacturacion(dataArray);
		diasPeriodo = getDiasPeriodo(dataArray);
		dibujaChartConsumoDiasPeriodoFacturacion(consumosPorDiaMesArray,diasPeriodo,'consumos-periodo-chart');
		
		//Dibuja gráfica consumo por horas del día 
		fechaStr = calculateMinDate(dataArray);
		dataSet = createDayDataSet(fechaStr,dataArray);
		dibujaChartConsumoHoras(JSON.stringify(dataSet), null, fechaStr, "consumos-dia-chart");
		
		//Dibuja las gráficas del apartado otros
		consumoDiasSemanaArray = calculaConsumosDiasSemana(dataArray);
		dibujaChartPorcentajeConsumoLaborablesFinSemana(consumoDiasSemanaArray, 'laborables-chart');
		dibujaChartPorcentajeConsumoHorasBaratas(dataArray, 'tramos-chart');
		
		crearDatepicker(dataArray);

		$("#datepicker-consumo").change(function(){
			var preciosDia;
			
			fechaStr = $("#datepicker-consumo").val();
			
			var fecha_seleccionada = {"fecha" : $("#datepicker-consumo").val()};
			var dirUrl = "/getgraficadia";
			
			$.ajax({
				url: dirUrl,//url de destino
				data: fecha_seleccionada,
				type: "get",
				dataType: "text",
				
				success: function(response){
					response = response.split("/");
					preciosHoy = response[0];

					fechaStr = fechaStr.split("-");
					fecha = fechaStr[2]+"/"+fechaStr[1]+"/"+fechaStr[0];
					dataSet = createDayDataSet(fecha,dataArray);

					dibujaChartConsumoHoras(JSON.stringify(dataSet),preciosHoy,fecha,"consumos-dia-chart");

				},
			
				error: function(xhr, status){
					console.log("ERROR EN LA PETICIÓN");
				}
			});
			
		});
		
	}
	
	//################ BUTTONS ################
	
	$("#consumos-periodo-button").click(function(){
		$('#consumos-periodo-chart').css("display","block");
		$('#consumos-dia-chart').css("display","none");
		$('#consumos-semana-chart').css("display","none");
		$('#consumos-otros-chart').css("display","none");
		
		$('#tramos-button').css("display","none");
		$('#laborables-button').css("display","none");
		
		$('#selector-fechas-consumo').css("display","none");
	});
	
	$("#consumos-dia-button").click(function(){
		$('#consumos-periodo-chart').css("display","none");
		$('#consumos-dia-chart').css("display","block");
		$('#consumos-semana-chart').css("display","none");
		$('#consumos-otros-chart').css("display","none");
		
		$('#tramos-button').css("display","none");
		$('#laborables-button').css("display","none");
		
		$('#selector-fechas-consumo').css("display","inline");
	});
	
	$("#consumos-semana-button").click(function(){
		$('#consumos-periodo-chart').css("display","none");
		$('#consumos-dia-chart').css("display","none");
		$('#consumos-semana-chart').css("display","block");
		$('#consumos-otros-chart').css("display","none");
		
		$('#tramos-button').css("display","none");
		$('#laborables-button').css("display","none");
		
		$('#selector-fechas-consumo').css("display","none");
	});
	
	$("#consumos-otros-button").click(function(){
		$('#consumos-periodo-chart').css("display","none");
		$('#consumos-dia-chart').css("display","none");
		$('#consumos-semana-chart').css("display","none");
		$('#consumos-otros-chart').css("display","block");
		
		$('#tramos-button').css("display","inline");
		$('#laborables-button').css("display","inline");
		$('#tramos-chart').css("display","block");
		$('#laborables-chart').css("display","none");
		
		$('#selector-fechas-consumo').css("display","none");
	});
	
	$("#tramos-button").click(function(){
		$('#tramos-chart').css("display","block");
		$('#laborables-chart').css("display","none");
	});
	
	$("#laborables-button").click(function(){
		$('#tramos-chart').css("display","none");
		$('#laborables-chart').css("display","block");
	});
	
	
	
	
	//###########################################
	//################ HISTORICO ################
	//###########################################
	$(".historico-button").click(function(){
		var buttonId = this.id;
		var elementId = buttonId.replace("button","json");
		
		var elementJson = retrieveJSON($("#"+elementId).val());
		
		$("#consumos-periodo-button").prop("disabled",false);
		$("#consumos-dia-button").prop("disabled",false);
		$("#consumos-semana-button").prop("disabled",false);
		$("#consumos-otros-button").prop("disabled",false);
		
		var dataArray = [];
		dataArray[0] = elementJson;
		
		//Dibuja gráfica consumo medio de cada día de la semana de los datos del CSV
		mediasConsumoDiaSemanaArray = calculaMediasDiasSemana(dataArray);
		dibujaChartMediasDiasSemana(mediasConsumoDiaSemanaArray,"consumos-semana-chart");
		
		//Dibuja gráfica consumo por cada día del periodo contenido en los datos del CSV
		consumosPorDiaMesArray = calculaConsumoDiasPeriodoFacturacion(dataArray);
		diasPeriodo = getDiasPeriodo(dataArray);
		dibujaChartConsumoDiasPeriodoFacturacion(consumosPorDiaMesArray,diasPeriodo,'consumos-periodo-chart');
		
		//Dibuja gráfica consumo por horas del día 
		
		//transformaciones de fechas para petición AJAX y para uso en createDayDataSet
		var fechaIniStr = dataArray[0].data[0].Fecha;
		fechaIniArray = fechaIniStr.split("/");
		var fechaIni = fechaIniArray[2]+"-"+fechaIniArray[1]+"-"+fechaIniArray[0];
		fechaIni = {"fecha" : fechaIni};
		var dirUrl = "/getgraficadia";
		
		$.ajax({
			url: dirUrl,//url de destino
			data: fechaIni,
			type: "get",
			dataType: "text",
			
			success: function(response){
				response = response.split("/");
				preciosHoy = response[0];

				dataSet = createDayDataSet(fechaIniStr,dataArray);

				dibujaChartConsumoHoras(JSON.stringify(dataSet),preciosHoy,fechaIniStr,"consumos-dia-chart");

			},
		
			error: function(xhr, status){
				console.log("ERROR EN LA PETICIÓN");
			}
		});
		
		//Dibuja las gráficas del apartado otros
		consumoDiasSemanaArray = calculaConsumosDiasSemana(dataArray);
		dibujaChartPorcentajeConsumoLaborablesFinSemana(consumoDiasSemanaArray, 'laborables-chart');
		dibujaChartPorcentajeConsumoHorasBaratas(dataArray, 'tramos-chart');
		
		var fecha = dataArray[0].data[0].Fecha;
		var fechaArray = fecha.split("/");
		var mes = meses[parseInt(fechaArray[1])-1]
		var año = fechaArray[2];
		
		//Almacena en sesión los valores que usaremos en otras vistas
		sessionStorage["loadedCSV"]=true;
		sessionStorage["porcentajeLaborables"]=porcentajeConsumoDiasLaborables(consumoDiasSemanaArray);
		sessionStorage["porcentajeTramoBarato"]=calculaPorcentajeTramoBarato(dataArray);
		sessionStorage["diaMayorConsumo"]=getDiaMayorConsumo(mediasConsumoDiaSemanaArray);
		sessionStorage["diaMenorConsumo"]=getDiaMenorConsumo(mediasConsumoDiaSemanaArray);
		sessionStorage["result"]=JSON.stringify(dataArray[0]);
		sessionStorage["mes"]=mes;
		sessionStorage["año"]=año;
		
		
		crearDatepicker(dataArray);

		$("#datepicker-consumo").change(function(){
			var preciosDia;
			
			fechaStr = $("#datepicker-consumo").val();
			
			var fecha_seleccionada = {"fecha" : $("#datepicker-consumo").val()};
			var dirUrl = "/getgraficadia";
			
			$.ajax({
				url: dirUrl,//url de destino
				data: fecha_seleccionada,
				type: "get",
				dataType: "text",
				
				success: function(response){
					response = response.split("/");
					preciosHoy = response[0];

					fechaStr = fechaStr.split("-");
					fecha = fechaStr[2]+"/"+fechaStr[1]+"/"+fechaStr[0];
					dataSet = createDayDataSet(fecha,dataArray);

					dibujaChartConsumoHoras(JSON.stringify(dataSet),preciosHoy,fecha,"consumos-dia-chart");

				},
			
				error: function(xhr, status){
					console.log("ERROR EN LA PETICIÓN");
				}
			});
			
		});
	});
	
	
	//###########################################
	//###########################################
	//###########################################
	
	

	$("#read-consumo-file").click(function(){
		//Set resultsArray a null para empezar de 0 una nueva carga de archivos
		resultsArray=[];
		
		//Se comprueba que al menos hay un fichero
		if (!$('#consumoFiles')[0].files.length){
			alert("Por favor, seleccione al menos un fichero.");
		}
				
		$('#consumoFiles').parse({
			config: {
				delimiter: ";",	
				newline: "",	// auto-detect
				complete: completeFn,
				header:true
			},
			before: function(file, inputElem){
				console.log("Parsing file...", file);
				if(file.name.substr(file.name.length-4, file.name.length) != ".csv"){
					alert("Debe cargar un fichero en formato CSV");
				}
			},
			error: function(err, file){
				console.log("ERROR:", err, file);
				firstError = firstError || err;
				errorCount++;
			},
			complete: function(){
				console.log("El archivo fue parseado correctamente.");
				var csvLoaded = true;
				
				
				$("#consumos-periodo-button").prop("disabled",false);
				$("#consumos-dia-button").prop("disabled",false);
				$("#consumos-semana-button").prop("disabled",false);
				$("#consumos-otros-button").prop("disabled",false);
				
				if(userId != null){
					var fecha = resultsArray[0].data[0].Fecha;
					var fechaArray = fecha.split("/");
					var mes = meses[parseInt(fechaArray[1])-1]
					//var jsonConsumo = JSON.stringify(resultsArray[0]);
					var jsonConsumo = getResultString(resultsArray[0]);
					
					var consumoTotal = getConsumoTotal(resultsArray);
					var porcentajeLaborables = porcentajeConsumoDiasLaborables(consumoDiasSemanaArray);
					var porcentajeTramoBarato = calculaPorcentajeTramoBarato(resultsArray);
					
					var jsonString = '{"userId":'+userId+', "fecha":"'+fecha+'","mes":"'+mes+'","año":"'+fechaArray[2]+'", "json":"'+jsonConsumo+
						'", "consumoPorDia":"'+consumoDiasSemanaArray+'", "consumoTotal":'+consumoTotal.toFixed(2)+
						', "porcentajeLaborables":'+porcentajeLaborables+',"porcentajeTramoBarato":'+porcentajeTramoBarato+'}';

					
					$.ajax({
						url: "/addconsumofile",//url de destino
						data: JSON.parse(jsonString),
						type: "post",
						dataType: "json",
						
						success: function(response){
							console.log("PETICIÓN CORRECTA");
							location.reload();
						},
					
						error: function(xhr, status){
							console.log("ERROR EN LA PETICIÓN "+status);
						}
					});
				}
				
				//Dibuja gráfica consumo medio de cada día de la semana de los datos del CSV
				mediasConsumoDiaSemanaArray = calculaMediasDiasSemana(resultsArray);
				dibujaChartMediasDiasSemana(mediasConsumoDiaSemanaArray,"consumos-semana-chart");
				
				//Dibuja gráfica consumo por cada día del periodo contenido en los datos del CSV
				consumosPorDiaMesArray = calculaConsumoDiasPeriodoFacturacion(resultsArray);
				diasPeriodo = getDiasPeriodo(resultsArray);
				dibujaChartConsumoDiasPeriodoFacturacion(consumosPorDiaMesArray,diasPeriodo,'consumos-periodo-chart');
				
				//Dibuja gráfica consumo por horas del día 
				
				//transformaciones de fechas para petición AJAX y para uso en createDayDataSet
				var fechaIniStr = resultsArray[0].data[0].Fecha;
				fechaIniArray = fechaIniStr.split("/");
				var fechaIni = fechaIniArray[2]+"-"+fechaIniArray[1]+"-"+fechaIniArray[0];
				fechaIni = {"fecha" : fechaIni};
				var dirUrl = "/getgraficadia";
				
				$.ajax({
					url: dirUrl,//url de destino
					data: fechaIni,
					type: "get",
					dataType: "text",
					
					success: function(response){
						response = response.split("/");
						preciosHoy = response[0];

						dataSet = createDayDataSet(fechaIniStr,resultsArray);

						dibujaChartConsumoHoras(JSON.stringify(dataSet),preciosHoy,fechaIniStr,"consumos-dia-chart");

					},
				
					error: function(xhr, status){
						console.log("ERROR EN LA PETICIÓN");
					}
				});
				
				//Dibuja las gráficas del apartado otros
				consumoDiasSemanaArray = calculaConsumosDiasSemana(resultsArray);
				dibujaChartPorcentajeConsumoLaborablesFinSemana(consumoDiasSemanaArray, 'laborables-chart');
				dibujaChartPorcentajeConsumoHorasBaratas(resultsArray, 'tramos-chart');
				
				var fecha = resultsArray[0].data[0].Fecha;
				var fechaArray = fecha.split("/");
				var mes = meses[parseInt(fechaArray[1])-1]
				var año = fechaArray[2];
				
				sessionStorage["loadedCSV"]=csvLoaded;
				sessionStorage["porcentajeLaborables"]=porcentajeConsumoDiasLaborables(consumoDiasSemanaArray);
				sessionStorage["porcentajeTramoBarato"]=calculaPorcentajeTramoBarato(resultsArray);
				sessionStorage["diaMayorConsumo"]=getDiaMayorConsumo(mediasConsumoDiaSemanaArray);
				sessionStorage["diaMenorConsumo"]=getDiaMenorConsumo(mediasConsumoDiaSemanaArray);
				sessionStorage["result"]=JSON.stringify(resultsArray[0]);
				sessionStorage["mes"]=mes;
				sessionStorage["año"]=año;
				
				
			}
		});
	});
	
	
	//Función que se ejecuta al completar el parseo de cada archivo
	function completeFn (results){
		if(results.meta.fields[0] != "CUPS" || results.meta.fields[1] != "Fecha"
			|| results.meta.fields[2] != "Hora" || results.meta.fields[3] != "Consumo_kWh" ||
			results.meta.fields[4] != "Metodo_obtencion"){
			
			alert("El fichero CSV contiene errores o no tiene un formato válido.");
		
		}else{
			resultsArray.push(results);
			console.log("Parsing complete:", results);
			processResults(resultsArray);
		}			
	}
	
	
	function processResults(resultsArray){
		//Crea Datepicker donde solo se puede seleccionar fechas incluidas en el csv
		crearDatepicker(resultsArray);

		$("#datepicker-consumo").change(function(){
			var preciosDia;
			
			fechaStr = $("#datepicker-consumo").val();
			
			var fecha_seleccionada = {"fecha" : $("#datepicker-consumo").val()};
			console.log(fechaStr);
			var dirUrl = "/getgraficadia";
			
			$.ajax({
				url: dirUrl,//url de destino
				data: fecha_seleccionada,
				type: "get",
				dataType: "text",
				
				success: function(response){
					response = response.split("/");
					preciosHoy = response[0];

					fechaStr = fechaStr.split("-");
					fecha = fechaStr[2]+"/"+fechaStr[1]+"/"+fechaStr[0];
					dataSet = createDayDataSet(fecha,resultsArray);

					dibujaChartConsumoHoras(JSON.stringify(dataSet),preciosHoy,fecha,"consumos-dia-chart");

				},
			
				error: function(xhr, status){
					console.log("ERROR EN LA PETICIÓN");
				}
			});
			
		});

	}
	
	
	//############################ DATEPICKER ###########################
	function crearDatepicker(resultsArray){
		results = resultsArray[0];

		$("#selector-fechas-consumo").remove();
		$("#charts-buttons").append('<div id="selector-fechas-consumo"><br/><br/><label>Seleccione una fecha: </label>'+
				'<input type="text" placeholder="aaaa-mm-dd" id="datepicker-consumo" ><input type="hidden" '+
				'id="fecha-seleccionada-consumo"/></div>');
		$('#selector-fechas-consumo').css("display","none");
		
		var minDateStr = calculateMinDate(resultsArray).split("/");
		var maxDateStr = calculateMaxDate(resultsArray).split("/");
		
		var minDate = new Date(minDateStr[2], minDateStr[1]-1, minDateStr[0]);
		var maxDate = new Date(maxDateStr[2], maxDateStr[1]-1, maxDateStr[0]);
		
		$("#datepicker-consumo").datepicker({ 
			beforeShowDay: function(date) {
        		for(var i=0; i<resultsArray.length; i++) {
					var startDateStr=resultsArray[i].data[0].Fecha.split("/");
					var endDateStr=resultsArray[i].data[resultsArray[i].data.length-10].Fecha.split("/");
					
					var startDate = new Date(startDateStr[2], startDateStr[1]-1, startDateStr[0]);
					var endDate = new Date(endDateStr[2], endDateStr[1]-1, endDateStr[0]);
		          
		        	if(date >= startDate && date <= endDate) return [true, ''];
		        }
		        return [false, ''];
		    },
		    minDate: minDate,
		    maxDate: maxDate,
			changeMonth: true,
		    changeYear: true,
		    dateFormat: "yy-mm-dd",
		    dayNamesMin: [ "Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa" ]
		});
			
		fechaStr = calculateMinDate(resultsArray);
		dataSet = createDayDataSet(fechaStr,resultsArray);

	}

});