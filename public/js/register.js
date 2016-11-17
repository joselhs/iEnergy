$(document).ready(function(){
	
	$("#register-form").submit(function(e) {
		e.preventDefault();
	}).validate({
		debug: false,
		rules: {
			"firstname": {
				required: true
			},
			"lastname": {
				required: true
			},
			"email": {
				required: true,
				email: true
			},
			"password": {
				required: true,
				minlength: 6
			}
		},
		messages: {
			"firstname":{
				required:"Introduce tu nombre"
			},
			"lastname":{
				required:"Introduce tus apellidos"
			},
			"email":{
				required:"Introduce tu email",
				email:""
			},
			"password":{
				required:"Introduce tu contraseña",
				minlength:"Tu contraseña debe tener al menos 6 dígitos"
			}
			
		}
	});	
});