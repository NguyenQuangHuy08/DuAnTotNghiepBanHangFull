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
            <th scope="col" style="text-align: center;color: black; width: 40px">#</th>
            <th scope="col" style="text-align: center;color: black">Tên giầy thể thao</th>
            <th scope="col" style="text-align: center;color: black">Hình ảnh</th>
            <th scope="col" style="text-align: center;color: black">Phân loại hàng</th>
            <th scope="col" style="text-align: center;color: black">Giá bán</th>
            <th scope="col" style="text-align: center;color: black">Số lượng</th>
            <th scope="col" style="text-align: center;color: black">Đơn giá</th>
<%--            <th scope="col" style="text-align: center;color: black">Functions</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="gioHangChiTiet" items="${listGioHangChiTiet}" varStatus="i">
                <tr>
                    <td style="padding-top: 20px; text-align: center; color: black">
<%--                           <input type="checkbox" name="chon" value="${gioHangChiTiet.giayTheThaoChiTiet.id}">--%>
<%--                            <input type="checkbox" name="chon-${gioHangChiTiet.giayTheThaoChiTiet.id}" value="${gioHangChiTiet.giayTheThaoChiTiet.id}">--%>
<%--                            <input type="checkbox" name="chon-${gioHangChiTiet.giayTheThaoChiTiet.id}" value="${gioHangChiTiet.giayTheThaoChiTiet.id}">--%>
                                <input type="checkbox" name="chon" value="${gioHangChiTiet.giayTheThaoChiTiet.id}" id="chon-${gioHangChiTiet.giayTheThaoChiTiet.id}">

                    </td>
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

                                <fmt:formatNumber type="" value="${gioHangChiTiet.giayTheThaoChiTiet.giayTheThao.giaBan}" pattern="#,##0.###" /> VNĐ

                    </td>
                    <td style="padding-top: 20px; text-align: center; color: black">
<%--                            ${gioHangChiTiet.soLuong}--%>
                            <input name="soLuong" type="number" value="${gioHangChiTiet.soLuong}">
                    </td>
                    <td style="padding-top: 20px; text-align: center; color: black;margin-right:40px ">

                            <fmt:formatNumber type="" value="${gioHangChiTiet.donGia}" pattern="#,##0.###" /> VNĐ
                            <input type="hidden" name="donGia" value="${gioHangChiTiet.donGia}">

                    </td>
<%--                    Xóa riêng--%>
<%--                    <td style="margin-bottom: 10px">--%>
<%--                        <a class="col-sm-4" href="${pageContext.request.contextPath}/GiayTheThao/delete/${gioHangChiTiet.id}">--%>
<%--                            <button class="btn btn-primary" style="text-align: center">--%>
<%--                                    Xóa--%>
<%--                            </button>--%>
<%--                        </a>--%>
<%--                    </td>--%>
                </tr>
        </c:forEach>
        </tbody>
    </table>
        <div class="thongBaoEr" style="margin-top: 10px">
            <span style="color: red;font-size: 15px">${soLuongMax}</span>
            <span>${soLuongMax1}</span>
            <span style="color: red;font-size: 15px">${erCheckNun}</span>
        </div>

<%--    <form action="${pageContext.request.contextPath}/GiayTheThao/nguoiDung/addHoaDon" method="post">--%>

<%--    <div style="margin-top: 30px">--%>
<%--&lt;%&ndash;        <a class="col-sm-4" href="${pageContext.request.contextPath}/GiayTheThao/nguoiDung/addHoaDon">&ndash;%&gt;--%>
<%--            <button type="submit" class="btn btn-primary">--%>
<%--                Mua hàng--%>
<%--            </button>--%>
<%--&lt;%&ndash;        </a>&ndash;%&gt;--%>
<%--    </div>--%>
<%--    </form>--%>

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

<%@ include file="../../Layouts/Index/_Session1.jsp" %>
</body>
</html>


