$(document).ready(function(){
	
	//############# VARIABLES ############# 
	var preciosHoyNoDisc = $("#preciosHoy").val();
	var preciosHoyDisc = $("#preciosHoyDisc").val();
	var preciosHoy = preciosHoyNoDisc;
	var mediasDiasSemana = $("#mediasDiasSemana").val();
	var mediasMeses = $("#mediasMeses").val();
	var precioUsuario = 0.0;
	var yMax = 0.17;
	var yMin = 0.05;
	var discriminacion = $('input:radio[name=discriminacion]:checked').val();
	
	//Para impedir que en el input precio usuario se inserten valores no numéricos
	function validateNumber(){
	    var v = this.value;
	       if($.isNumeric(v) === false) {
	            this.value = this.value.slice(0,-1);
	       }
	}
	$("#precio-fijo-usuario").numeric(".").keyup(validateNumber);
	
	////############# INIT ############# 
	$("#precios-dia-chart").css("display","block");
	$("#precios-semana-chart").css("display","none");
	$("#precios-meses-chart").css("display","none");
	
	$("#mostrar-precio-usuario").prop("disabled",true);
	$("#precio-fijo-usuario").change(function(){
		if($("#precio-fijo-usuario").val() >= 0){
			$("#mostrar-precio-usuario").prop("disabled",false);
		}
	});
	
	dibujaChartPreciosHoras(preciosHoy, precioUsuario, yMax, yMin, "precios-dia-chart");
	
	//############# BUTTONS #############
	$('input:radio[name=discriminacion]').change(function(){
		discriminacion = $('input:radio[name=discriminacion]:checked').val();
		if(discriminacion == "1"){
			preciosHoy = preciosHoyDisc;
		}else{
			preciosHoy = preciosHoyNoDisc;
		}
		
		dibujaChartPreciosHoras(preciosHoy, precioUsuario, yMax, yMin,"precios-dia-chart");
	});
	
	$("#precios-dia-button").click(function(){
		dibujaChartPreciosHoras(preciosHoy, precioUsuario, yMax, yMin,"precios-dia-chart");
		
		$("#precios-dia-chart").css("display","block");
		$("#precios-semana-chart").css("display","none");
		$("#precios-meses-chart").css("display","none");
		
		$('#selector-fechas').css("display","inline");
	});
	
	$("#precios-semana-button").click(function(){
		dibujaChartMediasDias(mediasDiasSemana,"precios-semana-chart");
		
		$("#precios-semana-chart").css("display","block");
		$("#precios-meses-chart").css("display","none");
		$("#precios-dia-chart").css("display","none");
		$('#selector-fechas').css("display","none");
		
	});
	
	$("#precios-meses-button").click(function(){
		dibujaChartMeses(mediasMeses, "precios-meses-chart");
		
		$("#precios-semana-chart").css("display","none");
		$("#precios-meses-chart").css("display","block");
		$("#precios-dia-chart").css("display","none");
		$('#selector-fechas').css("display","none");
	});
	
	
	$("#mostrar-precio-usuario").click(function(){
		precioUsuario = $("#precio-fijo-usuario").val();
		if(precioUsuario != ""){
			
			if(precioUsuario < 0.17 && precioUsuario > 0.05){
				yMax=yMax;
				yMin=yMin;
			}else{
				if(precioUsuario > 0.17){
					yMax=precioUsuario;
					yMin=yMin;
				}else{
					if(precioUsuario < 0.05){
						yMax=yMax;
						yMin=precioUsuario;
					}
				}
			}
			
			dibujaChartPreciosHoras(preciosHoy, precioUsuario, yMax, yMin, "precios-dia-chart");
		}
		
	});
	
	$("#borrar-precio-usuario").click(function(){
		precioUsuario = 0.0;
		yMax = 0.17;
		yMin = 0.05
		$("#precio-fijo-usuario").val('');
		$("#mostrar-precio-usuario").prop("disabled",true);
		dibujaChartPreciosHoras(preciosHoy, precioUsuario,  yMax, yMin, "precios-dia-chart");
	});
	
	
	
	//############# DATEPICKER #############
	
	$( "#datepicker-precio" ).datepicker({ 
		minDate: new Date(2014, 4 - 1, 1),
		maxDate: new Date(),
		changeMonth: true,
	    changeYear: true,
	    dateFormat: "yy-mm-dd",
	    dayNamesMin: [ "Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa" ]
	});
	
	$("#datepicker-precio").change(function(){
		var fecha_seleccionada = {"fecha" : $("#datepicker-precio").val()};
		var dirUrl = "/getgraficadia";	

		$.ajax({
			url: dirUrl,//url de destino
			data: fecha_seleccionada,
			type: "get",
			dataType: "text",
			
			success: function(response){
				response = response.split("/");
				preciosHoyNoDisc = response[0];
				preciosHoyDisc = response[1];
				
				if(discriminacion == "1"){
					preciosHoy = preciosHoyDisc;
				}else{
					preciosHoy = preciosHoyNoDisc;
				}

			
				dibujaChartPreciosHoras(preciosHoy, precioUsuario,  yMax, yMin, "precios-dia-chart");
			},
		
			error: function(xhr, status){
				console.log("ERROR EN LA PETICIÓN");
			}
		});
	
	});

});