<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="tile">

    <div class="tile-heading">Knowledge Base Article (<c:out value="${param.articleid}" />)</div>

    <div id="kb-content">
        <c:import
            url="http://dev.loudacre.com:8983/solr/kb_collection_shard1_replica1/select?q=id:${param.articleid}&wt=xml"
            var="xml" />
        <x:parse doc="${xml}" var="articles" scope="request" />

        <x:forEach var="item" select="$articles/response/result/doc">
            <x:set var="kbarticle" select="$item/str[@name='kb_content']/text()" />
            <c:set var="html"><x:out select="$kbarticle" escapeXml="false" /></c:set>
            ${fn:substringBefore(fn:substringAfter(html, "<body>"), "</body>")}
        </x:forEach>
    </div>

</div>
