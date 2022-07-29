<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<c:url var="apiUrl" value="/setting"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang xem trạng thái các tác vụ</title>
<style>
	.tab{
		overflow: hidden;
		border: 10px solid #ccc;
		background-color: #f1f1f1;
	}
	.complete{
		color: #0000FF;
	}
	.error{
		color: #FFFFFF;
		background-color: #FF0000 
	}
	.processing{
		color: #FF0000;
		background-color: #FFFF00;
	}
</style>
</head>
<body>
	<div class="tab">
		<table>
			<tr>
				<th>Tên tác vụ&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Số định danh&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Số tham chiếu&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Loại&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Ngày thực thi&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Bắt đầu&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Kết thúc&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Trạng thái&nbsp&nbsp&nbsp&nbsp&nbsp</th>
				<th>Thông báo</th>
			</tr>
			<c:if test="${not empty statusList}">
				<c:forEach var="status" items="${statusList}">
					<tr>
						<c:if test="${status.status == 'R'}">
							<td class="processing">${status.jobName}</td>
						</c:if>
						<c:if test="${status.status == 'E'}">
							<td class="error">${status.jobName}</td>
						</c:if>
						<c:if test="${status.status == 'F'}">
							<td class="complete">${status.jobName}</td>
						</c:if>
						
						<c:if test="${status.status == 'R'}">
							<td class="processing">${status.id}</td>
						</c:if>
						<c:if test="${status.status == 'E'}">
							<td class="error">${status.id}</td>
						</c:if>
						<c:if test="${status.status == 'F'}">
							<td class="complete">${status.id}</td>
						</c:if>
						
						<c:if test="${status.status == 'R'}">
							<td class="processing">${status.referenceId}</td>
						</c:if>
						<c:if test="${status.status == 'E'}">
							<td class="error">${status.referenceId}</td>
						</c:if>
						<c:if test="${status.status == 'F'}">
							<td class="complete">${status.referenceId}</td>
						</c:if>
						
						<c:if test="${status.type == 'Daily'}">
							<c:if test="${status.status == 'R'}">
								<td class="processing">Chạy hàng ngày</td>
							</c:if>
							<c:if test="${status.status == 'E'}">
								<td class="error">Chạy hàng ngày</td>
							</c:if>
							<c:if test="${status.status == 'F'}">
								<td class="complete">Chạy hàng ngày</td>
							</c:if>
						</c:if>
						<c:if test="${status.type == 'Weekly'}">
							<c:if test="${status.status == 'R'}">
								<td class="processing">Chạy hàng tuần</td>
							</c:if>
							<c:if test="${status.status == 'E'}">
								<td class="error">Chạy hàng tuần</td>
							</c:if>
							<c:if test="${status.status == 'F'}">
								<td class="complete">Chạy hàng tuần</td>
							</c:if>
						</c:if>
						<c:if test="${status.type == 'Monthly'}">
							<c:if test="${status.status == 'R'}">
								<td class="processing">Chạy hàng tháng</td>
							</c:if>
							<c:if test="${status.status == 'E'}">
								<td class="error">Chạy hàng tháng</td>
							</c:if>
							<c:if test="${status.status == 'F'}">
								<td class="complete">Chạy hàng tháng</td>
							</c:if>
						</c:if>
						
						<c:if test="${status.type == 'Daily'}">
							<c:if test="${status.status == 'R'}">
								<td class="processing">Ngày ${status.dateOfType}</td>
							</c:if>
							<c:if test="${status.status == 'E'}">
								<td class="error">Ngày ${status.dateOfType}</td>
							</c:if>
							<c:if test="${status.status == 'F'}">
								<td class="complete">Ngày ${status.dateOfType}</td>
							</c:if>
						</c:if>
						<c:if test="${status.type == 'Weekly'}">
							<c:if test="${status.dateOfType == 1}">
								<c:if test="${status.status == 'R'}">
									<td class="processing">Chủ nhật (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'E'}">
									<td class="error">Chủ nhật (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'F'}">
									<td class="complete">Chủ nhật (hàng tuần)</td>
								</c:if>
							</c:if>
							<c:if test="${status.dateOfType == 2}">
								<c:if test="${status.status == 'R'}">
									<td class="processing">Thứ hai (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'E'}">
									<td class="error">Thứ hai (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'F'}">
									<td class="complete">Thứ hai (hàng tuần)</td>
								</c:if>
							</c:if>
							<c:if test="${status.dateOfType == 3}">
								<c:if test="${status.status == 'R'}">
									<td class="processing">Thứ ba (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'E'}">
									<td class="error">Thứ ba (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'F'}">
									<td class="complete">Thứ ba (hàng tuần)</td>
								</c:if>
							</c:if>
							<c:if test="${status.dateOfType == 4}">
								<c:if test="${status.status == 'R'}">
									<td class="processing">Thứ tư (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'E'}">
									<td class="error">Thứ tư (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'F'}">
									<td class="complete">Thứ tư (hàng tuần)</td>
								</c:if>
							</c:if>
							<c:if test="${status.dateOfType == 5}">
								<c:if test="${status.status == 'R'}">
									<td class="processing">Thứ năm (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'E'}">
									<td class="error">Thứ năm (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'F'}">
									<td class="complete">Thứ năm (hàng tuần)</td>
								</c:if>
							</c:if>
							<c:if test="${status.dateOfType == 6}">
								<c:if test="${status.status == 'R'}">
									<td class="processing">Thứ sáu (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'E'}">
									<td class="error">Thứ sáu (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'F'}">
									<td class="complete">Thứ sáu (hàng tuần)</td>
								</c:if>
							</c:if>
							<c:if test="${status.dateOfType == 7}">
								<c:if test="${status.status == 'R'}">
									<td class="processing">Thứ bảy (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'E'}">
									<td class="error">Thứ bảy (hàng tuần)</td>
								</c:if>
								<c:if test="${status.status == 'F'}">
									<td class="complete">Thứ bảy (hàng tuần)</td>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${status.type == 'Monthly'}">
							<c:if test="${status.status == 'R'}">
								<td class="processing">Ngày ${status.dateOfType} (hàng tháng)</td>
							</c:if>
							<c:if test="${status.status == 'E'}">
								<td class="error">Ngày ${status.dateOfType} (hàng tháng)</td>
							</c:if>
							<c:if test="${status.status == 'F'}">
								<td class="complete">Ngày ${status.dateOfType} (hàng tháng)</td>
							</c:if>
						</c:if>
						
						<c:if test="${status.status == 'R'}">
							<td class="processing">${status.createdAt}</td>
						</c:if>
						<c:if test="${status.status == 'E'}">
							<td class="error">${status.createdAt}</td>
						</c:if>
						<c:if test="${status.status == 'F'}">
							<td class="complete">${status.createdAt}</td>
						</c:if>
						
						<c:if test="${status.status == 'R'}">
							<td class="processing">Chưa xác định</td>
						</c:if>
						<c:if test="${status.status == 'E'}">
							<td class="error">${status.updatedAt}</td>
						</c:if>
						<c:if test="${status.status == 'F'}">
							<td class="complete">${status.updatedAt}</td>
						</c:if>
						
						<c:if test="${status.status == 'R'}">
							<td class="processing">Đang xử lý</td>
						</c:if>
						<c:if test="${status.status == 'E'}">
							<td class="error">Xảy ra lỗi</td>
						</c:if>
						<c:if test="${status.status == 'F'}">
							<td class="complete">Hoàn thành</td>
						</c:if>
						
						<c:if test="${status.status == 'R'}">
							<td class="processing">Kiểm tra nếu job này xử lý quá lâu</td>
						</c:if>
						<c:if test="${status.status == 'E'}">
							<td class="error">Log lỗi: ${status.message}</td>
						</c:if>
						<c:if test="${status.status == 'F'}">
							<td class="complete">Không phát hiện lỗi</td>
						</c:if>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>