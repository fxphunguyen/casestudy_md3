<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- Vendor js -->
<script src="/assets\js\vendor.min.js"></script>

<!--
Neu page = list thi de trong
Neu page = sales thi them 3 dong js
-->

<c:if test="${param.page=='sales'}">
    <script src="/assets\libs\morris-js\morris.min.js"></script>
    <script src="/assets\libs\raphael\raphael.min.js"></script>

    <script src="/assets\js\pages\dashboard.init.js"></script>
</c:if>

<c:if test="${param.page=='create'}">
    <script src="/assets\libs\toastr\toastr.min.js"></script>
    <script src="/assets\js\pages\toastr.init.js"></script>
</c:if>

<!-- App js -->
<script src="/assets\js\app.min.js"></script>