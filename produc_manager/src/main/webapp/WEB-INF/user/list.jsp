<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>List-User</title>
    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>

</head>

<body data-layout="horizontal">

<!-- Begin page -->
<div id="wrapper">
    <%--    <img src="/WEB-INF/image/imageUser/a.png" alt="">--%>

    <!-- Navigation Bar-->
    <jsp:include page="/WEB-INF/layout/top_nav.jsp"></jsp:include>

    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <div class="container-fluid">

                <!-- start page title -->
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">
                                <a href="/users?action=create" class="btn btn-outline-pink">Create User</a>
                            </div>
                            <h4 class="page-title">Basic Tables</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="row">
                            <div class="table-responsive">
                                <table class="table m-0">

                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Image</th>
                                        <th>Name</th>
                                        <th>Phone</th>
                                        <th>Email</th>
                                        <th>Country</th>
                                        <th>Create</th>
                                        <th>Update</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${requestScope.listUser}" var="user">
                                        <tr>
                                            <td>${user.id}</td>
                                            <td>
                                                <img src="${user.imageUrl}" style="width: 50px;height: 50px">

                                            </td>
                                            <td>${user.username}</td>
                                            <td>${user.phone}</td>
                                            <td>${user.email}</td>
                                            <c:forEach items="${applicationScope.countryList}" var="country">
                                                <c:if test="${country.id==user.countryId}">
                                                    <td>${country.name}</td>
                                                </c:if>
                                            </c:forEach>

                                            <td>${user.createAt}</td>
                                            <td>${user.updateAt}</td>
                                            <td>
                                                        <a href="/users?action=edit&id=${user.id}"
                                                           class="btn btn-outline-brown">
                                                            <li class="ion ion-md-flower"></li>
                                                            Edit</a>

                                                        <a href="/users?action=delete&id=${user.id}"
                                                           class="btn btn-outline-brown"
                                                           onclick="return confirm('Bạn có muốn xóa tài khoản này')">
                                                            <li class="ion ion-md-trash"></li>
                                                            Delete</a>
                                            </td>
<%--                                            <td>--%>
<%--                                                <li>--%>
<%--                                                    <ul>--%>
<%--                                                        <li style="position: relative;float: left">--%>
<%--                                                            <button>--%>
<%--                                                                <a href="/users?action=edit&id=${user.getId()}"--%>
<%--                                                                   class="btn btn-outline-brown">--%>
<%--                                                                   <li class="ion ion-md-flower"></li>--%>
<%--                                                        Edit</a>--%>
<%--                                                            </button>--%>
<%--                                                        </li>--%>
<%--                                                        <li></li>--%>
<%--                                                    </ul>--%>

<%--                                                </li>--%>
<%--                                            </td>--%>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>


                        </div>
                        <!-- end card-box -->
                    </div>
                    <!-- end col -->
                </div>
                <!-- end row -->

            </div>
            <!-- end container-fluid -->

        </div>

        <!-- end content -->


        <!-- Footer Start -->
        <jsp:include page="/WEB-INF/layout/footer.jsp"></jsp:include>
        <!-- end Footer -->

    </div>

    <!-- ============================================================== -->
    <!-- End Page content -->
    <!-- ============================================================== -->

</div>
<!-- END wrapper -->

<jsp:include page="/WEB-INF/layout/rightbar.jsp"></jsp:include>

<jsp:include page="/WEB-INF/layout/footer_js.jsp">
    <jsp:param name="page" value="list"/>
</jsp:include>


</body>

</html>