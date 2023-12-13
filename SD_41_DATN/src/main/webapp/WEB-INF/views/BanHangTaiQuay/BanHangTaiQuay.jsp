<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bán hàng tại quầy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
          integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous"/>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/BanHangTaiQuay.css"
    />
    <style>
        .form-input-check {
            outline: none;
            border: none;
            background-color: rgb(252, 250, 250);
            text-align: right;
        }
        #customer-need-to-pay {
            color: #005cbf;
            font-weight: bold;
        }
        #listSP{
            height: 530px;
            overflow: auto;
        }
        #redirect-pay-form{
            width: 100%;
        }
        .button{
            margin-top: 10px;
            margin-bottom: 10px;
        }
        #scroll-kh{
            max-height: 500px;
            overflow: auto;
        }

        /**/

        .hide-row {
            display: none;
        }

    </style>

</head>
<body>

<%@ include file="../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>

<section style="margin-left: 20px;">

    <div class="row" style="width: 100%">
        <div class="col-8" style="border: 1px solid gray;border-radius: 10px 10px 10px">
            <div id="cart-detail">

                <div class="row">
                    <div class="col-12">
                        <h5 style="color: black">
                            <form id="filter-form">

                                <%--                                <span style="color: black">--%>
                                <%--                                    Tên giầy thể thao--%>
                                <%--                                </span>--%>
                                <input style="width: 500px; height: 30px;border-radius: 5px 5px 5px" type="text"
                                       id="tenGiayTheThao" placeholder="Vui lòng nhập tên giầy thể thao để tìm kiếm">

                            </form>
                            <div id="noResults" style="display: none;color: red">
                                Không có kết quả nào phù hợp.
                            </div>

                        </h5>
                    </div>
                    <div class="col-2">
                        <h5 style="color: black">Mã hóa đơn</h5>
                    </div>
                    <div class="col-2">
                        <div class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0"
                             style="width: 500px">
                            <input type="text"
                                   style="background-color: rgb(237, 237, 237); outline: none; width: 9000px"
                                   id="textCodeCheck" readonly="true">
                        </div>
                    </div>

                    <hr style="color: black">

                </div>

                <table class="table table-hover">
                    <tbody id="cart-detail-product">

                    </tbody>
                </table>
            </div>
            <%--                All sản phẩm--%>
            <div id="all-product" style="">
                <div class="container-fluid" style="display: flex; flex-wrap: wrap" id="listProducts">
                    <c:forEach items="${list}" var="gtt">
                        <c:if test="${gtt.giayTheThao.trangThai eq 1}">
                            <div class="product-item hide-row" data-tenGiayTheThao="${gtt.giayTheThao.tenGiayTheThao}"
                                 style="width: 14rem;border: 1px solid #10707f;border-radius: 10px 10px 10px;margin-left: 15px;margin-bottom: 10px">

                                <a href="GiayTheThao/update/${gtt.giayTheThao.id}">
                                    <img src="/upload/${gtt.giayTheThao.getAnhDau()}"
                                         class="card-img-top img-product-card" style="height: 140px; width: 100%"/>
                                </a>
                                <br>
                                <a style="font-size: 15px;width: 220px;margin-left: 15px" href="#"
                                   onclick="addToCart(`${gtt.id}`, `${gtt.giayTheThao.tenGiayTheThao}`, `${gtt.giayTheThao.giaBan}`, event)">
                                        ${gtt.giayTheThao.tenGiayTheThao}
                                </a>
                                <br>
                                <span style="color: black;margin-left: 15px">
                                            Số lượng: ${gtt.soLuong}
                                        </span>
                                <br>
                                <span style="color: black;margin-left: 15px">
                                            Size: ${gtt.size.size}
                                        </span>
                                <br>
                                <span style="color: black;margin-left: 15px">
                                            Màu sắc: ${gtt.mauSac.tenMauSac}
                                        </span>
                                <p class="card-text"
                                   style="color: red;font-size: 15px; font-weight: bold;margin-left: 15px">

                                    <fmt:formatNumber type="" value="${gtt.giayTheThao.giaBan}" pattern="#,##0.###"/>
                                    VNĐ
                                </p>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>


        <%--            Cho hóa đơn --%>
        <div class="col-4" style="border: 1px solid gray;border-radius: 10px 10px 10px">
            <div id="listSP">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th scope="col" style="text-align: center">#</th>
                        <th scope="col" style="text-align: center">Mã hóa đơn</th>
                        <th scope="col" style="text-align: center">Nhân viên</th>
                        <th scope="col" style="text-align: center">Khách hàng</th>
                        <th scope="col" style="text-align: center;color: red;">✖</th>
                    </tr>
                    </thead>
                    <tbody id="list-hdc">
                    <!-- <c:forEach items="${listHDC}" var="hdc" varStatus="status">
                              <tr onclick="getHDC(`${hdc.id}`)">
                                <td style="text-align: center" >${status.index+1}</td>
                                <td style="text-align: center">${hdc.maHoaDon}</td>
                                <td style="text-align: center">${hdc.khachHang.tenKhachHang}</td>
                              </tr>
                            </c:forEach> -->
                    </tbody>
                </table>
            </div>

            <div class="button">
                <!-- <button class="btn btn-primary" style="width: 100%;">Tạo hóa đơn mới</button> -->
                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#staticBackdropCheck"
                        style="width: 100%;">Tạo mới hóa đơn
                </button>
                <div
                        class="modal fade"
                        id="staticBackdropCheck"
                        data-bs-backdrop="static"
                        data-bs-keyboard="false"
                        tabindex="-1"
                        aria-labelledby="staticBackdropLabel"
                        aria-hidden="true"
                >
                    <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="staticBackdropLabel">
                                    Tạo hóa đơn
                                </h1>
                                <button
                                        type="button"
                                        class="btn-close"
                                        data-bs-dismiss="modal"
                                        aria-label="Close"
                                ></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="search-client" class="form-label"
                                    >Tìm kiếm khách hàng</label
                                    >
                                    <input
                                            type="search"
                                            class="form-control"
                                            id="search-client"
                                            placeholder="Nhập tên khách hàng..."
                                    />
                                </div>

                                <div id="scroll-kh">
                                    <table class="table table-hover">
                                        <thead>
                                        <th></th>
                                        <th>STT</th>
                                        <th>Tên</th>
                                        <th>Giới tính</th>
                                        <th>Ngày sinh</th>
                                        <th>SĐT</th>
                                        <th>Email</th>
                                        <th>Địa chỉ</th>
                                        </thead>
                                        <tbody id="list-kh">

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button
                                        type="button"
                                        class="btn btn-secondary"
                                        data-bs-dismiss="modal"
                                >
                                    Thoát
                                </button>
                                <button
                                        type="button"
                                        class="btn btn-primary"
                                        id="chooseButton"
                                >
                                    Tạo mới
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="button">
                    <a href="#" class="btn btn-primary" id="redirect-pay-form" onclick="redirect(event)">Thanh
                        Toán</a>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>

<%--Todo code tìm kiếm giầy thể thao theo tên--%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/noUiSlider/14.6.3/nouislider.min.js"></script>



<script>

    //Todo code giá bán


    function filterProducts() {
        // Lấy giá trị từ các trường input và select
        var tenGiayTheThao = document.getElementById('tenGiayTheThao').value.toLowerCase();

        // Lấy danh sách sản phẩm
        var products = document.getElementsByClassName('product-item');
        var anyResults = false; // Biến kiểm tra có kết quả hay không

        for (var i = 0; i < products.length; i++) {
            var product = products[i];
            var productTenGiayTheThao = product.getAttribute('data-tenGiayTheThao').toLowerCase();

            // Kiểm tra xem sản phẩm phù hợp với bộ lọc hay không
            if (
                productTenGiayTheThao.includes(tenGiayTheThao)
            ) {
                // Hiển thị sản phẩm nếu nó phù hợp với bộ lọc
                product.classList.remove('hide-row');
                anyResults = true;
            } else {
                // Ẩn sản phẩm nếu không phù hợp
                product.classList.add('hide-row');
            }
        }

        // Hiển thị hoặc ẩn thông báo không có kết quả
        var noResultsMessage = document.getElementById('noResults');
        if (anyResults) {

            noResultsMessage.style.display = 'none'; // Ẩn thông báo

        } else {

            noResultsMessage.style.display = 'block'; // Hiển thị thông báo
        }
    }

    // Thêm sự kiện người nghe đầu vào và thay đổi cho tất cả các trường bộ lọc
    document.getElementById('tenGiayTheThao').addEventListener('input', filterProducts);

    // Áp dụng bộ lọc ban đầu
    filterProducts();

</script>


<%--Định dạng tiền--%>

<script>

    function formatGiaBan() {
        var giaBanInput = document.getElementById("giaBan");
        var giaBanValue = giaBanInput.value;

        // Loại bỏ dấu phẩy và khoảng trắng (nếu có)
        var giaBanFormatted = giaBanValue.replace(/,/g, '').replace(/\s/g, '');

        // Kiểm tra xem giá trị là một số
        if (!isNaN(giaBanFormatted)) {
            // Định dạng giá trị thành chuỗi có dấu phẩy
            var formattedValue = giaBanFormatted.replace(/\B(?=(\d{3})+(?!\d))/g, ",");

            // Cập nhật giá trị trên trường nhập
            giaBanInput.value = formattedValue;
        }
    }

</script>


<%--Todo code bán hàng tại quầy--%>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"
></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="../../../resources/js/BanHangTaiQuay.js"></script>

<script>
    var priceProduct = document.getElementsByClassName("price-card-product");
    var priceArray = Array.from(priceProduct);
    priceArray.forEach((element) => {
        var price = parseInt(element.textContent).toLocaleString("en-US");
        element.textContent = price;
    });

    function getAllHDCho(callback) {
        fetch("http://localhost:8080/api/hd/unpaid" )
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(callback)
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
    }

    function renderHDCho(checks) {
        var htmls = checks.map((check, key) =>{
            return `
                <tr id="`+check.id+`" onclick="getHDC(\``+check.id+`\`)">
                    <td>`+(key+1)+`</td>
                    <td >`+check.maHoaDon+`</td>
                    <td>`+(check.user==null?"N/A":check.user.tenUser)+`</td>
                    <td>`+(check.khachHang==null?"Khách hàng lẻ":check.khachHang.tenKhachHang)+`</td>
                    <td style="text-align: center">
                      <i
                        class="bi bi-x-lg cancel"
                        onclick="removeCheck(\``+check.id+`\`)"
                      ></i>
                    </td>
                  </tr>

          `;
        });
        var html = htmls.join("");
        document.getElementById("list-hdc").innerHTML = html;

    }

    function removeCheck(id) {

        fetch("http://localhost:8080/api/hd/" + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }

            })
            .then(()=>{
                getAllHDCho(renderHDCho);
                localStorage.setItem("idHD","");
                document.getElementById("textCodeCheck").value="";

            })
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });


    }

    getAllHDCho(renderHDCho);



    function createCheck() {
        $("#chooseButton").click(function () {
            var idKH = localStorage.getItem("idKH");
            if(idKH.trim().length==0){
                idKH = "";
            }
            var formData = ["", idKH];
            fetch("http://localhost:8080/api/hd", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(()=>{
                    getAllHDCho(renderHDCho);
                    alert("Tạo mới hóa đơn thành công");
                    localStorage.setItem("idKH", "");
                    var radios = document.getElementsByClassName("rdo-check")
                    Array.from(radios).forEach(function(radio) {
                        radio.checked = false;
                    });
                })
                .catch(error => {
                    console.error('Error during POST request:', error);
                });

            $("#staticBackdropCheck").modal("hide");
        });
    }



    createCheck();

    function getAllKH(callback) {
        fetch("http://localhost:8080/api/kh" )
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(callback)
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
    }

    function renderKH(customers) {
        var htmls = customers.map((customer, key) =>{
            return `
                <tr id="`+customer.id+`" onclick="setLocalStorageKH(\``+customer.id+`\`, `+(key+1)+`)" >
                  <td>
                    <input class="form-check-input rdo-check" type="radio" name="kh" id="check-`+(key+1)+`">
                  </td>
                  <td>`+(key+1)+`</td>
                  <td class="name">`+customer.tenKhachHang+`</td>
                  <td>`+customer.gioiTinh+`</td>
                  <td>`+customer.ngaySinh+`</td>
                  <td>`+customer.soDienThoai+`</td>
                  <td>`+customer.email+`</td>
                  <td>`+customer.diaChi+`</td>
                </tr>

          `;
        });
        var html = htmls.join("");
        document.getElementById("list-kh").innerHTML = html;
    }

    function setLocalStorageKH(idKH, key) {
        document.getElementById("check-"+key).checked = true;

        localStorage.setItem("idKH", idKH);
    }

    getAllKH(renderKH);

    function addToCart(idGTTCT,name, price, event) {
        event.preventDefault();
        var idHD = localStorage.getItem("idHD");
        console.log(idHD);

        if (idHD == null || idHD.length <= 0) {
            alert("Hãy chọn 1 hóa đơn");
            return;
        }
        //xử lý request
        var gttct = {id: idGTTCT};
        var hd = {id: idHD};
        var formData = {
            hoaDon : hd,
            giayTheThaoChiTiet: gttct,
            donGia: price
        };
        fetch("http://localhost:8080/api/hdct", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                return response.text();
            })
            .then(datas => {
                //xử lý giao diện

                if(datas.trim() != "") {
                    var data = JSON.parse(datas);

                    if (data.trangThai == 2){
                        var id = data.id;
                        var tr = document.getElementById(id);
                        var inputElement = tr.querySelector(".qty");
                        if (inputElement) {
                            inputElement.value = parseInt(inputElement.value)+1;
                            updateTotal(inputElement);
                        } else {
                            console.error('Không tìm thấy ô input');
                        }
                    } else {
                        var table = document.querySelector("#cart-detail-product");
                        var newRow = document.createElement("tr");
                        newRow.id = data.id;

                        newRow.innerHTML = `

                              <td style="text-align: center">
                                <i
                                  class="bi bi-x-lg cancel"
                                  onclick="removeCartDetail(`+`\``+data.id+`\``+`,this)"
                                ></i>
                              </td>
                              <td>1</td>
                              <td>`+name+`</td>
                              <td class="unit-price">`+price+`</td>
                              <td class="col-qty">
                                <div class="quantity-box">
                                  <a
                                    class="btn btn-subtract-qty"
                                    onclick="subtractQty(`+`\``+data.id+`\``+`,this)"
                                    ><i class="bi bi-dash"></i
                                  ></a>
                                  <input
                                    type="text"
                                    class="qty"
                                    oninput="validateNumber(`+`\``+data.id+`\``+`,this)"
                                    onblur="validateQty(`+`\``+data.id+`\``+`,this)"
                                    value="1"
                                  />
                                  <a class="btn btn-plus-qty" onclick="addQty(`+`\``+data.id+`\``+`,this)"
                                    ><i class="bi bi-plus-lg"></i
                                  ></a>
                                </div>
                              </td>
                              <td class="total-price">`+price+`</td>

                `;
                        table.appendChild(newRow);
                    }} else {
                    alert("Sản phẩm trong kho đã hết");
                    console.log(data);
                }



            })
            .catch(error => {
                console.error('Error during POST request:', error);
            });


    }

    function getHDC(id) {
        localStorage.setItem("idHD",id);

        fetch("http://localhost:8080/api/hdct/" + id)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                getAllHDCho((checks)=>{
                    checks.forEach(check => {
                        if(check.id==id){
                            document.getElementById("textCodeCheck").value = check.maHoaDon;
                            return;
                        }
                    });
                })
                render(data);
            })
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
    }
    function render(listHDCT) {

        var htmls = listHDCT.map((hdct, key) => {
            var ten = hdct.giayTheThaoChiTiet.giayTheThao.tenGiayTheThao;

            var giaoDien = `
            <tr id="`+hdct.id+`">
                  <td style="text-align: center">
                    <i
                      class="bi bi-x-lg cancel"
                      onclick="removeCartDetail(`+`\``+hdct.id+`\``+`,this)"
                    ></i>
                  </td>
                  <td>`+(key+1)+`</td>
                  <td>`+ten+`</td>
                  <td>`+hdct.giayTheThaoChiTiet.mauSac.tenMauSac+`</td>
                  <td>`+hdct.giayTheThaoChiTiet.size.size+`</td>
                  <td class="unit-price">`+hdct.donGia+`</td>
                  <td class="col-qty">
                    <div class="quantity-box">
                      <a
                        class="btn btn-subtract-qty"
                        onclick="subtractQty(`+`\``+hdct.id+`\``+`,this)"
                        ><i class="bi bi-dash"></i
                      ></a>
                      <input
                        type="text"
                        class="qty"
                        oninput="validateNumber(`+`\``+hdct.id+`\``+`,this)"
                        onblur="validateQty(`+`\``+hdct.id+`\``+`,this)"
                        value="`+hdct.soLuong+`"
                      />
                      <a class="btn btn-plus-qty" onclick="addQty(`+`\``+hdct.id+`\``+`,this)"
                        ><i class="bi bi-plus-lg"></i
                      ></a>
                    </div>
                  </td>
                  <td class="total-price">`+(hdct.soLuong*hdct.donGia)+`</td>
                </tr>
          `;
            return giaoDien;
        });
        var html = htmls.join('');
        document.getElementById('cart-detail-product').innerHTML=html;
    }

    function addQty(id, element) {
        var qty = element.previousElementSibling;

        var number = Number(qty.value);
        if (qty.value <= 999) {
            number += 1;
        }


        check(id, number, (check)=>{
            if(check=="false") {
                qty.value = number;
                updateQuantity(id, number);
                updateTotal(qty);
            } else {
                alert("Sản phẩm trong kho đã hết");
            }
        });

    }

    function validateQty(id, element) {
        var qty = element;
        if (qty.value > 999) {
            qty.value = 999;
            updateQuantity(id, 999, (datas)=>{
                var data = JSON.parse(datas);
                if(data.id == null){
                    qty.value = data.soLuong;
                    updateQuantity(id, data.soLuong,()=>{});
                    updateTotal(qty);
                }
            });
        }
        if (!qty.value) {
            qty.value = 1;
            updateQuantity(id, 1, (datas)=>{

            });
            updateTotal(qty);
        }

        updateQuantity(id, qty.value, (datas)=>{
            var data = JSON.parse(datas);
            if(data.id == null){
                qty.value = data.soLuong;
                updateQuantity(id, data.soLuong,()=>{});
                updateTotal(qty);
            }
        });
        updateTotal(qty);
    }

    function subtractQty(id, element) {
        var qty = element.nextElementSibling;

        var number = Number(qty.value);
        if (qty.value > 1) {
            number -= 1;
        }

        updateQuantity(id, number, ()=> {
            qty.value = number;
            updateTotal(qty);
        });

    }

    function validateNumber(id, input) {
        input.value = input.value.replace(/[^\d]+/g, "");
        // input.value = input.value.replace(/[]/g, "");

    }

    function updateTotal(inputElement) {
        var unitPriceElement = inputElement
            .closest("tr")
            .querySelector(".unit-price");
        var totalElement = inputElement.closest("tr").querySelector(".total-price");

        var unitPrice = parseFloat(unitPriceElement.textContent, 10);
        var quantity = parseFloat(inputElement.value, 10);
        var total = unitPrice * quantity;

        totalElement.textContent = total;
    }

    function updateQuantity(id, quantity, callback) {
        fetch("http://localhost:8080/api/hdct/"+id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(quantity),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(callback)
            .catch(error => {
                console.error('Error during POST request:', error);
            });
    }

    function check(id, quantity, callback){
        fetch("http://localhost:8080/api/hdct/check/"+id, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(quantity),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(callback)
            .catch(error => {
                console.error('Error during POST request:', error);
            });
    }

    function redirect(event) {
        event.preventDefault();
        var idHD = localStorage.getItem("idHD");
        if(idHD.length > 0) {
            window.location.href = "${pageContext.request.contextPath}/BanHangTaiQuay/thanhToan/"+idHD;
        } else {
            alert("Hãy chọn 1 hóa đơn");
        }
    }

    function removeCartDetail(id, element) {
        fetch("http://localhost:8080/api/hdct/" + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }

            })
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
        var parentElement = element.closest("tr").parentNode;
        parentElement.removeChild(element.closest("tr"));

    }

    localStorage.setItem("idHD", "");
    localStorage.setItem("idKH", "");
</script>

</body>
</html>