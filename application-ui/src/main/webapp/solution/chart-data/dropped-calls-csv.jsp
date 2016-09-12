<%@page language="java" 
	contentType="text/csv; charset=UTF-8" 
	pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" %><%@taglib 

	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib 
	prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%><%

	int numRecords = 10;
	if (request.getParameter("numRecords") != null) {
		numRecords = Integer.valueOf(request.getParameter("numRecords"));
	}

%>"Model","Dropped Call","Count"<sql:query var="hits" dataSource="jdbc/impala">
SELECT deviceactivations.devicemodel, status, COUNT(status) AS statuscounts, topmodels.modelcount
FROM deviceactivations
JOIN calldetails ON (calldetails.from_phone = deviceactivations.phone_number)
AND calldetails.call_begin >= '2014-01-01'
INNER JOIN (
	SELECT devicemodel, count(*) as modelcount
	FROM deviceactivations
	WHERE activationtimestamp >= (UNIX_TIMESTAMP('2014-01-01') * 1000)
	GROUP BY devicemodel
	ORDER BY modelcount DESC
	LIMIT <%= numRecords %>
) topmodels
ON (deviceactivations.devicemodel = topmodels.devicemodel)
GROUP BY deviceactivations.devicemodel, status, topmodels.modelcount
ORDER BY deviceactivations.devicemodel DESC, statuscounts DESC
LIMIT <%= numRecords * 3 %>
</sql:query>
<c:forEach var="row" items="${hits.rows}">
"<c:out value="${row.devicemodel}"/>",<c:out value="${row.status}"/>,<c:out value="${row.statuscounts}"/>
</c:forEach>
