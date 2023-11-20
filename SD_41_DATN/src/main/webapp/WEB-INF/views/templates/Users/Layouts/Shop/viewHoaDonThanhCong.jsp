<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>


<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"--%>
<%--"http://www.w3.org/TR/html4/loose.dtd">--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Hóa đơn</title>--%>
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.6/dist/sweetalert2.min.css">--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.6/dist/sweetalert2.all.min.js"></script>--%>

<%--    <style>--%>
<%--        td, th {--%>
<%--            padding: 10px; /* Khoảng cách nội dung bên trong ô */--%>
<%--            color: black; /* Màu chữ */--%>
<%--            text-align: center; /* Căn giữa nội dung trong ô */--%>
<%--        }--%>

<%--    /*    Cho hóa đơn*/--%>

<%--        .form-group {--%>
<%--            margin-bottom: 20px;--%>
<%--        }--%>

<%--        label {--%>
<%--            font-weight: bold;--%>
<%--        }--%>

<%--        input[type="text"] {--%>
<%--            width: 100%;--%>
<%--            padding: 8px;--%>
<%--            border-radius: 4px;--%>
<%--            border: 1px solid #ccc;--%>
<%--        }--%>

<%--        .payment-methods {--%>
<%--            padding: 10px 0;--%>
<%--        }--%>

<%--        #ctgg-details,--%>
<%--        #banking-image,--%>
<%--        #cash-details {--%>
<%--            display: none;--%>
<%--        }--%>

<%--        button[type="submit"] {--%>
<%--            padding: 10px 20px;--%>
<%--            background-color: #4CAF50;--%>
<%--            color: white;--%>
<%--            border: none;--%>
<%--            border-radius: 4px;--%>
<%--            cursor: pointer;--%>
<%--        }--%>

<%--    </style>--%>

<%--</head>--%>
<%--<body>--%>
<%--&lt;%&ndash;Header cho giỏ hàng &ndash;%&gt;--%>
<%--<%@ include file="../../Layouts/Index/_Header_No_Register.jsp" %>--%>

<%--<div class="container">--%>

<%--    <h3 style="text-align: center">Đặt hàng thành công</h3>--%>

<%--</div>--%>


<%--<script>--%>

<%--    //Thêm giỏ hàng thành công--%>
<%--    var successMessage = "${successMessage}";--%>
<%--    if (successMessage) {--%>
<%--        Swal.fire({--%>

<%--            icon: 'success',--%>
<%--            title: '<span style="font-size: 17px;">Thêm sản phẩm vào giỏ hàng thành công</span>',--%>
<%--            showConfirmButton: false, // Ẩn nút OK--%>
<%--            timer: 1000, // Thời gian hiển thị thông báo (miligiây)--%>

<%--        })--%>
<%--    }--%>


<%--    //Code js cho hóa đơn--%>

<%--    var gia = parseFloat(document.getElementById("price").value);--%>
<%--    var gia1 = gia;--%>
<%--    document.getElementById("price1").value = gia1.toFixed(0);--%>

<%--    function togglePaymentDetails() {--%>
<%--        var bankingImage = document.getElementById("banking-image");--%>
<%--        var cashDetails = document.getElementById("cash-details");--%>
<%--        var bankingRadio = document.getElementById("banking");--%>
<%--        var cashRadio = document.getElementById("cash");--%>

<%--        if (bankingRadio.checked) {--%>
<%--            bankingImage.style.display = "block";--%>
<%--            cashDetails.style.display = "none";--%>
<%--        } else if (cashRadio.checked) {--%>
<%--            bankingImage.style.display = "none";--%>
<%--            cashDetails.style.display = "block";--%>
<%--        }--%>
<%--    }--%>

<%--    function togglePaymentDetails1() {--%>

<%--        var ctggDetails = document.getElementById("ctgg-details");--%>
<%--        var ctggRadio = document.getElementById("ctgg");--%>

<%--        if (ctggRadio.checked) {--%>
<%--            ctggDetails.style.display = "block";--%>
<%--        }--%>

<%--    }--%>

<%--    function updatePrice(radioButton) {--%>

<%--        var priceInput = document.getElementById("price");--%>
<%--        var priceInput1 = document.getElementById("price1");--%>
<%--        var currentPrice = parseFloat(${hoaDon.thanhTien});--%>
<%--        var selectedValue = parseFloat(radioButton.value);--%>
<%--        var newPrice = currentPrice - (currentPrice * selectedValue / 100);--%>
<%--        priceInput.value = newPrice.toFixed(2);--%>
<%--        priceInput1.value = newPrice.toFixed(2);--%>

<%--    }--%>
<%--    function toggleView(elementId) {--%>

<%--        var element = document.getElementById(elementId);--%>
<%--        element.style.display = (element.style.display === "block") ? "none" : "block";--%>

<%--    }--%>



<%--</script>--%>

<%--<%@ include file="../../Layouts/Index/_Session1.jsp" %>--%>
<%--</body>--%>
<%--</html>--%>


