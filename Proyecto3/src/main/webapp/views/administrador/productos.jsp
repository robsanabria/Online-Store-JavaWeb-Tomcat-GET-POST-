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
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.3/dist/sweetalert2.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.3/dist/sweetalert2.min.css">

<title>Administrar Productos</title>
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


	<!-- Titulo JSP administrador Productos -->


	<div class="col-lg-12 p-5">
		<h1>
			Administrar Productos <i class='fas fa-gifts' style='font-size: 24px'></i>
		</h1>
		<hr>
	</div>


	<!-- Formulario Productos -->
	<div class="container">
	<h1>Lista de Productos</h1>

				<!-- Tabla productos -->
				<table class="table table-striped">
				    <thead>
				        <tr>
				            <th>Código</th>
				            <th>Nombre</th>
				            <th>Descripción</th>
				            <th>Precio</th>
				            <th>Stock</th>
				            <th>Acciones</th>
				        </tr>
				    </thead>
				    <tbody>
				        <c:forEach var="producto" items="${productos}">
				            <tr>
				                <td>${producto.codigo}</td>
				                <td>${producto.nombre}</td>
				                <td>${producto.descripcion}</td>
				                <td>${producto.precio}</td>
				                <td>${producto.stock}</td>
				                <td>
				                    <a href="#" class="btn btn-primary btn-sm" data-toggle="modal"
				                        data-target="#editarProductoModal-${producto.codigo}">
				                        <i class="fas fa-edit"></i>
				                    </a>
							    <button class="btn btn-danger btn-sm eliminarProductoBtn" data-codigo="${producto.codigo}">
			                        <i class="fas fa-trash-alt"></i>
			                    </button>
				                </td>
				            </tr>
				            <!-- Modal editar producto -->
				            <div class="modal fade" id="editarProductoModal-${producto.codigo}" tabindex="-1"
				                role="dialog" aria-labelledby="editarProductoModal-${producto.codigo}Label"
				                aria-hidden="true">
				                <div class="modal-dialog modal-lg" role="document">
				                    <div class="modal-content">
				                        <div class="modal-header">
				                            <h5 class="modal-title" id="editarProductoModal-${producto.codigo}Label">Editar
				                                Producto</h5>
				                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				                                <span aria-hidden="true">&times;</span>
				                            </button>
				                        </div>
				                        <div class="modal-body">
				                            <!-- Formulario editar producto -->
				                            <form action="administradorProductos" method="POST">
				                                <input type="hidden" name="accion" value="editarProducto">
				                                <input type="hidden" name="codigo" value="${producto.codigo}">
				                                <div class="form-group">
				                                    <label for="nombre">Nombre</label>
				                                    <input type="text" class="form-control" id="nombre" name="nombre"
				                                        value="${producto.nombre}">
				                                </div>
				                                <div class="form-group">
				                                    <label for="descripcion">Descripción</label>
				                                    <textarea class="form-control" id="descripcion" name="descripcion"
				                                        rows="3">${producto.descripcion}</textarea>
				                                </div>
				                                <div class="form-group">
				                                    <label for="precio">Precio</label>
				                                    <input type="number" class="form-control" id="precio" name="precio"
				                                        value="${producto.precio}">
				                                </div>
				                                <div class="form-group">
				                                    <label for="stock">Stock</label>
				                                    <input type="number" class="form-control" id="stock" name="stock"
				                                        value="${producto.stock}">
				                                </div>
				                                <button type="submit" class="btn btn-primary">Guardar cambios</button>
				                            </form>
				                        </div>
				                    </div>
				                </div>
				            </div>
				            <!-- Fin modal editar producto -->
				        </c:forEach>
				    </tbody>
				</table>
				<!-- Botón Crear nuevo Producto -->
<button type="button" class="btn btn-success mb-3" data-toggle="modal" data-target="#crearProductoModal">
    <i class="fas fa-plus-circle"></i> Crear nuevo producto
</button>
</div>
							


<!-- Modal crear producto -->
<div class="modal fade" id="crearProductoModal" tabindex="-1" role="dialog" aria-labelledby="crearProductoModalLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearProductoModalLabel">Crear Producto</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Formulario crear producto -->
                <form action="administradorProductos" method="POST">
                    <input type="hidden" name="accion" value="crearProducto">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>
                    <div class="form-group">
                        <label for="descripcion">Descripción</label>
                        <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="precio">Precio</label>
                        <input type="number" class="form-control" id="precio" name="precio" required>
                    </div>
                    <div class="form-group">
                        <label for="stock">Stock</label>
                        <input type="number" class="form-control" id="stock" name="stock" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Crear Producto</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Fin modal crear producto -->



<!-- Modal de confirmación de eliminación -->
<div class="modal fade" id="eliminarProductoModal" tabindex="-1" role="dialog"
    aria-labelledby="eliminarProductoModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="eliminarProductoModalLabel">Eliminar Producto</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ¿Estás seguro de que quieres eliminar este producto?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="eliminarProductoConfirmar">Eliminar</button>
            </div>
        </div>
    </div>
</div>

<!-- Script para manejar la eliminación de productos -->
<script>
    $(document).ready(function () {
        var codigoProducto;

        // Mostrar el modal de confirmación de eliminación cuando se hace clic en el botón de eliminar
        $('.eliminarProductoBtn').click(function () {
            codigoProducto = $(this).data('codigo');
            $('#eliminarProductoModal').modal('show');
        });

        // Manejar la eliminación del producto cuando se confirma en el modal
        $('#eliminarProductoConfirmar').click(function () {
            $('#eliminarProductoModal').modal('hide');
            // redirigir al servlet de eliminación de productos
            window.location.href = 'administradorProductos?accion=eliminarProducto&codigo=' + codigoProducto;
        });
    });
</script>
			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>