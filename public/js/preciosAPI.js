
	
function dibujaChartPreciosHoras(data, selector){

	//################ GRAFICA PRECIOS DIA POR HORAS ################
	var chartPreciosHoras = new Highcharts.Chart({
			
		chart: {
	        type: 'line',
	        renderTo: selector
		},
	
		title:{
			text:""
		},
		        
		yAxis: {
		    //max: parseFloat(precioCaroDia)+0.001,
		    //min: parseFloat(precioBaratoDia)-0.001,
			max: 0.17,
			min: 0.05,
			tickInterval: 0.01,
		    title: {
		    	text: 'Precio (€/kwh)'
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
		        
		plotOptions: {
			series: {
		    	borderWidth: 0,
		        	dataLabels: {
		        		enabled: false,
		        		format: '{point.y:.3f}'
		            },
		            marker: {
		                enabled: false
		            }
		    }
		},
	
		xAxis: {
			categories: ['1:00', '2:00', '3:00', '4:00', '5:00', '6:00', '7:00', '8:00', '9:00', '10:00', '11:00', '12:00','13:00','14:00',
		               		'15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00','24:00']
			,
		    labels: {
		    	rotation: -45
		    }
		},
	
		series: [{
			name: "Precio variable(hora a hora)",
		  	type:'line',
		 	dataLabels:false,
		 	color: "#4A90E2",
		  	data: JSON.parse(data)
		}]
	});
}	


//################ GRAFICA PRECIOS MEDIOS POR DIAS DE LA SEMANA ################

function dibujaChartMediasDias(data, selector){
	var medias = JSON.parse(data);

	var chartDiasSemana = new Highcharts.Chart({
	  chart: {
	  	renderTo: selector
	  },

	  title:{
	  	text:""
	  },

      yAxis: {
      	max: 0.14,
      	min: 0.08,
        title: {
            text: 'Precio (€/kwh)'
        }
      },
      
      plotOptions: {
      	column:{
      		stacking:'normal'
      	},
        series: {
            borderWidth: 0,
            dataLabels: {
                enabled: true,
                format: '{point.y:.6f}'
            },
            marker: {
                enabled: false
            }
        }
      },

      legend: {
        	enabled: false
    	},

        exporting: {
	        buttons: {
	            contextButton: {
                	enabled: false
            	}    
        	}
    	},
      xAxis: {
          categories: ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo']
      },

      series: [{
      	name: "Media precios por día de la semana",
      	type: "column",
      	name: "Ahorro",
      	color: '#4A90E2',
        data: [medias[0],medias[1],medias[2],medias[3],medias[4],medias[5],medias[6]],
        dataLabels: false
      }]
  });
}
