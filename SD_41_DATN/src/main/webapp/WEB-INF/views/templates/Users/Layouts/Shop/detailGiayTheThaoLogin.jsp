<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Thông tin giầy thể thao</title>
    <link href="/css/KhachHang/TrangChuDetailGiayTheThao.css" rel="stylesheet">
    <script src="path/to/jquery.min.js"></script>
    <script src="path/to/magnific-popup.js"></script>
    <script src="path/to/your-custom-js-file.js"></script>
    <style>

        .image_Detail {

            width: 490px;
            height: 350px;
            margin-bottom: 10px;

        }

        .vertical-line {
            margin-right: 15px;
            margin-top: 23px;
            border-left: 1px solid gray;
            height: 60px;
        }

        .prev {

            margin-left: 0px;
            color: #CCCCCC;
            height: 40px;
            margin-top: 180px;
            border-radius: 10px 10px 5px;

        }

        .next {

            margin-left: 475px;
            color: #CCCCCC;
            height: 40px;
            margin-top: 180px;
            border-radius: 5px 10px 10px;

        }

        .prev,
        .next {
            position: absolute;
            top: 50%; /* Center the buttons vertically */
            transform: translateY(-50%); /* Center the buttons vertically */
            cursor: pointer;
        }

        .prev {
            left: 0;
        }

        /*    Css cho input*/

        .custom-radio {
            display: none; /* Ẩn input radio mặc định */
            margin-top: 10px;
        }

        .custom-radio + label {
            text-align: center;
            display: inline-block;
            width: 60px; /* Độ rộng của hình chữ nhật */
            height: 35px; /* Chiều cao của hình chữ nhật */
            background-color: white; /* Màu nền mặc định */
            border: 1px solid gray; /* Viền xung quanh hình chữ nhật */
            margin-right: 5px; /* Khoảng cách giữa các hình chữ nhật */
            cursor: pointer;
            padding: 5px;
            position: relative; /* Cần thêm thuộc tính này để có thể xử lý ::before pseudo-element */
        }

        .custom-radio:checked + label {

            border: 2px solid yellowgreen;

        }

        .custom-radio:checked + label::before {

            content: "";
            position: absolute;
            top: 0;
            right: 0;
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 13px 13px 0 0; /* Kích thước và hình dạng của gạch chéo */
            border-color: yellowgreen transparent transparent transparent; /* Màu gạch chéo */
            transform: rotate(100deg); /* Xoay gạch chéo */
            transform-origin: 57.9%; /* Điểm xoay ở phía bên phải */

        }


    </style>
</head>
<body>

<%--   Header--%>
<%@ include file="../../Layouts/Index/_Header_No_Register.jsp" %>

<div class="container" style="margin-top: 100px">

    <div class="row" style="margin-top: 30px">
        <div class="col-6">
            <img class="image_Detail" width="250px"
                 src="${pageContext.request.contextPath}/upload/${giayTheThao.image.get(0).link}">
            <div style="padding-top:5px; width: 490px; overflow: hidden;">
                <div id="imageContainer" style="width: 100%; display: flex; overflow: hidden;">
                    <c:forEach items="${listImage}" var="image">
                        <img class="images" src="/upload/${image.link}"
                             style="width: 160px; height: 150px; margin-right: 10px;"/>
                    </c:forEach>
                </div>
                <div style="margin-top:10px"></div>
                <button class="prev" onclick="prevImage()">&#9665;</button>
                <button class="next" onclick="nextImage()"> &#9655;</button>
            </div>
        </div>
        <div class="col-6">
            <div class="text_Detail" style="margin-top: 30px">
                <h5>
                    <span style="margin-left: 50px;color: black;font-size: 30px">
                        ${giayTheThao.tenGiayTheThao}
                    </span>
                </h5>

                <p style="color: black;margin-top: 30px;font-size: 18px">
                    Giá bán:  <span style="color: red">
                                <fmt:formatNumber  type="" value="${giayTheThao.giaBan}" pattern="#,##0.###" />
                              </span>
                    VNĐ

<%--                    Todo sủa code ở đây--%>

<%--                    Đang gặp vấn đề là sản phẩm không áp dụng cũng được giảm giá 10%--%>
                    <c:choose>
<%--                        Trường hợp này là có sale--%>
<%--                    Nếu mà sale khác 1--%>
                        <c:when test="${sale != 1}">
                            <div class="gia">
                                <p>
                                    <del>
<%--                                            Đây là giá bán ban đầu khi chưa được giảm--%>
                                            <fmt:formatNumber  type="" value="${giayTheThao.giaBan}" pattern="#,##0.###" /> VNĐ

                                    </del>
<%--                                            Đây là giá bán khi đã được giảm    --%>
                                    <strong>

                                            <fmt:formatNumber  type="" value="${giayTheThao.giaBan * (100 - sale )/100}" pattern="#,##0.###" /> VNĐ

                                    </strong>
                                </p>
                            </div>
                        </c:when>
                        <c:when test="${sale == 1}">
                            <div class="gia">
                                <p>
                                    <strong>
                                        <fmt:formatNumber  type="" value="${giayTheThao.giaBan}" pattern="#,##0.###" /> VNĐ
                                    </strong>
                                </p>
                            </div>
                        </c:when>
                    </c:choose>

                </p>

                <h6 style="color: gray">
                    Đã bán
                </h6>

                <h6 style="color: gray">
                    Vận chuyển : ${khachHangView.diaChi}
                </h6>

                <form method="post" action="/GiayTheThao/NguoiDung/AddToCart/${giayTheThao.id}">
                    <div class="muaHang">
                        <div>
                            <hr style="margin-top: 30px; margin-bottom: 30px">
                        </div>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-3">
                                <h5 style="margin-top: 10px">Size <span style="color: red">*</span></h5>
                            </div>
                            <div class="col-9">
                                        <span style="color: black; font-size: 17px;">
                                            <c:forEach items="${uniqueSizes}" var="size">
                                                <input class="custom-radio" type="radio" name="size" value="${size.id}" id="size-${size.id}">
                                                <label style="margin-top: 1px" for="size-${size.id}">
                                                    <span>
                                                            ${size.size}
                                                    </span>
                                                </label>
                                            </c:forEach>
                                        </span>
                            </div>
                        </div>

                    </div>
                    <div class="row" style="margin-top: 30px">
                        <div class="col-3">
                            <h5 style="margin-top: 10px">Màu sắc <span style="color: red">*</span></h5>
                        </div>
                        <div class="col-9">
                                <span style="color: black; font-size: 17px">
                                    <c:forEach items="${uniqueMauSac}" var="mauSac">
                                        <input class="custom-radio" style="margin-left: 20px" type="radio" name="mauSac" value="${mauSac.id}" id="mauSac-${mauSac.id}">
                                        <label style="margin-top: 1px" for="mauSac-${mauSac.id}">
                                             <span>
                                                     ${mauSac.tenMauSac}
                                             </span>
                                        </label>
                                    </c:forEach>
                                </span>
                        </div>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <div class="col-3">
                            <h5 style="margin-top: 7px">Số lượng <span style="color: red">*</span></h5>
                        </div>
                        <div class="col-9">
                            <input name="soLuong" type="number" style="width: 100px">
                            <span style="margin-left: 30px; font-size: 17px; color: red">${totalSoLuong} sản phẩm có sẵn trong kho</span>
                            <div class="" id="mauSacAndSize">

                            </div>
                            <div style="margin-bottom: 20px"></div>
                            <div style="color: red" id="soLuongTonStr"></div>
                            <input style="color: red" type="hidden" id="soLuongTon">
                        </div>

                    </div>
                    <div class="er" style="margin-top: 20px">
                        <%--                            Check số lượng--%>
                        <h6 style="color: red">${erCheckSoLuongAddToCart}</h6>
                        <h6 style="color: red">${erCheckNumberSoLuongAddToCart}</h6>
                        <h6 style="color: red">${erSoLuongAddToCartMin}</h6>
                        <h6 style="color: red">${erCheckAddToCart}</h6>
                        <h6 style="color: red">${erCheckSizeAndMuaSacNotFind}</h6>
                        <h6 style="color: red">${erSoLuongAddToCartMax}</h6>
                        <h6 style="color: red">${erFailNotLoginKhachHang}</h6>
                    </div>
                    <input type="hidden" name="gia" value="${giayTheThao.giaBan * (100 - sale)/100}">
                    <input type="hidden" id="idGiayTheThao" name="idGiayTheThao" value="${giayTheThao.id}">
                    <input type="hidden" name="idKhachHang" value="${idKhachHang}">
                    <input type="hidden" name="idGiayTheThaoChiTiet" value="${idGiayTheThaoChiTiet}">
                    <div class="button_MuaHang" style="margin-top: 80px; margin-left: 90px">
                        <a style="text-decoration: none">
                            <button style="margin-bottom: 10px; margin-right: 40px" type="submit" class="btn btn-primary">
                                <span style="color: black; font-size: 22px; font-weight: bold">
                                    🛒
                                </span>
                                Thêm vào giỏ hàng
                            </button>
                        </a>
                    </div>
                </form>
            </div>
        </div>



    </div>
</div>

<div style="margin-top: 30px"></div>

<script>
    var mauSacAndSizeDiv = document.getElementById("mauSacAndSize");
    mauSacAndSizeDiv.style.display = "none";

    function updateProductAvailability() {
        var selectedColor = document.querySelector("input[name='mauSac']:checked");
        var selectedSize = document.querySelector("input[name='size']:checked");

        if (selectedColor && selectedSize) {
            var colorId = selectedColor.value;
            var sizeId = selectedSize.value;

            // Kiểm tra sự tồn tại của phần tử 'idGiayTheThao'
            var idGiayTheThaoElement = document.getElementById("idGiayTheThao");

            if (idGiayTheThaoElement) {
                var idGiayTheThao = idGiayTheThaoElement.value;

                console.log("Id của giày thể thao", idGiayTheThao);
                console.log("Id size", sizeId);
                console.log("Id màu sắc", colorId);

                var xhr = new XMLHttpRequest();
                xhr.open("GET", "/find/" + idGiayTheThao + "/" + colorId + "/" + sizeId, true);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var response = JSON.parse(xhr.responseText);
                        if (response > 0) {

                            document.getElementById("soLuongTonStr").textContent = "Số lượng sản phẩm còn lại: " + response;
                            document.getElementById("soLuongTon").value = response;

                        } else {
                            document.getElementById("soLuongTonStr").textContent = "Đã hết sản phẩm";
                            document.getElementById("soLuongTon").value = 0;
                        }
                    } else {
                        document.getElementById("soLuongTonStr").textContent = "Đã hết sản phẩm";
                        document.getElementById("soLuongTon").value = 0;
                    }
                };

                xhr.send();

                mauSacAndSizeDiv.style.display = "block";
            } else {
                console.error("Element with ID 'idGiayTheThao' not found in the DOM");
            }
        }
    }

    var colorInputs = document.querySelectorAll("input[name='mauSac']");
    var sizeInputs = document.querySelectorAll("input[name='size']");

    colorInputs.forEach(function (input) {
        input.addEventListener("change", updateProductAvailability);
    });

    sizeInputs.forEach(function (input) {
        input.addEventListener("change", updateProductAvailability);
    });

</script>



<%@ include file="../../Layouts/Index/_Session4.jsp" %>
<%--   Footer--%>
<%@ include file="../../Layouts/Index/_Footer.jsp" %>

</body>

</html>