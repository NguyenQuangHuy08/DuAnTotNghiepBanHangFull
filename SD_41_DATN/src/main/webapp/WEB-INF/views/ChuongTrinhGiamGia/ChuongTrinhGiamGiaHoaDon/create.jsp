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
    <link href="/css/ChuongTrinhGiamGia/HoaDon/create.css" rel="stylesheet">


</head>
<body>

<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>

<div class="container">

     <div class="back">
         <a class="col-sm-4" href="${pageContext.request.contextPath}/ChuongTrinhGiamGiaHoaDon/list"><button class="btn btn-primary">Back</button></a>
     </div>

    <h3 style="text-align: center; color: black; margin-bottom: 70px">Thêm dữ liệu vào bảng giảm giá hóa đơn</h3>

    <frm:form
                    modelAttribute="chuongTrinhGiamGiaHoaDon"
                    action="${pageContext.request.contextPath}/ChuongTrinhGiamGiaHoaDon/create"
                    method="POST"
                    enctype="multipart/form-data">
                <div class="row">
                    <div class="col-6">
                        <div class="group">
                            <div style="">
                                <label style="color: red; margin-top: 40px" class="">${erCheckTenSanPham}</label>
                            </div>
                            <frm:input  required="" type="text" class="input" path="tenChuongTrinh" cssStyle="width: 450px;" placeholder="Input text" value=""/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Tên chương trình giảm giá hóa đơn</label>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="group">
                            <div style="">
                                <label style="color: red;margin-top: 40px">${erCheckPhanTramGiam}</label>
                                <label style="color: red;margin-top: 40px">${erCheckPhanTramGiamNumber}</label>
                                <label style="color: red;margin-top: 40px">${erCheckPhanTramGiamString}</label>
                            </div>
                            <frm:input  required="" type="text" class="input" path="phanTramGiam" cssStyle="width: 450px;" placeholder="Input number" value=""/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Phần trăm giảm</label>
                        </div>
                    </div>
                    <div style="margin-bottom: 10px; margin-top: 60px"></div>
                    <div class="col-6">
                        <div class="group">
                            <div style="">
                                <label style="color: red;margin-top: 40px">${erCheckSoLuong}</label>
                                <label style="color: red;margin-top: 40px">${erCheckSoLuongNumber}</label>
                                <label style="color: red;margin-top: 40px">${erCheckSoLuongString}</label>
                            </div>
                            <frm:input  required="" type="text" class="input" path="soLuongSanPham" cssStyle="width: 450px;" placeholder="Input number" value=""/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Số lượng</label>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="group">
                            <div style="">
                                <label style="color: red;margin-top: 40px">${erCheckSoTienHoaDon}</label>
                                <label style="color: red;margin-top: 40px">${erCheckNumber}</label>
                                <label style="color: red;margin-top: 40px">${erCheckNumberString}</label>
                            </div>
                            <frm:input  required="" type="text" class="input" path="soTienHoaDon" cssStyle="width: 450px;" placeholder="Input number" value=""/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Số tiền hóa đơn cần đạt VNĐ</label>
                        </div>
                    </div>
                    <div style="margin-bottom: 10px; margin-top: 60px"></div>
                    <div class="col-6">
                        <div class="group">
                            <frm:input  required="" type="date" class="input" path="ngayBatDau" cssStyle="width: 450px;" placeholder="Input number" value=""/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Ngày bắt đầu</label>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="group">
                            <frm:input  required="" type="date" class="input" path="ngayKetThuc" cssStyle="width: 450px;" placeholder="Input number" value=""/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Ngày kết thúc</label>
                        </div>
                    </div>
                    <div style="margin-bottom: 10px; margin-top: 60px"></div>
                    <div class="col-6" style="margin-top: 32px">
                        <div class="group">
                            <frm:input  required="" type="text" class="input" path="ghiChu" cssStyle="width: 450px;" placeholder="Input text" value=""/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Ghi chú</label>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="group">
                            <div>
                                <label class="label" style="font-size: 18px; margin-bottom: 20px; color:#5264AE;margin-left: 0px">Trạng thái</label>
                            </div>
                            <frm:select path="trangThai" cssStyle="width: 260px; margin-top: 50px">
                                <option value="1">
                                    Chưa hết hạn
                                </option>
                                <option value="0">
                                    Hết hạn
                                </option>
                            </frm:select>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                        </div>
                    </div>
                </div>
               <div class="" style="margin-top: 80px; margin-left: 450px">
                   <button class="cssbuttons-io-button" type="submit" onclick="if (!confirm('Bạn chắc chắn muốn thêm không ?')){return false;}else{return true;}">
                       <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"></path><path fill="currentColor" d="M11 11V5h2v6h6v2h-6v6h-2v-6H5v-2z"></path></svg>
                       <span>Create</span>
                   </button>
                   <label>${message}</label>
               </div>
            </frm:form>
</div>



<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>