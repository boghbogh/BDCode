<%@page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<div class="tile">
	<div class="tile-heading">Top Requested Knowledge Base Articles</div>
	<div id="top-requested-articles">
		<form action="articles/index.jsp" method="GET">
			<div class="row-name">Search All Articles:</div>
			<div class="row-value">
				<input name="query" id="query" type="text" size="50"/>
			</div>
			<div class="search-button">
				<input id="kb-main-search-button" class="submit-button" value="Search" type="submit" />
			</div>
		</form>
	</div>

	<div id="mostRequestedChartContainer">
		<script type="text/javascript">
			var mostRequestedSvg = dimple.newSvg("#mostRequestedChartContainer", 700, 200);
			d3.json("chart-data/most-requested-articles-json.jsp?numRecords=8", function(data) {
				var myChart = new dimple.chart(mostRequestedSvg, data);
				myChart.setBounds(50, 10, 650, 150)
				var x = myChart.addCategoryAxis("x", "Article");
				var y = myChart.addMeasureAxis("y", "Requests");
				myChart.addSeries("Requests", dimple.plot.bar);

				myChart.draw();
				x.shapes.selectAll("text").attr("transform", "rotate(0)");
			});
		</script>

	</div>
</div>
