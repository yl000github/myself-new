<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<button onclick='test()'>test</button>
</body> 
<!-- <script type="text/javascript" src="/resources/js/jquery.js"></script> -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
<script> 
	var context="${pageContext.request.contextPath}";
	function test(){
// 		alert(context);
		$.ajax({
			url:context+"/affair/test",
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			type:'post',
			success:function(data){
				alert(data);
			},
			error:function(er){
				console.dir(er);
				alert('出错了');
			}
		});
		alert('over')
	}
</script>
</html>