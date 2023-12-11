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


</head>
<body>

<%--   Header--%>
<%@ include file="../../Layouts/Index/_Header_No_Register.jsp" %>


<div class="container" style="margin-top: 120px">

    <form method="post">
        <div class="login">
            <H3 class="heading">Quên mật khẩu</H3>
            <form method="post">
                <input type="text" name="email" placeholder="Nhập email"
                       style="width: 950px; height: 50px;border-radius: 25px;margin-left: 10%">
                <button formaction="/KhachHang/QuenMatKhau" style="margin-left: 43%;margin-top: 30px">Gửi mật khẩu mới</button>
                <h1>${loi}</h1>
            </form>
        </div>
    </form>


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
            preview.src = "image.jpg"; // Ảnh mẫu
        }
    }

    function updateImage(updatedImageUrl) {
        // Thêm tham số ngẫu nhiên để tránh lưu trữ cache
        var randomParam = Date.now();
        updatedImageUrl += "?random=" + randomParam;

        // Cập nhật nguồn ảnh
        document.getElementById('preview-image').src = updatedImageUrl;

        console.log("Ảnh đã được cập nhật!");
    }

    function submitForm() {
        // Bạn có thể cần thêm logic ở đây để xử lý việc gửi biểu mẫu qua AJAX hoặc gửi thông thường

        // Ví dụ: Giả sử bạn sử dụng AJAX để gửi biểu mẫu
        $.ajax({
            url: "${pageContext.request.contextPath}/TrangChu/ThongTinCaNhan/Luu",
            type: "POST",
            data: new FormData($("#yourFormId")[0]),
            processData: false,
            contentType: false,
            success: function (data) {
                // Giả sử dữ liệu chứa URL ảnh đã cập nhật
                var updatedImageUrl = data.updatedImageUrl;

                // Gọi hàm để cập nhật ảnh
                updateImage(updatedImageUrl);
            },
            error: function () {
                // Xử lý lỗi nếu cần
            }
        });
    }

</script>

</body>
</html>

