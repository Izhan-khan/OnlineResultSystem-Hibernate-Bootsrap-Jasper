<!doctype html>
<%@page import="dto.UserDTO"%>
<%@page import="controller.ChangePassword_Controller"%>
<%@page import="controller.MyProfile_Controller"%>
<%@page import="controller.UserRegistration_Controller"%>
<%@page import="utility.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="utility.ServletUtility"%>
<%@page import="utility.DataUtility"%>
<%@page import="controller.Login_Controller"%>
<%@page import="controller.ORSView"%>
<html lang="en">
<head>


<!-- StyleSheet Link -->
<link href="<%=ORSView.STYLESHEET%>" rel="stylesheet">


<jsp:useBean id="dto" class="dto.UserDTO" scope="request"></jsp:useBean>

<%

UserDTO sessionDTO = (UserDTO) session.getAttribute("user");

%>


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



			<div id="login_backgound">
				<div class="form-block text-white ">
					<div class="text-center my-4 ">
						<h3>Change Password</h3>

					</div>


					<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">



						<div class="form-group first text-white mb-0">
							<label for="username">Username</label>
							<input name="login" type="text" class="form-control" readonly="readonly" placeholder="Enter Login Id" id="username" value="<%=DataUtility.getStringData(sessionDTO.getLogin())%>" required>
						</div>



						<div class="form-group last mb-0">
							<label for="oldPassword">Password</label>
							<input name="oldPassword" type="password" class="form-control" placeholder="Enter Current Password" id="oldPassword" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("oldPassword", request)%></font>
						</div>


						<div class="form-group last mb-0">
							<label for="newPassword">New Password</label>
							<input name="newPassword" type="password" class="form-control" placeholder="Enter New Password" id="newPassword" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("newPassword", request)%></font>
						</div>

						<div class="form-group last mb-0">
							<label for="password">Confirm Password</label>
							<input name="confirmPassword" type="password" class="form-control" placeholder="Confirm Password" id="confirmPassword" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("confirmPassword", request)%></font>
						</div>




						<div class="d-grid gap-2 col-8 mx-auto mt-3 mb-5 text-nowrap">
							<button type="submit" class="btn btn-outline-primary btn-block  " name="operation" value="<%=ChangePassword_Controller.OP_SAVE%>">Update Password</button>
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