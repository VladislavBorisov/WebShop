<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/fmt/orders_fmt.jspf" %>
<html>
<head>
    <title>${orders}</title>
    <c:set scope="session" var="url" value="/jsp/basket.jsp"/>
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
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                ${orders} <c:if test="${sessionScope.userOrders eq null}">${empty_order}</c:if>
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
                <c:if test="${not empty sessionScope.userOrders}">
                    <c:forEach var="order" items="${sessionScope.userOrders}">
                        <h3>${date}<c:out value="${order.date}"/></h3>

                        <table class="table table-condensed">
                            <thead>
                            <tr>
                                <th>${name}</th>
                                <th>${price}</th>
                                <th>${description}</th>
                                <th>${amount}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${order.orderList}">
                            <tr>
                                <td><c:out value="${item.name}"/></td>
                                <td><c:out value="${item.price}"/></td>
                                <td><c:out value="${item.description}"/></td>
                                <td><c:out value="${order.amountMap[item.productId]}"/></td>
                            </tr>

                            </c:forEach>
                            <tbody>
                        </table>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="delete_order"/>
                            <input type="hidden" name="order_id" value="${order.orderId}"/>
                            <button type="submit" class="btn btn-warning">${delete}</button>
                        </form>
                        <br>
                    </c:forEach>
                </c:if>
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