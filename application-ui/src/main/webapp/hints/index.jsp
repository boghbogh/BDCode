<jsp:include page="/common/includes/header.jsp">
	<jsp:param name="subtitle" value="Main Page" />
</jsp:include>

<div id="left-col">
	<jsp:include page="tiles/find-customer.jsp" />
	<jsp:include page="tiles/call-status-chart.jsp" />
</div>

<div id="main-col">
	<jsp:include page="tiles/most-requested-articles.jsp" />

	<div id="tables-side-by-side">
		<div id="left-table">
			<jsp:include page="tiles/most-popular-devices.jsp" />
		</div>
		<div id="right-table">
			<jsp:include page="tiles/busiest-base-stations.jsp" />
		</div>
	</div>
</div>

<jsp:include page="/common/includes/footer.jsp" />
