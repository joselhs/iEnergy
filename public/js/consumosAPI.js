//Función que dibuja la gráfica del consumo en cada día del periodo de facturación
function dibujaChartConsumoDiasPeriodoFacturacion(consumosArray, dias, selector){
	var chartConsumosPeriodoFacturacion = new Highcharts.Chart({
				
		chart:{
			renderTo:selector
		},

		title: {
			text: ''
		},
		      
		yAxis: {
		    title: {
		    	text: 'Consumo (kWh)'
		    }
		},
		      
		plotOptions: {
			series: {
				borderWidth: 0,
		        dataLabels: {
		        	enabled: false
		        },
		        marker: {
		        	enabled: false
		        }
		    }
		},
		legend: {
			enabled: true
		},

	   	exporting: {
			buttons: {
		    	contextButton: {
	            	enabled: false
	           	}    
	      	}
	  	},
		    
		tooltip: {
			valueSuffix: 'kWh',
			useHTML: true
		},
		    
		xAxis: {
			categories: dias,
		 	labels: {
		 		rotation: -45
		  	}
		},
		    
		series: [{
			name: "Consumos",
		   	type: "column",
		   	color:'#F46C3C',
		   	data: consumosArray
		}]
		    
	});
}