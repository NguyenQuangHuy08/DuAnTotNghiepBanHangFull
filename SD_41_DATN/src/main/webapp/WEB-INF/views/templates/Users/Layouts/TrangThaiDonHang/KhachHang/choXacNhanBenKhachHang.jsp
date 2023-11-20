<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chờ xác nhận đơn hàng</title>
    <style>


        .vertical-menu {
            width: 200px;
            background-color: #FAFAFA;
            position: absolute;
            left: 160px;
            height: 100%;
            overflow: auto;
        }

        .vertical-menu a {
            background-color: #FAFAFA;
            color: black;
            display: block;
            padding: 12px;
            text-decoration: none;
        }

        .vertical-menu a:hover {
            background-color: #b1dfbb;
            color: white;
        }

    </style>

</head>
<body>
<%@ include file="../../Index/_Header_No_Register.jsp" %>

<div class="container" style="padding-top: 90px">

    <div class="vertical-menu">
        <a href="/KhachHang/HoaDon/ChoThanhToan/${maKH}" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px;margin-top: 10px">Chờ thanh toán</a>
        <a href="/KhachHang/HoaDon/ChoXacNhan/${maKH}" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Chờ xác nhận</a>
        <a href="/KhachHang/HoaDon/DangGiaoHang/${maKH}" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Đang giao</a>
        <a href="/KhachHang/HoaDon/GiaoHangThanhCong/${maKH}" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Hoàn thành</a>
        <a href="/KhachHang/HoaDon/HuyDonHang/${maKH}" style="color: black;font-size: 18px;font-weight: lighter;margin-left: 10px">Đã hủy</a>
    </div>

    <div style="margin-left: 180px; padding: 1px 16px;">

        <h3 style="margin-top: 30px;margin-bottom: 30px">Các đơn hàng chờ xác nhận</h3>
        <form method="post">
            <input type="hidden" name="idKH" value="${idKH}">
            <table class="table" style="">
                <tr>
                    <td scope="col" style="color: black;font-size: 17px">Mã hóa đơn</td>
                    <td scope="col" style="color: black;font-size: 17px">Ngày thanh toán</td>
                    <td scope="col"  style="color: black;font-size: 17px">Thành tiền</td>
                    <td scope="col"  style="color: black;font-size: 17px">Thông tin nhận hàng</td>
                    <td scope="col"  style="color: black;font-size: 17px">Action</td>
                </tr>
                <c:forEach items="${page.content}" var="list">
                    <tr>
                        <td scope="row">${list.maHoaDon}</td>
                        <td style="padding-left: 40px">
                            <script>
                                var ngayThanhToan = "${list.ngayThanhToan}";
                                var parts = ngayThanhToan.split('-');
                                var formattedDate = parts[1] + '-' + parts[2] + '-' + parts[0];
                                document.write(formattedDate);
                            </script>
                        </td>
                        <td>
                                    <fmt:formatNumber type="" value="${list.thanhTien}" pattern="#,##0.###" /> VNĐ
                        </td>
                        <td style="width: 300px">${list.ghiChu}</td>
                        <td>

                            <button type="submit" formaction="/KhachHang/HoaDon/HuyDonHang" name="huyDonHang" value="${list.id}"
                                    class="btn btn-primary me-2" method="post">Hủy
                            </button>

                        </td>
                    </tr>
                </c:forEach>
            </table>
            <ul class="pagination" style="width: 30px;height: 30px">
                <c:if test="${not page.first}">
                    <li class="page-item">
                        <a href="?pageNo=${page.number -1}" style="width: 30px;height: 40px">Pre</a>
                    </li>
                </c:if>
                <c:forEach begin="0" end="${page.totalPages > 1 ? page.totalPages - 1 : 0}" var="i">
                    <li class="page-item <c:if test='${i == page.number}'>active</c:if>">
                        <a class="page-link" href="?pageNo=${i}" style="width: 30px;height: 40px">${i + 1}</a>
                    </li>
                </c:forEach>
                <c:if test="${not page.last}">
                    <li class="page-item">
                        <a href="?pageNo=${page.number +1}" style="width: 30px;height: 40px">Next</a>
                    </li>
                </c:if>
            </ul>
        </form>

    </div>

</div>
</body>
</html>
