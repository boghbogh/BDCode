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

%>"Model","Device Temp Avg","Count"<sql:query var="hits" dataSource="jdbc/impala">
SELECT devicemodel, AVG(device_temp) AS avgdevicetemp, modelcount
FROM devicestatus
INNER JOIN (
	SELECT devicemodel, count(*) as modelcount
	FROM deviceactivations
	WHERE activationtimestamp >= (UNIX_TIMESTAMP('2014-01-01') * 1000)
	GROUP BY devicemodel
	ORDER BY modelcount DESC
	LIMIT <%= numRecords %>
) topmodels
ON (devicestatus.name = topmodels.devicemodel)
AND time >= (UNIX_TIMESTAMP('2014-01-01') * 1000)
GROUP BY devicemodel, modelcount
ORDER BY modelcount DESC
LIMIT <%= numRecords %>
</sql:query>
<c:forEach var="row" items="${hits.rows}">
"<c:out value="${row.devicemodel}"/>",<c:out value="${row.avgdevicetemp}"/>,<c:out value="${row.modelcount}"/>
</c:forEach>
