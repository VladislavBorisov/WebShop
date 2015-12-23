<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/fmt/login_fmt.jspf" %>
<html>
<head><title>${head_login}</title>
    <c:set scope="session" var="url" value="/jsp/login.jsp"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-business.css" rel="stylesheet">
</head>
<body>
<c:if test="${not empty sessionScope.user}">
    <jsp:forward page="/jsp/main.jsp" />
</c:if>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-left" style="padding-top: 4px">
                <li>
                    <a class="navbar-brand" style="font-size: 20px; padding-top: 10px; padding-left: 30px">${shop}</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="language">
                        <input type="hidden" name="locale" value="ru">
                        <button type="submit" class="btn btn-link">${ru_button}</button>
                    </form>
                </li>
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="language">
                        <input type="hidden" name="locale" value="en">
                        <button type="submit" class="btn btn-link">${en_button}</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<section class="container">
    <div class="login">
        <h1>${logstore}</h1>

        <form method="post" action="controller">
            <input type="hidden" name="command" value="login"/>

            <p><input type="text" name="login" value="" placeholder="${log}"></p>

            <p><input type="password" name="password" value="" placeholder="${pass}"></p>

            <p class="remember_me">
                <label>
                    <c:if test="${not empty message }">
                        <h4>
                            <fmt:message key="${message}"/>
                        </h4>
                    </c:if>

                </label>
            </p>

            <button type="submit" class="btn btn-primary">${Log}</button>
        </form>
    </div>

    <div class="login-help">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="to_signup"/>
            <button type="submit" class="btn btn-link">${register}</button>
        </form>
    </div>

</section>
</body>
</html>
