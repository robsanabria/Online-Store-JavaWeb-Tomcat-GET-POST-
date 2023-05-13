<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Administrar Admins</title>
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
			<a class="navbar-brand" href="admin"> </a>
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
			<i class='fa fa-user'></i> Administradores
		</h1>
		<hr />
	</div>

	<div class="container mt-4">
    <h1>Lista de administradores</h1>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Password</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${administradores}" var="administrador">
                <tr>
                    <td>${administrador.id}</td>
                    <td>${administrador.email}</td>
                    <td>${administrador.password}</td>
                    <td>
                        <!-- Botón para abrir el modal de edición -->
                        <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editarModal-${administrador.id}">
                            <i class="fas fa-edit"></i> Editar
                        </button>
                        <!-- Botón para abrir el modal de eliminacion -->
                        <button class="btn btn-sm btn-danger" onclick="eliminarAdministrador(${administrador.id})"><i class="fa fa-trash"></i></button>
                    </td>
                </tr>
                <!-- Modal de edición -->
                <div class="modal fade" id="editarModal-${administrador.id}" tabindex="-1" role="dialog" aria-labelledby="editarModalLabel-${administrador.id}" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                             <form action="administrador?accion=editarAdministrador" method="post">
							                <div class="modal-header">
							                    <h5 class="modal-title" id="editarModalLabel">Editar administrador</h5>
							                    <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
							                        <span aria-hidden="true">&times;</span>
							                    </button>
							                </div>
							                <div class="modal-body">
							                    <div class="form-group">
							                        <label for="email">Email:</label>
							                        <input type="email" class="form-control" id="email" name="email" value="${administrador.email}">
							                    </div>
							                    <div class="form-group">
							                        <label for="password">Password:</label>
							                        <input type="password" class="form-control" id="password" name="password" value="${administrador.password}">
							                    </div>
							                    <input type="hidden" name="id" value="${administrador.id}">
							                </div>
							                <div class="modal-footer">
							                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
							                    <button type="submit" class="btn btn-primary">Guardar cambios</button>
							                </div>
           						 </form>
                            </div>
                           
                        </div>
                    </div>
                </div>
            </c:forEach>
        </tbody>
    </table>
</div>




 <!-- Script notificacion eliminar administrador -->
 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<script>
function eliminarAdministrador(id) {
    Swal.fire({
        title: '¿Está seguro de eliminar este Administrador?',
        text: "¡No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar!',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "administrador?accion=eliminarAdministrador&id=" + id;
            Swal.fire(
                '¡Eliminado!',
                'El administrador ha sido eliminado.',
                'success'
            )
        }
    })
}
</script>
	<!-- Carga de los archivos JS de Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi4+Ys6"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>


</body>
</html>
