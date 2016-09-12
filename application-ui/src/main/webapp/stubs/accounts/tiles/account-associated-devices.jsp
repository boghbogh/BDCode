<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date" />


<sql:query var="activations" dataSource="jdbc/impala">
    SELECT deviceactivations.device_id, deviceactivations.devicemodel,
    deviceactivations.activationTimestamp, MAX(devicestatus.time) AS lastStatusTime
    FROM deviceactivations
    FULL OUTER JOIN devicestatus ON (deviceactivations.device_id = devicestatus.device_id)
    WHERE acct_num=<%= request.getParameter("acct_num") %>
    GROUP BY device_id, deviceModel, activationTimestamp
</sql:query>


<div class="tile">

	<div class="tile-heading">Devices Associated with this Account</div>
	<div id="account-device-table">
		<table>
			<thead>
				<tr>
					<th>Device ID</th>
					<th>Name</th>
					<th>Activated</th>
					<th>Last Status Update</th>
				</tr>
			</thead>
			<tbody>
			    <c:forEach var="row" items="${activations.rows}">
				<tr>
					<td><a href="device-detail.jsp?deviceid=<c:out value="${row.device_id}" />"><c:out value="${row.device_id}" /></a></td>
					<td><c:out value="${row.devicemodel}" /></td>
					<td>
						<jsp:setProperty name="dateValue" property="time" value="${row.activationTimestamp}" />
						<fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm:ss" />
					</td>
					<td>
		                <c:choose>
        	    	        <c:when test="${not empty row.lastStatusTime}">
            	    	        <jsp:setProperty name="dateValue" property="time" value="${row.lastStatusTime}" />
		                        <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm:ss" />
        		            </c:when>
                		    <c:otherwise>
		                        N/A
        		            </c:otherwise>
                		</c:choose>
					</td>
				</tr>
		    	</c:forEach>
			</tbody>
		</table>
	</div>

</div>
