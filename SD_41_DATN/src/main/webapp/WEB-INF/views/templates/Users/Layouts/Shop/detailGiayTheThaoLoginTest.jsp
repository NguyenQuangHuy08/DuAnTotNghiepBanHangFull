<form method="post">
    <input type="hidden" name="idAo" value="${ao.id}" id="idAo">
    <div class="d-flex mb-4">
        <strong class="text-dark mr-3">Colors:</strong>
        <c:forEach items="${mauSacs}" var="list" varStatus="vTri">
            <div class="custom-control custom-radio custom-control-inline">
                <input type="radio" id="radio${vTri.index + 1}" name="mauSac" value="${list.id}">
                <label class="radio-label" for="radio${vTri.index + 1}"><span
                        style="padding-left: 10px;padding-top: 5px">${list.ten}</span></label>

                    <%--                                <input type="radio" class="custom-control-input" id="color-${vTri.index + 1}" name="mauSac" value="${list.id}">--%>
                    <%--                                <label class="custom-control-label" for="color-${vTri.index + 1}">${list.ten}</label>--%>
            </div>
        </c:forEach>
    </div>
    <div class="d-flex mb-3">
        <strong class="text-dark mr-3">Sizes:</strong>
        <c:forEach items="${sizes}" var="list" varStatus="vTri">
            <div class="custom-control custom-radio custom-control-inline" style="margin-left: 8px">
                <input type="radio" id="size${vTri.index + 1}" name="size" value="${list.id}">
                <label class="radio-label" for="size${vTri.index + 1}"><span
                        style="padding-left: 10px;padding-top: 5px">${list.ten}</span></label>

                    <%--                                <input type="radio" class="custom-control-input" id="size-${vTri.index + 1}" name="size" value="${list.id}">--%>
                    <%--                                <label class="custom-control-label" for="size-${vTri.index + 1}">${list.ten}</label>--%>
            </div>
        </c:forEach>
    </div>

    <div class="d-flex align-items-center mb-4 pt-2">
        <div class="input-group quantity mr-3" style="width: 130px;" id="mauSacAndSize">
            <div class="input-group-append">
                <button class="btn btn-primary btn-minus" type="button" onclick="decreaseQuantity()">
                    <i class="fa fa-minus"></i>
                </button>
                <input type="text" class="form-control bg-secondary border-0 text-center" value="1"
                       name="sl" id="quantityInput">
                <button class="btn btn-primary btn-plus" type="button" onclick="increaseQuantity()">
                    <i class="fa fa-plus"></i>
                </button>
            </div>
        </div>
        <input type="hidden" name="idKh" value="${idKh}">
    </div>
</form>



<script>

    var mauSacAndSizeDiv = document.getElementById("mauSacAndSize");

    // Ẩn div mauSacAndSize ban đầu
    mauSacAndSizeDiv.style.display = "none";

    function updateProductAvailability() {
        var selectedColor = document.querySelector("input[name='mauSac']:checked");
        var selectedSize = document.querySelector("input[name='size']:checked");

        if (selectedColor && selectedSize) {
            var colorId = selectedColor.value;
            var sizeId = selectedSize.value;
            var idAo = document.getElementById("idAo").value;

            // Tạo đối tượng XMLHttpRequest
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/find/" + idAo + "/" + colorId + "/" + sizeId, true);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    if (response > 0) {
                        // Cập nhật nội dung của phần tử div
                        document.getElementById("soLuongTonStr").textContent = "Số lượng sản phẩm còn lại: " + response;
                        document.getElementById("soLuongTon").value = response;

                    } else {
                        document.getElementById("soLuongTonStr").textContent = "Đã hết sản phẩm";
                        document.getElementById("soLuongTon").value = 0;
                    }
                } else {
                    document.getElementById("soLuongTonStr").textContent = "Đã hết sản phẩm";
                    document.getElementById("soLuongTon").value = 0;
                }
            };

            xhr.send();

            // Hiển thị div mauSacAndSize
            mauSacAndSizeDiv.style.display = "block";
        }
    }

    // Thêm sự kiện change cho các input radio
    var colorInputs = document.querySelectorAll("input[name='mauSac']");
    var sizeInputs = document.querySelectorAll("input[name='size']");

    colorInputs.forEach(function (input) {
        input.addEventListener("change", updateProductAvailability);
    });

    sizeInputs.forEach(function (input) {
        input.addEventListener("change", updateProductAvailability);
    });
</script>


