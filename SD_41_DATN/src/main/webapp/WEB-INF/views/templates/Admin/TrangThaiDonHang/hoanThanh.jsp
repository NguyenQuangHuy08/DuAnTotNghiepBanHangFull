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
    <title>Đơn hàng đang giao cho khách hàng thành công</title>
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

<div class="container" style="">

    <div class="vertical-menu">
        <a href="/Admin/xacNhanDonHangKhachHang" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Chờ xác nhận</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCong" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Đang giao</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Hoàn thành</a>
        <a href="" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Đã hủy</a>
    </div>

    <div style="margin-left: 100px; padding: 1px 16px;">

        <h3 style="margin-top: 30px;margin-bottom: 30px">Các đơn hàng giao thành công cho khách hàng</h3>
        <form method="post">
            <table class="table">
                <tr>
                    <td scope="col">Mã</td>
                    <td scope="col">Khách hàng</td>
                    <td scope="col">Ngày tạo</td>
                    <td scope="col">Tổng tiền</td>
                    <td scope="col">Ghi chú</td>
<%--                    <td scope="col">Action</td>--%>
                </tr>
                <c:forEach items="${page.content}" var="list">
                    <tr>
                        <td scope="row">${list.maHoaDon}</td>
                        <td scope="row">${list.khachHang.tenKhachHang}</td>
                        <td>
                            <script>
                                var ngayThanhToan = "${list.ngayThanhToan}";
                                var parts = ngayThanhToan.split('-');
                                var formattedDate = parts[1] + '-' + parts[2] + '-' + parts[0];
                                document.write(formattedDate);
                            </script>
                        </td>
                        <td>
                            <fmt:formatNumber type="" value="${list.thanhTien}" pattern="#,##0.###" />
                        </td>
                        <td>${list.ghiChu}</td>
<%--                        <td>--%>
<%--                            <a href="/hoa-don/${list.id}">Mua lại</a>--%>
<%--                        </td>--%>
                    </tr>
                </c:forEach>
            </table>
            <ul class="pagination">
                <c:if test="${not page.first}">
                    <li class="page-item">
                        <a href="?pageNo=${page.number -1}">Pre</a>
                    </li>
                </c:if>
                <c:forEach begin="0" end="${page.totalPages > 1 ? page.totalPages - 1 : 0}" var="i">
                    <li class="page-item <c:if test='${i == page.number}'>active</c:if>">
                        <a class="page-link" href="?pageNo=${i}">${i + 1}</a>
                    </li>
                </c:forEach>
                <c:if test="${not page.last}">
                    <li class="page-item">
                        <a href="?pageNo=${page.number +1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </form>


    </div>

</div>



</body>
</html>
