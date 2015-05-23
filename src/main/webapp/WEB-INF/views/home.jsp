<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <title>Crop Learning Objects</title>
	
		<!-- Bootstrap -->
	    <link href="/css/bootstrap.min.css" rel="stylesheet">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="/js/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="/js/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	    
		<link rel="stylesheet" type="text/css" href="/css/style.css" >
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="/js/jquery/1.11.2/jquery.min.js"></script>
	    <!-- Include all compiled plugins (below), or include individual files as needed -->
	    <script src="/js/bootstrap.min.js"></script>
	</head>

	<body>
		<div class="container">
			<div class="page-header">
				<h1>
				Crop service 
				</h1>
				<p class="lead">
					A semantic web service for crop learning objects
				</p>
			</div>
		
			
			<p><a href="<c:url value="/web/concepts" />">List of concepts</a></p>
			<p><a href="<c:url value="/web/kobjects" />">List of learning objects</a></p>
		
		</div>
		
		<footer class="footer">
			<div class="container">
				<p class="text-muted">footer content here.</p>
			</div>
		</footer>

	</body>
</html>