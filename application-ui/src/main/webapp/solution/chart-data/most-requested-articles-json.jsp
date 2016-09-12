<%@page language="java" 
	contentType="application/json; charset=UTF-8" 
	pageEncoding="UTF-8" 
	trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%><%

int numRecords = 10;
if (request.getParameter("numRecords") != null) {
	numRecords = Integer.valueOf(request.getParameter("numRecords"));
}

%><sql:query var="hits" dataSource="jdbc/impala">

	SELECT regexp_replace(kb_article, '\\.html', '') AS article, hits
		FROM mostpopularkbs
		ORDER BY hits DESC
		LIMIT <%= numRecords %>

</sql:query>
[ <c:forEach var="row" items="${hits.rows}" varStatus="loop">
	{
		"Article": "${row.article}", 
		"Requests": ${row.hits}
	}<c:if test="${! loop.last}">,</c:if>
</c:forEach>
]
