<div class="tile">
    <div class="tile-heading">CPU Load</div>


	<div id="cpuLoadChartContainer">
		<script type="text/javascript">
			var cpuLoadSvg = dimple.newSvg("#cpuLoadChartContainer", window.chartWidth, window.chartHeight);
			d3.csv("../chart-data/cpu-load-csv.jsp?numRecords=5", function(data) {
				var cpuChart = new dimple.chart(cpuLoadSvg, data);
				cpuChart.setBounds(25, 20, window.chartBoundsWidth, window.chartBoundsHeight)
				var x = cpuChart.addCategoryAxis("x", "Model");
				x.addOrderRule("Count", true)

				var y = cpuChart.addMeasureAxis("y", "CPU Avg");
				y.overrideMax = 100;

				cpuChart.addSeries("Model", dimple.plot.bar);

				cpuChart.draw();
			});
        </script>
    </div>


</div>

