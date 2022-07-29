<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<c:url var="statusUrl" value="/status"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>
<script src="<c:url value='/view/template/web/assets/js/ace-extra.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/view/template/web/js/jquery-1.7.2.min.js' />"></script>
<script src="<c:url value='/view/template/web/assets/js/ace-extra.min.js' />"></script>
<script type='text/javascript' src='<c:url value="/view/template/web/js/jquery-2.2.3.min.js" />'></script>
<script src="<c:url value='/view/template/web/assets/js/jquery.2.1.1.min.js' />"></script>
</head>
<body>
	<div>
		<ul>
			<li>
				<a href="<c:url value='putting?action=loading'/>">Tải các tác vụ mới</a>
			</li>
			<li>
				<a href="<c:url value='putting?action=insert'/>">Đặt lịch</a>
			</li>
			<li>
				<a href="<c:url value='putting?action=update'/>">Cập nhật lại lịch</a>
			</li>
			<li>
				<a href="<c:url value='putting?action=delete'/>">Xoá lịch</a>
			</li>
			<li>
				<a id="linkReset" href="<c:url value='putting?action=resetting'/>">Reset cache</a>
			</li>
		</ul>
	</div>
	<br/><br/>
	<div>
		<a id="linkStatus" href="#">Xem trạng thái các tác vụ</a>
		<div id="from">
			<label>Từ ngày</label>
			<div>
				<input type="text" id="fromDate" value="" placeholder="dd-MM-yyyy" pattern="[0-9]{2}-[0-9]{2}-[0-9]{4}"/>
			</div>
		</div>
		<div id="to">
			<label>Tới ngày</label>
			<div>
				<input type="text" id="toDate" value="" placeholder="dd-MM-yyyy" pattern="[0-9]{2}-[0-9]{2}-[0-9]{4}"/>
			</div>
		</div>
		<div id="task">
			<label>Tên tác vụ</label>
			<div>
				<input type="text" id="taskName" value="" placeholder="Enter job name in hr.job.impl" size="25"/>
				<label><i>(Ex: DailyEMailEODJob)</i></label>
			</div>
		</div>
	</div>
	
	<script>
	    $('#linkStatus').click(function (e) {
	    	var fromDate = $('#fromDate').val();
	    	var toDate = $('#toDate').val();
	    	var taskName = $('#taskName').val();
	    	window.location.href = "${statusUrl}?fromDate="+fromDate+"&toDate="+toDate+"&taskName="+taskName;
	    });
	</script>
</body>
</html>