<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Learning Object - Execute</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/kobj.css" />">
	<script type="text/javascript" src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/kobj.js" />"></script>
	<script type="text/javascript">	
		// traversed obj
		


	</script>
</head>

<body id="index" class="body">

<header class="body">
	<h1>CROP Learning Object &#8212; Sample Execution</h1>
</header>

<aside class="kobjs_canvas">

	<div id="main_svg">${model.main_kobj_svg}</div>
	<div class="caption">Main Learning Object</div>
	
	<div id="cur_svg">${model.cur_kobj_svg}</div>
	<div class="caption">Current Learning Object</div>
	
</aside>

<section id="kobj_content" class="kobj_content">

<header>
	<div>
	Main Learning Object: ${model.kobj_name} <br /> 
	Main Target Concept: ${model.kobj_target} 
	</div>
	
	<div>
	Current Learning Object: ${model.cur_kobj_name} <br /> 
	Current Target Concept: ${model.cur_kobj_target}
	</div>
</header>

<p>The actual content of the learning object <span id="resource_path"></span></p>

</section>


</body>
</html>