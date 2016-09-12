<%@page language="java" 
        contentType="application/json; charset=UTF-8" 
        pageEncoding="UTF-8" 
        trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<sql:query var="hits" dataSource="jdbc/impala">

	SELECT status, count(status) AS statuscount
		FROM calldetails
		GROUP BY status

</sql:query> [ <c:forEach var="row" items="${hits.rows}" varStatus="loop">
	{ 
		"Status" : "${row.status}",
		"Count"  : ${row.statuscount} 
	}<c:if test="${!loop.last}">,</c:if>
</c:forEach>
]
