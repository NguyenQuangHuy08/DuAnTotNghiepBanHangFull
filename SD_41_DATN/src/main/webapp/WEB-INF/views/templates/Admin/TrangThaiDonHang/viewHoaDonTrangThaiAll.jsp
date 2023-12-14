<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hóa đơn của khách hàng</title>
    <style>
        .container {
            display: flex;
            flex-wrap: wrap; /* Cho phép các phần tử con xuống dòng khi không đủ không gian */
        }

        .vertical-menu {
            display: flex;
            flex-direction: row;
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        .vertical-menu a {
            text-decoration: none;
            color: black;
            font-size: 20px;
            font-weight: bold;
            margin-left: 20px;
            padding-left: 20px;
            padding-right: 20px;
            margin: 5px;
        }

        .container > div {
            width: 100%; /* Đảm bảo rằng nội dung chiếm toàn bộ chiều rộng khi xuống dòng */
            margin-top: 16px; /* Thêm khoảng trắng giữa menu và nội dung */
            padding: 16px;
        }
    </style>
</head>
<body>

<%@ include file="../../../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThaoHoaDon.jsp" %>

<div class="container">

    <div class="vertical-menu" style="background-color: #bac8f3">
        <a href="/Admin/xacNhanDonHangKhachHang" style="color: black">Chờ xác nhận</a>
        <a href="/Admin/xacNhanDonHangKhachHang" style="">Chờ đóng gói</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCong"> Đang giao</a>
        <a href="#">Hoàn thành</a>
        <a href="#">Đã hủy</a>
    </div>
   <br>
    <div style="border: 1px solid red;">
        <h2>Nội dung trang web của bạn</h2>
        <p>Đây là nơi bạn có thể thêm các phần tử và nội dung trang web của mình.</p>
    </div>

</div>

</body>
</html>
