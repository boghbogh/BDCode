<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:query var="models" dataSource="jdbc/impala">
    SELECT devicemodel 
	FROM deviceactivations 
	WHERE device_id='<%= request.getParameter("deviceid") %>'
</sql:query>

<c:forEach var="row" items="${models.rows}">
    <c:set var="manufacturer" value="${fn:replace(row.devicemodel, ' ', '%2B')}" />
</c:forEach>

<div class="tile">

    <div class="tile-heading">Relevant Knowledge Base Articles</div>

	<table id="most-helpful-articles">
		<thead>
	    <tr>
    	    <th>ID</th>
    	    <th>Title</th>
	    </tr>
		</thead>
		<tbody>

	    <c:import
	        url="http://dev.loudacre.com:8983/solr/kb_collection_shard1_replica1/select?q=kb_title%3A%22${manufacturer}%22&wt=xml"
	        var="xml" />
	    <x:parse doc="${xml}" var="articles" scope="request" />

	    <x:forEach var="item" select="$articles/response/result/doc">
        <tr>
            <td><a href="../articles/display.jsp?articleid=<x:out select="$item/str[@name='id']/text()"/>"><x:out
                        select="$item/str[@name='id']/text()" /></a>
			</td>
            <td><x:out select="$item/str[@name='kb_title']/text()" /></td>
        </tr>
	    </x:forEach>

		</tbody>
	</table>


</div>
