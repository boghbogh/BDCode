<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="tile">

    <div class="tile-heading">Support Chat Transcript Search</div>
    <div id="account-chat-search">
        <form action="../transcripts/index.jsp" method="GET">
            <div class="row-name">Search All Transcripts:</div>
            <div class="row-value">
                <input name="query" id="query" type="text" value="chat_text:searchterm" size="50"/>
            </div>
            <div class="search-button">
                <input id="chat-main-search-button" class="submit-button" value="Search" type="submit" />
            </div>
        </form>
    </div>



<c:if test="${not empty param.query}">
	<table id="chat-search-result-table">
		<thead>
			<tr>
				<th>Conversation</th>
				<th>Agent</th>
				<th>Time</th>
				<th>Category</th>
			</tr>
		</thead>
		<tbody>

		<c:set var="queryPlus" value="${fn:replace(param.query,' ', '+')}" />
		<c:import
			url="http://dev.loudacre.com:8983/solr/chat_collection_shard1_replica1/select?q=${queryPlus}&wt=xml"
			var="xml" />
		<x:parse doc="${xml}" var="chats" scope="request" />



		<x:forEach var="item" select="$chats/response/result/doc">
		<tr>
			<td><a href="detail.jsp?conversationid=<x:out select="$item/str[@name='id']/text()"/>"><x:out
							select="$item/str[@name='id']/text()" /></a>
			</td>
			<td><x:out select="$item/str[@name='agent_name']/text()" /></td>
			<td>
				<x:set var="timestamp" select="string($item/arr[@name='timestamp']/date/text())" />
				<fmt:parseDate var="chatDateValue" value="${timestamp}" pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" />
				<fmt:formatDate value="${chatDateValue}" pattern="MM/dd/yyyy HH:mm:ss" />
			</td>
			<td><x:out select="$item/str[@name='category']/text()" /></td>
		</tr>
		</x:forEach>



		</tbody>
	</table>
</c:if>


</div>
