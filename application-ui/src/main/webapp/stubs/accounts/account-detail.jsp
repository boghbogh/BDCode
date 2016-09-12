<jsp:include page="/common/includes/header.jsp">
    <jsp:param name="subtitle" value="Account Details" />
</jsp:include>

<div id="left-col">
    <jsp:include page="tiles/account-info.jsp" />
</div>

<div id="main-col">
    <jsp:include page="tiles/account-chat-sessions.jsp" />
</div>


<div id="single-col" style="padding-top: 20px">
    <jsp:include page="tiles/account-associated-devices.jsp" />
</div>

<jsp:include page="/common/includes/footer.jsp" />
