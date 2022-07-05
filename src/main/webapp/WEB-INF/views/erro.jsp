<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<title>Home</title>
</head>
<body>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<jsp:include page="sidebar.jsp" />
		<div class="col py-3">
		<p>Ops, algo deu errado.</p>
		<div>Mensagem: <c:out value="${mensagemDeErro}"></c:out></div>
		</div>
	</div>
</div>
</body>
</html>