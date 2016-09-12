<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:query var="activations" dataSource="jdbc/impala">
    SELECT devicemodel, count(*) as modelcount
    FROM deviceactivations
    WHERE activationtimestamp >= (UNIX_TIMESTAMP('2014-01-01') * 1000)
    GROUP BY devicemodel
    ORDER BY modelcount DESC
    LIMIT 5
</sql:query>


<div class="tile">
	<div class="tile-heading">Most Popular Devices</div>
	<div id="most-popular-devices">
		<table>
			<thead>
				<tr>
					<th>Device</th>
					<th># Activated</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${activations.rows}">
					<tr>
						<td><c:out value="${row.devicemodel}" /></td>
						<td><c:out value="${row.modelcount}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
