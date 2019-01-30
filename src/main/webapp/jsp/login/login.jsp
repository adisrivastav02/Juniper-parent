
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Juniper Login</title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/iconfonts/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- plugin css for this page -->
  <!-- End plugin css for this page -->
  <!-- inject:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style2.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.png" />
</head>

<body>
  <div class="container-scroller" >
    <div class="container-fluid page-body-wrapper full-page-wrapper">
      <div class="content-wrapper d-flex align-items-center auth" style="background-image: url('${pageContext.request.contextPath}/assets/img/profile_city.jpg'); background-size: cover; background-position: top center;">
        <div class="row w-100">
          <div class="col-lg-4 mx-auto">
            <div class="auth-form-light text-left p-5">
              
               <h3>Juniper</h3> 
              
              <h5>Personalize your Data Movement Experience</h5>
              <h6 class="font-weight-light">Sign in to continue.</h6>
              <form class="pt-3" action="${pageContext.request.contextPath}/login" method="POST">
                <div class="form-group">
                  <input type="text" class="form-control form-control-lg" id="username" name="username" placeholder="Username" required="required">
                </div>
                <div class="form-group">
                  <input type="password" class="form-control form-control-lg" id="password" name="password" placeholder="Password" required="required">
                </div>
                <div class="mt-3">
                	<button type="submit" class="btn btn-block btn-facebook auth-form-btn">
                    SIGN IN
                  </button>
                </div>
                
                <div class="mt-3" align="center">
                	<a href="${pageContext.request.contextPath}/#works"><img src="${pageContext.request.contextPath}/assets/img/home.png" alt="Image"  height="30" width="30" class="rounded">
 </a>
                </div>
                                <div class="mt-3" align="center">
                
                <%
							if (request.getAttribute("successString") != null) {
						%>
						<p class="text-success h4">${successString}</p>
						<%
							}
						%>
						<%
							if (request.getAttribute("errorString") != null) {
						%>
						<p class="text-danger h4">${errorString}</p>
						<%
							}
						%>


									<c:if
										test="${sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message eq 'Bad credentials'}">
										<p class="text-danger h4">Username/Password entered is incorrect</p>
									</c:if>
									<c:if
										test="${sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message eq 'User is disabled'}">
										<p class="text-danger h4">Your account is disabled, please contact administrator</p>
									</c:if>
									<c:if
										test="${fn:containsIgnoreCase(sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message,'A communications error has been detected')}">
										<p class="text-danger h4">Connection is down, try after sometime</p>
									</c:if>


								</div>
              </form>
               
            </div>
          </div>
         
        </div>
        
          
      </div>
      <!-- content-wrapper ends -->
    </div>
    <!-- page-body-wrapper ends -->
  </div>
  <!-- container-scroller -->
  <!-- plugins:js -->
  <script src="${pageContext.request.contextPath}/assets/js/vendor.bundle.base.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/vendor.bundle.addons.js"></script>
  <!-- endinject -->
  <!-- inject:js -->
  <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
  <script src="${pageContext.request.contextPath}/js/misc.js"></script>
  <!-- endinject -->
</body>

</html>
