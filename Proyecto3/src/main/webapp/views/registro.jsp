<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro de usuario</title>
<!-- Agrega la referencia a los archivos de Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!-- Agrega la referencia a los archivos de SweetAlert -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/10.16.3/sweetalert2.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/10.16.3/sweetalert2.min.js"></script>
<!-- Agrega la referencia a los archivos de js/zxcvbn.js -->
<script src="js/zxcvbn/dist/zxcvbn.js"></script>
<!-- Estilo gral -->
<link rel="stylesheet" href="css/formularioRegister.css">
<!-- Estilo validacion campo email -->
<style>
.password-strength-meter {
	height: 10px;
	margin-top: 5px;
}

.password-strength-meter.strength-0 {
	background-color: #d9534f;
}

.password-strength-meter.strength-1 {
	background-color: #f0ad4e;
}

.password-strength-meter.strength-2 {
	background-color: #ffc107;
}

.password-strength-meter.strength-3 {
	background-color: #5cb85c;
}

.password-strength-meter.strength-4 {
	background-color: #5cb85c;
}

.circles{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
    z-index: 1; 
}

.circles li{
    position: absolute;
    display: block;
    list-style: none;
    width: 20px;
    height: 20px;
    background: rgba(255, 255, 255, 0.2);
    animation: animate 25s linear infinite;
    bottom: -150px;
    
}

.circles li:nth-child(1){
    left: 25%;
    width: 80px;
    height: 80px;
    animation-delay: 0s;
}


.circles li:nth-child(2){
    left: 10%;
    width: 20px;
    height: 20px;
    animation-delay: 2s;
    animation-duration: 12s;
}

.circles li:nth-child(3){
    left: 70%;
    width: 20px;
    height: 20px;
    animation-delay: 4s;
}

.circles li:nth-child(4){
    left: 40%;
    width: 60px;
    height: 60px;
    animation-delay: 0s;
    animation-duration: 18s;
}

.circles li:nth-child(5){
    left: 65%;
    width: 20px;
    height: 20px;
    animation-delay: 0s;
}

.circles li:nth-child(6){
    left: 75%;
    width: 110px;
    height: 110px;
    animation-delay: 3s;
}

.circles li:nth-child(7){
    left: 35%;
    width: 150px;
    height: 150px;
    animation-delay: 7s;
}

.circles li:nth-child(8){
    left: 50%;
    width: 25px;
    height: 25px;
    animation-delay: 15s;
    animation-duration: 45s;
}

.circles li:nth-child(9){
    left: 20%;
    width: 15px;
    height: 15px;
    animation-delay: 2s;
    animation-duration: 35s;
}

.circles li:nth-child(10){
    left: 85%;
    width: 150px;
    height: 150px;
    animation-delay: 0s;
    animation-duration: 11s;
}



@keyframes animate {

    0%{
        transform: translateY(0) rotate(0deg);
        opacity: 1;
        border-radius: 0;
    }

    100%{
        transform: translateY(-1000px) rotate(720deg);
        opacity: 0;
        border-radius: 50%;
    }

}

</style>
<body>

<div class="area" >
            <ul class="circles">
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
            </ul>
    </div >

	<input type="hidden" id="status"
		value="<%=request.getAttribute("status")%>">


	<div class="container mt-5" style="">
	
		<form action="registro" method="post"
			style="max-width: 600px; margin: 0 auto; background: radial-gradient(circle, #1c2e2e, #000000); padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5); color: #fff;">
		<h1>Registro de usuario</h1>
			<div class="form-group" style="position: relative;
	z-index: 999;">
				<label for="email">Email:</label> <input type="email" id="email"
					name="email" class="form-control" required
					aria-label="Dirección de correo electrónico" aria-required="true">
			</div>
			<div class="form-group" style="position: relative;
	z-index: 999;">
				<label for="password">Contraseña:</label> <input type="password"
					id="password" name="password" class="form-control" required
					aria-label="Contraseña" aria-required="true"> <br> <label
					for="password-strength">Fortaleza de la contraseña:</label> <input
					type="text" id="password-strength" name="password-strength"
					class="form-control" readonly>
				<div class="password-strength-meter"></div>
			</div>
			<div class="form-group"style="position: relative;
	z-index: 999;">
				<label for="confirmPassword">Confirmar contraseña:</label> <input
					type="password" id="confirm-password" name="confirmPassword"
					class="form-control" required aria-label="Confirmar contraseña"
					aria-required="true">
			</div>
			<div class="form-group" style="position: relative;
	z-index: 999;">
				<label for="tipo-usuario">Tipo de usuario:</label> <select
					id="tipo-usuario" name="tipo-usuario" class="form-control" required
					aria-label="Tipo de usuario" aria-required="true">
					<option value="cliente">Cliente</option>
					<option value="administrador">Administrador</option>
				</select>
			</div>
			<input type="submit" value="Registrar" class="btn btn-primary btn-lg btn-block mt-3"style="background-color: gray;"style="position: relative;z-index: 999;">
			
			<p class="mt-3" position: relative;z-index: 999;>
			Ya estás registrado <a
				href="${pageContext.request.contextPath}/login">Ingresa aquí</a>.
		</p>
		</form>

		
	</div>

	<!-- Javascript (Modal sweetalert)-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<link rel="stylesheet" href="alert/dist/sweetalert.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<script type="text/javascript">
		var status = document.getElementById("status").value;
		if (status == "success") {
			swal("¡Registro exitoso!",
					"El usuario ha sido registrado correctamente.", "success");
		} else if (status == "error") {
			swal(
					"Error en el registro",
					"Hubo un problema al registrar al usuario. Verificar credenciales ingresadas",
					"error");
		}
	</script>
	<!-- Javascript (zxcvbn)-->
	<script>
		$(document).ready(function() {
			$('#password').keyup(function() {
				var password = $(this).val();
				var passwordStrength = zxcvbn(password).score;
				var passwordStrengthText = '';

				if (passwordStrength == 0) {
					passwordStrengthText = 'Muy débil';
				} else if (passwordStrength == 1) {
					passwordStrengthText = 'Débil';
				} else if (passwordStrength == 2) {
					passwordStrengthText = 'Moderada';
				} else if (passwordStrength == 3) {
					passwordStrengthText = 'Fuerte';
				} else if (passwordStrength == 4) {
					passwordStrengthText = 'Muy fuerte';
				}

				$('#password-strength').val(passwordStrengthText);
			});
		});
	</script>

	<!-- Validacion en tiempo real campo email -->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#email').keyup(function() {
				var email = $(this).val();
				var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
				if (!emailRegex.test(email)) {
					$(this).addClass('is-invalid');
				} else {
					$(this).removeClass('is-invalid');
				}
			});
		});
	</script>

	<!-- Validacion en tiempo real campo contraseña-->
	<script>
		$(document)
				.ready(
						function() {
							$('#password')
									.keyup(
											function() {
												var password = $(this).val();
												var passwordStrength = zxcvbn(password).score;
												var passwordStrengthText = '';

												if (passwordStrength == 0) {
													passwordStrengthText = 'Muy débil';
												} else if (passwordStrength == 1) {
													passwordStrengthText = 'Débil';
												} else if (passwordStrength == 2) {
													passwordStrengthText = 'Moderada';
												} else if (passwordStrength == 3) {
													passwordStrengthText = 'Fuerte';
												} else if (passwordStrength == 4) {
													passwordStrengthText = 'Muy fuerte';
												}

												$('#password-strength').val(
														passwordStrengthText);
												$('.password-strength-meter')
														.removeClass()
														.addClass(
																'password-strength-meter')
														.addClass(
																'strength-'
																		+ passwordStrength);
											});
						});
	</script>


</body>
</html>
