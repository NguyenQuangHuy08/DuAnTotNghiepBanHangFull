<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thêm dữ liệu chương trình giảm giá giầy thể thao</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="/css/ChuongTrinhGiamGia/GiayTheThao/list.css" rel="stylesheet">


</head>
<body>

<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>

<div class="container">
    <h3 style="text-align: center; color: black; margin-top: 30px; margin-bottom: 40px">List chương trình giảm giá giầy thể thao </h3>
    <br>
    <div class="" style="margin-top: 0px; margin-bottom: 30px">
        <h4 style="color: black;margin-bottom: 40px">Tìm kiếm sản phẩm</h4>
        <form action="${pageContext.request.contextPath}/ChuongTrinhGiamGiaHoaDon/search" method="GET">
            <div class="row">
                <div class="col-6">
                    <div class="group">
                        <input required="" type="text" class="input" id="tenChuongTrinh" name="tenChuongTrinh">
                        <span class="highlight"></span>
                        <span class="bar"></span>
                        <label for="tenChuongTrinh">Tên chương trình</label>
                    </div>
                </div>
                <div class="col-6">
                    <div class="group">
                        <input required="" type="text" class="input" id="soLuongSanPham" name="soLuongSanPham">
                        <span class="highlight"></span>
                        <span class="bar"></span>
                        <label for="soLuongSanPham">Số lượng sản phẩm</label>
                    </div>
                </div>
                <div style="margin-top: 40px"></div>
                <div class="col-6">
                    <div class="group">
                        <input required="" type="text" class="input" id="phanTramGiam" name="phanTramGiam">
                        <span class="highlight"></span>
                        <span class="bar"></span>
                        <label for="phanTramGiam">Phần trăm giảm</label>
                    </div>
                </div>
                <div class="col-6">
                    <div class="group">
                        <input required="" type="text" class="input" id="soTienHoaDon" name="soTienHoaDon">
                        <span class="highlight"></span>
                        <span class="bar"></span>
                        <label for="soTienHoaDon">Số tiền hóa đơn</label>
                    </div>
                </div>
            </div>
            <button class="button" style="width: 130px;margin-top: 30px; margin-bottom: 20px">
                <svg class="svg-icon" fill="none" height="20" viewBox="0 0 20 20" width="20" xmlns="http://www.w3.org/2000/svg"><g stroke="#ff342b" stroke-linecap="round" stroke-width="1.5"><path d="m3.33337 10.8333c0 3.6819 2.98477 6.6667 6.66663 6.6667 3.682 0 6.6667-2.9848 6.6667-6.6667 0-3.68188-2.9847-6.66664-6.6667-6.66664-1.29938 0-2.51191.37174-3.5371 1.01468"></path><path d="m7.69867 1.58163-1.44987 3.28435c-.18587.42104.00478.91303.42582 1.0989l3.28438 1.44986"></path></g></svg>
                <span class="lable">Tìm kiếm</span>
            </button>
        </form>
    </div>



    <a href="/ChuongTrinhGiamGiaGiayTheThao/create" style="text-decoration: none">
        <button class="button" style="margin-bottom: 30px">
            <svg class="svg-icon" fill="none" height="20" viewBox="0 0 20 20" width="20" xmlns="http://www.w3.org/2000/svg"><g stroke="#ff342b" stroke-linecap="round" stroke-width="1.5"><path d="m3.33337 10.8333c0 3.6819 2.98477 6.6667 6.66663 6.6667 3.682 0 6.6667-2.9848 6.6667-6.6667 0-3.68188-2.9847-6.66664-6.6667-6.66664-1.29938 0-2.51191.37174-3.5371 1.01468"></path><path d="m7.69867 1.58163-1.44987 3.28435c-.18587.42104.00478.91303.42582 1.0989l3.28438 1.44986"></path></g></svg>
            <span class="lable">Create</span>
        </button>
    </a>
    <div class="thongBao">
        <%--           Tìm kiếm thành công --%>
        <label>${messageDone}</label>
        <%--            Không tìm thấy id có mã--%>
        <label>${messageFind}</label>
        <%--            Không tìm thầy sản phẩm--%>
        <label>${messageErFind}</label>
        <label>${message}</label>
        <label>${messageErFindSoLuong}</label>

    </div>
</div>
    <table style="border-collapse: collapse; width: 1299px">
        <thead>
        <tr>
            <th scope="col" style="color: black">#</th>
            <th scope="col" style="color: black">Tên Chương trình giảm giá</th>
            <th scope="col" style="color: black">Phần trăm giảm</th>
            <th scope="col" style="color: black">Ngày bắt đầu</th>
            <th scope="col" style="color: black">Ngày kết thúc</th>
            <th scope="col" style="color: black">Ghi chú</th>
            <th scope="col" style="color: black">Trạng thái</th>
            <th scope="col" style="color: black">Functions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="hd" items="${listPage}" varStatus="i">
            <tr>
                <th style="padding-top: 40px" scope="row">${i.index+1}</th>
                <td style="padding-top: 55px; text-align: center">${hd.tenChuongTrinhGiamGia}</td>
                <td style="padding-top: 55px; text-align: center">${hd.phanTramGiam}%</td>
                <td style="padding-top: 55px; text-align: center">${hd.ngayBatDau}</td>
                <td style="padding-top: 55px; text-align: center">${hd.ngayKetThuc}</td>
                <td style="padding-top: 55px; text-align: center">${hd.ghiChu}</td>
                <td style="padding-top: 55px; text-align: center">${hd.trangThai==1 ?"Chưa hết hạn" : "Hết hạn"}</td>
                <td>
                    <a class="col-sm-4" href="${pageContext.request.contextPath}/ChuongTrinhGiamGiaGiayTheThao/edit/${hd.id}"><button class="btn btn-primary">Edit</button></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <ul class="pagination" style="margin-left: 500px;margin-top: 30px">
        <c:forEach begin="1" end="${totalPage}" varStatus="status">
            <li class="page-item">
                <a href="${pageContext.request.contextPath}/ChuongTrinhGiamGiaGiayTheThao/list?pageNum=${status.index}" class="page-link">${status.index}</a>
            </li>
        </c:forEach>
    </ul>


<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>