<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar Sesión</title>
<!-- Agrega la referencia a los archivos de Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- Agrega la referencia a los archivos de SweetAlert -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/10.16.3/sweetalert2.min.css">
<!-- Agrega tu archivo de estilos -->
<link rel="stylesheet" href="styles.css">
<!-- Agrega las referencias a los scripts de jQuery, Popper.js, Bootstrap y SweetAlert -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/10.16.3/sweetalert2.min.js"></script>
<!-- Estilos -->
<style>
body {
	background-color:white ;
}

.container {
	max-width: 600px;
	margin: 0 auto;
	background: radial-gradient(circle, #1c2e2e, #000000);
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
	color: #fff;
	
}
.form-group {
    position: relative;
    z-index: 999;
}
.form-group label {
	font-weight: bold;
}

.btn-primary {
	background-color: #007bff;
	border-color: #007bff;
}

.btn-primary:hover {
	background-color: #0069d9;
	border-color: #0062cc;
}

.btn-primary:focus {
	box-shadow: 0 0 0 0.2rem rgba(38, 143, 255, 0.5);
}

.text-info {
	color: #17a2b8;
}
.circles{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
    z-index: 2; 
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
</head>
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


	<div class="container mt-5">
		<h1 class="text-center mb-4">Iniciar Sesión</h1>

		<form id="loginForm" method="post">
			<div class="form-group">
				<label for="email">Email:</label> <input type="email" name="email"
					id="email" class="form-control" required>
			</div>
			<div class="form-group">
				<label for="password">Contraseña:</label> <input type="password"
					name="password" id="password" class="form-control" required>
			</div>
			<input type="submit" value="Iniciar Sesión"
				class="btn btn-primary btn-lg btn-block mt-3" style="background-color: gray;">
				
				<p class="mt-3"style="position: relative; z-index: 999;">
				¿No estás registrado? <a
				href="${pageContext.request.contextPath}/registro" class="text-info">Regístrate
				aquí</a>.
			</p>
		</form>

		
	</div>

	<script>
		
	<%String status = (String) request.getAttribute("status");%>
		
	<%if (status != null && status.equals("error")) {%>
		Swal.fire({
			title : "Error",
			text : "Credenciales incorrectas. Por favor, inténtalo de nuevo.",
			icon : "error",
			confirmButtonText : "OK"
		});
	<%}%>
		
	</script>
	
	
</body>
</html>
