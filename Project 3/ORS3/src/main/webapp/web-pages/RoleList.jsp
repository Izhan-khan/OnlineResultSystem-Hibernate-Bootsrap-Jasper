<!doctype html>
<%@page import="java.util.Iterator"%>
<%@page import="dto.RoleDTO"%>
<%@page import="java.util.List"%>
<%@page import="controller.RoleList_Controller"%>
<%@page import="controller.Role_Controller"%>
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


<jsp:useBean id="dto" class="dto.RoleDTO" scope="request"></jsp:useBean>



<title></title>

</head>
<body>
	<div class="container" id="container">


		<div id="header">
			<jsp:include page="Header.jsp"></jsp:include>
		</div>


		<div class=" my-0 flex-column h-90vh" id="App_Body">

			<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post" class="d-flex flex-column w-100 mx-3 my-5 justify-content-evenly">

				<div class="ty-auto m-2 form-block text-white text-center w-100 flex-column">

					<h1 class="mb-4">Role List</h1>


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



					<div class="d-flex flex-wrap justify-content-evenly">

						<div class="text-white">
							<label for="name">Role</label>
							<input name="name" type="text" class="form-control" placeholder="Enter Role" id="name" value="<%=DataUtility.getStringData(dto.getName())%>">
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("name", request)%></font>
						</div>



						<div class="">
							<button type="submit" class="btn btn-outline-primary btn-block my-4 " name="operation" value="<%=RoleList_Controller.OP_SEARCH%>">Search</button>
						</div>
						<div class="">
							<button type="reset" class="btn btn-outline-light btn-block my-4 " name="operation" value="Reset">Reset</button>
						</div>
					</div>

					<!-- Hidden Fields -->

					<input type="hidden" name="id" value="<%=dto.getId()%>">
					<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
					<input type="hidden" name="createdDatetime" value="<%=dto.getCreatedDatetime()%>">
					<input type="hidden" name="modifiedDatetime" value="<%=dto.getModifiedDatetime()%>">


					<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);

						List list = ServletUtility.getList(request);

						if (list != null && list.size() != 0) {
							Iterator it = list.iterator();
					%>
					<div class="table-responsive">

						<table class="table table-fill  table-striped my-5">
							<thead class="table-dark">
								<tr>
									<th class="" scope="col">Select</th>
									<th class="" scope="col">Role Name</th>
									<th class="" scope="col">Description</th>
									<th class="" scope="col">Update</th>
								</tr>
							</thead>
							<tbody class="table-light">
								<%
									while (it.hasNext()) {
											dto = (RoleDTO) it.next();
								%>


								<tr>
									<td class="text-center"><input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>"></td>
									<td class="" scope="row"><%=dto.getName()%></td>
									<td class=""><%=dto.getDescription()%></td>
									<td class=""><a href="<%=ORSView.ROLE_CTL%>?id=<%=dto.getId()%>">Edit</a></td>
								</tr>

								<%
									}
									}
								%>

							</tbody>
						</table>
					</div>
					<div class="table-responsive">

						<table width="100%">
							<tr class="d-flex justify-content-around w-100 flex-wrap">
								<td><input type="submit" name="operation" class="btn btn-outline-light btn-md m-3" style="font-size: 17px" value="<%=RoleList_Controller.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
								</td>
								<td><input type="submit" name="operation" class="btn btn-outline-light btn-md m-3" style="font-size: 17px" value="<%=RoleList_Controller.OP_NEW%>"></td>
								<td><input type="submit" name="operation" class="btn btn-outline-light btn-md m-3" style="font-size: 17px" value="<%=RoleList_Controller.OP_DELETE%>"></td>
								<td><input type="submit" name="operation" class="btn btn-outline-light btn-md m-3" style="font-size: 17px" value="<%=RoleList_Controller.OP_NEXT%>"
										<%=(list.size() > 10) ? "" : "disabled"%>
									></td>
							</tr>
							<tr></tr>
						</table>
					</div>

				</div>

			</form>
		</div>


	</div>


	<div id="footer">
		<jsp:include page="Footer.jsp"></jsp:include>
	</div>


</body>
</html>