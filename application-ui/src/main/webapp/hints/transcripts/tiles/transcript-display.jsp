<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="tile">

    <div class="tile-heading">Support Chat Transcript Detail</div>

<c:import
	url="http://dev.loudacre.com:8983/solr/chat_collection_shard1_replica1/select?q=id:${param.conversationid}&wt=xml"
	var="xml" />
<x:parse doc="${xml}" var="chats" scope="request" />

<x:forEach var="item" select="$chats/response/result/doc" begin="0" end="0">
<div class="regular-main">
	<div class="tile-sub-heading">Conversation Summary</div>

    <div id="chat-detail-table">
		<div class="row-name">Conversation ID:</div>
		<div class="row-value"><x:out select="$item/str[@name='id']/text()" /></div>

<!--
   TODO: Using the 'Conversation ID' row above as a guide, add a new row 
         that will display the name of the agent who helped the customer. 
         The field is called 'agent_name' in the XML (for reference, the 
         'Conversation ID' field is called 'id' in the XML).
-->


		<div class="row-name">Account #:</div>
		<div class="row-value">
			<a href="../accounts/account-detail.jsp?acct_num=<x:out select="$item/int[@name='account_num']/text()" />">
                <x:out select="$item/int[@name='account_num']/text()" />
            </a>
		</div>

		<div class="row-name">Started At:</div>
		<div class="row-value">
            <x:set var="timestamp" select="string($item/arr[@name='timestamp']/date/text())" />
            <fmt:parseDate var="chatDateValue" value="${timestamp}" pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" />
            <fmt:formatDate value="${chatDateValue}" pattern="MM/dd/yyyy HH:mm:ss" />
		</div>
	</div>

	<p> </p>

	<div class="tile-sub-heading">Conversation Transcript</div>
	<table style="font-size: 85%">
		<x:forEach var="transcript" select="$item/arr[@name='chat_text']/str"
			varStatus="loopStatus">
			<c:set var="count" value="${loopStatus.count}" scope="page" />

				<tr>
					<td class="timestamp"><x:set var="timestamp"
							select="string(($item/arr[@name='timestamp']/date)[$pageScope:count]/text())" />
						<fmt:parseDate var="chatDateValue" value="${timestamp}" pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" />
						<fmt:formatDate value="${chatDateValue}" pattern="MM/dd/yyyy HH:mm:ss" />	
					</td>
					<td><x:out
							select="($item/arr[@name='sender']/str)[$pageScope:count]/text()" /></td>
					<td><x:out select="$transcript/text()" /></td>
				</tr>

		</x:forEach>
	</table>

</x:forEach>

	</div>
</div>
