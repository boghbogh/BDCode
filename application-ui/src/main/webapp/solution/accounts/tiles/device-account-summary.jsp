<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:query var="activations" dataSource="jdbc/impala">
    SELECT *,
    PHONENUMBERFORMATTER(phone_number) AS phone_formatted
    FROM deviceactivations
    WHERE device_id='<%= request.getParameter("deviceid") %>'
</sql:query>


<div class="tile">

    <div class="tile-heading">Account Summary</div>

	<table id="account-summary">
	    <c:forEach var="row" items="${activations.rows}">
        <tr>
            <th>Account #:</th>
            <td><a href="account-detail.jsp?acct_num=<c:out value="${row.acct_num}" />"><c:out
                value="${row.acct_num}" /></a>
            </td>
        </tr>
        <tr>
            <th>Manufacturer:</th>
            <td><c:out value="${row.devicemodel}" /></td>
        </tr>
        <tr>
            <th>Phone #:</th>
            <td><c:out value="${row.phone_formatted}" /></td>
        </tr>
	    </c:forEach>
	</table>


</div>
