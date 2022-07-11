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
		<h3>Lucro Mensal - tabela 1</h3>
		<p>Para lançar o lucro mensal, você deve ir no menu à esquerda e procurar por "Renda variável - Operações comuns/Day-Trade".
		Com isso, aparecerá uma tela na direita, onde você poderá lançar os lucros por mês. Só para lembrar que este módulo só trata
		de ações no mercado à vista, que é o primeiro item. Procure pela coluna relativa ao day-trade, que costuma ser a segunda.</p>
		<p>Só para deixar claro: você deve lançar não só o lucro, mas também o prejuízo. Isso servirá para o programa calcular o 
		prejuízo acumulado que poderá ser usado para, no futuro, abater em algum lucro que você eventualmente vai ter e, com isso,
		ter um benefício fiscal.</p>
		<small>Tabela 1</small>
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
					<td><fmt:formatNumber value="${mesLucro.value}" minFractionDigits="2" maxFractionDigits="2"></fmt:formatNumber></td>
				</tr>
				</c:forEach>
			</c:forEach>
		</table>
		<h3>Prejuízo acumulado mês a mês - tabela 2</h3>
		<p>O prejuízo acumulado não é para ser lançado. Ele já é calculado automaticamente a partir do laçamento dos lucros mensais.</p>
		<p>Entendo ser interessante ter esta tabela para que o investidor compare os valores mostrados por ela e os mostrados pelo programa
		da Receita Federal.</p>
		<p>Quando você vai na opção "Renda variável - Operações comuns/Day-Trade", existe, na parte direita, no final da tela, o campo 
		"prejuízo a compensar", que é o prejuízo acumulado desta tabela 2. Existe, também, o campo "resultado negativo até o mês anterior", 
		que é o prejuízo acumulado do mês anterior desta tabela 2.</p>
		<small>Tabela 2</small>
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
					<td><fmt:formatNumber value="${mesPrejuizoAcumulado.value}" minFractionDigits="2" maxFractionDigits="2"></fmt:formatNumber></td>
				</tr>
				</c:forEach>
			</c:forEach>
		</table>
		<h3>Imposto de renda mensal a pagar - tabela 3</h3>
		<p>Este valor de imposto de renda a pagar vai ser o mesmo valor que irá aparecer no campo "IMPOSTO DEVIDO" do software da
		Receita Federal.</p>
		<p>O imposto de renda que o investidor deve pagar precisa ser feito até o último dia útil do mês subsequente.</p>
		<p>É importante lançar as notas de corretagem o mais rápido possível para que o investidor veja que existe imposto de 
		renda a ser pago, dentro do prazo.</p>
		<p>Para reforçar: mesmo pagando o imposto de renda, ainda é preciso declarar que ele foi pago.</p>
		<small>Tabela 3</small>
		<table class="table table-bordered">
			<tr>
				<th>Ano</th>
				<th>Mês</th>
				<th>Imposto de renda</th>
			<tr>
			<c:forEach var="ano" items="${relatorio.anoMesImpostoDeRenda}">
				<c:forEach var="mesImpostoDeRenda" items="${ano.value}">
				<tr>
					<td><c:out value="${ano.key}"></c:out></td>
					<td><c:out value="${mesImpostoDeRenda.key}"></c:out></td>
					<td><fmt:formatNumber value="${mesImpostoDeRenda.value}" minFractionDigits="2" maxFractionDigits="2"></fmt:formatNumber></td>
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