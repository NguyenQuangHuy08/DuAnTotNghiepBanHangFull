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
            width: 79px; /* Độ rộng của hình chữ nhật */
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
                                <span style="margin-left: 30px; font-size: 17px; color: gray">${totalSoLuong} sản phẩm có sẵn</span>
                            </div>
                        </div>
                        <div class="er" style="margin-top: 20px">
<%--                            Check số lượng--%>
                            <label id="soLuongLabel"></label>

                            <h6 style="color: red">${erCheckSoLuongAddToCart}</h6>
                            <h6 style="color: red">${erCheckNumberSoLuongAddToCart}</h6>
                            <h6 style="color: red">${erSoLuongAddToCartMin}</h6>
                            <h6 style="color: red">${erCheckAddToCart}</h6>
                            <h6 style="color: red">${erCheckSizeAndMuaSacNotFind}</h6>
                            <h6 style="color: red">${erSoLuongAddToCartMax}</h6>
                            <h6 style="color: red">${erFailNotLoginKhachHang}</h6>
                        </div>
                    <input type="hidden" name="idGiayTheThao" value="${giayTheThao.id}">
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

<div style="margin-top: 30px"></div>

<%--js next hình ảnh--%>
<script>

    const imageContainer = document.getElementById("imageContainer");
    const images = document.getElementsByClassName("images");
    const imageDetail = document.querySelector(".image_Detail");
    let currentImageIndex = 0;

    updateImageDetail(images[currentImageIndex]);

    for (let i = 0; i < images.length; i++) {
        images[i].addEventListener("click", function () {
            updateImageDetail(images[i]);
        });
    }

    function updateImageDetail(image) {
        imageDetail.src = image.src;
        currentImageIndex = Array.from(images).indexOf(image);
    }

    function nextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.length;
        updateImageDetail(images[currentImageIndex]);
        scrollImages();
    }

    function prevImage() {
        currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
        updateImageDetail(images[currentImageIndex]);
        scrollImages();
    }

    function scrollImages() {
        const scrollAmount = currentImageIndex * (160 + 10);
        imageContainer.scrollLeft = scrollAmount;
    }

</script>
<%--Bỏ các size và màu sắc bị trùng lặp--%>
<script>
    function removeDuplicateSizes() {
        var sizeInputs = document.getElementsByName("size");
        var seenSizes = {};
        for (var i = 0; i < sizeInputs.length; i++) {
            var sizeInput = sizeInputs[i];
            var size = sizeInput.value;

            if (seenSizes[size]) {
                sizeInput.parentNode.removeChild(sizeInput);
            } else {
                seenSizes[size] = true;
            }
        }
    }

    // Gọi hàm loại bỏ trùng lặp khi trang đã tải xong
    window.onload = removeDuplicateSizes;
</script>
<%--Change or --%>
<%--Mã js   Ajax cho số lượng size và màu sắc--%>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <script>
        $(document).ready(function () {
            // Sự kiện khi chọn kích thước
            $('input[name=size]').click(function () {
                updateQuantityLabel();
            });

            // Sự kiện khi chọn màu sắc
            $('input[name=mauSac]').click(function () {
                updateQuantityLabel();
            });

            function updateQuantityLabel() {
                var selectedSize = $('input[name=size]:checked').val();
                var selectedMauSac = $('input[name=mauSac]:checked').val();
                var quantityLabel = $('#soLuongLabel');

                // Kiểm tra nếu đã chọn cả kích thước và màu sắc
                if (selectedSize && selectedMauSac) {
                    // Gửi yêu cầu Ajax để lấy số lượng từ API
                    $.ajax({
                        url: '/GiayTheThao/find/' + giayTheThaoId + '/' + mauSacId + '/' + sizeId + '?actions=' + actions,
                        type: 'GET',
                        success: function (response) {
                            // Cập nhật số lượng trong thẻ label
                            quantityLabel.text('Số lượng: ' + response);
                        },
                        error: function () {
                            console.log('Lỗi khi lấy số lượng từ API');
                        }
                    });
                } else {
                    // Nếu chưa chọn đủ thông tin, hiển thị label rỗng
                    quantityLabel.text('');
                }
            }
        });

        <%--var giayTheThaoId = "${giayTheThao.id}";--%>
        console.log('giayTheThaoId:', giayTheThaoId);

    </script>



<%@ include file="../../Layouts/Index/_Session4.jsp" %>
<%--   Footer--%>
<%@ include file="../../Layouts/Index/_Footer.jsp" %>

</body>
</html>