<!doctype html>
<%@page import="controller.ForgetPassword_Controller"%>
<%@page import="utility.ServletUtility"%>
<%@page import="utility.DataUtility"%>
<%@page import="controller.Login_Controller"%>
<%@page import="controller.ORSView"%>
<html lang="en">
<head>


<!-- StyleSheet Link -->
<link href="<%=ORSView.STYLESHEET%>" rel="stylesheet">


<jsp:useBean id="dto" class="dto.UserDTO" scope="request"></jsp:useBean>


<title></title>

</head>
<body>
	<div class="container" id="container">
		<div id="header">
			<jsp:include page="Header.jsp"></jsp:include>
		</div>



		<%
			if (ServletUtility.getErrorMessage(request) != "") {
		%>

		<div class="alert alert-danger alert-dismissible fade show text-center" role="alert">
			<%=ServletUtility.getErrorMessage(request)%>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<%
			}
			if (ServletUtility.getSuccessMessage(request) != "") {
		%>
		<div class="alert alert-success alert-dismissible fade show text-center" role="alert">
			<%=ServletUtility.getSuccessMessage(request)%>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<%
			}
		%>

		<div class="ty-auto" id="App_Body">


			<div id="login_backgound" style="width: 80vh; height: 42vh">
				<div class="form-block text-white">
					<div class="text-center mt-4">
						<h3>Forgot Password</h3>
					</div>
					<div class="text-center my-3">
						<h5>Password will be send to your Email Id</h5>
					</div>

					<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">


						<div class="form-group first text-white mb-0">
							<label for="username">Enter Email Id</label>
							<input name="login" type="text" class="form-control mb-2" placeholder="Email Id" id="username" value="<%=DataUtility.getStringData(dto.getLogin())%>" required>
						</div>

						<button type="submit" class="btn btn-outline-primary btn-block my-2 " name="operation" value="<%=ForgetPassword_Controller.OP_GO%>">Get Password</button>

						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("login", request)%></font>
						</div>



					</form>


				</div>
			</div>



		</div>

		<div id="footer">
			<jsp:include page="Footer.jsp"></jsp:include>
		</div>
	</div>







	<!-- Hidden Fields -->

	<input type="hidden" name="id" value="<%=dto.getId()%>">
	<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
	<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
	<input type="hidden" name="createdDatetime" value="<%=dto.getCreatedDatetime()%>">
	<input type="hidden" name="modifiedDatetime" value="<%=dto.getModifiedDatetime()%>">




</body>
</html>