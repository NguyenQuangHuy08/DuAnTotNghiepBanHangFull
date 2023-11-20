<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Hóa đơn</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.6/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.6/dist/sweetalert2.all.min.js"></script>

    <style>
        td, th {
            padding: 10px; /* Khoảng cách nội dung bên trong ô */
            color: black; /* Màu chữ */
            text-align: center; /* Căn giữa nội dung trong ô */
        }

        /*    Cho hóa đơn*/

        .form-group {
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        .payment-methods {
            padding: 10px 0;
        }

        #ctgg-details,
        #banking-image,
        #cash-details {
            display: none;
        }

        button[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

    </style>

</head>
<body>
<%--Header cho giỏ hàng --%>
<%@ include file="../../Layouts/Index/_Header_No_Register.jsp" %>


<div class="container" style="margin-top: 150px">


    <div class="row"
         style="border: 1px solid whitesmoke; width: 600px;margin-left: 250px;border-radius: 10px 10px 10px">
        <div class="col-12">
            <h2 style="text-align: center;margin-top: 10px;font-family:" Poppins", sans-serif">Hóa đơn</h3>
        </div>
        <div class="col-12" style="margin-top: 10px">
            <div class="row">
                <div class="col-3">
                    <h4>Mã hóa đơn:</h4>
                </div>
                <div class="col-9">
                    <p style="color: black;font-size: 15px;color: black">${maHoaDon}</p>
                </div>
            </div>
        </div>
        <div class="col-12">
            <h4>Thông tin sản phẩm</h4>
            <c:forEach items="${hoaDonChiTietList}" var="list">
                <p>
                    <span style="display: inline-block;color: black;font-size: 16px">Tên giầy thể thao: ${list.giayTheThaoChiTiet.giayTheThao.tenGiayTheThao}</span>
                    <span style="display: inline-block;padding-left: 20px;color: black;font-size: 16px">Size: ${list.giayTheThaoChiTiet.size.size}</span>
                    <span style="display: inline-block;padding-left: 20px;color: black;font-size: 16px">Màu sắc: ${list.giayTheThaoChiTiet.mauSac.tenMauSac}</span>
                    <span style="display: inline-block;padding-left: 20px">
                            <img src="/upload/${list.giayTheThaoChiTiet.giayTheThao.image.get(0).link}" width="110px"
                                 style="border-radius: 10px 10px 10px">
                        </span>
                </p>
            </c:forEach>
        </div>
        <div class="col-12">

            <div class="row">
                <div class="col-3">
                    <h4>Giá tiền :</h4>

                </div>
                <div class="col-6">
                    <%--                <label>${hoaDon.thanhTien}</label>--%>
                    <%--                <input type="text" id="price" value="${hoaDon.thanhTien}" readonly>--%>
                    <p style="font-size: 18px;color: red">
                        <fmt:formatNumber type="" value="${hoaDon.thanhTien}" pattern="#,##0.###"/> VNĐ
                    </p>
                </div>
            </div>
        </div>

        <form action="/nguoiDung/hoaDon/ThanhToan/${hoaDonId}" method="post">
            <div class="form-group">
                <h4 style="color: black;margin-left: 10px">Hình thức thanh toán:</h4>
                <div class="payment-methods" style="margin-left: 10px;color: black">
                    <label for="banking">
                        <input type="radio" name="payment-method" value="banking" id="banking" onchange="togglePaymentDetails()">
                        <span style="margin-left: 5px">
                                                    Thanh toán banking
                        </span>
                    </label>
                    <label for="cash" style="margin-left: 20px;color: black">
                        <input type="radio" name="payment-method" value="cash" id="cash" onchange="togglePaymentDetails()">
                        <span style="margin-left: 5px">
                                                    Thanh toán khi nhận hàng
                        </span>
                    </label>
                </div>

                <div id="banking-image">
                    <%--                    <img src="https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=${qrCodeText}" alt="QR Code">--%>
                    <h5>Hình ảnh của mã QR</h5>
                </div>

                <div id="cash-details" style="margin-left: 10px">
                    <input type="radio" name="diaChiChon" value="diaChiCu" onclick="toggleView('view_dia_chi_cu')">
                    <span style="margin-left: 5px;color: black">
                        Địa chỉ cũ
                    </span>
                    <input style="margin-left: 83px" type="radio" name="diaChiChon" value="diaChiMoi" onclick="toggleView('view_dia_chi_moi')">
                    <span style="margin-left: 5px;color: black">
                         Địa chỉ mới
                    </span>
                    <br>
                    <div id="view_dia_chi_cu" style="display:none;margin-top: 10px">
                        <p style="color: black">Số điện thoại: <input type="text" name="soDienThoai" value="${soDienThoai}" disabled></p>
                        <p style="color: black">Địa chỉ nhận hàng: <input type="text" name="diaChi" value="${diaChi}" disabled></p>
                        <input type="hidden" name="hiddenSoDienThoai" value="${soDienThoai}">
                        <input type="hidden" name="hiddenDiaChi" value="${diaChi}">
                        <input type="hidden" value="${hoaDon.thanhTien}" readonly>
                    </div>
                    <div id="view_dia_chi_moi" style="display:none">
                        <p style="color: black">Số điện thoại: <input type="text" name="soDienThoai"></p>
                        <p style="color: black">Địa chỉ nhận hàng: <input type="text" name="diaChi"></p>
                        <input type="hidden"  value="${hoaDon.thanhTien}" readonly>
                    </div>
                </div>
            </div>
            <div class="erThongBaoHoaDon">
                <p style="color: red;margin-left: 10px;font-size: 15px">${messageNoChon}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${messageNoChonNull}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erViewDiaChiChon}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erLogSoDienThoaiChon}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erLogDiaChiChon}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erLogDiaChiChonNo}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erLogSoDienThoai0}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erLogSoDienThoaiNumber}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erLogSoDienThoaiChonChu}</p>
                <p style="color: red;margin-left: 10px;font-size: 15px">${erLogDiaChiChonNo}</p>
            </div>
            <div class="row">
                <div class="col-4" style="margin-top: 20px;margin-bottom: 20px">
                    <button type="submit">Đặt hàng</button>
                </div>
                <div class="col-6" style="margin-top: 20px;margin-bottom: 20px" >
                    <a href="/TrangChu/listGiayTheThao">
                        <p style="padding-top: 10px;height: 45px;width: 100px;text-align: center;border-radius: 5px 5px 5px;background-color: #4CAF50;color: white">
                            Hủy đặt hàng
                        </p>
                    </a>
                </div>
            </div>
        </form>


    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


<script>

    //Code js cho hóa đơn

    var gia = parseFloat(document.getElementById("price").value);
    var gia1 = gia;
    document.getElementById("price1").value = gia1.toFixed(0);

    function togglePaymentDetails() {
        var bankingImage = document.getElementById("banking-image");
        var cashDetails = document.getElementById("cash-details");
        var bankingRadio = document.getElementById("banking");
        var cashRadio = document.getElementById("cash");

        if (bankingRadio.checked) {
            bankingImage.style.display = "block";
            cashDetails.style.display = "none";
        } else if (cashRadio.checked) {
            bankingImage.style.display = "none";
            cashDetails.style.display = "block";
        }
    }

    function togglePaymentDetails1() {

        var ctggDetails = document.getElementById("ctgg-details");
        var ctggRadio = document.getElementById("ctgg");

        if (ctggRadio.checked) {
            ctggDetails.style.display = "block";
        }

    }

    function updatePrice(radioButton) {

        var priceInput = document.getElementById("price");
        var priceInput1 = document.getElementById("price1");
        var currentPrice = parseFloat(${hoaDon.thanhTien});
        var selectedValue = parseFloat(radioButton.value);
        var newPrice = currentPrice - (currentPrice * selectedValue / 100);
        priceInput.value = newPrice.toFixed(2);
        priceInput1.value = newPrice.toFixed(2);

    }

    function toggleView(elementId) {

        var element = document.getElementById(elementId);
        element.style.display = (element.style.display === "block") ? "none" : "block";

    }


</script>

<%@ include file="../../Layouts/Index/_Session1.jsp" %>
</body>
</html>


