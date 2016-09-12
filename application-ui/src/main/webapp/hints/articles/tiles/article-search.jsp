<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="tile">

    <div class="tile-heading">Knowledge Base Article Search</div>

    <div id="top-requested-articles">
        <form action="index.jsp" method="GET">
            <div class="row-name">Search All Articles:</div>
            <div class="row-value">
                <input name="query" id="query" type="text" size="50"/>
            </div>
            <div class="search-button">
                <input id="kb-main-search-button" class="submit-button" value="Search" type="submit" />
            </div>
        </form>
    </div>


    <div id="article-search-results">
    <c:if test="${not empty param.query}">
        <table id="kbsearchtable" class="results_display_table">
            <tr>
                <th>Article ID</th>
                <th>Title</th>
            </tr>

            <c:set var="queryPlus" value="${fn:replace(param.query,' ', '+')}" />

            <c:import
                url="http://dev.loudacre.com:8983/solr/kb_collection_shard1_replica1/select?q=kb_content:${queryPlus}&wt=xml"
                var="xml" />
            <x:parse doc="${xml}" var="articles" scope="request" />

            <x:forEach var="item" select="$articles/response/result/doc">
                <c:set var="articlePreview" value="${fn:replace(param.query,' ', '+')}" />

                <tr>
                    <td><a
                        href="display.jsp?articleid=<x:out select="$item/str[@name='id']/text()"/>"><x:out
                                select="$item/str[@name='id']/text()" /></a></td>
                    <td><x:out select="$item/str[@name='kb_title']/text()" /></td>
                </tr>
            </x:forEach>
        </table>
    </c:if>
    </div>



</div>
