<%@page import="com.Fund"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Funds Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="./Components/jquery-3.2.1.min.js"></script>
<script src="./Components/funds.js"></script>
</head>
<body>
<div class="container"><div class="row"><div class="col-6"> 
<h1>Funds Management in GadgetBadget</h1>
<form id="formFund" name="formFund">
 
 Funder ID: 
 <input id="funderID" name="funderID" type="text" 
 class="form-control form-control-sm">
 <br> Research ID: 
 <input id="researchID" name="researchID" type="text" 
 class="form-control form-control-sm">
 <br> Donated Price: 
 <input id="Price" name="Price" type="text" 
 class="form-control form-control-sm">
 <br> Comments: 
 <input id="Comments" name="Comments" type="text" 
 class="form-control form-control-sm">
 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 <input type="hidden" id="hidIDSave" 
 name="hidIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divFundsGrid">
 <%
 Fund fundObj = new Fund(); 
 out.print(fundObj.readFunds()); 
 %>
</div>
</div> </div> </div>

</body>
</html>