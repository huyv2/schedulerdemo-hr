<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<c:url var="apiUrl" value="/loading"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang load các class file</title>
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
				<label>Job Name</label>
				<input type="text" id="jobName" value="" disabled="disabled"/>
			</div>
			<br/><br/>
			<div>
				<input type="button" value="Load job" id="btnLoad" disabled="disabled"/>
				<input type="button" value="Reload job" id="btnReload" disabled="disabled"/>
				<input type="button" value="Unload job" id="btnUnload" disabled="disabled"/>
				<input type="button" value="Load new jobs" id="btnLoadNewJobs"/>
			</div>
		</form>
	</div>
	<script>
	    $('#btnLoad').click(function (e) {
	        
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Post',
	            contentType: 'application/json',
	            data: getData(),
	            dataType: 'json',
	            success: function (result) {
	            	if (result.responseCode === '00'){
	            		alert("Load the job successfully");
	            	} else if (result.responseCode === '01'){
	            		alert("Load failed!");
	            	} else if (result.responseCode === '02'){
	            		alert("A job with the same name existed!");
	            	} else if (result.responseCode === '05'){
	            		alert("System error!");
	            	}
	            },
	            error: function (error) {
	            	alert("Http failed!");
	            }
	        });
	    });
	    
	    $('#btnReload').click(function (e) {
	        
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Put',
	            contentType: 'application/json',
	            data: getData(),
	            dataType: 'json',
	            success: function (result) {
	            	if (result.responseCode === '00'){
	            		alert("Reload the job successfully");
	            	} else if (result.responseCode === '01'){
	            		alert("Reload failed!");
	            	} else if (result.responseCode === '05'){
	            		alert("System error!");
	            	}
	            },
	            error: function (error) {
	            	alert("Http failed!");
	            }
	        });
	    });
	    
	    $('#btnUnload').click(function (e) {
	    	
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Delete',
	            contentType: 'application/json',
	            data: getData(),
	            dataType: 'json',
	            success: function (result) {
	            	if (result.responseCode === '00'){
	            		alert("Unload the job successfully");
	            	} else if (result.responseCode === '01'){
	            		alert("Unload failed!");
	            	} else if (result.responseCode === '03'){
	            		alert("Do not exist the job with this name!");
	            	} else if (result.responseCode === '05'){
	            		alert("System error!");
	            	}
	            },
	            error: function (error) {
	            	alert("Http failed!");
	            }
	        });
	    });
	    
	    $('#btnLoadNewJobs').click(function (e) {
	    	
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Get',
	            contentType: 'application/json',
	            success: function (result) {
	            	alert("The new jobs have been added: "+result.jobNameList);
	            },
	            error: function (error) {
	            	alert("Loading the new jobs failed");
	            }
	        });
	    });
	    
	    function getData() {
	    	
	        var data = {};
	        
	        var jobName = $('#jobName').val();
	        data["instanceJobName"] = jobName;
	        
	        return JSON.stringify(data);
	    }
	</script>
</body>
</html>