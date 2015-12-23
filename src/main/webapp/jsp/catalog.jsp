<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/fmt/catalog_fmt.jspf" %>
<html>
<head>
    <title>${catalog}</title>
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
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="all_user_orders"/>
                        <button type="submit" class="btn btn-link">${my_orders}</button>
                    </form>
                </li>
                <c:if test="${sessionScope.user.role eq 1}">
                    <li>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="to_add_product"/>
                            <button type="submit" class="btn btn-link">${add_product}</button>
                        </form>
                    </li>
                </c:if>
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
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                ${catalog}
            </h1>
            <c:if test="${not empty message }">
                <h4>
                    <fmt:message key="${message}"/>
                </h4>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">

            <div class="row">

                <c:forEach var="product" items="${products}">
                    <div class="col-sm-3 col-lg-3 col-md-3">
                        <div class="thumbnail">
                            <img src="images/${product.image}"
                                 alt="">

                            <div class="caption">
                                <h4 class="pull-right">${product.price}$</h4>
                                <h4 style="font-size: 17px">${product.name}</h4>

                                <p>
                                <ul>
                                    <li class="catalog-li">
                                        <form action="controller" method="post">
                                            <input type="hidden" name="command" value="show_product"/>
                                            <input type="hidden" name="id" value="${product.productId}"/>
                                            <button type="submit" class="btn btn-warning">${more}</button>
                                        </form>
                                    </li>
                                    <c:if test="${sessionScope.user.role eq 1}">
                                        <li class="catalog-li">
                                            <form action="controller" method="post">
                                                <input type="hidden" name="command" value="delete_product"/>
                                                <input type="hidden" name="id" value="${product.productId}"/>
                                                <button type="submit" class="btn btn-warning">${delete}</button>
                                            </form>
                                        </li>
                                    </c:if>
                                </ul>
                                </p>
                            </div>

                        </div>
                    </div>
                </c:forEach>

            </div>

        </div>

    </div>
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