<%@ page language="java" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>MyCaptcha</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<c:url value="js/jquery.min.js" var="jstljs"/>
	<script src="${jstljs}" type="text/javascript"></script>
<!--<script src="js/jquery.base64.min.js" type="text/javascript"></script>-->
</head>

<body>
    <h1>MyCaptcha</h1>
    <div>
		<img id="verImage" width="130px" height="48px"/>
    </div>
    <div>
		<input type="text" value="" id="verCode"/>
		<input type="button" value="校验" id="loginID"/>
    </div>

    <script type="text/javascript">
    	function imgRefresh() {
    		$.ajaxSettings.async = false;
    		$.getJSON("captcha.do", function(data, status, xhr) {
    			console.log(data.verCode);
    			console.log(data.verImage);
    			$('#verImage').attr('src', data.verImage);
    		});
    	}

    	$(document).ready(function(){
    		return imgRefresh();
        });

    	$('#verImage').click(function() {
    		return imgRefresh();
    	});

    	$('#loginID').click(function() {
    		var data = {};
    		data['verCode'] = $('#verCode').val();

    		$.ajaxSettings.processData = true;
			$.post("login.do", data, function(data, status, xhr) {
				if(data === "true") {
					alert("校验成功!");
					return true;
				} else {
					alert("校验失败...");
					return false;
				}
			});
    	});

    </script>
</body>
</html>
