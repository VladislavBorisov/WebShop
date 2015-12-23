<%@ page language="java" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/modern-business.css" type="text/css">
<title>Error page</title>
</head>
<body>
	<h1 align="right">Error page</h1>
	<c:choose>
		<c:when test="${empty message }">
			<h1 align="center">
				<fmt:message key="${message}" />
			</h1>
		</c:when>
		<c:otherwise>
			<h1 align="center">
				<fmt:message key="${error}" />
			</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>