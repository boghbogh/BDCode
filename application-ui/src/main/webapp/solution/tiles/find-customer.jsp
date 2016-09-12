<div class="tile">
	<div class="tile-heading">Find Customers</div>
	<div id="find-customer-form">
		<form action="accounts/index.jsp" method="POST">
			<input name="first_name" value="" type="hidden" />
			<input name="state" value="" type="hidden" />

			<div class="row-name">Last Name:</div>
			<div class="row-value"><input name="last_name" type="text" size="8" /></div>

			<div class="row-name">Account #:</div>
			<div class="row-value"><input name="acct_num" type="text" size="8" /></div>

			<div class="row-name">Phone #:</div>
			<div class="row-value"><input name="phone_number" type="text" size="8" /></div>

			<div class="search-button"><input id="customer-form-search-button" value="Search" type="submit" class="submit-button"></div>
		</form>
	</div>
</div>

