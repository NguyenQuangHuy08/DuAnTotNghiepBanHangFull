<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.6/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.6/dist/sweetalert2.all.min.js"></script>

    <style>
        td, th {
            padding: 10px; /* Khoảng cách nội dung bên trong ô */
            color: black; /* Màu chữ */
            text-align: center; /* Căn giữa nội dung trong ô */
        }
    </style>

</head>
<body>
<%--Header cho giỏ hàng --%>
<%@ include file="../../Layouts/Index/_Header_No_Register.jsp" %>

<div class="container" style="margin-top: 130px">

     <p style="color: black">Home/Detail/GioHang</p>

     <h3 style="text-align: center">
         Thông tin giỏ hàng
     </h3>


    <form action="${pageContext.request.contextPath}/GiayTheThao/nguoiDung/addHoaDon" method="post">

    <table style="border:1px solid #FAFAFA; width:1150px; margin-top: 40px">
        <thead>
        <tr>
            <th scope="col" style="text-align: center;color: black; width: 40px">
              <input type="checkbox" id="selectAll">
            </th>
            <th scope="col" style="text-align: center;color: black">Tên giầy thể thao</th>
            <th scope="col" style="text-align: center;color: black">Hình ảnh</th>
            <th scope="col" style="text-align: center;color: black">Phân loại hàng</th>
            <th scope="col" style="text-align: center;color: black">Giá bán</th>
            <th scope="col" style="text-align: center;color: black">Số lượng</th>
<%--            <th scope="col" style="text-align: center;color: black">Đơn giá</th>--%>
            <th scope="col" style="text-align: center;color: black">Functions</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="gioHangChiTiet" items="${listGioHangChiTiet}" varStatus="i">
                <tr>
                    <td style="padding-top: 20px; text-align: center; color: black">
<%--                            <input type="checkbox" name="chon" value="${i.index}" id="chon-${gioHangChiTiet.giayTheThaoChiTiet.id}">--%>

                            <input type="checkbox" name="chon" value="${i.index}" id="chon-${gioHangChiTiet.giayTheThaoChiTiet.id}">
<%--                            <input type="hidden" name="idGiayChiTiet" value="${gioHangChiTiet.giayTheThaoChiTiet.id}">--%>

                    </td>
                             <input type="hidden" name="idGiayChiTiet" value="${gioHangChiTiet.giayTheThaoChiTiet.id}">
                    <td style="padding-top: 20px; text-align: center; color: black">
                            ${gioHangChiTiet.giayTheThaoChiTiet.giayTheThao.tenGiayTheThao}
                    </td>
                    <td style="padding-top: 20px; text-align: center; color: black">
                         <img style="width: 100px;margin-bottom: 10px" src="/upload/${gioHangChiTiet.giayTheThaoChiTiet.giayTheThao.image.get(0).link}" alt="">
                    </td>
                    <td style="padding-top: 20px; text-align: center; color: black">
                            Size: ${gioHangChiTiet.giayTheThaoChiTiet.size.size},
                            Màu sắc: ${gioHangChiTiet.giayTheThaoChiTiet.mauSac.tenMauSac}
                    </td>
                    <td style="padding-top: 20px; text-align: center; color: black">

<%--                                <fmt:formatNumber type="" value="${gioHangChiTiet.giayTheThaoChiTiet.giayTheThao.giaBan * (100 - sale)/100}" pattern="#,##0.###" /> VNĐ--%>
                                <fmt:formatNumber type="" value="${gioHangChiTiet.giayTheThaoChiTiet.giayTheThao.giaBan * (100 - saleList[i.index])/100}" pattern="#,##0.###" /> VNĐ


                    </td>
<%--                    <td style="padding-top: 20px; text-align: center; color: black">--%>
<%--                        <button type="submit" name="actions" value="giam">-</button>--%>

<%--                        <input style="width: 30px" name="soLuong" type="number" value="${gioHangChiTiet.soLuong}">--%>

<%--                        <button type="submit" name="actions" value="tang">+</button>--%>

<%--                    </td>--%>
                    <td style="padding-top: 20px; text-align: center; color: black">
                        <button style="width: 30px" type="button" class="btn" onclick="updateQuantity('decrease', ${i.index})">-</button>

                        <input id="soLuong-${i.index}" style="width: 45px" name="soLuong" type="number" value="${gioHangChiTiet.soLuong}">

                        <button style="width: 30px" type="button" class="btn" onclick="updateQuantity('increase', ${i.index})">+</button>
                    </td>


<%--                    <td style="padding-top: 20px; text-align: center; color: black;margin-right:40px ">--%>

<%--                            <fmt:formatNumber type="" value="${gioHangChiTiet.donGia}" pattern="#,##0.###" /> VNĐ--%>
<%--                            <input type="hidden" name="donGia" value="${gioHangChiTiet.donGia}">--%>
<%--                    </td>--%>

<%--                    Xóa riêng--%>
                        <td>
                            <button formaction="/delete/${gioHangChiTiet.id}" class="btn" style="color: black">✖</button>
                        </td>

                </tr>
        </c:forEach>
        </tbody>
    </table>
        <div class="thongBaoEr" style="margin-top: 10px">
            <span style="color: red;font-size: 15px">${soLuongMax}</span>
            <span style="color: red">${soLuongMax1}</span>
            <span style="color: red;font-size: 15px">${erCheckNun}</span>
            <span style="color: red;font-size: 15px">${erCheckSoLuongAm}</span>
            <span style="color: red;font-size: 15px">${erCheckSoLuongMax}</span>


        </div>

        <div style="margin-top: 30px">
            <button type="submit" class="btn btn-primary">
                Mua hàng
            </button>
        </div>
    </form>

</div>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<script>

       //Thêm giỏ hàng thành công
        var successMessage = "${successMessage}";
        if(successMessage){
            Swal.fire({

                icon: 'success',
                title: '<span style="font-size: 17px;">Thêm sản phẩm vào giỏ hàng thành công</span>',
                showConfirmButton: false, // Ẩn nút OK
                timer: 1000, // Thời gian hiển thị thông báo (miligiây)

            })
        }

        //Code cho giỏ hàng chi tiết
           $(document).ready(function () {
           $('form').submit(function () {
               $('input[type=checkbox]:checked').each(function () {
                   // Bỏ ngoặc vuông hai đầu của giá trị checkbox
                   $(this).val($(this).val().replace('[', '').replace(']', ''));
               });
           });
       });

</script>

<%--Chọn tất cả sản phẩm trong giỏ hàng--%>
<script>
    document.getElementById('selectAll').addEventListener('change', function () {
        var checkboxes = document.querySelectorAll('[name="chon"]');
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = document.getElementById('selectAll').checked;
        });
    });
</script>

<%--Mã js cho số lượng tăng giảm--%>

<script>

    function updateQuantity(operation, index) {
        var quantityInput = document.getElementById("soLuong-" + index);
        var currentQuantity = parseInt(quantityInput.value);

        if (operation === "increase") {
            if (currentQuantity < 5) {

                quantityInput.value = currentQuantity + 1;

            } else {

                Swal.fire({

                    icon: 'success',
                    title: '<span style="font-size: 17px; color: red">Xin lỗi, chỉ được phép mua tối đa 5 sản phẩm.</span>',
                    showConfirmButton: false, // Ẩn nút OK
                    timer: 1500, // Thời gian hiển thị thông báo (miligiây)

                }).then(() => {

                    // window.location.href = '/templates/Users/Layouts/Shop/gioHangView';
                    window.location.href = '/GiayTheThao/NguoiDung/ViewGioHang';

                });

            }
        } else if (operation === "decrease") {
            if (currentQuantity > 1) {

                quantityInput.value = currentQuantity - 1;

            } else {

                Swal.fire({

                    icon: 'success',
                    title: '<span style="font-size: 17px; color: red">Xin lỗi số lượng của sản phẩm mua phải lớn hơn hoặc bằng 1!</span>',
                    showConfirmButton: false, // Ẩn nút OK
                    timer: 1500, // Thời gian hiển thị thông báo (miligiây)

                }).then(() => {

                    // window.location.href = '/templates/Users/Layouts/Shop/gioHangView';
                    window.location.href = '/GiayTheThao/NguoiDung/ViewGioHang';

                });

            }
        }
    }

</script>


<%--Todo code xác nhận xóa giầy thể thao chi tiết trong giỏ hàng--%>


<%@ include file="../../Layouts/Index/_Session1.jsp" %>
</body>
</html>


