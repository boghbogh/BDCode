<div class="tile">
    <div class="tile-heading">Device Temperature</div>

	<div id="deviceTempChartContainer">
		<script type="text/javascript">
			var deviceTempSvg = dimple.newSvg("#deviceTempChartContainer", window.chartWidth, window.chartHeight);
			d3.csv("../chart-data/device-temp-csv.jsp?numRecords=5", function(data) {
				var tempChart = new dimple.chart(deviceTempSvg, data);
				tempChart.setBounds(25, 20, window.chartBoundsWidth, window.chartBoundsHeight)
				var x = tempChart.addCategoryAxis("x", "Model");
				x.addOrderRule("Count", true)

				tempChart.addMeasureAxis("y", "Device Temp Avg");
				tempChart.addSeries("Model", dimple.plot.bar);

				tempChart.draw();
			});
        </script>
    </div>


</div>

