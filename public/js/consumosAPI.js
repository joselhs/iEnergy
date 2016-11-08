//##################################################################################
	//############################# CHART MEDIAS DIAS MES ##############################
	//##################################################################################
	
	//Función que calcula el consumo acumulado en cada día del periodo de facturación
	function calculaConsumoDiasPeriodoFacturacion(resultsArray){
		var consumoDias='[';
		var firstTime=true;
		var count=1;
		for(var i=0;i<=resultsArray.length-1;i++){
			var dateStr = resultsArray[i].data[0].Fecha.split("/");

			var sumatorioDia=0.0;
			for(var j=0;j<=resultsArray[i].data.length-1;j++){
				if(resultsArray[i].data[j].Fecha != null){
					sumatorioDia=sumatorioDia+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
					if(resultsArray[i].data[j].Hora == "24"){
						if(firstTime){
							firstTime=false;
							consumoDias=consumoDias+parseFloat(sumatorioDia.toFixed(1));
							sumatorioDia=0.0;
						}else{
							consumoDias=consumoDias+','+parseFloat(sumatorioDia.toFixed(1));
							sumatorioDia=0.0;
						}
						count++;
					}
				}
			}

			break;
		}

		consumoDias=consumoDias+"]";
		return JSON.parse(consumoDias);
	}
	
	//función que obtiene los días del periodo de facturación para ponerlos como categorías del chart
	//o para calcular el ahorro de un periodo
	function getDiasPeriodo(resultsArray){
		dias=[];

		for(var i=0;i<=resultsArray.length-1;i++){
			var dateStr = resultsArray[i].data[0].Fecha.split("/");
			
			for(var j=0;j<=resultsArray[i].data.length-1;j++){
				if(resultsArray[i].data[j].Fecha != null && resultsArray[i].data[j].Hora == "24"){
					dias.push(resultsArray[i].data[j].Fecha);
				}
			}
		}

		return dias;
	}


//Función que dibuja la gráfica del consumo en cada día del periodo de facturación
function dibujaChartConsumoDiasPeriodoFacturacion(consumosArray, dias, selector){
	var chartConsumosPeriodoFacturacion = new Highcharts.Chart({
				
		chart:{
			renderTo:selector
		},

		title: {
			text: 'Consumos por cada día del periodo de facturación'
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






//Función que calcula la fecha mínima en el datepicker
function calculateMinDate(resultsArray){
	var minDate = resultsArray[0].data[0].Fecha;

	for(var i=0;i<resultsArray.length;i++){
		var objectMinDate = resultsArray[i].data[0].Fecha;
		var objectMinDateStr = objectMinDate.split("/");
		var minDateStr = minDate.split("/");

		if(parseInt(minDateStr[2])<parseInt(objectMinDateStr[2])){
			minDate=minDate;
		}else if(parseInt(minDateStr[2])>parseInt(objectMinDateStr[2])){
			minDate=objectMinDate;
		}else{
			if(parseInt(minDateStr[1])<parseInt(objectMinDateStr[1])){
				minDate=minDate;
			}else if(parseInt(minDateStr[1])>parseInt(objectMinDateStr[1])){
				minDate=objectMinDate;
			}else{
				if(parseInt(minDateStr[0])<parseInt(objectMinDateStr[0])){
					minDate=minDate;
				}else{
					minDate=objectMinDate;
				}
			}
		}	
	}
	return minDate;
}

//Función que calcula la fecha máxima en el datepicker
function calculateMaxDate(resultsArray){
	var maxDate = resultsArray[0].data[resultsArray[0].data.length-10].Fecha;

	for(var i=0;i<resultsArray.length;i++){
		var objectMaxDate = resultsArray[i].data[resultsArray[i].data.length-10].Fecha;
		var objectMaxDateStr = objectMaxDate.split("/");
		var maxDateStr = maxDate.split("/");

		if(parseInt(maxDateStr[2])<parseInt(objectMaxDateStr[2])){
			maxDate=objectMaxDate;
		}else if(parseInt(maxDateStr[2])>parseInt(objectMaxDateStr[2])){
			maxDate=maxDate;
		}else{
			if(parseInt(maxDateStr[1])<parseInt(objectMaxDateStr[1])){
				maxDate=objectMaxDate;
			}else if(parseInt(maxDateStr[1])>parseInt(objectMaxDateStr[1])){
				maxDate=maxDate;
			}else{
				if(parseInt(maxDateStr[0])<parseInt(objectMaxDateStr[0])){
					maxDate=objectMaxDate;
				}else{
					maxDate=maxDate;
				}
			}
		}	
	}
	return maxDate;
}


//Función para sustituir la coma por punto (En CSV los valores de consumo vienen en formato con coma)
function replaceComa(value){
	if(value != null && value != undefined){
		value=value.replace(",",".");
	}
	
	return value;
}






//##################################################################################
//######################### CHART MEDIAS DIAS DE LA SEMANA #########################
//##################################################################################


//Función que calcula el consumo medio para cada día de la semana
function calculaMediasDiasSemana(resultsArray){
	var dayMeans=[0.0,0.0,0.0,0.0,0.0,0.0,0.0];
	var horasLunes = 0.0;
	var horasMartes = 0.0;
	var horasMiercoles = 0.0;
	var horasJueves = 0.0;
	var horasViernes = 0.0;
	var horasSabado = 0.0;
	var horasDomingo = 0.0;
	var lunes=0;
	var martes=0;
	var miercoles=0; 
	var jueves=0;
	var viernes=0;
	var sabados=0;
	var domingos=0;
	
	for(var i=0;i<=resultsArray.length-1;i++){
		for(var j=0;j<=resultsArray[i].data.length-1;j++){
			if(resultsArray[i].data[j].Fecha != null){
				dateStr = resultsArray[i].data[j].Fecha.split("/");
				
				//Si hora=24, entonces ya cambiamos de día, así que guardamos el consumo total de las horas en su
				//posición correspondiente de array,reseteamos el sumatorio a 0 e incrementamos el contador de días en 1;
				switch (new Date(dateStr[2], dateStr[1]-1, dateStr[0]).getDay()) {
				    case 0:
				        horasDomingo=horasDomingo+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        if(resultsArray[i].data[j].Hora == "24"){
				        	dayMeans[6]=dayMeans[6]+horasDomingo;
				        	horasDomingo=0.0;
				        	domingos++;
				        }
				        break;
				    case 1:
				    	horasLunes=horasLunes+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	dayMeans[0]=dayMeans[0]+horasLunes;
				        	horasLunes=0.0;
				        	lunes++;
				        }
				    	break;
				    case 2:
				    	horasMartes=horasMartes+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	dayMeans[1]=dayMeans[1]+horasMartes;
				        	horasMartes=0.0;
				        	martes++;
				        }
				    	break;
				    case 3:
				    	horasMiercoles=horasMiercoles+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	dayMeans[2]=dayMeans[2]+horasMiercoles;
				        	horasMiercoles=0.0;
				        	miercoles++;
				        }
				    	break;
				    case 4:
				    	horasJueves=horasJueves+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	dayMeans[3]=dayMeans[3]+horasJueves;
				        	horasJueves=0.0;
				        	jueves++;
				        }
				    	break;
				    case 5:
				    	horasViernes=horasViernes+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	dayMeans[4]=dayMeans[4]+horasViernes;
				        	horasViernes=0.0;
				        	viernes++;
				        }
				    	break;
				    case 6:
				    	horasSabado=horasSabado+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	dayMeans[5]=dayMeans[5]+horasSabado;
				        	horasSabado=0.0;
				        	sabados++;
				        }
				    	break;
				}
			}
		}
	}
	
	dayMeans[0]=parseFloat((dayMeans[0]/lunes).toFixed(2));
	dayMeans[1]=parseFloat((dayMeans[1]/martes).toFixed(2));
	dayMeans[2]=parseFloat((dayMeans[2]/miercoles).toFixed(2));
	dayMeans[3]=parseFloat((dayMeans[3]/jueves).toFixed(2));
	dayMeans[4]=parseFloat((dayMeans[4]/viernes).toFixed(2));
	dayMeans[5]=parseFloat((dayMeans[5]/sabados).toFixed(2));
	dayMeans[6]=parseFloat((dayMeans[6]/domingos).toFixed(2));

	return dayMeans;
}


//Funcion que dibuja el chart del consumo medio de cada día
function dibujaChartMediasDiasSemana(consumosArray,selector){
	var chartConsumosMediasDiasSemana = new Highcharts.Chart({
		
		chart:{
			renderTo: selector
		},
		title: {
          text: 'Consumo medio según día de la semana'
      },
      
      yAxis: {
        title: {
            text: 'Consumo (kWh)'
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
                enabled: false
            },
            marker: {
                enabled: false
            }
        }
      },
	  tooltip: {
		  valueSuffix: 'kWh'
	  },

      xAxis: {
          categories: ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo']
      },

      series: [{
      	name: "Consumo medio",
      	type: "column",
      	color:'#F46C3C',
        data: [consumosArray[0],consumosArray[1],consumosArray[2],consumosArray[3],
               consumosArray[4],consumosArray[5],consumosArray[6]]
      }]
  });
}


//##################################################################################
//############################# CONSUMOS POR HORAS DIA #############################
//##################################################################################
	function dibujaChartConsumoHoras(dataset,fecha,selector){

		dataset = JSON.parse(dataset);
		
		var chartConsumoHoras = new Highcharts.Chart({
			
			chart: {
      		type: 'area',
      		renderTo: selector
		    },
			title: {
	            text: 'Consumos por hora del día'
	        },

	        subtitle: {
	            text: "Fecha: "+fecha
	        },
	        yAxis: {
	          max: getMax(dataSet)+0.02,
	          min: 0,
	          title: {
	              text: 'Consumo (kWh)'
	          },
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
		                  enabled: true,
		                  format: '{point.y:.3f}'
		              },
		              marker: {
		                  enabled: false
		              }
		          }
		    },
		    tooltip: {
	            valueSuffix: 'kWh'
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
	        	name: "Consumo por Horas",
	        	dataLabels:false,
	        	color: '#F46C3C',
	            data: [dataset[0],dataset[1],dataset[2],dataset[3],dataset[4],dataset[5],dataset[6],dataset[7],
				       dataset[8],dataset[9],dataset[10],dataset[11],dataset[12],dataset[13],dataset[14],dataset[15],
				       dataset[16],dataset[17],dataset[18],dataset[19],dataset[20],dataset[21],dataset[22],dataset[23]]
	        }]
		        
		});

	
	}
	
//Funcion para obtener máximo valor del csv para dibujar la gráfica adecuadamente
function getMax(dataSet){
	//Usamos como primer máximo cualquier valor, por ejemplo el primer elemento
	var max = dataSet[0];
		
	for (var i=0; i<dataSet.length; i++) {
		if(dataSet[i] > max){
			max = dataSet[i];
		}
	}
	return max;
}
	

//Funcion Crear Data Set de consumos para un día concreto
function createDayDataSet(fechaStr,resultsArray){
	var dataSet = [];
	
	for(var j=0;j<=resultsArray.length-1;j++){
		
		for(var i=0;i<=resultsArray[j].data.length-1;i++){
			if(resultsArray[j].data[i].Fecha == fechaStr){
				dataSet.push(parseFloat(replaceComa(resultsArray[j].data[i].Consumo_kWh)));
			}
		}
	}
	
	return dataSet;
}



//##################################################################################
//################################# OTRAS GRAFICAS #################################
//##################################################################################

function calculaConsumosDiasSemana(resultsArray){
	var consumosDias=[0.0,0.0,0.0,0.0,0.0,0.0,0.0];
	var horasLunes = 0.0;
	var horasMartes = 0.0;
	var horasMiercoles = 0.0;
	var horasJueves = 0.0;
	var horasViernes = 0.0;
	var horasSabado = 0.0;
	var horasDomingo = 0.0;
	
	for(var i=0;i<=resultsArray.length-1;i++){
		for(var j=0;j<=resultsArray[i].data.length-1;j++){
			if(resultsArray[i].data[j].Fecha != null){
				dateStr = resultsArray[i].data[j].Fecha.split("/");
				
				//Si hora=24, entonces cambiamos de día, así que guardamos el consumo total de las horas en su
				//posición correspondiente de array,reseteamos el sumatorio a 0

				switch (new Date(dateStr[2], dateStr[1]-1, dateStr[0]).getDay()) {
				    case 0:
				        horasDomingo=horasDomingo+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        if(resultsArray[i].data[j].Hora == "24"){
				        	consumosDias[6]=consumosDias[6]+horasDomingo;
				        	horasDomingo=0.0;
				        }
				        break;
				    case 1:
				    	horasLunes=horasLunes+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	consumosDias[0]=consumosDias[0]+horasLunes;
				        	horasLunes=0.0;
				        }
				    	break;
				    case 2:
				    	horasMartes=horasMartes+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	consumosDias[1]=consumosDias[1]+horasMartes;
				        	horasMartes=0.0;
				        }
				    	break;
				    case 3:
				    	horasMiercoles=horasMiercoles+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	consumosDias[2]=consumosDias[2]+horasMiercoles;
				        	horasMiercoles=0.0;
				        }
				    	break;
				    case 4:
				    	horasJueves=horasJueves+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	consumosDias[3]=consumosDias[3]+horasJueves;
				        	horasJueves=0.0;
				        }
				    	break;
				    case 5:
				    	horasViernes=horasViernes+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	consumosDias[4]=consumosDias[4]+horasViernes;
				        	horasViernes=0.0;
				        }
				    	break;
				    case 6:
				    	horasSabado=horasSabado+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				    	if(resultsArray[i].data[j].Hora == "24"){
				        	consumosDias[5]=consumosDias[5]+horasSabado;
				        	horasSabado=0.0;
				        }
				    	break;
				}
			}
		}
	}

	return consumosDias;
}


//Dibuja un donut chart con el porcentaje de consumo en dias laborales y en fines de semana
//Recibe un arrayCon los consumos de todos 
function dibujaChartPorcentajeConsumoLaborablesFinSemana(consumosArray, selector){
	Highcharts.setOptions({
     colors: ['#F77502', '#F70223']
    });

	var porcentajeLaborables =  porcentajeConsumoDiasLaborables(consumosArray);
	
	 var chartPorcentajes = new Highcharts.Chart({
		chart: {
            type: 'pie',
            renderTo: selector
    	},
    	title: {
            text: 'Porcentaje de consumo en días laborables/fin de semana',
            style: {
            	//"font-size" : "10px"
            }
        },
        plotOptions: {
            pie: {
                borderColor: '#000000',
                innerSize: '60%',
                dataLabels: {
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
        series: [{
        	name: '%',
        	color:'#F46C3C',
            data: [
                ['Fin de semana', parseFloat((100-porcentajeLaborables).toFixed(2))],
                ['Laborables', porcentajeLaborables]
            ]
        }]
    });
}

//Fúnción que calcula el porcentaje de consumo en días laborables 
//(Podemos obtener el de los fines de semana haciendo la operacion 100-porcentajeConsumoDiasLaborables)
function porcentajeConsumoDiasLaborables(consumosArray){
	var consumoTotal=0.0;
	var consumoLaborables=0.0;
	var porcentajeConsumoLaborables=0.0;
	
	for(var i=0;i<consumosArray.length;i++){
		consumoTotal+=consumosArray[i];
		if(i != 5 && i != 6){
			consumoLaborables+=consumosArray[i];
		}
	}
	
	return parseFloat(((consumoLaborables*100)/consumoTotal).toFixed(2));
}


function calculaPorcentajeTramoBarato(resultsArray){
	horas=[1,2,3,4,5,6,7,8,9,10,11,12,23,24];

	var consumosHoras;
	var consumoTotal;

	consumosHoras = calculaConsumosAcumuladosHoras(resultsArray);
	consumoTotal = getConsumoTotal(resultsArray);

	var consumoAcumuladoHoras = 0.0;

	for(var i=0;i<horas.length;i++){
		consumoAcumuladoHoras+=consumosHoras[horas[i]-1];
	}

	var porcentaje = (consumoAcumuladoHoras*100)/consumoTotal;

	return porcentaje.toFixed(2);
}



function calculaConsumosAcumuladosHoras(resultsArray){
	var consumosHoras=[0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0];
	for(var i=0;i<=resultsArray.length-1;i++){
		for(var j=0;j<=resultsArray[i].data.length-1;j++){
				
				switch (resultsArray[i].data[j].Hora) {
				    case "1":
				        consumosHoras[0]=consumosHoras[0]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "2":
				    	consumosHoras[1]=consumosHoras[1]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "3":
				    	consumosHoras[2]=consumosHoras[2]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "4":
				    	consumosHoras[3]=consumosHoras[3]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "5":
				    	consumosHoras[4]=consumosHoras[4]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "6":
				    	consumosHoras[5]=consumosHoras[5]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "7":
				    	consumosHoras[6]=consumosHoras[6]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "8":
				    	consumosHoras[7]=consumosHoras[7]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "9":
				        consumosHoras[8]=consumosHoras[8]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "10":
				    	consumosHoras[9]=consumosHoras[9]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "11":
				    	consumosHoras[10]=consumosHoras[10]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "12":
				    	consumosHoras[11]=consumosHoras[11]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "13":
				    	consumosHoras[12]=consumosHoras[12]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "14":
				    	consumosHoras[13]=consumosHoras[13]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "15":
				    	consumosHoras[14]=consumosHoras[14]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "16":
				    	consumosHoras[15]=consumosHoras[15]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "17":
				        consumosHoras[16]=consumosHoras[16]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "18":
				    	consumosHoras[17]=consumosHoras[17]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "19":
				    	consumosHoras[18]=consumosHoras[18]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "20":
				    	consumosHoras[19]=consumosHoras[19]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "21":
				    	consumosHoras[20]=consumosHoras[20]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "22":
				    	consumosHoras[21]=consumosHoras[21]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "23":
				    	consumosHoras[22]=consumosHoras[22]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				    case "24":
				    	consumosHoras[23]=consumosHoras[23]+parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
				        break;
				}
		}
	}

	return consumosHoras;
}


function getConsumoTotal(resultsArray){
	var consumoTotal=0.0;
	for(var i=0;i<=resultsArray.length-1;i++){
		for(var j=0;j<resultsArray[i].data.length-1;j++){
			consumoTotal+=parseFloat(replaceComa(resultsArray[i].data[j].Consumo_kWh));
		}
	}

	return consumoTotal;
}


function dibujaChartPorcentajeConsumoHorasBaratas(resultsArray, selector){
	var porcentajeHorarioBarato =  calculaPorcentajeTramoBarato(resultsArray);

	Highcharts.setOptions({
     colors: ['#F77502', '#F70223']
    });
	
	var chartPorcentajesDiscriminacion = new Highcharts.Chart({
		chart: {
            type: 'pie',
            renderTo: selector
    	},
    	title: {
            text: 'Porcentaje de consumo en tramo barato/caro',
            style: {
            	//"font-size" : "10px"
            }
        },
        legend: {
        	enabled: true
    	},
        plotOptions: {
            pie: {
                borderColor: '#000000',
                innerSize: '60%',
                dataLabels: {
                    enabled: false
                }
            }
        },
        exporting: {
	        buttons: {
	            contextButton: {
                	enabled: false
            	}    
        	}
    	},
        series: [{
        	name: '%',
            data: [
                ['Tramo barato', parseFloat(porcentajeHorarioBarato)],
                ['Tramo caro', parseFloat((100-porcentajeHorarioBarato).toFixed(2))]
            ]
        }]
    });
}