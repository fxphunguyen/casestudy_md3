<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Product-List</title>
    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>

</head>

<body data-layout="horizontal">

<!-- Begin page -->
<div id="wrapper">

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
                                <a href="/products?action=create" class="btn btn-outline-pink">Create Product</a>
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
                                        <th>name</th>
                                        <th>price</th>
                                        <th>quantity</th>
                                        <th>Category</th>
                                        <th>CreateAt</th>
                                        <th>UpdateAt</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${requestScope.productList}" var="product">
                                        <tr>
                                            <td>${product.getId()}</td>
                                            <td>
                                                <a href="">
                                                    <img src="${product.getUrlImg()}" style="width: 50px;height: 50px">
                                                </a>
                                            </td>
                                            <td>${product.getName()}</td>
                                            <td><c:set var="price" value="${product.getPrice()}"/>
                                                <fmt:setLocale value="vi_VN"/>
                                                <fmt:formatNumber value="${price}" type="currency"/></td>

                                            <td>${product.getQuantity()}</td>
                                            <td>
                                                <c:forEach items="${applicationScope.categoryList}" var="category">
                                                    <c:if test="${category.getId()==product.getIdCategory()}">
                                                        ${category.getName()}
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td>${product.getCreateAt()}</td>
                                            <td>${product.getUpdateAt()}</td>
                                            <td>
                                                <a href="/products?action=edit&id=${product.getId()}"
                                                   class="btn btn-outline-brown">
                                                    <li class="ion ion-md-flower"></li>
                                                    Edit</a>
                                                <a href="/products?action=delete&id=${product.id}"
                                                   class="btn btn-outline-brown" onclick="return confirm('Bạn có muốn xóa sản phẩm này')">
                                                    <li class="ion ion-md-trash"></li>
                                                    Delete</a>
                                            </td>
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