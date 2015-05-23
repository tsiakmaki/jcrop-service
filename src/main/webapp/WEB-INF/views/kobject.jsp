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
		
			
			<div>
				<dl class="dl-horizontal">
					<dt>Learning Object</dt>
					<dd>${kobject.name}</dd>
				</dl>
			</div>
			
			<div class="row">
			<div class="col-md-8">
	        <dl class="dl-horizontal">
				  <dt>Target Concept</dt>
				  <dd><a href="/web/concepts/${kobject.targetEducationalObjective.name}">${kobject.targetEducationalObjective.name}</a></dd>
				  
				  <dt>Prerequisite Concepts</dt>
				  <dd>                    	
				  	<c:forEach var="p" items="${kobject.prerequisites}" varStatus="status">
	                	<ul class="list-inline">
	  						<li><a href="/web/concepts/${p.name}">${p.name}</a> | </li>
						</ul>
	                </c:forEach> 
	              </dd>
	              
				  <dt>Content Ontology</dt>
				  <dd>${kobject.associatedContentOntology.name}</dd>
				  
				  <br/>
				  <dt></dt>
				  <dd><c:if test="${not empty graph}">
						<div class="img-thumbnail" id="svg">
							${graph}
						</div>
					</c:if>
				  </dd>
				  
				  <br/>
				  <dt></dt>
				  <dd><a class="btn btn-default" href="/web/kobjects/${kobject.name}/korder">Start Course</a></dd>
				  
				</dl>

			
					
			</div>

			</div>
			
			</div> <!-- end row --> 
			
		</div> <!-- end container --> 
		
		<footer class="footer">
			<div class="container">
				<p class="text-muted">footer content here.</p>
			</div>
		</footer>

	</body>
</html>