<div class="tile">
    <div class="tile-heading">Battery Percent</div>

	<div id="batteryPercentChartContainer">
		<script type="text/javascript">
			var batteryPercentSvg = dimple.newSvg("#batteryPercentChartContainer", window.chartWidth, window.chartHeight);
			d3.csv("../chart-data/battery-percent-csv.jsp?numRecords=5", function(data) {
				var batteryChart = new dimple.chart(batteryPercentSvg, data);
				batteryChart.setBounds(25, 20, window.chartBoundsWidth, window.chartBoundsHeight)
				var x = batteryChart.addCategoryAxis("x", "Model");
				x.addOrderRule("Count", true)

				var y = batteryChart.addMeasureAxis("y", "Battery Avg");
				y.overrideMax = 100;

				batteryChart.addSeries("Model", dimple.plot.bar);

				batteryChart.draw();
			});
		</script>
	</div>

</div>
