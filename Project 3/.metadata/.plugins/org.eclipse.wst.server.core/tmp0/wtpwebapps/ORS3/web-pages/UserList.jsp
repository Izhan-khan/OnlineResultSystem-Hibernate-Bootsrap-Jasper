<!doctype html>
<%@page import="java.util.Iterator"%>
<%@page import="dto.UserDTO"%>
<%@page import="java.util.List"%>
<%@page import="controller.UserList_Controller"%>
<%@page import="controller.User_Controller"%>
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
	List roleList = (List) request.getAttribute("roleList");
%>


<title></title>

</head>
<body>
	<div class="container" id="container">


		<div id="header">
			<jsp:include page="Header.jsp"></jsp:include>
		</div>


		<div class="ty-auto flex-column h-90vh" id="App_Body">
			<div class="ty-auto m-2 form-block text-white text-center w-100 flex-column">

				<h1 class="mb-4">User List</h1>

				<%
			if (ServletUtility.getErrorMessage(request) != "") {
		%>

				<div class="alert alert-danger alert-dismissible fade show text-center position-sticky" role="alert">
					<%=ServletUtility.getErrorMessage(request)%>
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
				<%
			}
			if (ServletUtility.getSuccessMessage(request) != "") {
		%>
				<div class="alert alert-success alert-dismissible fade show text-center position-sticky" role="alert">
					<%=ServletUtility.getSuccessMessage(request)%>
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
				<%
			}
		%>




				<form action="<%=ORSView.USER_LIST_CTL%>" method="post" class="d-flex flex-column w-100 mx-3 my-5 justify-content-evenly">

					<div class="d-flex justify-content-evenly flex-wrap ">


						<div class="form-group first text-white mx-3">
							<label for="name">Name</label>
							<input name="name" type="text" class="form-control" placeholder="Enter First Name" id="name" value="<%=DataUtility.getStringData(dto.getName())%>">
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("name", request)%></font>
						</div>



						<div class="form-group first text-white mx-3">
							<label for="username">Login Id</label>
							<input name="login" type="text" class="form-control" placeholder="Enter Login Id" id="username" value="<%=DataUtility.getStringData(dto.getLogin())%>">
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("login", request)%></font>
						</div>



						<div class="form-group mx-3">
							<label for="password">Role </label>

							<%
								String roleDrop = HTMLUtility.getList("roleId", String.valueOf(dto.getRoleId()), roleList);
							%>

							<p class="form-group last mb-0"><%=roleDrop%></p>

						</div>




						<div>
							<button type="submit" class="btn btn-outline-primary btn-block my-4 " name="operation" value="<%=UserList_Controller.OP_SEARCH%>">Search</button>
							&nbsp;&nbsp;&nbsp;
							<button type="reset" class="btn btn-outline-light my-4 " name="operation" value="Reset">Reset</button>
						</div>



					</div>

					<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
					%>


					<!-- Hidden Fields -->
					<input type="hidden" name="id" value="<%=dto.getId()%>">
					<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
					<input type="hidden" name="createdDatetime" value="<%=dto.getCreatedDatetime()%>">
					<input type="hidden" name="modifiedDatetime" value="<%=dto.getModifiedDatetime()%>">
					<input type="hidden" name="pageNo" value="<%=pageNo%>">
					<input type="hidden" name="pageSize" value="<%=pageSize%>">





					<%
						List list = ServletUtility.getList(request);

						if (list != null && list.size() != 0) {
							Iterator it = list.iterator();
					%>
					<div class="table-responsive">
						<table class="table table-fill  table-striped my-5">
							<thead class="table-dark">
								<tr>
									<th class="" scope="col">Select</th>
									<th class="" scope="col">User Name</th>
									<th class="" scope="col">Gender</th>
									<th class="" scope="col">Email-Id</th>
									<th class="" scope="col">Password</th>
									<th class="" scope="col">Date of Birth</th>
									<th class="" scope="col">Mobile Number</th>
									<th class="" scope="col">Role</th>
									<th class="" scope="col">Update</th>
								</tr>
							</thead>
							<tbody class="table-light">
								<%
								while (it.hasNext()) {
										dto = (UserDTO) it.next();
							%>


								<tr>
									<td class="text-center">
										<input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>" <%if (dto.getRoleId() == 1) {%> disabled <%} else {%> <%}%>>
									</td>
									<td class="" scope="row"><%=dto.getName()%></td>
									<td class=""><%=dto.getGender()%></td>
									<td class=""><%=dto.getLogin()%></td>
									<td class=""><%=dto.getPassword()%></td>
									<td class=""><%=DataUtility.getDateString(dto.getDob())%></td>
									<td class=""><%=dto.getMobileNo()%></td>
									<td class=""><%=dto.getRoleId()%></td>
									<td class="">
										<%
										if (dto.getRoleId() == 1) {
									%>
										......
										<%
										} else {
									%>
										<a href="<%=ORSView.USER_CTL%>?id=<%=dto.getId()%>">Edit</a>
										<%
 	}
 %>
									</td>
								</tr>

								<%
								}
								}
							%>

							</tbody>
						</table>
					</div>

					<table width="100%">
						<tr class="d-flex flex-wrap justify-content-around ">
							<td>
								<input type="submit" name="operation" class="btn btn-outline-light btn-md m-2" style="font-size: 17px" value="<%=UserList_Controller.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
							</td>
							<td>
								<input type="submit" name="operation" class="btn btn-outline-light btn-md m-2" style="font-size: 17px" value="<%=UserList_Controller.OP_NEW%>">
							</td>
							<td>
								<input type="submit" name="operation" class="btn btn-outline-light btn-md m-2" style="font-size: 17px" value="<%=UserList_Controller.OP_DELETE%>">
							</td>
							<td>
								<input type="submit" name="operation" class="btn btn-outline-light btn-md m-2" style="font-size: 17px" value="<%=UserList_Controller.OP_NEXT%>" <%=(list.size() == 10) ? "" : "disabled"%>>
							</td>
						</tr>
						<tr></tr>
					</table>



				</form>
			</div>


		</div>


		<div id="footer">
			<jsp:include page="Footer.jsp"></jsp:include>
		</div>


	</div>


</body>
</html>