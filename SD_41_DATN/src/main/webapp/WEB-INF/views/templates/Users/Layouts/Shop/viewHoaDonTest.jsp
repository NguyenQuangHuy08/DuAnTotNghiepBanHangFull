
<%--&lt;%&ndash;Mã code js cho giao hàng nhanh &ndash;%&gt;--%>


<%--<script>--%>

<%--    var gia = parseFloat(document.getElementById("price").value);--%>
<%--    var gia1 = gia;--%>
<%--    document.getElementById("price1").value = gia1.toFixed(0);--%>

<%--    var gia1Formatted1 = gia.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 3});--%>

<%--    var gia1WithCurrency1 = gia1Formatted1 + " VNĐ";--%>

<%--    document.getElementById("price").value = gia1WithCurrency1;--%>
<%--    document.getElementById("tongTienString").innerText = gia1WithCurrency1;--%>

<%--    function togglePaymentDetails1() {--%>
<%--        var ctggDetails = document.getElementById("ctgg-details");--%>
<%--        var ctggRadio = document.getElementById("ctgg");--%>

<%--        if (ctggRadio.checked) {--%>
<%--            ctggDetails.style.display = "block";--%>
<%--        }--%>
<%--    }--%>

<%--    function updatePrice(radioButton) {--%>
<%--        var priceInput = document.getElementById("price");--%>
<%--        var currentPrice = parseFloat(${hoaDon.thanhTien});--%>
<%--        var selectedValue = parseFloat(radioButton.value);--%>
<%--        var newPrice = currentPrice - (currentPrice * selectedValue / 100);--%>

<%--        var gia1Formatted = newPrice.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 3});--%>

<%--        var gia1WithCurrency = gia1Formatted + " VNĐ";--%>

<%--        priceInput.value = gia1WithCurrency;--%>
<%--        document.getElementById("tongTienString").innerText = gia1WithCurrency;--%>
<%--    }--%>

<%--    function toggleView(elementId) {--%>
<%--        var element = document.getElementById(elementId);--%>
<%--        element.style.display = (element.style.display === "block") ? "none" : "block";--%>
<%--    }--%>

<%--    function toggleView(elementId) {--%>
<%--        var element = document.getElementById(elementId);--%>
<%--        element.style.display = (element.style.display === "block") ? "none" : "block";--%>
<%--    }--%>
<%--</script>--%>
<%--<script>--%>
<%--    function showCondition(condition) {--%>
<%--        document.getElementById("condition-text").innerHTML = condition;--%>
<%--        document.getElementById("condition-popup").style.display = "block";--%>
<%--    }--%>

<%--    function closeCondition() {--%>
<%--        document.getElementById("condition-popup").style.display = "none";--%>
<%--    }--%>
<%--</script>--%>
<%--<script>--%>

<%--    var checkbox1 = document.getElementById("checkbox1");--%>
<%--    var checkbox2 = document.getElementById("checkbox2");--%>
<%--    var view_dia_chi_cu = document.getElementById("view_dia_chi_cu");--%>
<%--    var view_dia_chi_moi = document.getElementById("view_dia_chi_moi");--%>

<%--    var bothUnchecked = true;--%>

<%--    // Thêm sự kiện cho checkbox1 để kiểm tra trạng thái của checkbox2--%>
<%--    checkbox1.addEventListener("change", async function () {--%>
<%--        if (checkbox1.checked) {--%>
<%--            checkbox2.checked = false;--%>
<%--            view_dia_chi_moi.style.display = "none";--%>
<%--            view_dia_chi_cu.style.display = "block";--%>
<%--            document.getElementById("province").selectedIndex = 0;--%>
<%--            document.getElementById("district").selectedIndex = 0;--%>
<%--            document.getElementById("ward").selectedIndex = 0;--%>
<%--            document.getElementById('ten1').style.borderColor = 'gray';--%>
<%--            document.getElementById('email1').style.borderColor = 'gray';--%>
<%--            document.getElementById('sdt1').style.borderColor = 'gray';--%>
<%--            document.getElementById('province').style.borderColor = 'gray';--%>
<%--            document.getElementById('district').style.borderColor = 'gray';--%>
<%--            document.getElementById('ward').style.borderColor = 'gray';--%>
<%--            await findCalculateShipping();--%>
<%--        }--%>
<%--        checkBothUnchecked();--%>
<%--    });--%>

<%--    // Thêm sự kiện cho checkbox2 để kiểm tra trạng thái của checkbox1--%>
<%--    checkbox2.addEventListener("change", function () {--%>
<%--        if (checkbox2.checked) {--%>
<%--            checkbox1.checked = false;--%>
<%--            view_dia_chi_moi.style.display = "block";--%>
<%--            document.getElementById('ten').style.borderColor = 'gray';--%>
<%--            document.getElementById('email').style.borderColor = 'gray';--%>
<%--            document.getElementById('sdt').style.borderColor = 'gray';--%>
<%--            document.getElementById('tinh').style.borderColor = 'gray';--%>
<%--            document.getElementById('huyen').style.borderColor = 'gray';--%>
<%--            document.getElementById('xa').style.borderColor = 'gray';--%>
<%--            // document.getElementById('mess').style.borderColor = 'gray';--%>

<%--        }--%>
<%--        checkBothUnchecked();--%>
<%--    });--%>

<%--    function checkBothUnchecked() {--%>
<%--        if (!checkbox1.checked && !checkbox2.checked) {--%>
<%--            const shippingCost = 0;--%>

<%--            var tongSoTien = currentPrice + shippingCost;--%>
<%--            priceInput1.value = tongSoTien;--%>

<%--            var gia1Formatted3 = shippingCost.toLocaleString('vi-VN', {--%>
<%--                minimumFractionDigits: 0,--%>
<%--                maximumFractionDigits: 3--%>
<%--            });--%>

<%--            var gia1WithCurrency3 = gia1Formatted3 + " VNĐ";--%>

<%--            document.getElementById("ship").value = gia1WithCurrency3;--%>
<%--            document.getElementById("shipcode").innerText = gia1WithCurrency3;--%>

<%--            var gia1Formatted2 = tongSoTien.toLocaleString('vi-VN', {--%>
<%--                minimumFractionDigits: 0,--%>
<%--                maximumFractionDigits: 3--%>
<%--            });--%>

<%--            var gia1WithCurrency2 = gia1Formatted2 + " VNĐ";--%>

<%--            priceInput.value = gia1WithCurrency2;--%>
<%--            document.getElementById("tongTienString").innerText = gia1WithCurrency2;--%>
<%--            bothUnchecked = true;--%>
<%--            document.getElementById("province").selectedIndex = 0;--%>
<%--            document.getElementById("district").selectedIndex = 0;--%>
<%--            document.getElementById("ward").selectedIndex = 0;--%>
<%--        }--%>
<%--        if (!checkbox1.checked && checkbox2.checked) {--%>
<%--            const shippingCost = 0;--%>

<%--            var tongSoTien = currentPrice + shippingCost;--%>
<%--            priceInput1.value = tongSoTien;--%>
<%--            document.getElementById("tongTienString").innerText = tongSoTien;--%>

<%--            var gia1Formatted3 = shippingCost.toLocaleString('vi-VN', {--%>
<%--                minimumFractionDigits: 0,--%>
<%--                maximumFractionDigits: 3--%>
<%--            });--%>

<%--            var gia1WithCurrency3 = gia1Formatted3 + " VNĐ";--%>

<%--            document.getElementById("ship").value = gia1WithCurrency3;--%>
<%--            document.getElementById("shipcode").innerText = gia1WithCurrency3;--%>

<%--            var gia1Formatted2 = tongSoTien.toLocaleString('vi-VN', {--%>
<%--                minimumFractionDigits: 0,--%>
<%--                maximumFractionDigits: 3--%>
<%--            });--%>

<%--            var gia1WithCurrency2 = gia1Formatted2 + " VNĐ";--%>

<%--            priceInput.value = gia1WithCurrency2;--%>
<%--            document.getElementById("tongTienString").innerText = gia1WithCurrency2;--%>
<%--            bothUnchecked = true;--%>
<%--        }--%>
<%--    }--%>

<%--    // Thêm sự kiện onchange cho các combobox--%>

<%--    // Hàm để cập nhật giá trị các input dựa trên các combobox đã chọn--%>


<%--    async function findProvinceIdByName(provinceName) {--%>
<%--        const response = await fetch(`http://localhost:8080/public/provinces`);--%>
<%--        const data = await response.json();--%>

<%--        for (const province of data.data) {--%>
<%--            if (province.ProvinceName === provinceName) {--%>
<%--                return province.ProvinceID;--%>
<%--            }--%>
<%--        }--%>

<%--        return null;--%>
<%--    }--%>

<%--    async function findProvinceNameById(provinceid) {--%>
<%--        const response = await fetch(`http://localhost:8080/public/provinces`);--%>
<%--        const data = await response.json();--%>

<%--        for (const province of data.data) {--%>
<%--            if (province.ProvinceID === provinceid) {--%>
<%--                return province.ProvinceName;--%>
<%--            }--%>
<%--        }--%>

<%--        return null;--%>
<%--    }--%>

<%--    async function findDistrictIdByName(districtName, provinceId) {--%>
<%--        const response = await fetch('http://localhost:8080/public/districts?province_id=' + provinceId);--%>
<%--        const data = await response.json();--%>

<%--        for (const district of data.data) {--%>
<%--            if (district.DistrictName === districtName) {--%>
<%--                return district.DistrictID;--%>
<%--            }--%>
<%--        }--%>

<%--        // Trả về null nếu không tìm thấy huyện nào có tên tương ứng--%>
<%--        return null;--%>
<%--    }--%>

<%--    async function findWardIdByName(wardName, districtId) {--%>
<%--        const response = await fetch('http://localhost:8080/public/wards?district_id=' + districtId);--%>
<%--        const data = await response.json();--%>

<%--        for (const ward of data.data) {--%>
<%--            if (ward.WardName === wardName) {--%>
<%--                return ward.WardCode;--%>
<%--            }--%>
<%--        }--%>

<%--        // Trả về null nếu không tìm thấy xã nào có tên tương ứng--%>
<%--        return null;--%>
<%--    }--%>

<%--    async function findCalculateShipping() {--%>
<%--        var tinh = document.getElementById("tinh").value;--%>
<%--        var provinceName = tinh;--%>
<%--        var provinceId = await findProvinceIdByName(provinceName);--%>


<%--        var huyen = document.getElementById("huyen").value;--%>
<%--        var districtName = huyen;--%>
<%--        var districtId = await findDistrictIdByName(districtName, provinceId);--%>

<%--        var xa = document.getElementById("xa").value;--%>
<%--        var wardName = xa;--%>
<%--        var wardId = await findWardIdByName(wardName, districtId);--%>

<%--        // var mess = document.getElementById("mess").value;--%>
<%--        // var wardName = mess;--%>
<%--        // var wardId = await findWardIdByName(wardName, districtId);--%>

<%--        const transportationFeeDTO = {--%>
<%--            toDistrictId: districtId,--%>
<%--            toWardCode: wardId,--%>
<%--            quantity: 1,--%>
<%--            insuranceValue: 0--%>
<%--        };--%>

<%--        try {--%>
<%--            const response = await fetch('http://localhost:8080/public/transportationFee', {--%>
<%--                method: 'POST',--%>
<%--                headers: {--%>
<%--                    'Content-Type': 'application/json'--%>
<%--                },--%>
<%--                body: JSON.stringify(transportationFeeDTO)--%>
<%--            });--%>

<%--            const data = await response.json();--%>

<%--            if (data && data.code === 200) {--%>
<%--                const shippingCost = data.data.total;--%>

<%--                var tongSoTien = currentPrice + shippingCost;--%>
<%--                priceInput1.value = tongSoTien;--%>
<%--                document.getElementById("tongTienString").innerText = tongSoTien;--%>


<%--                var gia1Formatted3 = shippingCost.toLocaleString('vi-VN', {--%>
<%--                    minimumFractionDigits: 0,--%>
<%--                    maximumFractionDigits: 3--%>
<%--                });--%>

<%--                var gia1WithCurrency3 = gia1Formatted3 + " VNĐ";--%>

<%--                document.getElementById("ship").value = gia1WithCurrency3;--%>
<%--                document.getElementById("shipcode").innerText = gia1WithCurrency3;--%>

<%--                var gia1Formatted2 = tongSoTien.toLocaleString('vi-VN', {--%>
<%--                    minimumFractionDigits: 0,--%>
<%--                    maximumFractionDigits: 3--%>
<%--                });--%>

<%--                var gia1WithCurrency2 = gia1Formatted2 + " VNĐ";--%>

<%--                priceInput.value = gia1WithCurrency2;--%>
<%--                document.getElementById("tongTienString").innerText = gia1WithCurrency2;--%>

<%--            } else {--%>
<%--                document.getElementById("shippingCost").innerText = "Không thể tính phí vận chuyển.";--%>
<%--            }--%>
<%--        } catch (error) {--%>
<%--            console.error('Error:', error);--%>
<%--            document.getElementById("shippingCost").innerText = "Lỗi khi tính phí vận chuyển. Chi tiết lỗi: " + error.message;--%>
<%--        }--%>
<%--    }--%>


<%--    // JavaScript code để kiểm tra địa chỉ cũ--%>

<%--</script>--%>
<%--<script>--%>

<%--    var priceInput = document.getElementById("price");--%>
<%--    var priceInput1 = document.getElementById("price1");--%>
<%--    var currentPrice = parseFloat(${hoaDon.thanhTien});--%>

<%--    async function loadProvinces() {--%>
<%--        const response = await fetch(`http://localhost:8080/public/provinces`);--%>
<%--        const data = await response.json();--%>

<%--        const provinceSelect = document.getElementById("province");--%>
<%--        provinceSelect.innerHTML = '<option value="">Chọn tỉnh</option>';--%>

<%--        for (const province of data.data) { // Thay đổi dòng này để lấy dữ liệu từ data.data--%>
<%--            const option = document.createElement("option");--%>
<%--            option.value = province.ProvinceID;--%>
<%--            option.text = province.ProvinceName;--%>
<%--            provinceSelect.appendChild(option);--%>
<%--            console.log(province.ProvinceID, province.ProvinceName);--%>
<%--        }--%>
<%--    }--%>

<%--    async function loadDistricts() {--%>
<%--        const provinceId = document.getElementById("province").value;--%>

<%--        if (!provinceId) {--%>
<%--            return;--%>
<%--        }--%>

<%--        const response = await fetch('http://localhost:8080/public/districts?province_id=' + provinceId);--%>
<%--        const data = await response.json();--%>

<%--        const districtSelect = document.getElementById("district");--%>
<%--        districtSelect.innerHTML = '<option value="">Chọn huyện</option>';--%>

<%--        for (const district of data.data) {--%>
<%--            const option = document.createElement("option");--%>
<%--            option.value = district.DistrictID;--%>
<%--            option.text = district.DistrictName;--%>
<%--            districtSelect.appendChild(option);--%>
<%--        }--%>

<%--        districtSelect.disabled = false;--%>
<%--        document.getElementById("ward").disabled = true;--%>
<%--        document.getElementById("message").innerText = "";--%>
<%--    }--%>

<%--    async function loadWards() {--%>
<%--        const districtId = document.getElementById("district").value;--%>

<%--        if (!districtId) {--%>
<%--            return;--%>
<%--        }--%>

<%--        const response = await fetch('http://localhost:8080/public/wards?district_id=' + districtId);--%>
<%--        const data = await response.json();--%>

<%--        const wardSelect = document.getElementById("ward");--%>
<%--        wardSelect.innerHTML = '<option value="">Chọn xã</option>';--%>

<%--        for (const ward of data.data) {--%>
<%--            const option = document.createElement("option");--%>
<%--            option.value = ward.WardCode;--%>
<%--            option.text = ward.WardName;--%>
<%--            wardSelect.appendChild(option);--%>
<%--        }--%>

<%--        wardSelect.disabled = false;--%>
<%--        document.getElementById("message").innerText = "";--%>
<%--    }--%>

<%--    function checkSelection() {--%>
<%--        const provinceSelect = document.getElementById("province");--%>
<%--        const districtSelect = document.getElementById("district");--%>
<%--        const wardSelect = document.getElementById("ward");--%>

<%--        const provinceName = provinceSelect.options[provinceSelect.selectedIndex].text;--%>
<%--        const districtName = districtSelect.options[districtSelect.selectedIndex].text;--%>
<%--        const wardName = wardSelect.options[wardSelect.selectedIndex].text;--%>

<%--        if (provinceName) {--%>
<%--            document.getElementById("tinh1").value = provinceName;--%>
<%--        }--%>

<%--        if (districtName) {--%>
<%--            document.getElementById("huyen1").value = districtName;--%>
<%--        }--%>

<%--        if (wardName) {--%>
<%--            document.getElementById("xa1").value = wardName;--%>
<%--        }--%>

<%--        if (provinceName && districtName && wardName) {--%>
<%--            calculateShipping();--%>
<%--        } else {--%>
<%--            document.getElementById("message").innerText = "";--%>
<%--            // Khóa nút tính phí nếu không đủ điều kiện--%>
<%--            document.getElementById("shippingCost").innerText = "";--%>
<%--        }--%>
<%--    }--%>

<%--    async function calculateShipping() {--%>
<%--        const districtSelect = document.getElementById("district");--%>
<%--        const wardSelect = document.getElementById("ward");--%>
<%--        const toDistrictId = districtSelect.value;--%>
<%--        const toWardCode = wardSelect.value;--%>

<%--        const transportationFeeDTO = {--%>
<%--            toDistrictId: toDistrictId,--%>
<%--            toWardCode: toWardCode,--%>
<%--            quantity: 1,--%>
<%--            insuranceValue: 0--%>
<%--        };--%>

<%--        try {--%>
<%--            const response = await fetch('http://localhost:8080/public/transportationFee', {--%>
<%--                method: 'POST',--%>
<%--                headers: {--%>
<%--                    'Content-Type': 'application/json'--%>
<%--                },--%>
<%--                body: JSON.stringify(transportationFeeDTO)--%>
<%--            });--%>

<%--            const data = await response.json();--%>

<%--            if (data && data.code === 200) {--%>
<%--                const shippingCost = data.data.total;--%>

<%--                var tongSoTien = currentPrice + shippingCost;--%>
<%--                priceInput1.value = tongSoTien;--%>
<%--                document.getElementById("tongTienString").innerText = tongSoTien;--%>

<%--                var gia1Formatted3 = shippingCost.toLocaleString('vi-VN', {--%>
<%--                    minimumFractionDigits: 0,--%>
<%--                    maximumFractionDigits: 3--%>
<%--                });--%>

<%--                var gia1WithCurrency3 = gia1Formatted3 + " VNĐ";--%>

<%--                document.getElementById("ship").value = gia1WithCurrency3;--%>
<%--                document.getElementById("shipcode").innerText = gia1WithCurrency3;--%>

<%--                var gia1Formatted2 = tongSoTien.toLocaleString('vi-VN', {--%>
<%--                    minimumFractionDigits: 0,--%>
<%--                    maximumFractionDigits: 3--%>
<%--                });--%>

<%--                var gia1WithCurrency2 = gia1Formatted2 + " VNĐ";--%>

<%--                priceInput.value = gia1WithCurrency2;--%>
<%--                document.getElementById("tongTienString").innerText = gia1WithCurrency2;--%>

<%--            } else {--%>
<%--                document.getElementById("shippingCost").innerText = "Không thể tính phí vận chuyển.";--%>
<%--            }--%>
<%--        } catch (error) {--%>
<%--            console.error('Error:', error);--%>
<%--            document.getElementById("shippingCost").innerText = "Lỗi khi tính phí vận chuyển. Chi tiết lỗi: " + error.message;--%>
<%--        }--%>
<%--    }--%>

<%--    loadProvinces();--%>
<%--    document.getElementById("province").addEventListener("change", loadDistricts);--%>
<%--    document.getElementById("district").addEventListener("change", loadWards);--%>
<%--    document.getElementById("ward").addEventListener("change", checkSelection);--%>

<%--</script>--%>
<%--<script>--%>
<%--    var loiHoaDonBySL = "${loiHoaDonBySL}";--%>
<%--    if (loiHoaDonBySL == "2") {--%>
<%--        Swal.fire({--%>
<%--            icon: 'warning',--%>
<%--            html: '<div class="swal-text">Xin lỗi, sản phẩm trong hóa đn của bạn nhiều hơn số lượng tồn</div><div class="progress-bar-container"></div>',--%>
<%--            allowOutsideClick: true--%>

<%--        });--%>
<%--        setTimeout(() => {--%>
<%--            Swal.close();--%>
<%--            addFormContainer.style.display = 'block';--%>
<%--        }, 1000);--%>
<%--    }--%>
<%--</script>--%>
