<jsp:include page="cdg_header.jsp" />

<script>
	function pass(val) {
		document.getElementById('src_val').value = val;
		var usr=document.getElementById('usr').value;
		var proj=document.getElementById('proj').value;
		var jwt=document.getElementById('jwt').value;
		document.getElementById('connectionHome').submit();
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
						<form class="forms-sample" id="connectionHome" name="connectionHome"
							method="post" action="${pageContext.request.contextPath}/login/connectionDetails">
							<input type="hidden" name="src_val" id="src_val" value="">
							<input type="hidden" name="usr" id="usr" value="${usr}">
							<input type="hidden" name="proj" id="proj" value="${proj}">
							<input type="hidden" name="jwt" id="jwt" value="${jwt}">
						<div class="container">
								  <div class="row text-center text-lg-left">
									 <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('Oracle');">
								      		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/oracle.png" >
								      	</a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('Teradata');">
								      		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/teradata.png">
								      	</a> 
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100" onclick="pass('Unix');">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/linux.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a class="d-block mb-4 h-100" href="#"  onclick="pass('Hive');">
								      	<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/hive.png">
								      </a> 
								    </div>
								     <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100" onclick="pass('Hadoop');">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/hadoop.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								     	<a class="d-block mb-4 h-100" href="#">
								     		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/mysql.png">
								     	</a> 
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100" onclick="pass('Mssql');">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/sqlserver.png">
								          </a>
								    </div>
								    
								     <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      	<a href="#" class="d-block mb-4 h-100">
								      		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/gstorage.png">
								      	</a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6" onclick="pass('Db2');">
								    	<a class="d-block mb-4 h-100" href="#">
								    		<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/db2.png">
								    	</a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/kafka.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								     <a class="d-block mb-4 h-100" href="#">
								     	<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/hbase.png">
								     </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/mongodb.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/exadata.png">
								          </a>
								    </div>
								   <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/windows.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/aws.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/azure.png">
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