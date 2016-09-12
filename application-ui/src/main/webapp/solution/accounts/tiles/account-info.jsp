<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="detailDateValue" class="java.util.Date" />

<sql:query var="accounts" dataSource="jdbc/impala">
	SELECT acct_num, acct_create_dt, acct_close_dt, first_name, last_name, 
	address, city, state, zipcode, PHONENUMBERFORMATTER(phone_number) AS phone_number
	FROM accounts
	where acct_num=<%= request.getParameter("acct_num") %>
</sql:query>

<div class="tile">

	<div class="tile-heading">Account Details</div>
	<c:forEach var="row" items="${accounts.rows}">
	<div id="account-details">
		<div class="row-name">Account #:</div>
		<div class="row-value"><c:out value="${row.acct_num}" /></div>

		<div class="row-name">Name:</div>
		<div class="row-value">
			<c:out value="${row.first_name}" />
			<c:out value="${row.last_name}" />
		</div>

		<div class="row-name">Address:</div>
		<div class="row-value" style="height:60px">
			<c:out value="${row.address}" /> 
			<c:out value="${row.city}" />, 
			<c:out value="${row.state}" /> 
			<c:out value="${row.zipcode}" />
		</div>

		<div class="row-name">Opened On:</div>
		<div class="row-value">
			<jsp:setProperty name="detailDateValue" property="time" value="${row.acct_create_dt.time}" />
			<fmt:formatDate value="${detailDateValue}" pattern="MM/dd/yyyy" />
		</div>

		<div class="row-name">Closed On:</div>
		<div class="row-value">
			<c:choose>
				<c:when test="${not empty row.acct_close_dt}">
					<jsp:setProperty name="detailDateValue" property="time" value="${row.acct_close_dt.time}" />
					<fmt:formatDate value="${detailDateValue}" pattern="MM/dd/yyyy" />
				</c:when>
				<c:otherwise>
					<i>Currently active</i>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	</c:forEach>

</div>
