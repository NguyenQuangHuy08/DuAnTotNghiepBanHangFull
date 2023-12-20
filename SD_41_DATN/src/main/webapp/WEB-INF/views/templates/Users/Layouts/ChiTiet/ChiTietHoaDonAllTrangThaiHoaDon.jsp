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
    <title>Chi tiết hóa đơn của all trạng thái hơn đơn</title>
    <style>
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
<%@ include file="../../../../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThaoHoaDon.jsp" %>
<div class="container" style="padding-top: 90px">

    <div class="back">

        <a href="/Admin/xacNhanDonHangKhachHangAll">
            <button class="btn btn-primary">Back</button>
        </a>

    </div>

    <h3 style="color: black;text-align: center;margin-bottom: 30px">Thông tin chi tiết hóa đơn</h3>

    <table class="table table-striped;" style="border-radius: 10px 10px 10px">
        <tr>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 130px">Mã hóa đơn</td>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 200px">Khách hàng</td>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 300px">Ngày thanh toán</td>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 170px">Tổng tiền</td>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 300px">Thông tin nhận hàng</td>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 210px;text-align: center">Ghi chú</td>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 200px">Hình thức</td>
            <td scope="col" style="color: black;font-weight: bold;font-size: 17px;background-color: #bac8f3;width: 200px">Trạng thái</td>
        </tr>
            <tr>
                <td style="font-size: 14px;color: black;font-weight: bold">${maHoaDonView}</td>
                <td style="font-size: 14px;color: black;font-weight: bold">${khachHangView}</td>

                <td style="font-size: 14px;color: black;font-weight: bold">${ngayThanhToanView}</td>
                <td style="font-size: 14px;color: black;font-weight: bold">
                    <fmt:formatNumber type="" value="${tongTienView}" pattern="#,##0.###" /> VNĐ
                </td>
                <td style="font-size: 14px;color: black;font-weight: bold">${thongTienNhanHangView}</td>
                <td style="font-size: 14px;color: black;font-weight: bold">${messView}</td>
                <td style="font-size: 14px;color: black;font-weight: bold">${hinhThucView == 0 ? "Online" : "Tại quầy"}</td>
                <td style="font-size: 14px;color: black;font-weight: bold">
                    <c:choose>
                        <c:when test="${trangThaiView == 0}">Chưa thanh toán</c:when>
                        <c:when test="${trangThaiView == 1}">Chờ xác nhận</c:when>
                        <c:when test="${trangThaiView == 2}">Đang đóng gói hàng</c:when>
                        <c:when test="${trangThaiView == 3}">Đang giao</c:when>
                        <c:when test="${trangThaiView == 4}">Giao hàng thành công</c:when>
                        <c:when test="${trangThaiView == 5}">Hủy đơn hàng</c:when>
                        <c:otherwise>Trạng thái không xác định</c:otherwise>
                    </c:choose>
                </td>
            </tr>
    </table>

    <br>

    <h3 style="text-align: center;color: black;margin-bottom: 50px">Thông tin chi tiết sản phẩm</h3>
    <c:forEach items="${hoaDonChiTiets}" var="list" varStatus="i">
        <p>
                <span style="display: inline-block;color: black;font-size: 16px;margin-left: 15px">${i.index+1} :</span>
                <span style="display: inline-block;color: black;font-size: 16px">${list.giayTheThaoChiTiet.giayTheThao.tenGiayTheThao}</span>
                <span style="display: inline-block;padding-left: 20px;color: black;font-size: 16px">Size: ${list.giayTheThaoChiTiet.size.size}</span>
                <span style="display: inline-block;padding-left: 20px;color: black;font-size: 16px">Màu sắc: ${list.giayTheThaoChiTiet.mauSac.tenMauSac}</span>
                <span style="display: inline-block;padding-left: 20px;color: black;font-size: 16px">Số lượng mua: ${list.soLuong}</span>
                <span style="display: inline-block;padding-left: 20px;color: black;font-size: 16px;margin-top: 30px">Giá bán:
                <fmt:formatNumber type="" value="${list.giayTheThaoChiTiet.giayTheThao.giaBan}" pattern="#,##0.###"/> VNĐ
            </span>
                <span style="display: inline-block;padding-left: 20px">
                <img src="/upload/${list.giayTheThaoChiTiet.giayTheThao.image.get(0).link}" width="110px"
                     style="border-radius: 10px 10px 10px">
            </span>
            </p>
    </c:forEach>
    <div style="margin-bottom: 100px"></div>

</div>

</body>
</html>
