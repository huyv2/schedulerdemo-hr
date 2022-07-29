<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<c:url var="apiUrl" value="/resetting"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang tải lại cache</title>
<script src="<c:url value='/view/template/web/assets/js/ace-extra.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/view/template/web/js/jquery-1.7.2.min.js' />"></script>
<script src="<c:url value='/view/template/web/assets/js/ace-extra.min.js' />"></script>
<script type='text/javascript' src='<c:url value="/view/template/web/js/jquery-2.2.3.min.js" />'></script>
<script src="<c:url value='/view/template/web/assets/js/jquery.2.1.1.min.js' />"></script>
</head>
<body>
	<div>
		<form id="formSubmit">
			<div>
				<label>Cache name</label>
				<input type="text" id="cacheName" value=""/>
			</div>
			<br/><br/>
			<div>
				<input type="button" value="Reset Cache" id="btnReset"/>
			</div>
		</form>
	</div>
	<script>
	    $('#btnReset').click(function (e) {
	    	
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Put',
	            contentType: 'application/json',
	            data: getData(),
	            dataType: 'json',
	            success: function (result) {
	            	if (result.responseCode === '00'){
	            		alert("Reset the cache successfully");
	            	} else if (result.responseCode === '01'){
	            		alert("Reset the cache failed");
	            	} else if (result.responseCode === '03'){
	            		alert("The cache does not exist");
	            	}
	            },
	            error: function (error) {
	            	alert("Http failed");
	            }
	        });
	    });
	    
	    function getData() {
	        var data = {};
	        
	        var cacheName = $('#cacheName').val();
	        data["cacheName"] = cacheName;
	        
	        return JSON.stringify(data);
	    }
	</script>
</body>
</html>