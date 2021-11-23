<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	var status; // 저역 변수 select의 변경된 값을 저장.

	function authorEdit(str, count){
		var id = str;
		var author = $('#author'+count).val();
		//ajax로 처리 해야 하는 부분.
		//권한 값을 어떻게 찾을지 고민(?).
		$.ajax({							
			url:'ajaxAuthorUpdate.do',		//호출명
			type:'post',					//전송방식 GET,POST
			data: {id : id, author : author }, //전송 방식 K:V
			dataType: 'text',				   //처리된 결과를 받을 타입 html, json, xml
			success: function(data){		   //data에 리턴값이 담겨있다.
				if(data == 'yes'){
					alert(id + "님의 권한이 변경되었습니다.");
				}else{
					alert(id + "님의 권한 변경이 실패했습니다.");
				}
			}				
		})
	
	}
</script>

</head>
<body>
<jsp:include page="../home/header.jsp" />
	<div align="center">
		<div><h1>멤 버 목 록</h1></div>
		<div>
			<table border="1">
				<tr>
					<th width="150">아 이 디</th>
					<th width="150">이  름</th>
					<th width="150">전화번호</th>
					<th width="400">주  소</th>
					<th width="100">권  한</th>
					<th width="100">권한수정</th>
				</tr>
				<c:forEach items="${members }" var="member" varStatus="status">  <!-- 여기서 속성 값 .id같은 값들은 vo객체에 담긴값들로 형성된다. -->
					<tr onmouseover="this.style.background='#ebf7fd';"
						onmouseleave="this.style.background='#FFFFFF';">
						<td align="center">${member.id } </td>
						<td align="center">${member.name } </td>
						<td align="center">${member.tel } </td>
						<td>&nbsp;${member.address } </td>
						<td align="center">
							<select id = "author${status.count}" name = "author">
									<option value="ADMIN" <c:if test="${member.author eq 'ADMIN' }"> selected="selected"</c:if>>ADMIN</option>
									<option value="USER" <c:if test="${member.author eq 'USER' }"> selected="selected" </c:if>>USER</option>		
							</select>
						</td>
						<td align="center">
						<button type="button" onclick="authorEdit('${member.id }', '${status.count}')">변  경</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div><br>
		<button type ="button" onclick="location.href='home.do'">홈 으로</button>
	</div>
</body>
</html>