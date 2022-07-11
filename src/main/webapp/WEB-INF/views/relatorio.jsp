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
		<h2>Relat�rio Day Trade</h2>
		<h3>Lucro Mensal - tabela 1</h3>
		<p>Para lan�ar o lucro mensal, voc� deve ir no menu � esquerda e procurar por "Renda vari�vel - Opera��es comuns/Day-Trade".
		Com isso, aparecer� uma tela na direita, onde voc� poder� lan�ar os lucros por m�s. S� para lembrar que este m�dulo s� trata
		de a��es no mercado � vista, que � o primeiro item. Procure pela coluna relativa ao day-trade, que costuma ser a segunda.</p>
		<p>S� para deixar claro: voc� deve lan�ar n�o s� o lucro, mas tamb�m o preju�zo. Isso servir� para o programa calcular o 
		preju�zo acumulado que poder� ser usado para, no futuro, abater em algum lucro que voc� eventualmente vai ter e, com isso,
		ter um benef�cio fiscal.</p>
		<small>Tabela 1</small>
		<table class="table table-bordered">
			<tr>
				<th>Ano</th>
				<th>M�s</th>
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
		<h3>Preju�zo acumulado m�s a m�s - tabela 2</h3>
		<p>O preju�zo acumulado n�o � para ser lan�ado. Ele j� � calculado automaticamente a partir do la�amento dos lucros mensais.</p>
		<p>Entendo ser interessante ter esta tabela para que o investidor compare os valores mostrados por ela e os mostrados pelo programa
		da Receita Federal.</p>
		<p>Quando voc� vai na op��o "Renda vari�vel - Opera��es comuns/Day-Trade", existe, na parte direita, no final da tela, o campo 
		"preju�zo a compensar", que � o preju�zo acumulado desta tabela 2. Existe, tamb�m, o campo "resultado negativo at� o m�s anterior", 
		que � o preju�zo acumulado do m�s anterior desta tabela 2.</p>
		<small>Tabela 2</small>
		<table class="table table-bordered">
			<tr>
				<th>Ano</th>
				<th>M�s</th>
				<th>Preju�zo acumulado</th>
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
		<p>Este valor de imposto de renda a pagar vai ser o mesmo valor que ir� aparecer no campo "IMPOSTO DEVIDO" do software da
		Receita Federal.</p>
		<p>O imposto de renda que o investidor deve pagar precisa ser feito at� o �ltimo dia �til do m�s subsequente.</p>
		<p>� importante lan�ar as notas de corretagem o mais r�pido poss�vel para que o investidor veja que existe imposto de 
		renda a ser pago, dentro do prazo.</p>
		<p>Para refor�ar: mesmo pagando o imposto de renda, ainda � preciso declarar que ele foi pago.</p>
		<small>Tabela 3</small>
		<table class="table table-bordered">
			<tr>
				<th>Ano</th>
				<th>M�s</th>
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