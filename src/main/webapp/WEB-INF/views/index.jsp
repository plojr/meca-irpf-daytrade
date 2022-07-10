<jsp:include page="header.jsp" />
<title>Home</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<h2>MeCa-IRPF-DayTrade</h2>
		<p>Os trades são gerados a partir das notas de corretagem que foram adicionadas usando o módulo Meca-IRPF-Cadastro.</p>
		<p>Clique em "Gerar day trades do investidor" para que os day trades sejam gerados e adicionados no banco de dados.
		Eles serão gerados a partir do cadastro das notas de corretagem no módulo Meca-IRPF-Cadastro. Lembrando que, neste módulo,
		não é possível fazer nenhum cadastro.</p>
		<p>Clique em "Mostrar day trades do investidor" para que você veja quais são os day trades que foram feitos a partir das notas
		de corretagem que foram cadastradas. Um ponto a ser notado: quando você clica em "Gerar day trades do investidor", a página
		é direcionada para a funcionalidade de "Mostrar day trades do investidor" após a inserção dos dados no banco de dados.</p>
		<p>Clique em "Gerar relatório day trade" para ver os dados que devem ser lançados no programa de imposto de renda da 
		Receita Federal.</p>
		</div>
	</div>
</div>
</body>
</html>