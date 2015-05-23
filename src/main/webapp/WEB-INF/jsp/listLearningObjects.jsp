<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/kobj.css" />">
	<title>List of LearningObjects</title>
</head>
<body>


<header>
	<h1>CROP Learning Objects</h1>
</header>

<section>



<table> 
        <thead>
            <tr>
            	<th>#</th>
                <th>Learning Object</th>
                <th>Target Concept</th>
                <th>Type</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="lobj" items="${learningobjects}" varStatus="status">
                <tr>
                	<td>${status.index+1}</td>
                    <td><a href="/jcrop-service/executeLearningObject1?kobj=${lobj.name}">${lobj.name}</a></td>
                    <td>${lobj.target}</td>
                    <td>${lobj.type}</td>
                </tr>
            </c:forEach> 
        </tbody>
    </table>
</section>    
 
 <footer>
CROP Learning Objects
</footer>
     
</body>
</html>