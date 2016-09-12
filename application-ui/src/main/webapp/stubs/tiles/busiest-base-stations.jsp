<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
    url="jdbc:mysql://dev.loudacre.com/loudacre" user="training"
    password="training" />

<sql:query dataSource="${snapshot}" var="result">
	SELECT *, sum(activity) AS totalactivity FROM mostactivestations
	JOIN basestations ON (mostactivestations.station_num = basestations.station_num)
	GROUP BY basestations.station_num
	ORDER BY totalactivity DESC limit 5;
</sql:query>


<div class="tile">

	<div class="tile-heading">Busiest Base Stations</div>
	<div id="busiest-base-stations">
		<table>
			<thead>
				<tr>
					<th>Station ID</th>
					<th>Location</th>
					<th># of Connections</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${result.rows}">
					<tr>
						<td><c:out value="${row.station_num}" /></td>
						<td><c:out value="${row.city}" />, <c:out value="${row.state}" /></td>
						<td><c:out value="${row.totalactivity}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>

