<div class="tile">
	<div id="chart-column">
		<div class="tile-heading">Call Status</div>
		<div id="callStatusChartContainer">
			<script type="text/javascript">
				var callStatusSvg = dimple.newSvg("#callStatusChartContainer", 300, 370);
               	d3.json("chart-data/call-status-json.jsp", function(callData) {
					var callStatusChart = new dimple.chart(callStatusSvg, callData);
					callStatusChart.setBounds(55, 20, 220, 310)
					var x = callStatusChart.addCategoryAxis("x", "Status");
					x.addOrderRule(["FAILED", "DROPPED", "SUCCESS"]);

					callStatusChart.addMeasureAxis("y", "Count");
					callStatusChart.addSeries("Status", dimple.plot.bar);
					callStatusChart.assignColor("SUCCESS", "green", "black", 0.7);
					callStatusChart.assignColor("DROPPED", "orange", "black", 0.7);
					callStatusChart.assignColor("FAILED", "red", "black", 0.7);

					callStatusChart.draw();
				});
			</script>
		</div>
	</div>
</div>

