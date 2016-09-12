<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<div class="tile">

    <div class="tile-heading">Recent Calls</div>

    <div class="tile-sub-heading">Calls Placed From this Device</div>
	<sql:query var="placed_calls" dataSource="jdbc/impala">
        SELECT call_begin, call_end, status,
        PHONENUMBERFORMATTER(to_phone) AS to_formatted
        FROM calldetails
        JOIN deviceactivations ON (calldetails.from_phone = deviceactivations.phone_number)
        WHERE deviceactivations.device_id='<%= request.getParameter("deviceid") %>'
        LIMIT 5
	</sql:query>

	<table id="recent-calls-to">
	    <thead>
		    <tr>
    		    <th>Begin</th>
	    	    <th>End</th>
	        	<th>Status</th>
		        <th>To</th>
		    </tr>
	    </thead>
	    <tbody>
		    <c:forEach var="row" items="${placed_calls.rows}">
    	    <tr>
	            <td><c:out value="${row.call_begin}" /></td>
	            <td><c:out value="${row.call_end}" /></td>
	            <td><c:out value="${row.status}" /></td>
	            <td><c:out value="${row.to_formatted}" /></td>
	        </tr>
		    </tbody>
	    	</c:forEach>
	</table>

    <div class="tile-sub-heading">Calls Placed To this Device</div>
	<sql:query var="received_calls" dataSource="jdbc/impala">
        SELECT call_begin, call_end, status,
        PHONENUMBERFORMATTER(from_phone) AS from_formatted
        FROM calldetails
        JOIN deviceactivations ON (calldetails.to_phone = deviceactivations.phone_number)
        WHERE deviceactivations.device_id='<%= request.getParameter("deviceid") %>'
        LIMIT 5
	</sql:query>

	<table id="recent-calls-from">
	    <thead>
		    <tr>
    		    <th>Begin</th>
	    	    <th>End</th>
	        	<th>Status</th>
		        <th>From</th>
		    </tr>
	    </thead>
	    <tbody>
		    <c:forEach var="row" items="${received_calls.rows}">
    	    <tr>
	            <td><c:out value="${row.call_begin}" /></td>
	            <td><c:out value="${row.call_end}" /></td>
	            <td><c:out value="${row.status}" /></td>
	            <td><c:out value="${row.from_formatted}" /></td>
	        </tr>
		    </tbody>
	    	</c:forEach>
	</table>


</div>
