<jsp:include page="cdg_header.jsp" />

<script>
	function pass(val) {
		document.getElementById('src_val').value = val;
		var usr=document.getElementById('usr').value;
		var proj=document.getElementById('proj').value;
		var jwt=document.getElementById('jwt').value;
		document.getElementById('ConnectionHome').submit();
	}
</script>

<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Extraction</h4>
						<p class="card-description">Connection Details</p>
						<form class="forms-sample" id="ConnectionHome" name="ConnectionHome"
							method="post" action="${pageContext.request.contextPath}/login/publishingDetails">
							<input type="hidden" name="src_val" id="src_val" value="">
							<input type="hidden" name="usr" id="usr" value="${usr}">
							<input type="hidden" name="proj" id="proj" value="${proj}">
							<input type="hidden" name="jwt" id="jwt" value="${jwt}">
						<div class="container">
							<div class="row text-center text-lg-left">
								<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('BigQuery');">
								      		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/bigQuery.png" >
								      	</a>
								</div>
			           			<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('Bigtable');">
								      		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/bigTable.png" >
								      	</a>
								</div>
			           			<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('MySQL');">
								      		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/mysql.png" >
								      	</a>
								</div>
			           			<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('PostgreSQL');">
								      		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/postgresql.png" >
								      	</a>
								</div>
			          		</div>
			          	</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="cdg_footer.jsp" />