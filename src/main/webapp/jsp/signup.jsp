<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/jspf/lang_bundle.jspf" %>
<%@include file="/jsp/jspf/fmt/sign_up_fmt.jspf" %>
<html>
<head>
<title>${sign_up}</title>
    <c:set scope="session" var="url" value="/jsp/signup.jsp"/>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/modern-business.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">${shop}</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
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
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="to_index"/>
                        <button type="submit" class="btn btn-primary">${tologin}</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<section class="container">
    <div class="login">
        <h1>${registration}</h1>

        <form action="controller" method="post">
            <input type="hidden" name="command" value="sign_up"/>

            <p><input type="text" name="login" value="" placeholder="${log}" pattern="^[a-z0-9_-]{3,16}$"
                      title="Input correct login."></p>
            <p><input type="password" name="password" value="" placeholder="${pass}" pattern="^[a-zA-Z\d]{8,}$"
                      title="Input correct password."></p>
            <p><input type="text" name="firstName" value="" placeholder="${fname}" pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
                      title="Input correct first name."></p>
            <p><input type="text" name="lastName" value="" placeholder="${lname}" pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
                      title="Input correct last name."></p>
            <p><input type="text" name="address" value="" placeholder="${address}"></p>
            <p><input type="text" name="email" value="" placeholder="${email}"></p>

            <p class="remember_me">
                <label>
                    <c:if test="${not empty message }">
                        <fmt:message key="${message}"/>
                    </c:if>
                </label>
            </p>

            <p class="submit"><button type="submit" class="btn btn-danger">${register}</button></p>
        </form>
    </div>
</section>
</body>
</html>
