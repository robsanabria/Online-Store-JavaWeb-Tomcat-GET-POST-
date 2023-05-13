<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="ar.roberto.proyecto3.model.Cliente"%>
<%@ page import="ar.roberto.proyecto3.service.daos.ClienteDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Panel Saldo</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
<!-- icons -->
<script src='https://kit.fontawesome.com/a076d05399.js'
	crossorigin='anonymous'></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">

<!-- jQuery -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- Bootstrap JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.min.js"></script>
<!-- SweetAlert CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	integrity="sha512-4o4SY4MigLJj+7dbiml1OPdOJnnlI+nOoZtjK+13X9pwWhJ8OQFXIyj3LsDhef67M24RtjJkzGss89qNq/m7Ig=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />

<!-- SweetAlert JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"
	integrity="sha512-5GkH0rtdcW8CYXgtyLCry0jKvTY9Hyl+h2Q3kK1Zjq2iOoDz+0HbRfRJyFAsPvC9XcWlGq3jK2egUCQzN25NLw=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</head>
<body>
	<!-- Navbar -->

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="admin">
				<h3>
					</h1>
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">

				<ul class="navbar-nav ms-auto">
					<li class="nav-item">
						<button type="button"
							class="btn btn-outline-primary dropdown-toggle"
							data-bs-toggle="dropdown" aria-expanded="false">
							<i class='far fa-user-circle' style='font-size: 24px'></i>
						</button>
						<ul class="dropdown-menu dropdown-menu-end">

							<li><a class="dropdown-item disabled" href="#"> <%=session.getAttribute("usuarioEmail")%></a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="cliente?accion=exit"><i
									class="fa fa-power-off"></i>Cerrar sesión</i> </a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- Btn volver -->
	<button type="button" class="btn btn-primary"
		onclick="window.location.href='cliente?accion=volver'"
		style="margin-left: 20px; margin-top: 30px;">
		<i class="fa fa-arrow-left"></i> Volver atrás
	</button>

	<div class="col-lg-12 p-5">
		<h1>
			<i class="fa fa-money" style="font-size: 24px"> </i> Saldo
		</h1>
		<hr />
	</div>

	<div class="container d-flex justify-content-center">
		<div class="card">
			<div class="card-header">Datos de su Saldo  :</div>
			<div class="card-body">
				<h5 class="card-title">Saldo actual: ${saldo}</h5>
				<p class="card-text">Puedes realizar las siguientes acciones:</p>
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#sumarSaldoModal">Sumar Saldo</button>
				<button type="button" class="btn btn-primary" data-bs-toggle="modal"
					data-bs-target="#transferModal">Transferir</button>
			</div>
		</div>
	</div>




	<!-- Modal enviar saldo -->
	<div class="modal fade" id="sumarSaldoModal" tabindex="-1"
		role="dialog" aria-labelledby="sumarSaldoModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="sumarSaldoModalLabel">Sumar Saldo</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="cliente?accion=sumarSaldo" method="post">
						<div class="form-group">
							<label for="cantidad">Cantidad:</label> <input type="number"
								class="form-control" id="cantidad" name="cantidad" required>
						</div>
						<button type="submit" class="btn btn-primary">Sumar Saldo</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="transferModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Transferir
						saldo</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="cliente?accion=transferir" method="post">
						<div class="mb-3">
							<label for="destinatario" class="form-label">Destinatario:</label>
							<select class="form-control" id="destinatario"
								name="destinatario" required>
								<option value="">Selecciona un destinatario</option>
								<%
								for (Cliente cliente : ClienteDAO.obtenerTodosLosClientes()) {
								%>
								<option value="<%=cliente.getEmail()%>"><%=cliente.getEmail()%></option>
								<%
								}
								%>
							</select>
						</div>
						<div class="mb-3">
							<label for="monto" class="form-label">Monto a enviar:</label> <input
								type="number" class="form-control" id="monto" name="monto"
								min="0" step="0.01" required>
						</div>
						<button type="submit" class="btn btn-primary">Transferir</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Script notificaciones -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

	<input type="hidden" id="status"
		value="<%=request.getAttribute("status")%>">
	<script type="text/javascript">
		var status = document.getElementById("status").value;
		if (status == "success") {
			swal("Felicitaciones", "Su saldo fue actualizado", "success");
		} else if (status == "transferenciaOk") {
			swal("Atencion", "La transferencia se realizo con exito", "success");
		} else if (status == "error") {
			swal("Error", "No se pudo realizar la transferencia, saldo insuficiente", "error");
		}
	</script>


	<!-- Validacion Enviar Dinero -->
	<script>
		const form = document.querySelector("#sumarSaldoModal form");
		form
				.addEventListener(
						"submit",
						function(event) {
							const cantidad = parseFloat(document
									.querySelector("#cantidad").value);
							if (isNaN(cantidad) || cantidad <= 0) {
								event.preventDefault();
								alert("Por favor ingrese una cantidad válida mayor que cero.");
							}
						});
	</script>

	<!-- jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<!-- Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
</body>
</html>