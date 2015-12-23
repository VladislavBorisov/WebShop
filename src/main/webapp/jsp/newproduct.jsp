<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/jspf/lang_bundle.jspf" %>
<%@include file="/jsp/jspf/fmt/newproduct_fmt.jspf" %>
<html>
<head>
    <title>${newproduct}</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-business.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-left" style="padding-top: 4px">
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="to_main"/>
                        <button type="submit" class="btn btn-link" style="font-size: 20px">${shop}</button>
                    </form>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%@include file="jspf/change_lang.jspf" %>
                <%@include file="jspf/logout.jspf" %>
            </ul>
        </div>
    </div>
</nav>
<section class="container">
    <div class="login">
        <h1>${newproduct}</h1>
        <hr>
        <form action="controller" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="add_product"/>

            <p><input type="text" name="name" value="" placeholder="${name}"></p>

            <p><input type="text" name="price" value="" placeholder="${price}"></p>

            <p><input type="text" name="description" value="" placeholder="${description}"></p>

            <p><input type="text" name="category" value="" placeholder="${category}"></p>

            <p>${image}</p>
            <input type="file" name="file" accept="image/jpeg">
            <br>

            <p><input type="number" name="amount" value="" placeholder="${amount}"></p>

            <p class="remember_me">
                <label>
                    <c:if test="${not empty message }">
                        <fmt:message key="${message}"/>
                    </c:if>
                </label>
            </p>

            <p class="submit">
                <button type="submit" class="btn btn-danger">${addproduct}</button>
            </p>
        </form>
    </div>
</section>
<hr>
<!-- Footer -->
<footer>
    <div class="row">
        <div class="col-lg-12">
            <p align="center">${copyright}</p>
        </div>
    </div>
</footer>
</body>
</html>
