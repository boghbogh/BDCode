<div class="tile">
    <div class="tile-heading">Call Status</div>

    <div id="droppedCallChartContainer">
        <script type="text/javascript">
            var droppedCallSvg = dimple.newSvg("#droppedCallChartContainer", window.chartWidth, window.chartHeight);
            d3.csv("../chart-data/dropped-calls-csv.jsp?numRecords=5", function(data) {
                var callChart = new dimple.chart(droppedCallSvg, data);
                callChart.setBounds(35, 20, window.chartBoundsWidth, window.chartBoundsHeight)
                var x = callChart.addCategoryAxis("x", "Model");
                x.addOrderRule("Count", true)

                callChart.addPctAxis("y", "Count");

                callChart.addLegend(60, 5, 450, 15, "right");
                callChart.addSeries("Dropped Call", dimple.plot.bar);

                callChart.draw();
            });
        </script>
    </div>


</div>
