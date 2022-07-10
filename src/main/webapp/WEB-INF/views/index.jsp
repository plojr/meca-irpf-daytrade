<jsp:include page="header.jsp" />
<title>Home</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<h2>MeCa-IRPF-DayTrade</h2>
		<p>Os trades s�o gerados a partir das notas de corretagem que foram adicionadas usando o m�dulo Meca-IRPF-Cadastro.</p>
		<p>Clique em "Gerar day trades do investidor" para que os day trades sejam gerados e adicionados no banco de dados.
		Eles ser�o gerados a partir do cadastro das notas de corretagem no m�dulo Meca-IRPF-Cadastro. Lembrando que, neste m�dulo,
		n�o � poss�vel fazer nenhum cadastro.</p>
		<p>Clique em "Mostrar day trades do investidor" para que voc� veja quais s�o os day trades que foram feitos a partir das notas
		de corretagem que foram cadastradas. Um ponto a ser notado: quando voc� clica em "Gerar day trades do investidor", a p�gina
		� direcionada para a funcionalidade de "Mostrar day trades do investidor" ap�s a inser��o dos dados no banco de dados.</p>
		<p>Clique em "Gerar relat�rio day trade" para ver os dados que devem ser lan�ados no programa de imposto de renda da 
		Receita Federal.</p>
		</div>
	</div>
</div>
</body>
</html>