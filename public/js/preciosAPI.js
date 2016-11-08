
	
function dibujaChartPreciosHoras(data, precioUsuario, yMax, yMin, selector){
	
	precioUsuario = parseFloat(precioUsuario);
	
	//Creando data para usuario
	dataUsuario = "["+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","
		+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","
		+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","+precioUsuario+","
		+precioUsuario+"]";

	//################ GRAFICA PRECIOS DIA POR HORAS ################
	var chartPreciosHoras = new Highcharts.Chart({
			
		chart: {
	        type: 'line',
	        renderTo: selector
		},
	
		title:{
			text:"Precios del día hora a hora"
		},
		        
		yAxis: {
		    max: parseFloat(yMax)+0.001,
		    min: parseFloat(yMin)-0.001,
			tickInterval: 0.01,
		    title: {
		    	text: 'Precio (€/kWh)'
		    }
		},
	
		legend: {
			enabled: true
	    },
	    
	    tooltip: {
            valueSuffix: '€/kWh'
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
		},
	    {
	        type: 'line',
	        name: 'Precio Usuario',
	        color: "#E88113",
	        dataLabels: false,
	        visible: true,
	        data: JSON.parse(dataUsuario)
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
	  	text:"Precios medios por día de la semana"
	  },
      yAxis: {
      	max: 0.14,
      	min: 0.08,
        title: {
            text: 'Precio (€/kwh)'
        }
      },
      plotOptions: {
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
      tooltip: {
    	  valueSuffix: '€/kWh'
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
      	name: "Precio medio",
      	color: '#4A90E2',
        data: [medias[0],medias[1],medias[2],medias[3],medias[4],medias[5],medias[6]],
        dataLabels: true
      }]
  });
}


//################ GRAFICA PRECIOS MEDIOS POR DIAS DE LA SEMANA ################

function dibujaChartMeses(data, selector){
	
	data = JSON.parse(data);

	var chartMeses = new Highcharts.Chart({
		
		chart: {
			renderTo: selector
		},
		title: {
            text: 'Precios medios por mes del año'
        },

        legend: {
        	enabled: false
    	},
    	tooltip: {
            valueSuffix: '€/kWh'
	    },

        exporting: {
	        buttons: {
	            contextButton: {
                	enabled: true
            	}    
        	}
    	},
        
        yAxis: {
        	max: 0.14,
        	min: 0.06,
        	tickinterval: 0.01,
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
                  format: '{point.y:.3f}'
              },
              marker: {
                  enabled: false
              }
          }
        },

        xAxis: {
            categories: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre']
        },

        series: [{
	        	name: "Precio medio",
	        	color:"#4A90E2",
	        	type: "column",
	        	data: data,
	        	dataLabels: false
	    }]
    });
}
