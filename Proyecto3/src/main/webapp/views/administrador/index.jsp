<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Panel Administrador</title>
<!-- Bootstrap -->
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
<style>
.card:hover {
  box-shadow: 0px 0px 10px #888888;
  transform: scale(1.05);
  transition: all 0.3s ease;
}

.card {
  background: #D3CCE3;  /* fallback for old browsers */
background: -webkit-linear-gradient(to right, #E9E4F0, #D3CCE3);  /* Chrome 10-25, Safari 5.1-6 */
background: linear-gradient(to right, #E9E4F0, #D3CCE3); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
</style>
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
							<li><a class="dropdown-item"
								href="administrador?accion=exit"><i class="fa fa-power-off"></i>Cerrar
									sesión</i> </a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Titulo JSP administrador -->


	<div class="col-lg-12 p-5">
		<h1>
			<i class="fas fa-tachometer-alt"></i> Panel de Administrador
		</h1>
		<hr />
	</div>

	<div class="container">
		<div class="row row-cols-3">
			<div class="col-md-4">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">
							Clientes <i class='fas fa-users' style='font-size: 24px'></i>
						</h5>
						<p class="card-text">
							<a href="administrador?accion=usuarios">Administre los datos
								de los Clientes.</a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">
							Administradores <i class='fa fa-user' style='font-size: 24px'></i>
						</h5>
						<p class="card-text">
							<a href="administrador?accion=administradores">Gestione los
								datos de los Administradores.</a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">
							Productos <i class='fas fa-gifts' style='font-size: 24px'></i>
						</h5>
						<p class="card-text">
							<a href="administradorProductos?accion=productos">Administre
								los productos.</a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card" style="margin-top: 20px;">
					<div class="card-body">
						<h5 class="card-title">
							Saldos <i class='fas fa-comments-dollar' style='font-size: 24px'></i>
						</h5>
						<p class="card-text">
							<a href="administrador?accion=saldos">Administre los saldos
								de los Clientes.</a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card" style="margin-top: 20px;">
					<div class="card-body">
						<h5 class="card-title">
							Ventas <i class='fas fa-receipt' style='font-size: 24px'></i>
						</h5>
						<p class="card-text">
							<a href="administrador?accion=ventas">Historial y gestion de
								ventas.</a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card" style="margin-top: 20px;">
					<div class="card-body">
						<h5 class="card-title">
							Ventas por producto <i class='fas fa-receipt'
								style='font-size: 24px'></i>
						</h5>
						<p class="card-text">
							<a href="administrador?accion=ventasporProducto">Ventas por
								producto</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
		crossorigin="anonymous"></script>
</body>
</html>