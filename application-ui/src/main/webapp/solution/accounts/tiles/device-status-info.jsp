<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<div class="tile">

    <div class="tile-heading">Status Info</div>

	<!-- 
	   NOTE: division by 1000 is needed since the time is in milliseconds, but
	   from_unixtime requires a value in seconds. The FLOOR is there so that we
	   get an INT value instead of DOUBLE after division.
	-->
	<sql:query var="status" dataSource="jdbc/impala">
	    SELECT from_unixtime(FLOOR(time / 1000),"yyyy-MM-dd HH:mm:ss") AS tstamp,
	    device_temp, ambient_temp, battery_pct, signal_pct, cpu_load,
	    ram_usage, gps_status, bluetooth_status, wifi_status,
	    ROUND(latitude, 3) AS lat, ROUND(longitude, 3) AS lon
	    FROM devicestatus
	    WHERE device_id='<%= request.getParameter("deviceid") %>'
	    ORDER BY tstamp DESC LIMIT 1
	</sql:query>

	<table id="status-info">
    <c:forEach var="row" items="${status.rows}">
        <tr>
            <th>Time:</th>
            <td><c:out value="${row.tstamp}" /></td>
        </tr>
        <tr>
            <th>Device Temp:</th>
            <td><c:out value="${row.device_temp}" /></td>
        </tr>
        <tr>
            <th>Ambient Temp:</th>
            <td><c:out value="${row.ambient_temp}" /></td>
        </tr>
        <tr>
            <th>Battery %:</th>
            <td><c:out value="${row.battery_pct}" /></td>
        </tr>
        <tr>
            <th>Signal %:</th>
            <td><c:out value="${row.signal_pct}" /></td>
        </tr>
        <tr>
            <th>CPU Load:</th>
            <td><c:out value="${row.cpu_load}" /></td>
        </tr>
        <tr>
            <th>RAM Usage:</th>
            <td><c:out value="${row.ram_usage}" /></td>
        </tr>
        <tr>
            <th>Bluetooth Status:</th>
            <td><c:out value="${row.bluetooth_status}" /></td>
        </tr>
        <tr>
            <th>WiFi Status:</th>
            <td><c:out value="${row.wifi_status}" /></td>
        </tr>
        <tr>
            <th>GPS Status:</th>
            <td><c:out value="${row.gps_status}" /></td>
        </tr>
        <tr>
            <th>Latitude:</th>
            <td><c:out value="${row.lat}" /></td>
        </tr>
        <tr>
            <th>Longitude:</th>
            <td><c:out value="${row.lon}" /></td>
        </tr>
    </c:forEach>
</table>




</div>
