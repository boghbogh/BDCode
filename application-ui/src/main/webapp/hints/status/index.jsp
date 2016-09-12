<jsp:include page="/common/includes/header.jsp">
    <jsp:param name="subtitle" value="Aggregate Device Statistics Visualization"/>
</jsp:include>

<script type="text/javascript">
    window.chartWidth = 500;
    window.chartHeight = 250;

    window.chartBoundsWidth = 450;
    window.chartBoundsHeight = 150;
</script>


<div id="single-col">
	<table>
		<tr>
			<td><jsp:include page="tiles/chart-temp.jsp" /></td>
			<td><jsp:include page="tiles/chart-battery.jsp" /></td>
		</tr>
		<tr>
			<td><jsp:include page="tiles/chart-calls.jsp" /></td>
			<td><jsp:include page="tiles/chart-cpu.jsp" /></td>
		</tr>
	</table>
</div>

<jsp:include page="/common/includes/footer.jsp" />
