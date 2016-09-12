<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<!--

	TODO: Add a query that will select the five devices from 
          the deviceactivations table that were activated more 
          than any others from January 1, 2014 to the present 
          time. Afterwards, remove the JSP comment characters 
          so the results will be displayed.

-->

<%--
<sql:query var="activations" dataSource="jdbc/impala">
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
--%>
			</tbody>
		</table>
	</div>
</div>
