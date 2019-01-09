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
  <!-- endinject -->
  <!-- inject:css --> 
  <link rel="stylesheet" href="/assets/css/style2.css">
  <!-- endinject -->
  <!-- Include multi.js -->
  <link rel="stylesheet" type="text/css" href="../assets/css/multi.min.css">
  <script src="${pageContext.request.contextPath}/assets/js/multi.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
   <link rel="stylesheet" href="/assets/iconfonts/mdi/css/materialdesignicons.min.css">

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
.fs {
	  border:1px groove #DCDCDC;
	  padding:10px;
	  margin:10px;
	  border-radius:10px;
	}
	
.btn{

    border:1px solid transparent;
    white-space:nowrap;
    padding:6px 12px;
    font-size:14px;
    line-height:1.42857143;
    border-radius:4px;
}	
	
.cust {
max-width:50px;
}

.card-counter{
    box-shadow: 2px 2px 10px #DADADA;
    margin: 5px;
    padding: 10px 5px;
    background-color: #fff;
    height: 100px;
    border-radius: 5px;
    transition: .3s linear all;
  }

  .card-counter:hover{
    box-shadow: 4px 4px 20px #DADADA;
    transition: .3s linear all;
  }

  .card-counter.primary{
    background-color: #9FCCF6;
    color: #FFF;
  }

  .card-counter.danger{
    background-color: #FF8A8A;
    color: #FFF;
  }  

  .card-counter.success{
    background-color: #66bb6a;
    color: #FFF;
  }  

  .card-counter.info{
    background-color: #26c6da;
    color: #FFF;
  }  

  .card-counter i{
    font-size: 5em;
    opacity: 0.2;
  }

  .card-counter .count-numbers{
    position: absolute;
    right: 35px;
    top: 20px;
    font-size: 32px;
    display: block;
  }

  .card-counter .count-name{
    position: absolute;
    right: 35px;
    top: 65px;
    font-style: italic;
    text-transform: capitalize;
    opacity: 0.5;
    display: block;
    font-size: 18px;
  }


</style>
</head>
<body>
  <div class="container-scroller" style="background-image:url('${pageContext.request.contextPath}/assets/img/bg2.jpg');" >
    <!-- partial:partials/_navbar.html -->
    <nav class="col-lg-12 col-12 fixed-top " style="height:10px;position:relative;">
     <div style="text-align:center;padding-top:20px;color:white;"> <h2>HIP Dashboard</h2></div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:partials/_sidebar.html -->

