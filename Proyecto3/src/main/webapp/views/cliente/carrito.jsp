<!-- La página web se compone de elementos HTML y CSS, incluyendo algunos elementos y estilos de la biblioteca Bootstrap. -->
<!-- La página se abre con un encabezado que incluye el título "Panel Cliente", la carga de la biblioteca Bootstrap y la carga de la biblioteca de iconos Font Awesome. Además, se carga el archivo sweetalert.css que se utiliza para mostrar alertas modales en la página. -->
<!-- La barra de navegación incluye un enlace a la página principal del usuario y un botón desplegable para acceder al menú del usuario, que muestra el correo electrónico del usuario y un botón para cerrar sesión. -->
<!-- El título de la página es "Carrito de Compras" y se muestra como un encabezado grande encima de la tabla de productos disponibles. -->
<!-- La tabla de productos se muestra en una tabla de Bootstrap con seis columnas: "Código", "Nombre", "Descripción", "Precio", "Stock" y "Agregar". La tabla se llena con una lista de productos recuperados de la base de datos y se muestra en la página usando la biblioteca JSTL (JSP Standard Tag Library). -->
<!-- Cada fila de la tabla tiene un botón "Agregar" que permite al usuario agregar el producto a su carrito de compras. Al hacer clic en el botón, se envía una solicitud HTTP POST al servidor que incluye el código del producto seleccionado. -->
<!-- Debajo de la tabla, se muestra el contenido actual del carrito de compras del usuario. Si el carrito está vacío, se muestra un mensaje de que el carrito está vacío. Si hay elementos en el carrito, se muestra una tabla de Bootstrap que muestra el código, nombre, cantidad y precio de cada elemento. Además, se muestra el precio total de todos los elementos en el carrito. -->
<!-- En la parte inferior de la página, hay un botón "Confirmar Compra" que permite al usuario confirmar su compra. Al hacer clic en el botón, se envía una solicitud HTTP POST al servidor que incluye el contenido del carrito de compras. Si la compra se confirma correctamente, se muestra un mensaje de éxito en la página. Si hay un error, se muestra un mensaje de error. -->


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Panel Cliente</title>
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
	
		<!-- Boton Volver al Index -->
	<button type="button" class="btn btn-primary"
		onclick="window.location.href='cliente?accion=volver'"
		style="margin-left: 20px;">
		<i class="fa fa-arrow-left"></i> Volver atrás
	</button>

	<!-- Titulo JSP usuario -->


	<div class="col-lg-12 p-5">
		<h1>
			<i class="fa fa-shopping-cart" style="font-size: 24px"> </i> Carrito
			de Compras
		</h1>
		<hr />
	</div>

<div class="container mt-3">
  <h1>Productos disponibles</h1>
  <br>
  <table class="table table-striped table-bordered display table-light" id="productosTabla">
    <thead>
      <tr>
        <th>Código</th>
        <th>Nombre</th>
        <th>Descripción</th>
        <th>Precio</th>
        <th>Stock</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${productos}" var="producto">
        <tr>
          <td>${producto.codigo}</td>
          <td>${producto.nombre}</td>
          <td>${producto.descripcion}</td>
          <td>${producto.precio}</td>
          <td>${producto.stock}</td>
          <td>
            <form action="cliente?accion=agregar" method="post">
              <input type="hidden" name="codigo" value="${producto.codigo}">
              <input type="number" name="cantidad" min="1" max="${producto.stock}" required pattern="\d+">
              <input type="hidden" name="precio" value="${producto.precio}">
              <button type="submit" class="btn btn-primary">Agregar al carrito <i class='fas fa-cart-plus'></i></button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
<script>
  $(document).ready(function() {
    $('#productosTabla').DataTable({
      "paging": true,
      "pageLength": 5
    });
  });
</script>


	<div class="container mt-3">
		<h1>Tu carrito</h1>
		<br>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>Codigo Producto</th>
					<th>Cantidad</th>
					<th>Total</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="total" value="0" />
				<c:forEach items="${carritos}" var="carrito">
					<tr>
						<td>${carrito.idProducto}</td>
						<td>${carrito.cantidad}</td>
						<td>${carrito.total}</td>
						<td>
							<!-- Formulario de eliminación -->
							<form id="eliminarForm" action="cliente?accion=eliminar"
								method="post">
								<input type="hidden" name="idProducto"
									value="${carrito.idProducto}">
								<button type="button" class="btn btn-danger eliminarBtn"
									data-toggle="modal" data-target="#confirmarEliminarModal">
									<i class='fas fa-trash'></i>
								</button>
							</form>
						</td>
					</tr>
					<c:set var="total" value="${total + carrito.total}" />
				</c:forEach>
				<tr>
					<td colspan="2"></td>
					<td><b>Total:</b></td>
					<td>${total}</td>
				</tr>
			</tbody>
		</table>
		
		<div class="row">
				  <div class="col-12 d-table-cell" style="margin-bottom: 50px;">
				    <form method="POST" action="cliente?accion=comprar" id="form-compra">
				      <c:forEach items="${carritos}" var="carrito">
				        <input type="hidden" name="productos[]" value="${carrito.idProducto}">
				        <input type="hidden" name="cantidades[]" value="${carrito.cantidad}">
				      </c:forEach>
				      <button type="button" class="btn btn-primary btn-lg btn-block" id="btn-finalizar" style="width: 100%;">Finalizar compra</button>
				    </form>
				  </div>
				</div>
		</div>
		<!-- modal saldo insuficiente -->
		<%
		if (request.getAttribute("saldoInsuficiente") != null && (boolean) request.getAttribute("saldoInsuficiente")) {
		%>
		<div class="modal fade" id="saldoInsuficienteModal" tabindex="-1"
			role="dialog" aria-labelledby="saldoInsuficienteModalLabel"
			aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="saldoInsuficienteModalLabel">Saldo
							insuficiente</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Lo siento, no tienes suficiente saldo para realizar la
							compra</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Entendido</button>
					</div>
				</div>
			</div>
		</div>

		<script>
			$(document).ready(function() {
				$('#saldoInsuficienteModal').modal('show');
				$('#saldoInsuficienteModal').on('hidden.bs.modal', function() {
					$(this).removeData('bs.modal');
				});
				$('#saldoInsuficienteModal').on('click', function() {
					$('#saldoInsuficienteModal').modal('hide');
				});
			});
		</script>

		<%
		}
		%>

		<!-- Modal confirmacion -->
		<div class="modal fade" id="confirmarEliminarModal" tabindex="-1"
			role="dialog" aria-labelledby="confirmarEliminarModalLabel"
			aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="confirmarEliminarModalLabel">Eliminar
							producto</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">¿Está seguro que desea eliminar este
						producto del carrito? Se eliminaran los productos con el ID
						seleccionado.</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Cancelar</button>
						<button type="button" class="btn btn-danger"
							id="eliminarProductoBtn">Eliminar</button>
					</div>
				</div>
			</div>
		</div>

				

		<!-- Modal carrito vacio-->
		<div class="modal fade" id="modalError" tabindex="-1" role="dialog"
			aria-labelledby="modalErrorLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="modalErrorLabel">Error</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Debes seleccionar al menos un producto para poder finalizar
							la compra.</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Entendido</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Script carrito vacio -->
		<script>
			$(document).ready(function() {
				$('#btn-finalizar').click(function() {
					var productos = $("input[name='productos[]']").length;
					if (productos == 0) {
						$('#modalError').modal('show');
					} else {
						$('#form-compra').submit();
					}
				});
			});
		</script>

		<!-- Script confirmacion -->
		<script>
			$(document).ready(function() {
				$("#eliminarProductoBtn").click(function() {
					$("#eliminarForm").submit();
					$("#confirmarEliminarModal").modal("hide");
				});
			});
		</script>

	
	

		<!-- Script notificaciones -->
		<script src="vendor/jquery/jquery.min.js"></script>
		<script src="js/main.js"></script>
		<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

		<input type="hidden" id="status"
			value="<%=request.getAttribute("status")%>">
		<script type="text/javascript">
			var status = document.getElementById("status").value;
			if (status == "success") {
				swal("Felicitaciones", "Se agrego el articulo al carrito",
						"success")
			} else if (status == "arteliminadook") {
				swal("Atencion",
						"El o los articulos fueron eliminado de su carrito",
						"success")
			}
		</script>
		<!-- DataTable -->
		<script src="DataTables/datatables.min.js"></script>
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