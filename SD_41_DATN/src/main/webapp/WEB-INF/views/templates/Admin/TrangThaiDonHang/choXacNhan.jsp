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
    <title>Đơn hàng chờ xác nhận của khách hàng</title>
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

    <div class="vertical-menu" style="background-color: #bac8f3">
        <a href="/Admin/xacNhanDonHangKhachHang">Chờ xác nhận</a>
        <a href="/Admin/xacNhanDonHangKhachHang">Chờ xác nhận</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCong">Đang giao</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh">Hoàn thành</a>
        <a href="">Đã hủy</a>
    </div>

    <div style="margin-left: 0px; padding: 1px 16px;">

        <h3 style="margin-top: 30px;margin-bottom: 30px">Các đơn hàng chờ xác nhận</h3>
        <form method="post">
            <input type="hidden" name="idKH" value="${idKH}">
            <table class="table-sm" style="width: 1010px">
                <tr>
                    <td scope="col" style="font-size: 15px">Mã</td>
                    <td scope="col" style="font-size: 15px">Tên khách hàng</td>
                    <td scope="col" style="font-size: 15px">Ngày thanh toán</td>
                    <td scope="col" style="font-size: 15px">Tổng tiền</td>
                    <td scope="col" style="font-size: 15px">Ghi chú</td>
                    <td scope="col" style="font-size: 15px">Action</td>
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
                        <td>
                            <button formaction="/Admin/HoaDon/XacNhanHoaDonKhachHang" name="huy" value="${list.id}"
                                    class="btn btn-primary me-2">Xác nhận
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br>
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
