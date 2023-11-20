<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>List chương trình giảm giá giầy thể thao</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="/css/ChuongTrinhGiamGia/HoaDon/list.css" rel="stylesheet">
    <style>
        td, th {
            padding: 10px; /* Khoảng cách nội dung bên trong ô */
            color: black; /* Màu chữ */
            text-align: center; /* Căn giữa nội dung trong ô */
        }
        .hide-row {
            display: none;
        }
    </style>
</head>
<body>

<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>

<div class="container">
    <h3 style="color: black;text-align: center;margin-top: 40px; margin-bottom: 60px">List danh sách chương trình giảm giá hóa đơn</h3>
    <div class="" style="margin-top: 0px; margin-bottom: 30px">
        <h4 style="color: black;margin-bottom: 40px">Tìm kiếm hóa đơn</h4>
    </div>
</div>
<form action="${pageContext.request.contextPath}/ChuongTrinhGiamGiaHoaDon/search" method="GET">
    <label for="tenChuongTrinh" style="margin-left: 30px">Tên chương trình </label>
    <input style="width: 400px" type="text" id="tenChuongTrinh1" name="tenChuongTrinh">
    <br>
    <button type="submit">Tìm kiếm</button>
</form>
<%--Todo code làm tạm cái bộ lọc--%>

<div class="sidebar-filter mt-50">
    <div class="common-filter">
        <form id="filter-form">
            <input type="text" id="tenChuongTrinh" placeholder="Tên chương trình">
            <input type="text" id="phanTramGiam" placeholder="Phần trăm giảm">
            <input type="text" id="soLuongSanPham" placeholder="Số lượng sản phẩm">
            <input type="text" id="soTienHoaDon" placeholder="Số tiền hóa đơn">
        </form>
    </div>
</div>

<table style="margin-top: 30px;width: 100%">
    <thead>
    <tr>
        <th style="padding-top: 40px; text-align: center; color: black">STT</th>
        <th style="padding-top: 55px; text-align: center; color: black">Tên Chương Trình</th>
        <th style="padding-top: 55px; text-align: center; color: black">Phần Trăm Giảm</th>
        <th style="padding-top: 55px; text-align: center; color: black">Số Lượng Sản Phẩm</th>
        <th style="padding-top: 55px; text-align: center; color: black">Số Tiền Hóa Đơn</th>
        <th style="padding-top: 55px; text-align: center; color: black">Ngày Bắt Đầu</th>
        <th style="padding-top: 55px; text-align: center; color: black">Ngày Kết Thúc</th>
        <th style="padding-top: 55px; text-align: center; color: black">Ghi Chú</th>
        <th style="padding-top: 55px; text-align: center; color: black">Trạng Thái</th>
        <th style="padding-top: 55px; text-align: center; color: black">Action</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="hd" items="${listPage}" varStatus="i">
        <tr class="product-item hide-row" data-tenChuongTrinh="${hd.tenChuongTrinh}" data-phanTramGiam="${hd.phanTramGiam}" data-soLuongSanPham="${hd.soLuongSanPham}" data-soTienHoaDon="${hd.soTienHoaDon}">
            <td style="padding-top: 40px; text-align: center; color: black">${i.index+1}</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.tenChuongTrinh}</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.phanTramGiam}%</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.soLuongSanPham}</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.soTienHoaDon} VNĐ</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.ngayBatDau}</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.ngayKetThuc}</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.ghiChu}</td>
            <td style="padding-top: 55px; text-align: center; color: black">${hd.trangThai==1 ? "Chưa hết hạn" : "Hết hạn"}</td>
            <td>
                <a class="col-sm-4" href="${pageContext.request.contextPath}/ChuongTrinhGiamGiaHoaDon/edit/${hd.id}"><button class="btn btn-primary">Edit</button></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<ul class="pagination" style="margin-left: 500px">
    <c:forEach begin="1" end="${totalPage}" varStatus="status">
        <li class="page-item">
            <a href="${pageContext.request.contextPath}/ChuongTrinhGiamGiaHoaDon/list?pageNum=${status.index}" class="page-link">${status.index}</a>
        </li>
    </c:forEach>
</ul>

<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>

<script>
    var filterForm = document.getElementById('filter-form');
    var searchPerformed = false; // Tạo biến để kiểm tra xem tìm kiếm đã được thực hiện hay chưa

    filterForm.addEventListener('input', function () {
        var tenChuongTrinh = document.getElementById('tenChuongTrinh').value.toLowerCase();
        var phanTramGiam = document.getElementById('phanTramGiam').value.toLowerCase();
        var soLuongSanPham = document.getElementById('soLuongSanPham').value.toLowerCase();
        var soTienHoaDon = document.getElementById('soTienHoaDon').value.toLowerCase();

        var products = document.getElementsByClassName('product-item');

        for (var i = 0; i < products.length; i++) {
            var product = products[i];

            var productTenChuongTrinh = product.getAttribute('data-tenChuongTrinh').toLowerCase();
            var productPhanTramGiam = product.getAttribute('data-phanTramGiam').toLowerCase();
            var productSoLuongSanPham = product.getAttribute('data-soLuongSanPham').toLowerCase();
            var productSoTienHoaDon = product.getAttribute('data-soTienHoaDon').toLowerCase();

            if (productTenChuongTrinh.includes(tenChuongTrinh) &&
                productPhanTramGiam.includes(phanTramGiam) &&
                productSoLuongSanPham.includes(soLuongSanPham) &&
                productSoTienHoaDon.includes(soTienHoaDon))
            {

                product.classList.remove('hide-row'); // Loại bỏ lớp ẩn

            } else {

                product.classList.add('hide-row'); // Thêm lớp ẩn

            }
        }

        // Đặt biến searchPerformed thành true khi người dùng thực hiện tìm kiếm
        searchPerformed = true;

    });

    // Trước khi kích hoạt tìm kiếm, kiểm tra xem searchPerformed đã được thiết lập thành true hay chưa
    if (!searchPerformed) {
        var initialProducts = document.getElementsByClassName('product-item');
        for (var i = 0; i < initialProducts.length; i++) {

            initialProducts[i].classList.remove('hide-row'); // Loại bỏ lớp ẩn

        }
    }
</script>
</body>
</html>
