<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<div class="tile">

	<form action="index.jsp" method="POST">

		<table id="find-customer-form-adv">
			<tr>
				<th class="row-name">First Name:</th>
				<td class="row-value"><input name="first_name" type="text" size="9" value="${param.first_name}" /></td>
				<th class="row-name">Last Name:</th>
				<td class="row-value"><input name="last_name" type="text" size="9" value="${param.last_name}" /></td>
			</tr>
			<tr>
				<th class="row-name">Account #:</th>
				<td class="row-value"><input name="acct_num" type="text" size="9" value="${param.acct_num}"></td>
				<th class="row-name">Phone #:</th>
				<td class="row-value"><input name="phone_number" type="text" size="9" value="${param.phone_number}" /></td>
			</tr>
			<tr>
				<th class="row-name">City:</th>
				<td class="row-value"><input name="city" type="text" size="9" value="${param.city}" /></td>
				<th class="row-name">State:</th>
				<td class="row-value">
					<select name="state">
						<option value="">Select a State</option>
						<option value="AZ" <c:if test="${param.state == 'AZ'}">selected="selected"</c:if>>Arizona</option>
						<option value="CA" <c:if test="${param.state == 'CA'}">selected="selected"</c:if>>California</option>
						<option value="NV" <c:if test="${param.state == 'NV'}">selected="selected"</c:if>>Nevada</option>
						<option value="OR" <c:if test="${param.state == 'OR'}">selected="selected"</c:if>>Oregon</option>
					</select>
				</td>
			</tr>
		</table>
		<input id="customer-form-search-button" value="Search" type="submit" class="submit-button">

	</form>

	<c:if test="${not empty param.last_name or not empty param.first_name or not empty param.phone_number or not empty param.acct_num or not empty param.state or not empty param.city}">

		<%
			String qry = "SELECT first_name, last_name, city, state, acct_num FROM accounts WHERE ";

			boolean hasWhere = false;

			// Handle which parameters are filled in and query accordingly
			String paramLastName = request.getParameter("last_name");
			if (paramLastName != null && ! paramLastName.trim().isEmpty() ) {
				qry += " last_name LIKE '%" + paramLastName + "%'";
				hasWhere = true;
			}

			String paramFirstName = request.getParameter("first_name");
			if (paramFirstName != null && ! paramFirstName.trim().isEmpty() ) {
				if (hasWhere) {
					qry += " AND ";
				}
				qry += " first_name LIKE '%" + paramFirstName + "%'";
				hasWhere = true;
			}

			String paramState = request.getParameter("state");
			if (paramState != null && ! paramState.trim().isEmpty()) {
				if (hasWhere) {
					qry += " AND ";
				}

				qry += " state='" + paramState + "' ";
				hasWhere = true;
			}

			String paramCity = request.getParameter("city");
			if (paramCity != null && ! paramCity.trim().isEmpty()) {
				if (hasWhere) {
					qry += " AND ";
				}

				qry += " city='" + paramCity + "' ";
				hasWhere = true;
			}

			String paramPhoneNumber = request.getParameter("phone_number");
			if (paramPhoneNumber != null && ! paramPhoneNumber.trim().isEmpty() ) {
				if (hasWhere) {
					qry += " AND ";
				}
				qry += " phone_number LIKE '%" + paramPhoneNumber + "%'";
				hasWhere = true;
			}

			String paramAcctNum = request.getParameter("acct_num");
			if (paramAcctNum != null && ! paramAcctNum.trim().isEmpty()) {
				if (hasWhere) {
					qry += " AND ";
				}

				qry += " acct_num=" + paramAcctNum + " ";
				hasWhere = true;
			}

			qry += " LIMIT 10";
		%>

		<sql:query var="accounts" dataSource="jdbc/impala">
			<%= qry %>
		</sql:query>

		<hr />
		<p> </p>


		<div class="tile-heading">Search Results</div>
		<table id="account-results-table" class="results-display-table">
			<tr>
				<th>Account #</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>City</th>
				<th>State</th>
			</tr>

			<c:forEach var="row" items="${accounts.rows}">
				<tr>
					<td><a href="account-detail.jsp?acct_num=<c:out value="${row.acct_num}" />"><c:out
								value="${row.acct_num}" /></a></td>
					<td><c:out value="${row.first_name}" /></td>
					<td><c:out value="${row.last_name}" /></td>
					<td><c:out value="${row.city}" /></td>
					<td><c:out value="${row.state}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>
