<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>MultiShop - Online Shop Website Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <!-- Favicon -->
    <link href="../../../resources/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="../../../resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../../../resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../../resources/css/style.css" rel="stylesheet">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
</head>

<body>

<!-- Cart Start -->
<div class="container-fluid">

    <div class="row px-xl-5">
        <div class="col-lg-10">
            <div class="contact-form bg-light p-30">
                <div id="success"></div>
                <form method="post" action="/user/contact-done/${kh.ma}">

                    <div class="control-group">
                        <label for="province">Tỉnh:</label>
                        <select id="province" name="quocGia" class="form-control" onchange="loadDistricts(); checkSelection()">
                            <option value="">${kh.quoc_gia}</option>
                        </select>
                        <input type="hidden" id="tinh1" name="quocGia1">
                    </div>
                    <div class="control-group">
                        <label for="district">Huyện:</label>
                        <select id="district" name="thanhPho" class="form-control" onchange="loadWards(); checkSelection()"
                                disabled>
                            <option value="">${kh.thanh_pho}</option>
                        </select>
                        <input type="hidden" id="huyen1" name="thanhPho1">
                    </div>
                    <div class="control-group">
                        <label for="ward">Xã:</label>
                        <select id="ward" name="diaChi" class="form-control" onchange="checkSelection()" disabled>
                            <option value="">${kh.dia_chi}</option>
                        </select>
                        <input type="hidden" id="xa1" name="diaChi1">
                    </div>

                    <button class="btn btn-primary py-2 px-4" type="submit" onclick="addProduct()">Cập nhật thông tin
                    </button>
                </form>
            </div>
        </div>
    </div>

</div>
<!-- Cart End -->


<!-- Footer Start -->
<!-- Footer End -->


<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<script src="../../../resources/lib/easing/easing.min.js"></script>
<script src="../../../resources/lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Contact Javascript File -->
<script src="../../../resources/mail/jqBootstrapValidation.min.js"></script>
<script src="../../../resources/mail/contact.js"></script>

<!-- Template Javascript -->
<script src="../../../resources/js/main.js"></script>
<script>

    var priceInput = document.getElementById("price");
    var priceInput1 = document.getElementById("price1");
    var currentPrice = parseFloat(${tongTien});
    var quocGiaValue = '${kh.quoc_gia}';
    async function loadProvinces() {
        const response = await fetch(`http://localhost:8080/public/provinces`);
        const data = await response.json();

        const provinceSelect = document.getElementById("province");
        if (quocGiaValue===null||quocGiaValue===""){
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
<%--<script>--%>
<%--    function addProduct() {--%>

<%--        var ten = document.getElementsByName('ten')[0].value;--%>
<%--        var ngaySinh = document.getElementsByName('ngaySinh')[0].value;--%>
<%--        var diaChi = document.getElementsByName('diaChi')[0].value;--%>
<%--        var thanhPho = document.getElementsByName('thanhPho')[0].value;--%>
<%--        var quocGia = document.getElementsByName('quocGia')[0].value;--%>
<%--        var soDienThoai = document.getElementsByName('soDienThoai')[0].value;--%>
<%--        var matKhau = document.getElementsByName('matKhau')[0].value;--%>

<%--        var errorTen = document.getElementById('errorTen');--%>
<%--        var errorNgayChon = document.getElementById('errorNgayChon');--%>
<%--        var errorDiaChi = document.getElementById('errorDiaChi');--%>
<%--        var errorThanhPho = document.getElementById('errorThanhPho');--%>
<%--        var errorQuocGia = document.getElementById('errorQuocGia');--%>
<%--        var errorSoDienThoai = document.getElementById('errorSoDienThoai');--%>
<%--        var errorMatKhau = document.getElementById('errorMatKhau');--%>

<%--        var hasError = false;--%>

<%--        if (ten.trim() === '') {--%>
<%--            errorTen.innerText = 'Vui lòng nhập tên.';--%>
<%--            hasError = true;--%>
<%--        } else {--%>
<%--            errorTen.innerText = '';--%>
<%--        }--%>

<%--        if (ngaySinh.trim() === '') {--%>
<%--            errorNgayChon.innerText = 'Vui lòng nhập ngày sinh';--%>
<%--            hasError = true;--%>
<%--        } else {--%>
<%--            errorNgayChon.innerText = '';--%>
<%--        }--%>

<%--        if (diaChi.trim() === '') {--%>
<%--            errorDiaChi.innerText = 'Vui lòng nhập địa chỉ.';--%>
<%--            hasError = true;--%>
<%--        } else {--%>
<%--            errorTen.innerText = '';--%>
<%--        }--%>

<%--        if (thanhPho.trim() === '') {--%>
<%--            errorThanhPho.innerText = 'Vui lòng nhập thành phố.';--%>
<%--            hasError = true;--%>
<%--        } else {--%>
<%--            errorTen.innerText = '';--%>
<%--        }--%>

<%--        if (quocGia.trim() === '') {--%>
<%--            errorQuocGia.innerText = 'Vui lòng nhập quốc gia.';--%>
<%--            hasError = true;--%>
<%--        } else {--%>
<%--            errorTen.innerText = '';--%>
<%--        }--%>

<%--        if (soDienThoai.trim() === '') {--%>
<%--            errorSoDienThoai.innerText = 'Vui lòng nhập số điện thoại.';--%>
<%--            hasError = true;--%>
<%--        } else {--%>
<%--            errorTen.innerText = '';--%>
<%--        }--%>

<%--        if (matKhau.trim() === '') {--%>
<%--            errorMatKhau.innerText = 'Vui lòng nhập mật khẩu.';--%>
<%--            hasError = true;--%>
<%--        } else {--%>
<%--            errorTen.innerText = '';--%>
<%--        }--%>

<%--        if (hasError) {--%>
<%--            event.preventDefault();--%>
<%--        }--%>
<%--    }--%>
<%--</script>--%>


</body>

</html>