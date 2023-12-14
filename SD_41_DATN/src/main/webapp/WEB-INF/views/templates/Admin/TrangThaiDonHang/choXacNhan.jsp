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

    /*    Css form mới */

        /* CSS */

        /* CSS */
        /* CSS */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 60%;
            position: relative;
        }

        .close {
            color: #aaa;
            position: absolute;
            top: 0;
            right: 0;
            font-size: 28px;
            font-weight: bold;
            padding: 10px;
            cursor: pointer;
            z-index: 2; /* Thêm dòng này để đẩy lên trên form */
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

    </style>

</head>
<body>

<%@ include file="../../../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThaoHoaDon.jsp" %>

<div class="container" style="">

    <div class="vertical-menu" style="background-color: #bac8f3">
        <a href="/Admin/xacNhanDonHangKhachHangAll" style="color: black">All trạng thái đơn hàng</a>
        <a href="/Admin/xacNhanDonHangKhachHang">Chờ xác nhận</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonDangDongGoi">Đang đóng gói</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonKhachHangDangGiao">Đang giao</a>
        <a href="/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh">Hoàn thành</a>
        <a href="/Admin/HoaDon/DonHangBiHuy">Đã hủy</a>
    </div>

    <div style="overflow-x: auto;width: 100%">
        <h3 style="margin-top: 30px;margin-bottom: 60px;text-align: center;color: black">Các đơn hàng chờ xác nhận</h3>
        <form method="post">
            <table class="table table-striped;" style="border-radius: 10px 10px 10px">
                <tr>
                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 130px">Mã hóa đơn</td>
                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 200px">Khách hàng</td>
                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 300px">Ngày thanh toán</td>
                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 170px">Tổng tiền</td>
                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 300px">Thông tin nhận hàng</td>
                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 210px;text-align: center">Ghi chú</td>
<%--                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 200px">Hình thức</td>--%>
                    <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 130px">Action</td>
                </tr>
                <c:forEach items="${page.content}" var="list">
                    <tr>

                        <td style="font-size: 14px;color: black;font-weight: bold">${list.maHoaDon}</td>
                        <td style="font-size: 14px;color: black;font-weight: bold">${list.khachHang.tenKhachHang}</td>

                        <td  style="display: none">
                            <input type="hidden" name="idHoaDon" value="${list.id}">
                        </td>

                        <td  style="display: none">
                            <input type="hidden" name="maHoaDon" value="${list.maHoaDon}">
                        </td>
                        <td  style="display: none">
                            <input type="hidden" name="email" value="${list.khachHang.email}">
                        </td>
                        <td  style="display: none">
                            <input type="hidden" name="tenKhachHang" value="${list.khachHang.tenKhachHang}">
                        </td>
                        <td  style="display: none">
                            <input type="hidden" name="ngayThanhToan" value="${list.ngayThanhToan}">
                        </td>
                        <td  style="display: none">
                            <input type="hidden" name="thanhTien" value="${list.thanhTien}">
                        </td>

                        <td style="font-size: 14px;color: black;font-weight: bold">
                            <script>
                                var ngayThanhToan = "${list.ngayThanhToan}";
                                var parts = ngayThanhToan.split('-');
                                var formattedDate = parts[1] + '-' + parts[2] + '-' + parts[0];
                                document.write(formattedDate);
                            </script>
                        </td>
                        <td style="font-size: 14px;color: black;font-weight: bold">
                            <fmt:formatNumber type="" value="${list.thanhTien}" pattern="#,##0.###" /> VNĐ
                        </td>
                        <td style="font-size: 14px;color: black;font-weight: bold">${list.ghiChu}</td>
                        <td style="font-size: 14px; color: black; font-weight: bold">
                            <c:choose>
                                <c:when test="${not empty list.mess}">${list.mess}</c:when>
                                <c:otherwise>N/A</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <button style="width: 100px;font-size: 15px;margin-top: 20px" formaction="/Admin/HoaDon/XacNhanHoaDonKhachHang" name="huy" value="${list.id}"
                                    class="btn btn-primary">Done
                            </button>
                            <button style="width: 100px;font-size: 15px;margin-top: 20px" formaction="/Admin/HoaDon/HuyDonHangCuaKhachHang" name="huyDonHang" value="${list.id}"
                                    class="btn btn-primary">Hủy
                            </button>
<%--                            <button formaction="/test" style="width: 100px; font-size: 15px; margin-top: 20px" class="btn btn-primary" onclick="openModal('${list.maHoaDon}', '${list.khachHang.email}', '${list.khachHang.tenKhachHang}', '${list.ngayThanhToan}', '${list.thanhTien}', '${list.ghiChu}')">View</button>--%>
                            <button formaction="/test" style="width: 100px; font-size: 15px; margin-top: 20px" class="btn btn-primary" onclick="openModal('${list.maHoaDon}', '${list.khachHang.email}', '${list.khachHang.tenKhachHang}', '${list.ngayThanhToan}', '${list.thanhTien}', '${list.ghiChu}', event)" type="button">View</button>
<%--                            <button style="width: 100px; font-size: 15px; margin-top: 20px" class="btn btn-primary" onclick="openModalAndRedirect('/test', '${list.maHoaDon}', '${list.khachHang.email}', '${list.khachHang.tenKhachHang}', '${list.ngayThanhToan}', '${list.thanhTien}', '${list.ghiChu}', event)" type="button">View</button>--%>

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


<%--    Form mới --%>

    <div id="myModal" class="modal">
        <div class="modal-content" style="width:60%">
            <span class="close" onclick="closeModal()" style="margin-left: 840px">&times;</span>
            <!-- Nội dung modal sẽ được cập nhật bằng JavaScript -->
            <div id="modalContent"></div>
        </div>
    </div>



</div>

</body>


<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>


<script>
    // JavaScript
    function openModal(maHoaDon, email, tenKhachHang, ngayThanhToan, thanhTien, ghiChu, event) {
        event.preventDefault(); // Ngăn chặn sự kiện mặc định
        var modalContent = document.getElementById("modalContent");
        modalContent.innerHTML =
            "<p>Mã hóa đơn: " + maHoaDon + "</p>" +
            "<p>Email: " + email + "</p>" +
            "<p>Tên khách hàng: " + tenKhachHang + "</p>" +
            "<p>Ngày thanh toán: " + ngayThanhToan + "</p>" +
            "<p>Thành tiền: " + thanhTien + "</p>" +
            "<p>Ghi chú: " + ghiChu + "</p>";

        document.getElementById("myModal").style.display = "block";
    }

    function closeModal() {
        document.getElementById("myModal").style.display = "none";
    }

</script>




</html>
