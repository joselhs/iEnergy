$(document).ready(function(){
	
	var isLoaded = sessionStorage["loadedCSV"];
	
	$("#nocsv").css("display","none");
	$("#recomendaciones-consumo").css("display","none");
	
	if(isLoaded){
		var porcentajeLaborales = sessionStorage["porcentajeLaborables"];
		var porcentajeTramoBarato = sessionStorage["porcentajeTramoBarato"];
		var diaMenorConsumo = sessionStorage["diaMenorConsumo"];
		var diaMayorConsumo = sessionStorage["diaMayorConsumo"];
		var año = sessionStorage["año"];
		var mes = sessionStorage["mes"];
		
		var diaMasBarato = $("#diaMasBarato").val();
		var diaMasCaro = $("#diaMasCaro").val();
		
		if(diaMasBarato == diaMayorConsumo){
			$("#diaMayorConsumo").html("<strong>Enhorabuena!</strong> El día que más consumes (<span class='barato'>"+diaMayorConsumo+"</span>) es el día con los precios <strong>más baratos</strong> de media, sigue así, <strong>estás ahorrando</strong>!!");
		}else{
			if(diaMasCaro == diaMayorConsumo){
				$("#diaMayorConsumo").html("<strong>Cuidado!</strong> El día que más consumes (<span class='caro'>"+diaMayorConsumo+"</span>) es el día con los precios <strong>más caros</strong>, deberías intentar evitar consumir demasiado este día.");
			}else{
				$("#diaMayorConsumo").html("El día que más consumes es el <strong>"+diaMayorConsumo+"</strong>, el día más barato es el <span class='barato'>"+diaMasBarato+"</span>.");
			}
		}
		
		if(diaMasCaro == diaMenorConsumo){
			$("#diaMenorConsumo").html("<strong>Enhorabuena!</strong> El día que menos consumes (<span class='barato'>"+diaMenorConsumo+"</span>) es el día con los precios <strong>más caros</strong> de media, sigue así, <strong>estás ahorrando</strong>!!");
		}else{
			if(diaMasBarato == diaMenorConsumo){
				$("#diaMenorConsumo").html("<strong>Cuidado!</strong> El día que menos consumes (<span class='caro'>"+diaMenorConsumo+"</span>) es el día con los precios <strong>más baratos</strong>, deberías intentar consumir más este día de la semana.");
			}else{
				$("#diaMenorConsumo").html("El día que menos consumes es el <strong>"+diaMenorConsumo+"</strong>, el día más caro es el <span class='caro'>"+diaMasCaro+"</span>.");
			}
		}
		
		if(parseFloat(porcentajeTramoBarato) > 35){
			$("#tramoBarato").html("Consumes un <strong class='barato'>"+porcentajeTramoBarato+"%</strong> de tu energía entre las 23:00 y las 12:00, si aún no estás, deberías pensar en cambiarte a <strong>discriminación horaria</strong>.");
		}else{
			$("#tramoBarato").html("Solo consumes un <stron clas='caro'>"+porcentajeTramoBarato+"% entre las 23:00 y las 12:00. Si aumentas tu consumo en este horario y te pasas a <strong>discriminación horaria</strong> estarás ahorrando.");
		}
		
		$("#laborales").html("Tu consumo de Lunes a Viernes es del <strong>"+porcentajeLaborales+"%</strong>. Mientras más consumas en <span class='barato'>fin de semana</span>, más ahorrarás.");
		
		
		$("#nocsv").css("display","none");
		$("#recomendaciones-consumo").css("display","inline");
		
		$("#resumen-fecha").html(mes+" del "+año);
	}else{
		$("#nocsv").css("display","inline");
		$("#recomendaciones-consumo").css("display","none");
	}
	
	
});