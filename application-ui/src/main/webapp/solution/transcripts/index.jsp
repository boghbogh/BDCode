<jsp:include page="/common/includes/header.jsp">
    <jsp:param name="subtitle" value="Support Chat Transcript Search" />
</jsp:include>

<div id="left-col" style="width:210px">
	<jsp:include page="tiles/facets.jsp" />
</div>

<div id="main-col">
	<jsp:include page="tiles/transcript-search.jsp" />
</div>

<jsp:include page="/common/includes/footer.jsp" />
