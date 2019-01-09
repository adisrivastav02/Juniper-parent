<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="javax.servlet.http.*"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Juniper Home</title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="/assets/iconfonts/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="/assets/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="/assets/css/style2.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="/assets/img/favicon.ico" />
  <!-- Include multi.js -->
  <link rel="stylesheet" type="text/css" href="../assets/css/multi.min.css">
  <script src="../assets/js/multi.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet">

  <script type="text/javascript">
  function tog(ids)
  {
	  if(ids=="max")
	{
	  document.getElementById("min").style.display="block";
	  document.getElementById("max").style.display="none";
	}
	  if(ids=="min")
	{
		document.getElementById("max").style.display="block";
		document.getElementById("min").style.display="none";
	}
  }
  function multisel(src_id,tgt_id)
  {
		var el = document.getElementById(src_id);
		var result = "";
		var options = el.options;
		var opt;
		for (var i = 0, iLen = options.length; i < iLen; i++) {
			opt = options[i];
			if (opt.selected) {
				if(opt.value==="*")
				{
					result="";
					for (var j = 0, jLen = options.length; j < jLen; j++) {
						opt1 = options[j];
						if(opt1.value!="*")
						{
							result=result+","+opt1.value;
						}
					}
					break;
				}
				else
				{
					result=result+","+opt.value;
				}
			}
		}
		result=result.substring(1);
		document.getElementById(tgt_id).value=result;
  }
  
  
  
  
  $(document).ready(function() {
	
		 $("#projects").change(function() {

				var project = $(this).val();
	
				 $.post('/login/features', {
					project : project
				}, function(data) {
					//alert(data);
					
					window.location.href="/login/dashboard";
				}); 
	 
		}); 
	  
  });
  
  </script>
   <style>
.cust {
  width:95%;
  margin:5px;
  padding:0.875rem 0.5rem;
}
  
.row1 {
  display: flex;
  flex-wrap: wrap;
  padding: 0 4px;
}

/* Create four equal columns that sits next to each other */
.column1,.column2 {
  flex: 16.66%;
  max-width: 16.66%;
  padding: 0 5px;
}

.column1 img {
  margin-top: 8px;
  vertical-align: middle;
  width: 150px;
  height: 175px;
  border: 1px solid black;
}
.column2 img {
  margin-top: 8px;
  vertical-align: middle;
  width: 150px;
  border: 1px solid black;
}
/* Responsive layout - makes a two column-layout instead of four columns */
@media screen and (max-width: 800px) {
  .column1,.column2 {
    flex: 50%;
    max-width: 50%;
  }
}
/* Responsive layout - makes the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .column1,.column2{
    flex: 100%;
    max-width: 100%;
  }
}
#bgDiv {
  position:fixed;
  top:0px;
  bottom:0px;
  left:260px;
  right:0px;
  overflow:hidden;
  padding:0;
  margin:0;
  background-color:white;
  filter:alpha(opacity=25);
  opacity:0.25;
  z-index:1000;
}

.fs {
  border:1px groove #DCDCDC;
  padding:10px;
  margin:10px;
  border-radius:10px;
}
.leg {
  font-size:0.8em;
  font-weight:bold;
}
</style>
</head>
<body>
  <div class="container-scroller" style="background-image:url('../../assets/img/bg2.jpg');color:white;" >
    <!-- partial:partials/_navbar.html -->
    <nav class="col-lg-12 col-12 fixed-top " style="height:10px;position:relative;">
     <div style="text-align:center;padding-top:20px;"> <h2>Feed Master Dashboard</h2></div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:partials/_sidebar.html -->

