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
    <link href="/css/ChuongTrinhGiamGia/GiayTheThao/create.css" rel="stylesheet">


</head>
<body>

<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>

<div class="container">
    <div>
        <h3 style="text-align: center; color: black; margin-top: 50px; margin-bottom: 100px">Thêm dữ liệu bảng giảm giá giầy thể thao</h3>

        <frm:form
                    modelAttribute="chuongTrinhGiamGiaGiayTheThao"
                    action="${pageContext.request.contextPath}/ChuongTrinhGiamGiaGiayTheThao/create"
                    method="POST"
                    enctype="multipart/form-data">
                <div class="form-group">
                    <frm:hidden path="id" value="${chuongTrinhGiamGiaGiayTheThao.id}"/>
                </div>
                <div class="row">
                    <div class="col-6">
                        <div class="group">
                            <div style="">
                                <label class="" style="color: red; margin-top: 40px">${erCheckTenSanPhamNotTrim}</label>
                                <label class="" style="color: red; margin-top: 40px">${erCheckTenSanPham}</label>
                            </div>
                            <frm:input  required="" type="text" class="input" path="tenChuongTrinhGiamGia" cssStyle="width: 450px;" placeholder="Input text" value = "${chuongTrinhGiamGiaGiayTheThao.tenChuongTrinhGiamGia}"/>
                            <frm:errors path="tenChuongTrinhGiamGia"></frm:errors>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px;">Tên chương trình giảm giá giầy thể thao</label>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="group">
                            <div>
                                <label class="" style="color: red;margin-top: 40px">${erCheckPhanTramGiamNumber}</label>
                                <label class="" style="color: red;margin-top: 40px">${erCheckPhanTramGiamString}</label>
                            </div>
                            <frm:input  required="" type="text" class="input" path="phanTramGiam" cssStyle="width: 450px;" placeholder="Input number" value = "${chuongTrinhGiamGiaGiayTheThao.phanTramGiam}"/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px">Phần trăm giảm %</label>
                        </div>
                    </div>
                    <div style="margin-bottom: 10px; margin-top: 60px"></div>
                    <div class="col-6">
                        <div class="group">
                            <frm:input  required="" type="date" class="input" path="ngayBatDau" cssStyle="width: 450px;" value="${chuongTrinhGiamGiaGiayTheThao.ngayBatDau}"/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px">Ngày bắt đầu</label>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="group">
                            <frm:input disabled="false" required="" type="date" class="input" path="ngayKetThuc" cssStyle="width: 450px;" value="${chuongTrinhGiamGiaGiayTheThao.ngayKetThuc}"/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
                            <label style="font-size: 18px">Ngày kết thúc</label>
                        </div>
                    </div>
                    <div style="margin-bottom: 10px; margin-top: 60px"></div>
                    <div class="col-6">
                        <div>
                            <label class="label" style="font-size: 18px; margin-bottom: 20px; color:#5264AE;margin-left: 10px">Ghi chú</label>
                        </div>
                        <div class="group" style="margin-top: 30px">
                            <frm:input  required="" type="text" class="input" path="ghiChu" cssStyle="width: 450px;" placeholder="Input text" value="${chuongTrinhGiamGiaGiayTheThao.ghiChu}"/>
                            <span class="highlight"></span>
                            <span class="bar"></span>
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
                <div style="margin-top: 70px; margin-left: 440px">
                    <button class="cssbuttons-io-button" type="submit" onclick="if (!confirm('Bạn chắc chắn muốn update không ?')){return false;}else{return true;}">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"></path><path fill="currentColor" d="M11 11V5h2v6h6v2h-6v6h-2v-6H5v-2z"></path></svg>
                        <span>Update</span>
                    </button>
                </div>
<%--                <button type="submit" onclick="if (!confirm('Bạn chắc chắn muốn edit không ?')){return false;}else{return true;}" style="margin-left: 400px; margin-bottom: 190px" class="btn btn-primary">Update</button>--%>
            </frm:form>
    </div>
</div>



<%@ include file="../../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>