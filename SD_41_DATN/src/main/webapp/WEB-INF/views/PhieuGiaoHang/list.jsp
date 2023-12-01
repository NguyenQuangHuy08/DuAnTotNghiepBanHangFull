<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách dữ liệu phiếu giao hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<%--    <link href="/css/ChuongTrinhGiamGia/HoaDon/list.css" rel="stylesheet">--%>
    <style>
        /*    Table */
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

<%@ include file="../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>


<div class="container">

    <h3 style="color: black;text-align: center;margin-top: 10px; margin-bottom: 20px">List danh sách Phiếu Giao Hàng</h3>
    <h4 style="color: red; font-size: 20px; font-weight: bold; margin-top: 40px; margin-left: 7px; margin-bottom: 30px">${messageDone}</h4>
    <div class="" style="margin-top: 0px; margin-bottom: 20px">
        <h4 style="color: black;margin-bottom: 20px">Tìm kiếm phiếu giao hàng</h4>
<%--        <form action="${pageContext.request.contextPath}/ChuongTrinhGiamGiaHoaDon/search" method="GET">--%>
<%--            <div class="row">--%>
<%--                <div class="col-6">--%>
<%--                    <div class="group">--%>
<%--                        <input required="" type="text" class="input" id="tenChuongTrinh" name="tenChuongTrinh">--%>
<%--                        <span class="highlight"></span>--%>
<%--                        <span class="bar"></span>--%>
<%--                        <label for="tenChuongTrinh">Tên giầy thể thao</label>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="col-6">--%>
<%--                    <div class="group">--%>
<%--                        <input required="" type="text" class="input" id="soLuongSanPham" name="soLuongSanPham">--%>
<%--                        <span class="highlight"></span>--%>
<%--                        <span class="bar"></span>--%>
<%--                        <label for="soLuongSanPham">Thương hiệu</label>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div style="margin-top: 25px"></div>--%>
<%--                <div class="col-6">--%>
<%--                    <div class="group">--%>
<%--                        <input required="" type="text" class="input" id="phanTramGiam" name="phanTramGiam">--%>
<%--                        <span class="highlight"></span>--%>
<%--                        <span class="bar" style="color: black"></span>--%>
<%--                        <label for="phanTramGiam">Trạng thái</label>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="col-6">--%>
<%--                    <div class="group">--%>
<%--                        <input required="" type="text" class="input" id="soTienHoaDon" name="soTienHoaDon">--%>
<%--                        <span class="highlight"></span>--%>
<%--                        <span class="bar"></span>--%>
<%--                        <label for="soTienHoaDon">Giá bán VNĐ</label>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>

<%--                    <button class="button" style="width: 130px;margin-top: 30px; margin-bottom: 0px">--%>
<%--                        <svg class="svg-icon" fill="none" height="20" viewBox="0 0 20 20" width="20" xmlns="http://www.w3.org/2000/svg"><g stroke="#ff342b" stroke-linecap="round" stroke-width="1.5"><path d="m3.33337 10.8333c0 3.6819 2.98477 6.6667 6.66663 6.6667 3.682 0 6.6667-2.9848 6.6667-6.6667 0-3.68188-2.9847-6.66664-6.6667-6.66664-1.29938 0-2.51191.37174-3.5371 1.01468"></path><path d="m7.69867 1.58163-1.44987 3.28435c-.18587.42104.00478.91303.42582 1.0989l3.28438 1.44986"></path></g></svg>--%>
<%--                        <span class="lable">Tìm kiếm</span>--%>
<%--                    </button>--%>
<%--        </form>--%>
        <div class="row">
            <div class="col-1">
                <a href="/PhieuGiaoHang/create" style="text-decoration: none">
                    <button class="button" style="width: 130px;margin-top: 30px; margin-bottom: 20px">
                        <svg class="svg-icon" fill="none" height="20" viewBox="0 0 20 20" width="20" xmlns="http://www.w3.org/2000/svg"><g stroke="#ff342b" stroke-linecap="round" stroke-width="1.5"><path d="m3.33337 10.8333c0 3.6819 2.98477 6.6667 6.66663 6.6667 3.682 0 6.6667-2.9848 6.6667-6.6667 0-3.68188-2.9847-6.66664-6.6667-6.66664-1.29938 0-2.51191.37174-3.5371 1.01468"></path><path d="m7.69867 1.58163-1.44987 3.28435c-.18587.42104.00478.91303.42582 1.0989l3.28438 1.44986"></path></g></svg>
                        <span class="lable">Create</span>
                    </button>
                </a>
            </div>
            <div class="col-2" style="margin-left: 45px">
                <a href="/PhieuGiaoHang/listPhieuGiaoHang" style="text-decoration: none">
                    <button class="button" style="width: 130px;margin-top: 30px; margin-bottom: 20px">
                        <svg class="svg-icon" fill="none" height="20" viewBox="0 0 20 20" width="20" xmlns="http://www.w3.org/2000/svg"><g stroke="#ff342b" stroke-linecap="round" stroke-width="1.5"><path d="m3.33337 10.8333c0 3.6819 2.98477 6.6667 6.66663 6.6667 3.682 0 6.6667-2.9848 6.6667-6.6667 0-3.68188-2.9847-6.66664-6.6667-6.66664-1.29938 0-2.51191.37174-3.5371 1.01468"></path><path d="m7.69867 1.58163-1.44987 3.28435c-.18587.42104.00478.91303.42582 1.0989l3.28438 1.44986"></path></g></svg>
                        <span class="lable">Reload</span>
                    </button>
                </a>
            </div>
        </div>
    </div>

    <div class="row">
        <h3 style="margin-top: 5px; color: black; margin-bottom: 20px">Lọc</h3>
        <div class="common-filter">
            <form id="filter-form">
                <div class="row">
                    <div class="col-6">
                        <h6 style="color:black">Tên khách hàng</h6>
                        <input style="width: 500px; height: 35px;border-radius: 5px 5px 5px" type="text"
                               id="tenKhachHang" placeholder="Tên khách hàng">
                    </div>
                    <div class="col-6">
                        <h6 style="color:black">Tên user</h6>
                        <input style="width: 500px; height: 35px;border-radius: 5px 5px 5px" type="text" id="tenUser"
                               placeholder="Tên user">
                    </div>

                </div>
            </form>
            <div>

            </div>
        </div>
    </div>


<%--Tab list--%>
    <table class="data-table" style="margin-top: 30px;width: 100%;border-radius: 50px 50px 50px">
        <thead>
        <tr>
            <th style="padding-top: 5px; text-align: center; color: black">STT</th>
            <th style="padding-top: 5px; text-align: center; color: black">Tên khách hàng</th>
            <th style="padding-top: 5px; text-align: center; color: black">Tên User</th>
            <th style="padding-top: 5px; text-align: center; color: black">Tên người nhận</th>
            <th style="padding-top: 5px; text-align: center; color: black">Phí ship</th>
<%--            <th style="padding-top: 5px; text-align: center; color: black">Functions</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="phieuGiaoHang" items="${listPage}" varStatus="i">
            <tr class="product-item hide-row" data-tenKhachHang="${phieuGiaoHang.khachHang.tenKhachHang}" data-tenUser="${phieuGiaoHang.user.tenUser}">

                <td style="padding-top: 3px; text-align: center; color: black">${i.index+1}</td>
                <td style="padding-top: 3px; text-align: center; color: black">${phieuGiaoHang.khachHang.tenKhachHang}</td>
                <td style="padding-top: 3px; text-align: center; color: black">${phieuGiaoHang.user.tenUser}</td>
                <td style="padding-top: 3px; text-align: center; color: black">${phieuGiaoHang.tenNguoiNhan}</td>
                <td style="padding-top: 3px; text-align: center; color: black">${phieuGiaoHang.phiShip}</td>
                <td style="margin-bottom: 10px">
                    <a class="col-sm-4" href="${pageContext.request.contextPath}/PhieuGiaoHang/detail/${phieuGiaoHang.id}"><button class="btn btn-primary" style="margin-left: 30px;float: left; margin-top: 24px">Details</button></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>




</div>

<%@ include file="../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
        integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    var filterForm = document.getElementById('filter-form');
    var searchPerformed = false; // Tạo biến để kiểm tra xem tìm kiếm đã được thực hiện hay chưa

    filterForm.addEventListener('input', function () {
        var tenKhachHang = document.getElementById('tenKhachHang').value.toLowerCase();
        var tenUser = document.getElementById('tenUser').value.toLowerCase();

        var products = document.getElementsByClassName('product-item');

        for (var i = 0; i < products.length; i++) {
            var product = products[i];

            var productTenKhachHang = product.getAttribute('data-tenKhachHang').toLowerCase();
            var productTenUser = product.getAttribute('data-tenUser').toLowerCase();

            if (productTenKhachHang.includes(tenKhachHang) &&
                productTenUser.includes(tenUser)) {

                product.classList.remove('hide-row'); // Loại bỏ lớp ẩn

            } else {

                product.classList.add('hide-row'); // Thêm lớp ẩn

            }
        }

        var noResultsMessage = document.getElementById('noResults');
        if (anyResults) {
            noResultsMessage.style.display = 'none'; // Ẩn thông báo
        } else {
            noResultsMessage.style.display = 'block'; // Hiển thị thông báo
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

