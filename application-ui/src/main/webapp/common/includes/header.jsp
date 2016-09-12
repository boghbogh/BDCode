<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Loudacre Mobile Support Application - <%=request.getParameter("subtitle")%></title>
		<link rel="stylesheet" href="/common/css/style.css" type="text/css" media="screen" />
		<script src="/common/js/dimple.v1.1.5.min.js"></script>
		<script src="/common/js/d3.v3.min.js"></script>
	</head>

	<body>
		<section id="header">
			<div id="header-logo-col"><img src="/common/img/loudacre-logo-small.png" alt="Loudacre Mobile Company Logo" /></div>
			<div id="header-nav-col">
				<div id="menu">
					<ul id="nav">
					<%
						// calculate whether we are in hints, stubs, or solution
						String path = request.getServletPath();
						String basePath = path.substring(0, path.indexOf("/", 1));
					%>

		   				<li><a href="<%= basePath %>/" class="unselected-item">Home</a></li>
						<li><a href="<%= basePath %>/accounts/" class="unselected-item">Accounts</a></li>
						<li><a href="<%= basePath %>/articles/" class="unselected-item">KB Articles</a></li>
		   				<li><a href="<%= basePath %>/transcripts/" class="unselected-item">Transcripts</a></li>
		   				<li><a href="<%= basePath %>/status" class="unselected-item">Device Stats</a></li>
					</ul>
				</div>
			</div>
		</section>

 		<section id="content">
