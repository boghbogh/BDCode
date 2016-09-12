<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="tile">

    <div class="tile-heading">Knowledge Base Article (<c:out value="${param.articleid}" />)</div>

<!--
   TODO: replace the 'FIXME' in the 'url' attribute below with a URL
         that will select the article from the knowledge base collection 
         with an 'id' parameter that matches ${param.articleid} and 
         returns the result in XML format.

         Hint: The following URL is an incomplete example to show you 
               how the 'id' query parameter should be constructed.

         http://myserver.example.com:8983/solr/mycollection_shard1_replica1/select?q=id:${param.articleid}

         Afterwards, be sure to remove the JSP comment characters (e.g., 
         the less than sign followed by a percent sign and two dashes, 
         plus the corresponding closing sequence) below so your code
         will run.

-->

<%--
    <div id="kb-content">
        <c:import
            url="FIXME"
            var="xml" />
        <x:parse doc="${xml}" var="articles" scope="request" />

        <x:forEach var="item" select="$articles/response/result/doc">
            <x:set var="kbarticle" select="$item/str[@name='kb_content']/text()" />
            <c:set var="html"><x:out select="$kbarticle" escapeXml="false" /></c:set>
            ${fn:substringBefore(fn:substringAfter(html, "<body>"), "</body>")}
        </x:forEach>

    </div>
--%>

</div>
