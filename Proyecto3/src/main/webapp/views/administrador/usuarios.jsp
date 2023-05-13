<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Administrar Clientes</title>
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
<!-- SweetAlert -->
<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.3/dist/sweetalert2.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.3/dist/sweetalert2.min.css">

<style type="text/css">
/* 	Estilos botones */
.btn-primary:hover {
	background-color: #0069d9;
	border-color: #0062cc;
}

.btn-outline-danger:hover {
	color: #fff;
	background-color: #dc3545;
	border-color: #dc3545;
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
	
	<!-- Boton Volver al Index -->
	<button type="button" class="btn btn-primary"
		onclick="window.location.href='administrador?accion=volver'"
		style="margin-left: 20px; margin-top: 30px">
		<i class="fa fa-arrow-left"></i> Volver atrás
	</button>
	

	<!-- Titulo JSP administrador -->


	<div class="col-lg-12 p-5">
		<h1>
			<i class='fas fa-users'></i> Administrar Clientes
		</h1>
		<hr />
	</div>

<div class="container">
	<h1>Listado de Clientes</h1>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>ID</th>
				<th>Email</th>
				<th>Password</th>
				<th>Saldo</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="usuario" items="${usuarios}">
				<tr>
					<td>${usuario.id}</td>
					<td>${usuario.email}</td>
					<td>${usuario.password}</td>
					<td>${usuario.saldo}</td>
					<td>
						<button type="button" class="btn btn-sm btn-primary btn-hover"
							data-toggle="modal"
							data-target="#editarUsuarioModal${usuario.id}">
							<i class="fa fa-pencil"></i>
						</button>
						<button type="button" class="btn btn-danger btn-hover"
							data-toggle="tooltip" data-placement="top" title="Eliminar"
							onclick="confirmarEliminarCliente(${usuario.id})">
							<i class="fa fa-trash-o"></i>
						</button>
					</td>
				</tr>
				<!-- Modal de edición para cada usuario -->
				<div class="modal fade" id="editarUsuarioModal${usuario.id}"
					tabindex="-1" role="dialog"
					aria-labelledby="editarUsuarioModalLabel${usuario.id}"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title"
									id="editarUsuarioModalLabel${usuario.id}">Editar usuario</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Cerrar">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<form
									action="administrador?accion=editarCliente&id=${usuario.id}"
									method="POST">
									<div class="form-group">
										<label for="email">Email</label> <input type="email"
											class="form-control" id="email" name="email"
											value="${usuario.email}" required>
									</div>
									<div class="form-group">
										<label for="password">Contraseña</label> <input
											type="password" class="form-control" id="password"
											name="password" value="${usuario.password}" required>
									</div>
									<div class="form-group">
										<label for="saldo">Saldo</label> <input type="number"
											class="form-control" id="saldo" name="saldo"
											value="${usuario.saldo}" required>
									</div>
									<button type="submit" class="btn btn-primary">Guardar cambios</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5">
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#nuevoUsuarioModal">Crear cliente</button>
				</td>
			</tr>
		</tfoot>
	</table>
</div>






	<!-- Modal para crear un nuevo usuario -->
	<div class="modal fade" id="nuevoUsuarioModal" tabindex="-1"
		role="dialog" aria-labelledby="nuevoUsuarioModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="nuevoUsuarioModalLabel">Crear
						nuevo usuario</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Cerrar">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="nuevoUsuarioForm"
						action="administrador?accion=crearCliente" method="POST">
						<div class="form-group">
							<label for="email">Email</label> <input type="email"
								class="form-control" id="email" name="email"
								placeholder="Ingrese el email del nuevo usuario">
						</div>
						<div class="form-group">
							<label for="password">Password</label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="Ingrese el password del nuevo usuario">
						</div>
						<div class="form-group">
							<label for="saldo">Saldo</label> <input type="number"
								class="form-control" id="saldo" name="saldo"
								placeholder="Ingrese el saldo del nuevo usuario">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Cerrar</button>
					<button type="submit" class="btn btn-primary"
						form="nuevoUsuarioForm">Guardar</button>
				</div>
			</div>
		</div>
	</div>


	<!-- Script eliinacion Cliente -->
	<script>
	function confirmarEliminarCliente(id) {
		  Swal.fire({
		    title: '¿Está seguro?',
		    text: "¡No podrás revertir esto!",
		    icon: 'warning',
		    showCancelButton: true,
		    confirmButtonColor: '#3085d6',
		    cancelButtonColor: '#d33',
		    confirmButtonText: 'Sí, eliminar!',
		    cancelButtonText: 'Cancelar'
		  }).then((result) => {
		    if (result.isConfirmed) {
		      // Aquí puedes hacer una petición AJAX para eliminar el cliente
		      // y mostrar una notificación de éxito si se completa correctamente.
		      window.location.href = "administrador?accion=eliminarCliente&id=" + id;
		      Swal.fire(
		        '¡Eliminado!',
		        'El cliente ha sido eliminado.',
		        'success'
		      )
		    }
		  })
		}
</script>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
		crossorigin="anonymous"></script>
</body>
</html>
