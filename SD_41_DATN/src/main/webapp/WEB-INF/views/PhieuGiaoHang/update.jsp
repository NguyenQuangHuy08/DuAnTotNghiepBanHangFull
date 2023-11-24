<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Update dữ liệu phiếu giao hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<%--    <link href="/css/PhieuGiaoHang/All_PhieuGiaoHang/update.css" rel="stylesheet">--%>


</head>

<body>

<%@ include file="../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>

<section class="scrollable-list" style="width: 100%; max-height: 700px; overflow-y: auto; overflow-x: hidden">
  <div class="container">
      <div class="row">
          <div class="col-3">
              <a href="/PhieuGiaoHang/listPhieuGiaoHang" style="text-decoration: none">
                  <button style="margin-bottom: 30px;" type="submit" class="btn btn-primary">Back</button>
              </a>
          </div>
          <div class="col-lg-12">
              <marquee>
                  <h5 style="font-size: 50px; font-weight: bold; font-family: Brush Script MT ; color: #b1dfbb;">
                      Hãy tiếp tục cập nhật thêm thông tin phiếu giao hàng ! để phiếu giao hàng được kích hoạt.
                  </h5>
              </marquee>
          </div>
      </div>


      <h3 style="text-align: center; color: black; margin-bottom: 50px; margin-top: 10px">Update dữ liệu phiếu giao hàng</h3>
      <h4 style="color: red; font-weight: bold;font-size: 20px; margin-bottom: 30px">${messDoneUpdate}</h4>

              <frm:form
                      modelAttribute="phieuGiaoHang"
                      action="${pageContext.request.contextPath}/PhieuGiaoHang/create"
                      method="POST"
                      enctype="multipart/form-data"
                    >

                  <div class="form-group">
                      <frm:hidden path="id" value="${phieuGiaoHang.id}"/>
                  </div>

                  <div class="form-row">
                      <div class="form-group col-md-4">
                          <label >Khách Hàng </label>
                          <select class="form-control" name="khachHang.id">
                              <c:forEach items="${khachHang}" var="khachHang">
                                  <option ${phieuGiaoHang.khachHang.id == khachHang.id ? 'selected' :''} value="${khachHang.id}">${khachHang.tenKhachHang}</option>
                              </c:forEach>
                          </select>
                      </div>
                      <div class="form-group col-md-4">
                          <label >User</label>
                          <select  class="form-control" name="user.id">
                              <c:forEach items="${user}" var="user">
                                  <option ${phieuGiaoHang.user.id == user.id ? 'selected': ''}  value="${user.id}">${user.tenUser}</option>
                              </c:forEach>
                          </select>
                      </div>
                      <div class="form-group col-md-4">
                          <label>Hóa Đơn </label>
                          <select  class="form-control" name="hoaDon.id">
                              <c:forEach items="${hoaDon}" var="hoaDon">
                                  <option ${phieuGiaoHang.hoaDon.id == hoaDon.id ? 'selected' : ''}  value="${hoaDon.id}">${hoaDon.id}</option>
                              </c:forEach>
                          </select>
                      </div>
                  </div>


                  <div class="form-row">
                      <div class="form-group col-md-4">
                          <label>Tên Người Nhận</label>
<%--                          value="${phieuGiaoHang.tenNguoiNhan}--%>
                          <frm:input type="text" class="form-control"  placeholder="Tên người nhận" path="tenNguoiNhan"/>
                          <div class="er">
                              <label style="color: red">${erTenNguoiNhanIsEmty}</label>
                              <label style="color: red">${erCheckTenNguoiNhanSo}</label>
                              <label style="color: red">${erCheckTenNguoiNhanTrung}</label>
                          </div>
                      </div>
                      <div class="form-group col-md-4">
                          <label>Phí Ship VNĐ</label>
                          <frm:input type="text" class="form-control"  placeholder="Phí ship" path="phiShip"/>
                          <div class="er">
                              <label style="color: red">${erCheckPhiShipNumber}</label>
                          </div>
                      </div>
                  </div>

                  <button type="submit" class="btn btn-primary">Update</button>
              </frm:form>
  </div>



</section>
<%@ include file="../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>