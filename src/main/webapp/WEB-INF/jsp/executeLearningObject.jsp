<%@ page language="java" contentType="text/html;charset=utf-8"	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Learning Object - Execute</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/kobj.css" />">
	<script type="text/javascript" src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/executekobj.js" />"></script>
	<script type="text/javascript">
		
		
		var visitedArr = new Array();

		<c:forEach items="${model.visited_list}" var="v" varStatus="status"> 
			var d = "${v}";
			var dArr = d.split(":");
			if(dArr[1] == 1) {
				visitedArr.push(dArr[2] + "_act");
			}
		</c:forEach> 

	</script>
</head>

<body id="index" class="body">



<header>
	<h1>CROP Learning Object &#8212; Sample Execution</h1>
</header>

<form id="adminform" name="adminform" action="/jcrop-service/executeLearningObject" method="post">

<input type="hidden" name="kobj" id="kobj" value="${model.main_kobj}" />
<input type="hidden" name="cur_kobj" id="cur_kobj" value="${model.cur_kobj}" />

<c:forEach items="${model.traverse_list}" var="tl" varStatus="status"> 
	<input type="hidden" name="traverse_list[]" id="traverse_list[]" value="${tl}" />
</c:forEach> 


 <c:choose>
	<c:when test="${fn:length(model.visited_list) gt 0}">
 		<c:forEach items="${model.visited_list}" var="vl" varStatus="status"> 
			<input type="hidden" name="visited_list[]" id="visited_list[]" value="${vl}" />
		</c:forEach> 
  	</c:when>

  	<c:otherwise>
    	<input type="hidden" name="visited_list[]" id="visited_list[]" value="" />
  	</c:otherwise>
</c:choose>



<aside class="kobjs_canvas">

	<div id="main_svg">${model.main_kobj_svg}</div>
	<div class="caption">Main Learning Object Execution Graph</div>
	
	
 	<c:choose>
		<c:when test="${not empty model.cur_kobj_svg}">
			<div id="cur_svg">${model.cur_kobj_svg}</div>
			<div class="caption">Current Learning Object Execution Graph</div>
		</c:when>
	</c:choose>	
</aside>

<section id="kobj_content" class="kobj_content">

<header>
	<div>
	Main Learning Object: ${model.main_kobj} <br /> 
	Main Target Concept: ${model.main_kobj_target} 
	</div>
	
	<div>
	Current Learning Object: ${model.cur_kobj} <br /> 
	Current Target Concept: ${model.cur_kobj_target}
	</div>
	<div>
		<input class="next_button" type="submit" value="Next" <c:if test="${model.isEnd}"> disabled </c:if> /> 
	</div>
</header>


<p>${model.resource}</p>

</section>

</form>

<footer>
CROP Learning Objects
</footer>


</body>
</html>