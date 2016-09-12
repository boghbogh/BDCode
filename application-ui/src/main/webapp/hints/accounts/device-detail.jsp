<jsp:include page="/common/includes/header.jsp">
    <jsp:param name="subtitle" value="Device Details" />
</jsp:include>

<div id="left-col">
    <jsp:include page="tiles/device-account-summary.jsp" />
    <jsp:include page="tiles/device-status-info.jsp" />
</div>

<div id="main-col" style="padding-left: 25px; padding-right: 5px">
    <jsp:include page="tiles/device-relevant-articles.jsp" />
    <jsp:include page="tiles/device-recent-calls.jsp" />
</div>


<jsp:include page="/common/includes/footer.jsp" />
