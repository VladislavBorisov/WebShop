<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/fmt/product_fmt.jspf" %>
<html>
<head>
    <title>${title_product}</title>
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
<div class="container-fluid">
    <div class="content-wrapper">
        <div class="item-container" style="margin-top: 40px;">
            <div class="container">
                <div class="col-md-5">
                    <div class="product col-md-12 service-image-left">
                        <img id="item-display" src="images/${product.image}" alt="">
                    </div>
                </div>
                <div class="col-md-7">
                    <div class="product-title">
                        <h3 style="margin-top: 0px"><c:out value="${name}"/>:</h3>
                        <h4><c:out value="${product.name}"/></h4>
                    </div>
                    <div class="product-desc">
                        <h3><c:out value="${description}"/>:</h3>
                        <h4><c:out value="${product.description}"/></h4>
                    </div>
                    <h3><c:out value="${amount}"/>:</h3>
                    <h4><c:out value="${product.amount}"/></h4>
                    <hr>
                    <div class="product-price"><h4><c:out value="$${product.price}"/></h4></div>
                    <hr>
                    <div class="btn-group cart">
                        <c:if test="${not empty sessionScope.user}">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="add_to_basket"/>
                                <input type="hidden" name="id" value="${product.productId}"/>
                                <button type="submit" class="btn btn-success">${to_basket}</button>
                            </form>
                        </c:if>
                        <c:if test="${not empty message }">
                            <h4>
                                <fmt:message key="${message}"/>
                            </h4>
                        </c:if>
                        <c:if test="${sessionScope.user.role eq 1}">
                            <form action="controller" method="post" style="margin-top: 14px">
                                <input type="hidden" name="command" value="to_edit"/>
                                <input type="hidden" name="id" value="${product.productId}"/>
                                <button type="submit" class="btn btn-warning">${edit}</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<hr>
<footer>
    <div class="row">
        <div class="col-lg-12">
            <p align="center">${copyright}</p>
        </div>
    </div>
</footer>
</body>
</html>
