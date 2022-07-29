<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<c:url var="apiUrl" value="/setting"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang cài đặt lịch chạy cho các tác vụ</title>
<script src="<c:url value='/view/template/web/assets/js/ace-extra.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/view/template/web/js/jquery-1.7.2.min.js' />"></script>
<script src="<c:url value='/view/template/web/assets/js/ace-extra.min.js' />"></script>
<script type='text/javascript' src='<c:url value="/view/template/web/js/jquery-2.2.3.min.js" />'></script>
<script src="<c:url value='/view/template/web/assets/js/jquery.2.1.1.min.js' />"></script>
</head>
<body>
	<div>
		<form id="formSubmit">
			<c:if test="${type == 'insert' || type == 'update'}">
				<div>
					<label>Chọn loại tác vụ</label>
					<div>
						<select id="type">
							<option value="Daily">Tác vụ chạy hàng ngày - Daily</option>
							<option value="Weekly">Tác vụ chạy hàng tuần - Weekly</option>
							<option value="Monthly">Tác vụ chạy hàng tháng - Monthly</option>
						</select>
					</div>
				</div>
				<br/>
				<br/>
			</c:if>
			<div>
				<label>Tác vụ</label>
				<div>
					<select id="jobList">
						<c:if test="${type == 'insert'}">
							<c:forEach var="job" items="${jobList}">
								<option value="${job.name}">${job.name}</option>
							</c:forEach>
						</c:if>
						<c:if test="${type == 'update'}">
							<c:forEach var="job" items="${jobList}">
								<option value="${job.name}:${job.id}">${job.name} - ${job.id}</option>
							</c:forEach>
						</c:if>
						<c:if test="${type == 'delete'}">
							<c:forEach var="job" items="${jobList}">
								<option value="${job.name}:${job.id}">${job.name} - ${job.id}</option>
							</c:forEach>
						</c:if>
					</select>
					<c:if test="${type == 'update'}">
						<input type="button" value="Lấy thông tin" id="btnRefresh"/>
					</c:if>
				</div>
			</div>
			<c:if test="${type == 'insert' || type == 'update'}">
				<br/>
				<br/>
				<div>
					<label>Thời gian bắt đầu</label>
					<div>
						<input type="text" id="beginAt" value="00:00"/>
					</div>
				</div>
				<br/>
				<br/>
				<div>
					<label>Thời gian kết thúc</label>
					<div>
						<input type="text" id="endAt" value="00:00"/>
					</div>
				</div>
				<br/>
				<br/>
				<div>
					<c:if test="${intervalUnit >= '60000'}">
						<label>Đơn vị thời gian (phút)</label>
					</c:if>
					<c:if test="${intervalUnit < '60000'}">
						<label>Đơn vị thời gian (${intervalUnit/1000} giây)</label>
					</c:if>
					<div>
						<input type="text" id="interval" value="1"/>
					</div>
				</div>
				<br/>
				<br/>
				<div>
					<label>Chọn ngày chạy trong tuần - Only Weekly</label>
					<div>
						<select id = "dayOfWeek">
							<option value="1">Chủ nhật</option>
							<option value="2">Thứ hai</option>
							<option value="3">Thứ ba</option>
							<option value="4">Thứ tư</option>
							<option value="5">Thứ năm</option>
							<option value="6">Thứ sáu</option>
							<option value="7">Thứ bảy</option>
						</select>
					</div>
				</div>
				<br/>
				<br/>
				<div>
					<label>Chọn ngày chạy trong tháng - Only Monthly</label>
					<div>
						<input type="text" id="dayOfMonth" value="1"/>
					</div>
				</div>
			</c:if>
			<br/>
			<br/>
			<div>
				<div>
					<input type="checkbox" id="isRunning">
					<label>Dùng luôn trong ngày</label>
				</div>
			</div>
			<br/>
			<br/>
			<div>
				<div>
					<c:if test="${type == 'insert'}">
						<input type="button" value="Đặt lịch" id="btnInsert"/>
					</c:if>
					<c:if test="${type == 'update'}">
						<input type="button" value="Cập nhật" id="btnUpdate"/>
					</c:if>
					<c:if test="${type == 'delete'}">
						<input type="button" value="Xoá lịch" id="btnDelete"/>
					</c:if>
				</div>
			</div>
		</form>
	</div>
	<script>
	    $('#btnInsert').click(function (e) {
	        
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Post',
	            contentType: 'application/json',
	            data: getData('insert'),
	            dataType: 'json',
	            success: function (result) {
	            	alert("Insert success: id - " + result.id + '-' + result.responseCode);
	            },
	            error: function (error) {
	            	alert("Insert failed: id - " + error.id + '-' + error.responseCode);
	            }
	        });
	    });
	    
	    $('#btnUpdate').click(function (e) {
	        
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Put',
	            contentType: 'application/json',
	            data: getData('update'),
	            dataType: 'json',
	            success: function (result) {
	            	alert("Update success: id - " + result.id + '- responseCode: ' + result.responseCode);
	            },
	            error: function (error) {
	            	alert("Update failed: responseCode - " + error.responseCode);
	            }
	        });
	    });
	    
	    $('#btnDelete').click(function (e) {
	    	
	        $.ajax({
	            url: '${apiUrl}',
	            type: 'Delete',
	            contentType: 'application/json',
	            data: getData('delete'),
	            dataType: 'json',
	            success: function (result) {
	            	alert("Update success: id - " + result.id + '- responseCode: ' + result.responseCode);
	            	/*var selectedJob = $('#jobList').val();
	            	$("#jobList option[value='selectedJob']").remove();*/
	            },
	            error: function (error) {
	            	alert("Update failed: responseCode - " + error.responseCode);
	            }
	        });
	    });
	    
	    $('#btnRefresh').click(function (e) {
	    	var e = document.getElementById("jobList");
	    	var job = e.options[e.selectedIndex].value;
        	jobNameIdArray = job.split(":");
	        $.ajax({
	            url: '${apiUrl}' + '?jobName=' + jobNameIdArray[0] + '&id=' + jobNameIdArray[1],
	            type: 'Get',
	            dataType: 'json',
	            success: function (result) {
	            	alert("Get success - responseCode: " + result.responseCode);
	            	$('#beginAt').val(padLeft(result.beginHourAt, 2, "0")+':'+padLeft(result.beginMinuteAt, 2, "0"));
	            	$('#endAt').val(padLeft(result.endHourAt, 2, "0")+':'+padLeft(result.endMinuteAt, 2, "0"));
	            	$('#interval').val(result.interval);
	            	$('#dayOfWeek').val(result.dayOfWeek);
	            	$('#dayOfMonth').val(result.dayOfMonth);
	            	$('#type').val(result.type);
	            	if (result.isRunning == 'false') {
	            		$('#isRunning').prop("checked", 0);
	            	} else {
	            		$('#isRunning').prop("checked", 1);
	            	}
	            },
	            error: function (error) {
	            	alert("Get failed - responseCode: " + error.responseCode);
	            }
	        });
	    });
	    
	    function getData(action) {
	        var data = {};
	        
	        var job = $('#jobList').val();
	        if (action == 'insert') {
	        	data["jobName"] = job;
	        } else if (action == 'update' || action == 'delete') {
	        	jobNameIdArray = job.split(":");
	        	data["jobName"] = jobNameIdArray[0];
	        	data["id"] = jobNameIdArray[1];
	        	if (action == 'update') {
	        		var isRunningCheckBox = document.getElementById("isRunning");
	        		if (isRunningCheckBox.checked == false) {
	        			data["isRunning"] = 'false';
	        		} else {
	        			data["isRunning"] = 'true';
	        		}
	        	}
	        }
	        
	        if (action != 'delete') {
		        var type = $('#type').val();
		        var beginAt = $('#beginAt').val();
		        var endAt = $('#endAt').val();
		        var interval = $('#interval').val();
		        var dayOfWeek = $('#dayOfWeek').val();
		        var dayOfMonth = $('#dayOfMonth').val();
		        
		        var beginArray = beginAt.split(":");
		        data["beginHourAt"] = beginArray[0];
		        data["beginMinuteAt"] = beginArray[1];
		        var endArray = endAt.split(":");
		        data["endHourAt"] = endArray[0];
		        data["endMinuteAt"] = endArray[1];
		        data["interval"] = interval;
		        data["dayOfWeek"] = dayOfWeek;
		        data["dayOfMonth"] = dayOfMonth;
		        data["type"] = type;
	        }
	        
	        return JSON.stringify(data);
	    }
	    function padLeft(value, length, padTemplate) {
	    	var pad = "";
	    	value = "" + value;
	    	for(i = 0; i < length - value.length; i++) {
	    		pad += padTemplate;
	    	}
	    	pad += value;
	    	
	    	return pad;
	    }
	</script>
</body>
</html>