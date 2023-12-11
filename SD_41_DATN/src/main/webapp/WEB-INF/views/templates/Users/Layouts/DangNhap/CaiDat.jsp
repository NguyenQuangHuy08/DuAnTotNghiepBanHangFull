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
    <title>Thông tin cá nhân</title>
    <link href="/css/KhachHang/TrangChuDetailGiayTheThao.css" rel="stylesheet">
    <script src="path/to/jquery.min.js"></script>
    <script src="path/to/magnific-popup.js"></script>
    <script src="path/to/your-custom-js-file.js"></script>

    <style>
        .custom-file-upload {
            border: 2px dashed #cacaca;
            border-radius: 50% 50% 50%;
            background-color: rgba(255, 255, 255, 1);
            padding: 1.5rem;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 170px;
            width: 190px;
        }

        .custom-file-upload .icon {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .custom-file-upload .icon img {
            max-height: 100%;
            max-width: 100%;
            border-radius: 50%;
        }

        .custom-file-upload .text {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .custom-file-upload .text span {
            font-weight: 400;
            color: rgba(75, 85, 99, 1);
        }

        .custom-file-upload input {
            display: none;
        }

        #preview-image {
            max-height: 100%;
            max-width: 100%;
        }
    </style>

</head>
<body>

<%--   Header--%>
<%@ include file="../../Layouts/Index/_Header.jsp" %>


<div class="container" style="margin-top: 120px">

    <div class="row">
        <div class="col-3" style="border:  1px solid red; height: 400px">
            <label class="custom-file-upload" for="file">
                <div class="icon">
                    <img id="preview-image" src="image.jpg" alt="Placeholder Image">
                </div>
                <div class="text">
                    <span></span>
                </div>
                <input type="file" id="file" onchange="previewImage()">
            </label>
        </div>
        <div class="col-7" style="border: 1px solid red; height: 400px">

            <h4>Thông tin đăng nhập</h4>

            <h5>Email: ${khachHang.email}</h5>
            <h5>Tên: ${khachHang.tenKhachHang}</h5>
            <h5>Giới tính:${khachHang.gioiTinh}</h5>
            <h5>Số điện thoại${khachHang.soDienThoai}</h5>
            <h5>Địa chỉ: ${khachHang.diaChi}</h5>

            <form action="${pageContext.request.contextPath}/TrangChu/ThongTinCaNhan/Luu" method="post">


                <div class="row">
                    <div class="col-6">
                        <label for="email" style="color: black">
                                Email
                        </label>
                        <input class="form-control" type="text" id="email" name="email" value="${khachHang.email}" />
                    </div>
                    <div class="col-6">
                        <label for="matKhau" style="color: black">
                            Mật khẩu
                        </label>
                        <input class="form-control" type="password" id="matKhau" name="matKhau" value="${khachHang.matKhau}" />
                    </div>
                    <div class="col-6">
                        <label for="tenKhachHang" style="color: black">
                            Tên khách hàng:
                        </label>
                        <input class="form-control" type="text" id="tenKhachHang" name="tenKhachHang" value="${khachHang.tenKhachHang}" />
                    </div>
                    <div class="col-6">
                        <label for="gioiTinh">Giới tính:</label>
                        <input  class="form-control" type="text" id="gioiTinh" name="gioiTinh" value="${khachHang.gioiTinh}" />
                    </div>
                    <div class="col-6">
                        <label for="soDienThoai">Số điện thoại:</label>
                        <input class="form-control" type="text" id="soDienThoai" name="soDienThoai" value="${khachHang.soDienThoai}" />
                    </div>
                    <div class="col-6">
                        <label for="diaChi">Địa chỉ cụ thể:</label>
                        <input class="form-control" type="text" id="diaChi" name="diaChi" value="${khachHang.diaChi}" />
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Lưu thông tin</button>
            </form>

        </div>
    </div>


</div>

<%--Mã js cho chọn ảnh--%>

<script>
    function previewImage() {
        var preview = document.getElementById('preview-image');
        var fileInput = document.getElementById('file');

        var file = fileInput.files[0];
        var reader = new FileReader();

        reader.onloadend = function () {
            preview.src = reader.result;
        }

        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.src = "image.jpg"; // Placeholder image
        }
    }
</script>

<%--Địa chỉ cho giao hàng nhanh --%>

<script>

    var priceInput = document.getElementById("price");
    var priceInput1 = document.getElementById("price1");
    var currentPrice = parseFloat(${tongTien});
    var quocGiaValue = '${thanhPho}';

    async function loadProvinces() {
        const response = await fetch(`http://localhost:8080/public/provinces`);
        const data = await response.json();

        const provinceSelect = document.getElementById("province");
        if (quocGiaValue === null || quocGiaValue === "") {
            provinceSelect.innerHTML = '<option value="">Chọn tỉnh</option>';
        }


        for (const province of data.data) { // Thay đổi dòng này để lấy dữ liệu từ data.data
            const option = document.createElement("option");
            option.value = province.ProvinceID;
            option.text = province.ProvinceName;
            provinceSelect.appendChild(option);
            console.log(province.ProvinceID, province.ProvinceName);
        }
    }


    async function loadDistricts() {
        const provinceId = document.getElementById("province").value;

        if (!provinceId) {
            return;
        }

        const response = await fetch('http://localhost:8080/public/districts?province_id=' + provinceId);
        const data = await response.json();

        const districtSelect = document.getElementById("district");
        districtSelect.innerHTML = '<option value="">Chọn huyện</option>';

        for (const district of data.data) {
            const option = document.createElement("option");
            option.value = district.DistrictID;
            option.text = district.DistrictName;
            districtSelect.appendChild(option);
        }

        districtSelect.disabled = false;
        document.getElementById("ward").disabled = true;
        document.getElementById("message").innerText = "";
    }

    async function loadWards() {
        const districtId = document.getElementById("district").value;

        if (!districtId) {
            return;
        }

        const response = await fetch('http://localhost:8080/public/wards?district_id=' + districtId);
        const data = await response.json();

        const wardSelect = document.getElementById("ward");
        wardSelect.innerHTML = '<option value="">Chọn xã</option>';

        for (const ward of data.data) {
            const option = document.createElement("option");
            option.value = ward.WardCode;
            option.text = ward.WardName;
            wardSelect.appendChild(option);
        }

        wardSelect.disabled = false;
        document.getElementById("message").innerText = "";
    }

    function checkSelection() {
        const provinceSelect = document.getElementById("province");
        const districtSelect = document.getElementById("district");
        const wardSelect = document.getElementById("ward");

        const provinceName = provinceSelect.options[provinceSelect.selectedIndex].text;
        const districtName = districtSelect.options[districtSelect.selectedIndex].text;
        const wardName = wardSelect.options[wardSelect.selectedIndex].text;

        if (provinceName) {
            document.getElementById("tinh1").value = provinceName;
        }

        if (districtName) {
            document.getElementById("huyen1").value = districtName;
        }

        if (wardName) {
            document.getElementById("xa1").value = wardName;
        }

        if (provinceName && districtName && wardName) {
            calculateShipping();
        } else {
            document.getElementById("message").innerText = "";
            // Khóa nút tính phí nếu không đủ điều kiện
            document.getElementById("shippingCost").innerText = "";
        }
    }

    async function calculateShipping() {
        const districtSelect = document.getElementById("district");
        const wardSelect = document.getElementById("ward");
        const toDistrictId = districtSelect.value;
        const toWardCode = wardSelect.value;

        const transportationFeeDTO = {
            toDistrictId: toDistrictId,
            toWardCode: toWardCode,
            quantity: 1,
            insuranceValue: 0
        };

        try {
            const response = await fetch('http://localhost:8080/public/transportationFee', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(transportationFeeDTO)
            });

            const data = await response.json();

            if (data && data.code === 200) {
                const shippingCost = data.data.total;

                var tongSoTien = currentPrice + shippingCost;
                priceInput1.value = tongSoTien;

                var gia1Formatted3 = shippingCost.toLocaleString('vi-VN', {
                    minimumFractionDigits: 0,
                    maximumFractionDigits: 3
                });

                var gia1WithCurrency3 = gia1Formatted3 + " VNĐ";

                document.getElementById("ship").value = gia1WithCurrency3;

                var gia1Formatted2 = tongSoTien.toLocaleString('vi-VN', {
                    minimumFractionDigits: 0,
                    maximumFractionDigits: 3
                });

                var gia1WithCurrency2 = gia1Formatted2 + " VNĐ";

                priceInput.value = gia1WithCurrency2;

            } else {
                document.getElementById("shippingCost").innerText = "Không thể tính phí vận chuyển.";
            }
        } catch (error) {
            console.error('Error:', error);
            document.getElementById("shippingCost").innerText = "Lỗi khi tính phí vận chuyển. Chi tiết lỗi: " + error.message;
        }
    }

    loadProvinces();
    document.getElementById("province").addEventListener("change", loadDistricts);
    document.getElementById("district").addEventListener("change", loadWards);
    document.getElementById("ward").addEventListener("change", checkSelection);

</script>

<%----%>
<script src="/js/main.js"></script>

</body>
</html>

