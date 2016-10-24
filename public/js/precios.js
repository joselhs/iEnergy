$(document).ready(function(){
	
	//############# VARIABLES ############# 
	var preciosHoy = $("#preciosHoy").val();
	var mediasDiasSemana = $("#mediasDiasSemana").val();
	
	////############# INIT ############# 
	$("#precios-dia-chart").css("display","block");
	$("#precios-semana-chart").css("display","none");
	$("#precios-meses-chart").css("display","none");
	
	//############# BUTTONS ############# 
	$("#precios-dia-button").click(function(){
		dibujaChartPreciosHoras(preciosHoy,"precios-dia-chart");
		
		$("#precios-dia-chart").css("display","block");
		$("#precios-semana-chart").css("display","none");
		$("#precios-meses-chart").css("display","none");
	});
	
	$("#precios-semana-button").click(function(){
		dibujaChartMediasDias(mediasDiasSemana,"precios-semana-chart");
		
		$("#precios-semana-chart").css("display","block");
		$("#precios-meses-chart").css("display","none");
		$("#precios-dia-chart").css("display","none");
		
	});
	
	$("#precios-meses-button").click(function(){
		
		$("#precios-semana-chart").css("display","none");
		$("#precios-meses-chart").css("display","block");
		$("#precios-dia-chart").css("display","none");
	});
	
	//############# GRAFICAS ############# 
	dibujaChartPreciosHoras(preciosHoy,"precios-dia-chart");
	
});