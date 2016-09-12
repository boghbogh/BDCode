<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="tile">

	<div class="tile-heading">Facets</div>

	<div class="tile-sub-heading">Agent</div>
	<c:import url="http://dev.loudacre.com:8983/solr/chat_collection_shard1_replica1/select?q=*%3A*&rows=0&facet=true&facet.field=agent_name&facet.limit=10&wt=xml" var="xml" />
	<x:parse doc="${xml}" var="chatsfacet" scope="request" />

	<div id="agent-facet">
		<x:forEach var="item" select="$chatsfacet/response/lst[@name='facet_counts']/lst[@name='facet_fields']/lst[@name='agent_name']/int">

		<c:set var="agentName">
			<x:out select="$item/@name" />
		</c:set>

		<c:set var="agentNamePlus" value="${fn:replace(agentName,' ', '+')}" />

		<a href="index.jsp?query=agent_name%3A%22${agentNamePlus}%22">${agentName}
			(<x:out select="$item/text()" />)
		</a>
        <br />
   		</x:forEach>
    </div>


	<div class="tile-sub-heading">Category</div>
	<c:import url="http://dev.loudacre.com:8983/solr/chat_collection_shard1_replica1/select?q=*%3A*&rows=0&facet=true&facet.field=category&facet.limit=10&wt=xml" var="xml" />
	<x:parse doc="${xml}" var="chatsfacet" scope="request" />

	<div id="category-facet">
    <x:forEach var="item"
        select="$chatsfacet/response/lst[@name='facet_counts']/lst[@name='facet_fields']/lst[@name='category']/int">

        <c:set var="category">
            <x:out select="$item/@name" />
        </c:set>

        <c:set var="categoryPlus" value="${fn:replace(category,' ', '+')}" />

        <a href="index.jsp?query=category%3A%22${categoryPlus}%22">${category}
            (<x:out select="$item/text()" />)
        </a>
        <br />
    </x:forEach>
    </div>

</div>
