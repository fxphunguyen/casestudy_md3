<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Create-Product</title>

    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>

    <link href="/assets\libs\toastr\toastr.min.css" rel="stylesheet" type="text/css">

</head>

<body data-layout="horizontal">

<!-- Begin page -->
<div id="wrapper">

    <!-- Navigation Bar-->
    <jsp:include page="/WEB-INF/layout/top_nav.jsp"></jsp:include>
    <!-- End Navigation Bar-->


    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <div class="container-fluid">

                <!-- start page title -->
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">

                                <a href="/products" class="btn btn-outline-danger">List Product</a>
                            </div>
                            <h4 class="page-title">Form elements</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->

                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-horizontal" method="post" enctype="multipart/form-data">
                            <%--                            <c:if test="${requestScope.errors!=null}">--%>
                            <%--                                <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0"--%>
                            <%--                                     role="alert">--%>
                            <%--                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                            <%--                                        <span aria-hidden="true">×</span>--%>
                            <%--                                    </button>--%>
                            <%--                                    <c:forEach items="${requestScope.errors}" var="e">--%>
                            <%--                                        <strong>*Field: ${fn:toUpperCase(e.key)}</strong> </br>--%>
                            <%--                                        <c:forEach items="${e.value}" var="item">--%>
                            <%--                                            <span>${item}</span> </br>--%>
                            <%--                                        </c:forEach>--%>
                            <%--                                    </c:forEach>--%>

                            <%--                                </div>--%>
                            <%--                            </c:if>--%>

                            <div class="form-group row">
                                <input type="hidden" name="id" value="${requestScope["product"].getId()}" placeholder="${requestScope["product"].getId()}"/>
                                <label class="col-md-2 control-label">Title</label>
                                <div class="col-md-10">
                                    <input type="text" name="name" class="form-control"
                                           value="${requestScope["product"].getName()}" placeholder="${requestScope["product"].getName()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label" for="example-price">Price</label>
                                <div class="col-md-10">
                                    <input type="text" id="example-price" name="price"
                                           value="${requestScope["product"].getPrice()}" placeholder="${requestScope["product"].getPrice()}" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Quantity</label>
                                <div class="col-md-10">
                                    <input type="text" name="quantity" class="form-control"
                                           value="${requestScope["product"].getQuantity()}" placeholder="${requestScope["product"].getQuantity()}">
                                </div>
                            </div>
                                <div class="form-group row">
                                    <label class="col-md-2 control-label">Ảnh</label>
                                    <div class="row">
                                        <div style="display: none">
                                            <input type="text"  name="img"
                                                   value="${requestScope["product"].urlImg}" placeholder="${requestScope["product"].urlImg}">
                                            ${requestScope["product"].urlImg}
                                        </div>
                                        <div>
                                            <img src="${requestScope["product"].urlImg}" style="width: 200px; height: 200px;">
                                        </div>
                                        <div>
                                            <input type="file" name="image" class="form-control"
                                                   value="${requestScope["product"].urlImg}">
                                        </div>
                                    </div>
                                </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Category</label>
                                <div class="col-md-10">
                                    <select class="form-control" name="idCategory">
                                        <c:forEach var="category" items="${applicationScope.categoryList}">
                                            <option value="${category.getId()}">${category.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label"></label>
                                <div class="col-md-10">
                                    <button type="submit" class="btn btn-purple waves-effect waves-light">Submit
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div>
                        ${errors}
                        ${sussceess}
                    </div>
                </div>
                <!-- end row -->

            </div>
            <!-- end container-fluid -->

        </div>
        <!-- end content -->


        <!-- Footer Start -->
        <footer class="footer">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        2018 - 2020 &copy; Zircos theme by <a href="">Coderthemes</a>
                    </div>
                </div>
            </div>
        </footer>
        <!-- end Footer -->

    </div>


</div>
<!-- END wrapper -->

<!-- Right Sidebar -->
<jsp:include page="/WEB-INF/layout/rightbar.jsp"></jsp:include>


<jsp:include page="/WEB-INF/layout/footer_js.jsp">
    <jsp:param name="page" value="create"/>
</jsp:include>
<c:if test="${requestScope.success!=null}">
    <script>
        $(document).ready(function () {
            <% String success= (String) request.getAttribute("success"); %>
            var js_Success = "<%= success %>";
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }
            toastr["success"](js_Success)
        });
    </script>
</c:if>
</body>

</html>