<%--
  Created by IntelliJ IDEA.
  User: kkj9491
  Date: 2022-01-12
  Time: 오후 3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>main</title>
</head>
<body>
<input type="hidden" id="count" value = "${restaurantCount}">
<table>
	<thead>
		<tr>
			<th>번호</th>
			<th>레스토랑 이름</th>
			<th>종류</th>
			<th>기타</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${selectRestaurant }" var="restaurant" varStatus="i">
			<tr>
				<td><c:out value="${restaurant.list_number }"/></td>
				<td><c:out value="${restaurant.restaurant_name }"/></td>
				<td><c:out value="${restaurant.type }"/></td>
				<td><c:out value="${restaurant.etc }"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<br>
	<%
		for(int i=1; i<=18; i++){ int j= (i-1)*10;%>
			
			<a href="main?page=<%=j %>">[<%=i%>]
			</a>
		<% }; %>
<br>
</body>
</html>
