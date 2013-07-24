<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="sv">
<head>
<meta charset="utf-8">
<title>Rent It - L�ttare hyresl�genheter</title>

<!-- Loading Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet">

<!-- Loading Flat UI -->
<link href="resources/css/flat-ui.css" rel="stylesheet">

<link rel="shortcut icon" href="resources/resources/images/favicon.ico">

<link rel="stylesheet" type="text/css" href="resources/css/bootstrap-sortable.css">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->
</head>

<body>
	<div class="container">
		<div class="headline">
			<h1 class="main-logo">
				<div class="logo"></div>
				Rent It <small>L�ttare hyresl�genheter</small>
			</h1>
		</div>
		<!-- /headline -->

		<h1>Lediga l�genheter</h1>

		<div class="span12">
			<table class="table table-striped sortable">
				<thead>
					<tr>
						<th class="hidden-phone">Bild</th>
						<th>Omr�de</th>
						<th>Adress</th>
						<th>Hyra</th>
						<th>Storlek</th>
						<th>Rum</th>
						<th>Hyresv�rd</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="i" items="${appartments}">
						<tr class="appartment" data="${i.url}">
							<td class="hidden-phone"><img alt="L�genhetsbild" src="${i.imageUrl}"></td>
							<td>${i.area}</td>
							<td>${i.address}</td>
							<td>${i.rent}</td>
							<td>${i.size}</td>
							<td>${i.rooms}</td>
							<td>${i.landlord}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>

		</div>
	</div>
	<!-- /container -->

	<footer>
		<div class="container">
			<div class="row">
				<div class="span7">
					<h3 class="footer-title">Kontakt</h3>
					<p>Fr�gor och f�rb�ttringsf�rslag kan skickas till tobias@tobiashansson.nu</p>

					<p class="pvl"></p>

				</div>
				<!-- /span8 -->

			</div>
		</div>
	</footer>

	<!-- Load JS here for greater good =============================-->
	<script src="resources/js/jquery-1.8.3.min.js"></script>
	<script src="resources/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="resources/js/jquery.ui.touch-punch.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/bootstrap-select.js"></script>
	<script src="resources/js/bootstrap-switch.js"></script>
	<script src="resources/js/flatui-checkbox.js"></script>
	<script src="resources/js/flatui-radio.js"></script>
	<script src="resources/js/jquery.tagsinput.js"></script>
	<script src="resources/js/jquery.placeholder.js"></script>
	<script src="resources/js/jquery.stacktable.js"></script>
	<script src="http://vjs.zencdn.net/c/video.js"></script>
	<script src="resources/js/application.js"></script>
	<script src="resources/js/bootstrap-sortable.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".appartment").on("click", function() {
				window.open($(this).attr('data'));
				return false;
			});
		});
	</script>
</body>
</html>

