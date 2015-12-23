<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tft" uri="/WEB-INF/tld/custom.tld" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/fmt/main_fmt.jspf" %>
<html>
<head>
    <title>${main}</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-business.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-left" style="padding-top: 4px">
                <li>
                    <a class="navbar-brand" style="font-size: 20px; padding-top: 10px; padding-left: 30px">${shop}</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${sessionScope.user.role eq 1}">
                    <%@include file="jspf/admin_forms.jspf" %>
                </c:if>
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="all_user_orders"/>
                        <button type="submit" class="btn btn-link">${my_orders}</button>
                    </form>
                </li>
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="to_basket"/>
                        <button type="submit" class="btn btn-link">${basket}</button>
                    </form>
                </li>
                <%@include file="jspf/change_lang.jspf" %>
                <%@include file="jspf/logout.jspf" %>
            </ul>
        </div>
    </div>
</nav>

<!-- Page Content -->
<div class="container">

    <!-- Marketing Icons Section -->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                <tft:hello locale="${sessionScope.locale}"/>${user.firstName}!
            </h1>
        </div>
    </div>

    <div class="jumbotron">
        <form class="catalog" action="controller" method="post">
            <input type="hidden" name="command" value="show_catalog"/>
            <button type="submit" class="btn btn-warning">${catalog}</button>
        </form>
    </div>
    <!-- /.row -->
    <hr>
    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p align="center">${copyright}</p>
            </div>
        </div>
    </footer>

</div>


</body>
</html>