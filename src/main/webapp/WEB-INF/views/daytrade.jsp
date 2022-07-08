<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><jsp:include page="header.jsp" />
<title>Home</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<h3>Day Trades Realizados</h3>
			<table class="table table-bordered">
				<tr>
					<th>Código</th>
					<th>Data do trade</th>
					<th>Quantidade</th>
					<th>Preço de compra</th>
					<th>Preço de venda</th>
				<tr>
				<c:forEach items="${dayTrades}" var="dayTrade">
					<tr>
						<td><c:out value="${dayTrade.ticker.codigo}"></c:out></td>
						<td><c:out value="${dayTrade.dataEvento}"></c:out></td>
						<td><c:out value="${dayTrade.quantidade}%"></c:out></td>
						<td><c:out value="${dayTrade.precoCompra}"></c:out></td>
						<td><c:out value="${dayTrade.precoVenda}"></c:out></td>
					</tr>
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