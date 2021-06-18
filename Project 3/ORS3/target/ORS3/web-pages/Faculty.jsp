<!doctype html>
<%@page import="java.util.List"%>
<%@page import="controller.Faculty_Controller"%>
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


<jsp:useBean id="dto" class="dto.FacultyDTO" scope="request"></jsp:useBean>


<% List collegeList = (List) request.getAttribute("collegeList"); 

						 List courseList =  (List) request.getAttribute("courseList"); 

						 List subjectList = (List) request.getAttribute("subjectList"); %>



<%
	String fName = null;
	String lName = null;

	if (dto != null && dto.getName() != null) {
		String fnlName = DataUtility.getStringData(dto.getName());
		String[] Name = fnlName.split(" ", 2);

		fName = Name[0];
		lName = Name[1];
	}
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

		<div class="ty-auto h-auto" id="App_Body">


			<div id="login_backgound" class="card">
				<div class="form-block text-white ">
					<div class="text-center my-4 ">

						<%
							if (dto.getId() > 0) {
						%>
						<h3>Update Faculty</h3>
						<%
							} else {
						%>
						<h3>Add Faculty</h3>
						<%
							}
						%>


					</div>


					<form action="<%=ORSView.FACULTY_CTL%>" method="post">





					<!-- Hidden Fields -->
				
					<input type="hidden" name="id" value="<%=dto.getId()%>">
					<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
					<input type="hidden" name="createdDatetime" value="<%=dto.getCreatedDatetime()%>">
					<input type="hidden" name="modifiedDatetime" value="<%=dto.getModifiedDatetime()%>">
	




						<div class="form-group first text-white mb-0">
							<label for="username">First Name</label>
							<input name="firstName" type="text" class="form-control" placeholder="Enter First Name" id="firstname" value="<%=DataUtility.getStringData(fName)%>" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("firstName", request)%></font>
						</div>


						<div class="form-group first text-white mb-0">
							<label for="username">Last Name</label>
							<input name="lastName" type="text" class="form-control" placeholder="Enter Last Name" id="lastname" value="<%=DataUtility.getStringData(lName)%>" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("lastName", request)%></font>
						</div>




						<div class="form-group first text-white mb-0">
							<label for="qualification">Qualification</label>
							<input name="qualification" type="text" class="form-control" placeholder="Enter Qualification" id="qualification" value="<%=DataUtility.getStringData(dto.getQualification())%>" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("qualification", request)%></font>
						</div>


						<div class="form-group first text-white mb-0">
							<label for="email">Email Id</label>
							<input name="email" type="text" class="form-control" placeholder="Enter Email Id" id="email" value="<%=DataUtility.getStringData(dto.getEmail())%>" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("email", request)%></font>
						</div>



						<div class="form-group first text-white mb-0">
							<label for="mobileNo">Mobile Number</label>
							<input name="mobileNo" type="text" maxlength="10" class="form-control" placeholder="Enter Mobile Number" id="mobileNo" value="<%=DataUtility.getStringData(dto.getMobileNo())%>" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("mobileNo", request)%></font>
						</div>




						<div class="form-group last mb-0">
							<label for="password">Gender </label>

							<%
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("Male", "Male");
								map.put("Female", "Female");

								String gender = HTMLUtility.getList("gender", dto.getGender(), map);
							%>

							<p class="form-group last mb-0"><%=gender%></p>

						</div>



						<div class="form-group last mb-0">
							<label for="dob">Date of Birth</label>
							<input name="dob" type="text" class="form-control " placeholder="dd/mm/yyyy" id="datepicker1" value="<%=DataUtility.getDateString(dto.getDob())%>" readonly="readonly" required>
						</div>
						<div>
							<font style="color: red;"><%=ServletUtility.getMessage("dob", request)%></font>
						</div>



						<div class="form-group last mb-0">
							<label for="password">College </label>

							<%
								String collegeDrop = HTMLUtility.getList("collegeId", String.valueOf(dto.getCollegeId()), collegeList);
							%>

							<p class="form-group last mb-0"><%=collegeDrop%></p>

						</div>




						<div class="form-group last mb-0">
							<label for="password">Course </label>

							<%
								String courseDrop = HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList);
							%>

							<p class="form-group last mb-0"><%=courseDrop%></p>

						</div>




						<div class="form-group last mb-0">
							<label for="password">Subject </label>

							<%
								String subjectDrop = HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList);
							%>

							<p class="form-group last mb-0"><%=subjectDrop%></p>

						</div>





<%
							if (dto.getId() > 0) {
						%>
						<div class="mx-auto my-2 flex-column">
							<button type="submit" class="btn btn-outline-primary btn-block my-4 mx-3" name="operation" value="<%=Faculty_Controller.OP_SAVE%>">Update</button>
							<button type="submit" class="btn btn-outline-light btn-block my-4 mx-3" name="operation" value="<%=Faculty_Controller.OP_CANCEL%>">Cancel</button>
						</div>
						<%
							} else {
						%>
						<div class="mx-auto my-2 flex-column">
							<button type="submit" class="btn btn-outline-primary btn-block my-4 mx-3" name="operation" value="<%=Faculty_Controller.OP_SAVE%>">Add</button>
							<button type="reset" class="btn btn-outline-light btn-block my-4 mx-3" name="operation" value="Reset">Reset</button>
						</div>
						<%
							}
						%>



					</form>


				</div>
			</div>



		</div>

		<div id="footer">
			<jsp:include page="Footer.jsp"></jsp:include>
		</div>
	</div>




</body>
</html>