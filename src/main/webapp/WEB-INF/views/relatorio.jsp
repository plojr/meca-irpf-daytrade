<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<title>Home</title>
<style>
td, th {
	text-align: center
}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<h2>Relatório Day Trade</h2>
		<h3>Lucro Mensal</h3>
		<table class="table table-bordered">
			<tr>
				<th>Ano</th>
				<th>Mês</th>
				<th>Lucro</th>
			<tr>
			<c:forEach var="ano" items="${relatorio.anoMesLucro}">
				<c:forEach var="mesLucro" items="${ano.value}">
				<tr>
					<td><c:out value="${ano.key}"></c:out></td>
					<td><c:out value="${mesLucro.key}"></c:out></td>
					<td><c:out value="${mesLucro.value}"></c:out></td>
				</tr>
				</c:forEach>
			</c:forEach>
		</table>
		
		<h3>Prejuízo acumulado mês a mês</h3>
		<table class="table table-bordered">
			<tr>
				<th>Ano</th>
				<th>Mês</th>
				<th>Prejuízo acumulado</th>
			<tr>
			<c:forEach var="ano" items="${relatorio.anoMesPrejuizoAcumulado}">
				<c:forEach var="mesPrejuizoAcumulado" items="${ano.value}">
				<tr>
					<td><c:out value="${ano.key}"></c:out></td>
					<td><c:out value="${mesPrejuizoAcumulado.key}"></c:out></td>
					<td><c:out value="${mesPrejuizoAcumulado.value}"></c:out></td>
				</tr>
				</c:forEach>
			</c:forEach>
		</table>
		
		<button class="btn" onclick="document.body.scrollTop = 0; document.documentElement.scrollTop = 0;">
		Ir para o topo
		</button>
		</div>
	</div>
</div>
</body>
</html>