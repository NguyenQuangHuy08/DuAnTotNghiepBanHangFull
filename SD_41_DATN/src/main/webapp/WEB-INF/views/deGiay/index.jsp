<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="f"
uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <title>Đế giày</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    />
    <style>
      .search-container {
        text-align: center;
      }
      #search-input {
        width: 500px;
        border: none;
        outline: none;
        border-bottom: 1px solid #ccc;
        border-color: #e8e8e8;
      }
      #search-input:focus {
        border-bottom: 1px solid #000000;
      }
      #search-submit {
        outline: none;
        border: none;
        background-color: white;
      }
      #search-submit:hover {
        padding: 5px;
        background-color: #e2e2e2;
        cursor: pointer;
        border-radius: 5px;
      }
      .update-btn > p {
        text-align: right;
      }
    </style>
  </head>
  <body>
  <%@ include file="../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>
    <div class="container">
      <h1 style="line-height: 100px; text-align: center; color: red">Đế giày</h1>
      <div class="search-container">
        <form action="${pageContext.request.contextPath}/deGiay/search">
          <input
            type="text"
            placeholder="Nhập tên đế giày. . ."
            name="name"
            id="search-input"
          />
          <button type="submit" id="search-submit">
            <i class="fa fa-search"></i>
          </button>
        </form>
      </div>
    </div>
    <br />
    <section class="container">
      <table class="table table-hover" style="text-align: center">
        <thead>
          <tr>
            <th scope="col">STT</th>
            <th scope="col">Tên</th>
            <th scope="col">Ghi chú</th>
            <th scope="col">Ngày tạo</th>
            <th scope="col">Ngày sửa</th>
            <th scope="col">Trạng thái</th>
            <th scope="col">Chức năng</th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${f:length(list.getContent())==0}">
            <span>Không có dữ liệu</span>
          </c:if>
          <c:if test="${f:length(list.getContent())!=0}">
            <c:forEach items="${list.getContent()}" var="dg" varStatus="status">
              <tr>
                <td>${status.index+1}</td>
                <td style="text-align: left">${dg.tenDeGiay}</td>
                <td>${dg.ghiChu}</td>
                <td>${dg.ngayTao}</td>
                <td>${dg.ngaySua}</td>
                <td>${dg.trangThai ==  1 ? "✔" : "✖"}</td>
                <td>
                  <a href="deGiay/edit/${dg.id}" class="btn btn-primary"
                    >Cập nhật</a
                  >
                </td>
              </tr>
            </c:forEach>
          </c:if>
        </tbody>
      </table>


      <ul class="pagination" style="margin-left: 500px">
        <c:forEach begin="1" end="${list.getTotalPages()}" var="i">
          <li class="page-item">
            <a
              href="${pageContext.request.contextPath}/${read}page=${i}"
              class="page-link"
              >${i}</a
            >
          </li>
        </c:forEach>
      </ul>
      <p style="text-align: right; bottom: 0px">
        <a
          href="${pageContext.request.contextPath}/deGiay/create"
          class="btn btn-primary"
          >Thêm</a
        >
      </p>
    </section>
  <%@ include file="../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>
  </body>
</html>
