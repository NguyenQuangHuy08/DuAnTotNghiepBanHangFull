<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="f"
uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
uri="http://www.springframework.org/tags/form" prefix="sf"%>

<html>
<head>
    <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bán hàng tại quầy</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />

    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
      integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e"
      crossorigin="anonymous"
    />
    <style>
  .info {
  
    margin: 0 auto;
    height: 640px;
    width: 1000px;
    border: 1px solid rgb(173, 172, 172);
  }
  #listSP{
    margin-top: 20px;
    margin-bottom: 20px;
    height: 420px;
    overflow: auto;
    
  }
  ::-webkit-scrollbar {
  width: 2px;
}
::-webkit-scrollbar-thumb {
  background: #888; 
}
</style>
</head>
<body>
    <header class="p-3 mb-3 border-bottom">
      <div class="container">
        <div
          class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start"
        >
          <a
            href="http://localhost:8080/BanHangTaiQuay"
            class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none"
            id="banner"
          >
            BeeShoes
          </a>

          <div
            class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0"
          >
            
          </div>

          <div class="dropdown text-end">
            <a
              href="#"
              class="d-block link-dark text-decoration-none dropdown-toggle"
              id="dropdownUser1"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <img
                src="https://github.com/mdo.png"
                alt="mdo"
                width="32"
                height="32"
                class="rounded-circle"
              />
            </a>
            <ul
              class="dropdown-menu text-small"
              aria-labelledby="dropdownUser1"
            >
              <li><a class="dropdown-item" href="#">New project...</a></li>
              <li><a class="dropdown-item" href="#">Settings</a></li>
              <li><a class="dropdown-item" href="#">Profile</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">Sign out</a></li>
            </ul>
          </div>
        </div>
      </div>
    </header>

    <section>
        <div class="info">
            <h3 style="text-align: center;line-height: 50px;">HÓA ĐƠN THANH TOÁN</h3>
            <div id="listSP">
               
                <table class="table table-hover" style="text-align: center">
        <thead>
          <tr>
                <th scope="col">STT</th>
                <th scope="col">Tên</th>
                <th scope="col">Size</th>
                <th scope="col">Màu sắc</th>
                <th scope="col">Số lượng</th>
                <th scope="col">Đơn giá</th>
                <th scope="col">Tổng</th>
          </tr>
        </thead>
        <tbody>
          
          <c:if test="${f:length(list)!=0}">
            <c:forEach items="${list}" var="hdct" varStatus="status">
              <tr>
                     <td>${status.index+1}</td>
                     <td >${hdct.giayTheThaoChiTiet.giayTheThao.tenGiayTheThao}</td>
                     <td >${hdct.giayTheThaoChiTiet.size.size}</td>
                     <td >${hdct.giayTheThaoChiTiet.mauSac.tenMauSac}</td>
                     <td>${hdct.soLuong}</td>
                     <td>${hdct.donGia}</td>
                     <td>${hdct.soLuong*hdct.donGia}</td>
              </tr>
            </c:forEach>
          </c:if>
        </tbody>
      </table>
        </div>
          <form>
              <div style="margin: 0 auto; width: 80%;">
                  <button class="btn btn-success" style="width:100%;" onclick="pay(`${id}`, event)">Thanh toán</button>
              </div>
          </form>
          <div style="margin: 0 auto; width: 80%;">
                  <a href="${pageContext.request.contextPath}/BanHangTaiQuay" class="btn btn-success" style="width:100%;">Quay lại</a>
          </div>
        </div>
          
    </section>

    <div class="container-fluid">
      <footer
        class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top"
      >
        <div class="col-md-4 d-flex align-items-center">
          <a
            href="/"
            class="mb-3 me-2 mb-md-0 text-body-secondary text-decoration-none lh-1"
          >
            <svg class="bi" width="30" height="24">
              <use xlink:href="#bootstrap"></use>
            </svg>
          </a>
          <span class="mb-3 mb-md-0 text-body-secondary"
            >© 2023 Company, Inc</span
          >
        </div>

        <ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
          <li class="ms-3">
            <a class="text-body-secondary" href="#"
              ><i class="bi bi-twitter" width="24" height="24"></i
            ></a>
          </li>
          <li class="ms-3">
            <a class="text-body-secondary" href="#"
              ><i class="bi bi-instagram" width="24" height="24"></i
            ></a>
          </li>
          <li class="ms-3">
            <a class="text-body-secondary" href="#"
              ><i class="bi bi-facebook" width="24" height="24"></i
            ></a>
          </li>
        </ul>
      </footer>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
      crossorigin="anonymous"
    ></script>
    <script>
      function pay(id, event) {
        event.preventDefault();
        fetch("http://localhost:8080/api/hd/pay/"+id, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({}),
          })
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.text();
          })
          .then((datas)=>{
            <%--if(datas.trim().length!=0) {--%>
            <%--  var data = JSON.parse(datas);--%>
            <%--  alert("Thanh toán thành công");--%>
            <%--  window.location.href = "${pageContext.request.contextPath}/BanHangTaiQuay";--%>
            <%--} else {--%>
            <%--  alert("Không thể thanh toán vì không có sản phẩm nào.\nVui lòng chọn sản phẩm để thanh toán.");--%>
            <%--}--%>

              alert(datas);
              if(datas=="Thanh toán thành công"){
                  window.location.href = "${pageContext.request.contextPath}/BanHangTaiQuay";
              }

          })
          .catch(error => {
            console.error('Error during POST request:', error);
          });
      }
    </script>
</body>
</html>