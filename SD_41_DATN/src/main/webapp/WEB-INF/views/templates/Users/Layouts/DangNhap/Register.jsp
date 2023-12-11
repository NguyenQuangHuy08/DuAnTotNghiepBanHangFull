<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Đăng kí tài khoản khách hàng</title>
    <!--===============================================================================================-->
    <link rel="icon" type="/image/png" href="/images/icons/favicon.jpg"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="/vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="/vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="/vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="/vendor/select2/select2.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="/css/util.css">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <!--===============================================================================================-->

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <style>

        /* Ẩn ô input mặc định */
        #fileInput {
            display: none;
        }

        /* Tùy chỉnh nút chọn tệp ảnh */
        .custom-file-upload {
            height: 200px;
            /*width: 300px;*/
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            gap: 20px;
            cursor: pointer;
            border: 2px dashed #cacaca;
            background-color: rgba(255, 255, 255, 1);
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0px 48px 35px -48px rgba(0, 0, 0, 0.1);
            position: relative;
        }

        .custom-file-upload .icon {
            display: flex;
            align-items: center;
            justify-content: center;

        }

        .custom-file-upload .icon svg {
            height: 80px;
            fill: rgba(75, 85, 99, 1);

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


        .preview-image {

            position: absolute;
            top: 0px;
            width: 100%;
            height: 100%;
            border-radius: 10px 10px 10px;


        }


    </style>
</head>
<body>

<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100" style="">
            <frm:form cssStyle="width: 520px;"
                      modelAttribute="khachHang"
                      action="${pageContext.request.contextPath}/KhachHang/create"
                      method="post"
                      enctype="multipart/form-data">

                <div class="row" style="width: 800px">

                    <div class="col-3" style="margin-top: 70px">

                    </div>

                    <div class="col-9">
                        <h5 style="margin-bottom: 30px; margin-left: 100px; font-weight: bold">Tạo tài khoản</h5>
                        <div class="row">
                            <div class="col-6">
                                <label style="margin-left: 40px">Email <span style="color: red">*</span></label>
                                <frm:input class="form-control" placeholder="Email" cssStyle="width: 250px; margin-left: 40px; margin-right: 30px" path="email"/>
                                <frm:errors path="email" style="color: red;margin-left: 35px"></frm:errors>
                                <label style="color: red;margin-left: 40px">${erCheckEmailKhachHangNull}</label>
                                <label style="color: red;margin-left: 40px">${erMail}</label>
                                <label style="color: red;margin-left: 40px">${erCheckEmail}</label>
                                <label style="color: red;margin-left: 40px">${erCheckEmailSo}</label>
                                <label style="color: red;margin-left: 40px">${erCheckTrungEmailKhachHang}</label>

                            </div>
                            <div class="col-6">
                                <label style="margin-left: 40px;">Mật khẩu <span style="color: red">*</span></label>
                                <frm:input class="form-control" type="password" placeholder="Mật khẩu"
                                           cssStyle="width: 250px; margin-left: 30px" path="matKhau"/>
                                <frm:errors path="matKhau"></frm:errors>
                                <label class="">${matKhauNotNull}</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <label style="margin-left: 40px; margin-top: 20px">Tên khách hàng <span
                                        style="color: red">*</span></label>
                                <frm:input class="form-control" placeholder="Tên khách hàng"
                                           cssStyle="width: 250px; margin-left: 40px; margin-right: 30px"
                                           path="tenKhachHang"/>
                                <frm:errors path="tenKhachHang"></frm:errors>
                                <label style="color: red;margin-left: 40px">${tenKhachHangNotNull}</label>
                                <label style="color: red;margin-left: 40px">${tenKhachHangHopLe}</label>
                            </div>
                            <div class="col-6">
                                <label style="margin-left: 50px; margin-top: 20px; margin-bottom: 20px">Giới
                                    tính</label>
                                <br>
                                <frm:radiobutton path="gioiTinh" value="Nam" checked="checked"
                                                 cssStyle="margin-left: 50px"/>Nam
                                <frm:radiobutton cssStyle="margin-left: 30px" path="gioiTinh" value="Nữ"/>Nữ
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <label style="margin-left: 40px; margin-top: 20px">Ngày sinh</label>
                                <frm:input type="date" class="form-control" placeholder="Ngày sinh"
                                           cssStyle="width: 250px; margin-left: 40px; margin-right: 30px"
                                           path="ngaySinh"/>
                            </div>
                            <div class="col-6">
                                <label style="margin-left: 40px; margin-top: 20px">Số điện thoại <span
                                        style="color: red;margin-left: 10px">*</span></label>
                                <frm:input class="form-control" placeholder="Số điện thoại"
                                           cssStyle="width: 250px; margin-left: 30px" path="soDienThoai"/>
                                <frm:errors path="soDienThoai"></frm:errors>
                                <label class="">${soDienThoaiNotNull}</label>
                                <label>${soDienThoaiHopLe}</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <label style="margin-left: 40px; margin-top: 20px">Địa chỉ cụ thể<span
                                        style="color: red;margin-left: 5px">*</span></label>
                                <frm:input class="form-control" placeholder="Địa chỉ" cssStyle="width: 250px; margin-left: 40px" path="diaChi"/>
                            </div>
                            <div class="col-6">
                                <label for="province" style="margin-left: 40px; margin-top: 20px">Thành phố/Tỉnh<span
                                        style="color: red; margin-left: 5px">*</span>
                                </label>
                                <select style="width: 250px;margin-left: 30px" id="province" name="quocGia" class="form-control"
                                        onchange="loadDistricts(); checkSelection()">
                                    <option value="">${thanhPho}</option>
                                </select>
                                <input type="hidden" id="tinh1" name="quocGia1">
                                <div class="er">
                                    <span style="color: red">${quocGia1}</span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <label for="district" style="margin-left: 40px; margin-top: 20px">Quận/Huyện<span
                                        style="color: red;margin-left: 5px">*</span></label>
                                <select style="margin-left: 40px; width: 250px" id="district" name="thanhPho" class="form-control"
                                        onchange="loadWards(); checkSelection()"
                                        disabled>
                                    <option value="">${huyen}</option>
                                </select>
                                <input type="hidden" id="huyen1" name="thanhPho1">
                                <div class="er">
                                    <span style="color: red">${thanhPho1}</span>
                                </div>
                            </div>
                            <div class="col-6">
                                <label for="ward" style="margin-left: 40px; margin-top: 20px">Xã/Phường<span
                                        style="color: red; margin-left: 5px">*</span>
                                </label>
                                <select style="margin-left: 30px; width: 250px" id="ward" name="diaChi" class="form-control" onchange="checkSelection()"
                                        disabled>
                                    <option value="">${xa}</option>
                                </select>
                                <input type="hidden" id="xa1" name="diaChi1">
                                <div class="er">
                                    <span style="color: red">${diaChi1}</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="container-login100-form-btn"
                         style="width: 150px; margin-top: 30px; margin-bottom: 30px; margin-left: 450px">
                        <button type="submit" class="login100-form-btn">
                            Đăng kí
                        </button>
                    </div>

                </div>
            </frm:form>

        </div>
    </div>
</div>


<!--===============================================================================================-->
<script src="/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="/vendor/bootstrap/js/popper.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="/vendor/tilt/tilt.jquery.min.js"></script>


<script>
    $('.js-tilt').tilt({
        scale: 1.1
    })

    //    js cho image
    const fileInput = document.getElementById('fileInput');
    const customFileUpload = document.querySelector('.custom-file-upload');

    fileInput.addEventListener('change', function () {
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();

            reader.onload = function (e) {
                const previewImage = document.createElement('img');
                previewImage.src = e.target.result;
                previewImage.alt = 'Uploaded Image';
                previewImage.classList.add('preview-image');

                // Xóa các hình ảnh xem trước hiện tại (nếu có)
                const existingPreviewImages = customFileUpload.querySelectorAll('.preview-image');
                existingPreviewImages.forEach(img => img.remove());

                customFileUpload.appendChild(previewImage);
            };

            reader.readAsDataURL(file);
        }
    });

</script>
<!--===============================================================================================-->

<%--Todo code js bên cho giao hàng nhanh --%>

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