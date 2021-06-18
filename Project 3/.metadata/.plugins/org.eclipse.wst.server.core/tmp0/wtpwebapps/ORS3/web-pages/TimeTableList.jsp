<!doctype html>
<%@page import="java.util.Iterator"%>
<%@page import="dto.TimeTableDTO"%>
<%@page import="java.util.List"%>
<%@page import="controller.TimeTableList_Controller"%>
<%@page import="controller.TimeTable_Controller"%>
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


<jsp:useBean id="dto" class="dto.TimeTableDTO" scope="request"></jsp:useBean>

<%
	List courseList = (List) request.getAttribute("courseList");

	List subjectList = (List) request.getAttribute("subjectList");
%>

<title></title>

</head>
<body>
	<div class="container" id="container">


		<div id="header">
			<jsp:include page="Header.jsp"></jsp:include>
		</div>



		<div class="ty-auto flex-column h-90vh" id="App_Body">

			<form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="post" class="d-flex flex-column w-100 mx-3 my-5 justify-content-evenly">

				<div class="ty-auto m-2 form-block text-white text-center w-100 flex-column ">

					<h1 class="mb-4">TimeTable List</h1>

					<%
						if (ServletUtility.getErrorMessage(request) != "") {
					%>

					<div class="alert alert-danger alert-dismissible fade show text-center m-4" role="alert">
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


						<div class="form-group ">
							<label for="examDate">Exam Date</label>
							<input name="examDate" type="text" class="form-control " placeholder="dd/mm/yyyy" id="datepicker2" value="<%=DataUtility.getDateString(dto.getExamDate())%>" readonly="readonly" required>
						</div>



						<div class="form-group  ">
							<label for="password">Course </label>

							<%
								String courseDrop = HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList);
							%>

							<p class=""><%=courseDrop%></p>

						</div>


						<div class="form-group ">
							<label for="password">Subject </label>

							<%
								String subjectDrop = HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList);
							%>

							<p class=""><%=subjectDrop%></p>

						</div>


						<div class=" ">
							<button type="submit" class="btn btn-outline-primary btn-block my-4 " name="operation" value="<%=TimeTableList_Controller.OP_SEARCH%>">Search</button>
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
									<th class="" scope="col">Course Id</th>
									<th class="" scope="col">Semester</th>
									<th class="" scope="col">Subject Id</th>
									<th class="" scope="col">Exam Date</th>
									<th class="" scope="col">Exam Time</th>
									<th class="" scope="col">Update</th>
								</tr>
							</thead>
							<tbody class="table-light">
								<%
									while (it.hasNext()) {
											dto = (TimeTableDTO) it.next();
								%>


								<tr>
									<td align="center"><input type="checkbox" name="ids" value="<%=dto.getId()%>"></td>
									<td class="" scope="row"><%=dto.getCourseId()%></td>
									<td class=""><%=dto.getSemester()%></td>
									<td class=""><%=dto.getSubjectId()%></td>
									<td class=""><%=DataUtility.getDateString(dto.getExamDate())%></td>
									<td class=""><%=dto.getExamTime()%></td>
									<td class=""><a href="<%=ORSView.TIMETABLE_CTL%>?id=<%=dto.getId()%>">Edit</a></td>
								</tr>

								<%
									}
									}
								%>

							</tbody>
						</table>
					</div>

				</div>

				<div class="table-responsive">

					<table width="100%">
						<tr class="d-flex justify-content-around w-100 flex-wrap">
							<td><input type="submit" name="operation" class="btn btn-outline-light btn-md mx-2" style="font-size: 17px" value="<%=TimeTableList_Controller.OP_PREVIOUS%>"
									<%=pageNo > 1 ? "" : "disabled"%>
								></td>
							<td><input type="submit" name="operation" class="btn btn-outline-light btn-md mx-2" style="font-size: 17px" value="<%=TimeTableList_Controller.OP_NEW%>"></td>
							<td><input type="submit" name="operation" class="btn btn-outline-light btn-md mx-2" style="font-size: 17px" value="<%=TimeTableList_Controller.OP_DELETE%>"></td>
							<td><input type="submit" name="operation" class="btn btn-outline-light btn-md mx-2" style="font-size: 17px" value="<%=TimeTableList_Controller.OP_NEXT%>"
									<%=(list.size() > 10) ? "" : "disabled"%>
								></td>
						</tr>
						<tr></tr>
					</table>
				</div>
			</form>
		</div>


		<div id="footer">
			<jsp:include page="Footer.jsp"></jsp:include>
		</div>


	</div>

</body>
</html>