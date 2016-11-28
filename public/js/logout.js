$(document).ready(function(){
	
	$("#logout").click(function(){
		sessionStorage["loadedCSV"]=null;
		sessionStorage["porcentajeLaborables"]=null;
		sessionStorage["porcentajeTramoBarato"]=null;
		sessionStorage["diaMayorConsumo"]=null;
		sessionStorage["diaMenorConsumo"]=null;
		sessionStorage["result"]=null;
		sessionStorage["mes"]=null;
		sessionStorage["año"]=null;
	});
	
});