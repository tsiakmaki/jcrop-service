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
		
		
		<!--  -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="/js/jquery/1.11.2/jquery.min.js"></script>
	    <!-- Include all compiled plugins (below), or include individual files as needed -->
	    <script src="/js/bootstrap.min.js"></script>
	    
	    <script type="text/javascript">
			var traversablelist = ${traversableList};
		</script>
	    <script src="/js/korder.js"></script>
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
		
			
			<div>
				<dl class="dl-horizontal">
				
					<dt>Learning Object</dt>
					<dd><span id="mainkobj">${kobject.name}</span></dd>
					
					<dt>Target Concept</dt>
				  <dd><a href="/web/concepts/${kobject.targetEducationalObjective.name}">
				  		<span id="maintarget">${kobject.targetEducationalObjective.name}</span></a>
				  </dd>
				  		
				  		
				  <dt>Current Learning Object</dt>
					<dd><span id="curkobj">${kobject.name}</span></dd>
					
					<dt>Current Target Concept</dt>
				  <dd><a id="curtargethref" href="/web/concepts/${kobject.targetEducationalObjective.name}">
				  	<span id="curtarget">${kobject.targetEducationalObjective.name}</span></a>
				  </dd>

				</dl>
			</div>
			
			<div class="row">
				<div class="col-md-8">
		         	<p id="resource">${resource}</p>
				</div>
				
				<div class="col-md-4" id="svg">
					
					<div class="row">
						<div class="col-md-12 img-thumbnail" id="mainsvg">
							<c:if test="${not empty graph}">
								${graph}
							</c:if>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12 img-thumbnail empty" id="cursvg">
						
						</div>
					</div>

				</div>
			</div> <!-- end main row --> 
			<div class="row">
					<p><input id="nextbutton" class="btn btn-default" type="submit" value="Next" /> 
		      <input class="hidden" id="resetbutton" class="btn btn-default" type="submit" value="Reset" /></p>

		     	<ol class="breadcrumb">
						<li class="active">Your Concepts</li>
						
					</ol>
			</div>



		</div> <!-- end container --> 
		




		<footer class="footer">
			<div class="container">
				<p class="text-muted">footer content here.</p>
			</div>
		</footer>

	</body>
</html>