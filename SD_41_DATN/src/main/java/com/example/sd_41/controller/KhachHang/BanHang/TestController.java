//package com.example.sd_41.controller.KhachHang.BanHang;
//
//import org.springframework.web.bind.annotation.PostMapping;
//
//public class TestController {
//
//    ////Todo code add giầy thể thao chi tiết vào giỏ hàng chi tiết
//
//    @PostMapping("/GiayTheThao/nguoiDung/addHoaDon")
//    public String nguoiDungAddHoaDon(Model model,
//                                     @RequestParam(value = "chon", required = false) List<String> chon,
//                                     @RequestParam(value = "idGiayChiTiet", required = false) List<UUID> idGiayChiTiet,
//                                     @RequestParam(value = "soLuong", required = false) List<String> soLuong,
//                                     @RequestParam(value = "donGia", required = false) List<String> donGia,
//
//                                     HttpSession session,
//                                     HttpServletRequest request,
//                                     RedirectAttributes attributes) {
//
//        // Đã lưu mã vào session
//        UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
//        KhachHang khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
//
//        // Chọn là null
//        if (chon == null || chon.isEmpty()) {
//
//            attributes.addFlashAttribute("erCheckNun", "Xin lỗi hãy chọn ít nhất một sản phẩm để thanh toán !");
//
//        } else {
//            HoaDon hoaDon = new HoaDon();
//            // Thêm vào Hóa đơn
//            LocalTime localTime = LocalTime.now();
//            LocalDate ngayThanhToan = LocalDate.now();
//            String ngayThanhToanToDate = ngayThanhToan.toString();
//
//            hoaDon.setMaHoaDon("MaHD" + localTime.getHour() + localTime.getMinute() + localTime.getSecond());
//            hoaDon.setKhachHang(khachHang);
//            hoaDon.setTrangThai(0);
//            hoaDon.setNgayThanhToan(LocalDateTime.now());
//            hoaDon.setNgayTao(LocalDateTime.now());
//
//            hoaDonRepository.save(hoaDon);
//
//            BigDecimal tongDonGiaHoaDon = BigDecimal.ZERO;
//
//            // Thêm vào hóa đơn chi tiết
//            for (String stt : chon) {
//
//                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
//                hoaDonChiTiet.setHoaDon(hoaDon);
//                hoaDonChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTietRepository.findById(idGiayChiTiet.get(Integer.parseInt(stt))).orElse(null));
//
//                //Todo code mới
//                GiayTheThaoChiTiet giayTheThaoChiTiet = giayTheThaoChiTietRepository.findById(idGiayChiTiet.get(Integer.parseInt(stt))).orElse(null);
//
//                System.out.println("Số lượng request trả về : "+ soLuong);
//                int soLuongMua = Integer.parseInt(soLuong.get(Integer.parseInt(stt)));
//                int soLuongCo = Integer.parseInt(giayTheThaoChiTiet.getSoLuong());
//
//                //Giỏ hàng chi tiết đã chon
//
//                String idGiayChiTietGioHang = request.getParameter("idGiayChiTiet");
//
//                if(giayTheThaoChiTiet != null) {
//                    if (soLuongMua > soLuongCo) {
//
//                        System.out.println("Er");
//                        attributes.addFlashAttribute("erSoLuong","Xin lỗi hiện tại sản phẩm này chỉ còn duy nhất "+soLuongCo + " đôi !");
//                        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
//
//                    }
//                    if(soLuongMua <= 0){
//
//                        System.out.println("Er");
//                        attributes.addFlashAttribute("erSoLuongAm","Xin lỗi số lượng mua không được âm !");
//                        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
//
//                    }
//                }
//
//                System.out.println("Số lượng mua int: "+ soLuongMua);
//                System.out.println("Số lượng có int : "+ soLuongCo);
//
//                //
//                System.out.println("Danh sách idGiayChiTiet đã chọn:");
//                System.out.println(idGiayChiTiet.get(Integer.parseInt(stt)));
//
//                System.out.println("Danh sách idGiayChiTiet đã chọn:");
//                UUID selectedGiayChiTietId = idGiayChiTiet.get(Integer.parseInt(stt));
//                System.out.println(selectedGiayChiTietId);
//
//                // Tìm và cập nhật GioHangChiTiet
//                GioHangChiTiet gioHangChiTietSetSoLuong = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(selectedGiayChiTietId);
//
//                if (gioHangChiTietSetSoLuong != null) {
//                    // Cập nhật số lượng mua
//                    System.out.println("Set số lượng lại thành công !");
//                    gioHangChiTietSetSoLuong.setSoLuong(String.valueOf(soLuongMua));
//
//                    // Get the associated GiayTheThao
//                    GiayTheThaoChiTiet giayTheThaoChiTietSL = gioHangChiTietSetSoLuong.getGiayTheThaoChiTiet();
//
//                    if (giayTheThaoChiTietSL != null) {
//
//                        GiayTheThao giayTheThao = giayTheThaoChiTiet.getGiayTheThao();
//
//                        System.out.println("Id của GiayTheThao: " + giayTheThao.getId());
//
//                        String giaBanNew = giayTheThao.getGiaBan();
//
//                        // Convert the String to BigDecimal
//                        BigDecimal giaBan = new BigDecimal(giaBanNew);
//
//                        // Calculate DonGia
//                        BigDecimal donGiaNew = giaBan.multiply(BigDecimal.valueOf(soLuongMua));
//
//                        // Set the calculated value for DonGia
//                        gioHangChiTietSetSoLuong.setDonGia(donGiaNew);
//
//                        //Set cho đơn giá
//
//                        //set số lượng vào hóa đơn chi tiết
//                        hoaDonChiTiet.setSoLuong(String.valueOf(Integer.parseInt(soLuong.get(Integer.parseInt(stt)))));
//
//
//                        // Convert the String to BigDecimal
//                        BigDecimal giaBanHoaDonChiTiet = new BigDecimal(giaBanNew);
//
//                        BigDecimal donGiaHoaDonChiTiet = giaBanHoaDonChiTiet.multiply(BigDecimal.valueOf(soLuongMua));
//
//                        hoaDonChiTiet.setDonGia(donGiaHoaDonChiTiet);
//                        hoaDonChiTiet.setTrangThai(1);
//                        System.out.println("Đơn giá mới của hóa đơn là : "+donGiaHoaDonChiTiet);
//
//                        hoaDonChiTietRepository.save(hoaDonChiTiet);
//
//                        //Sau khi lưu đơn giá của hóa đơn chi tiết xong tôi muốn set thành tiền cho hóa đơn thành tiền
//                        //của hóa đơn sẽ là tổng đơn giá trong hóa đơn chi tiết
//
//
//                        tongDonGiaHoaDon = tongDonGiaHoaDon.add(donGiaHoaDonChiTiet);
//                        hoaDon.setThanhTien(tongDonGiaHoaDon);
//
//                        hoaDonRepository.save(hoaDon);
//
//                    }
//
//                    gioHangChiTietRepository.save(gioHangChiTietSetSoLuong);
//
//                } else {
//
//                    System.out.println("Không tìm thấy GioHangChiTiet cho id: " + selectedGiayChiTietId);
//
//                }
//
//                model.addAttribute("hoaDonChiTiet", hoaDonChiTiet);
//
//            }
//
//            //Bên
//            model.addAttribute("hoaDon", hoaDon);
//
//            return "redirect:/nguoiDung/HoaDon/" + hoaDon.getId();
//        }
//
//        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
//
//    }
//
//
//}


//import jakarta.transaction.Transactional;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@PostMapping("nguoiDung/hoaDon/ThanhToan/{id}")
//@Transactional
//public String showEditViewThanhToanHoaDon(Model model,
//@PathVariable String id,
//        HttpSession session,
//        HttpServletRequest request,
//        RedirectAttributes attributes) throws JsonProcessingException {
//
//        if(session.getAttribute("khachHangLog") != null) {
//
//        UUID hoaDonId = UUID.fromString(id);
//        session.setAttribute("hoaDonId", hoaDonId);
//
//        //Todo code mã code mới bên controller
//
//        String diaChiChon = request.getParameter("diaChiChon");
//
//        //Địa chỉ chon là null
//        if (diaChiChon == null || diaChiChon.isEmpty()) {
//
//        attributes.addFlashAttribute("diaChiChonNull", "Xin lỗi vui lòng chọn địa chỉ để có thể thanh toán!");
//        System.out.println("Vui lòng chọn một địa chỉ để có thể thanh toán");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        } else {
//
//        //Hình thức thanh thanh toán không được là null
//        String hinhThucThanhToan = request.getParameter("payment");
//
//        //Hình thức thanh toán
//        if (hinhThucThanhToan == null || hinhThucThanhToan.isEmpty()) {
//
//        attributes.addFlashAttribute("hinhThucThanhToanNull", "Vui lòng chọn hình thức thanh toán!");
//        System.out.println("Vui lòng chọn hình thức thanh toán");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        } else {
//
//        //Chọn địa chỉ cũ để thanh toán
//        if ("diaChiCu".equals(diaChiChon)) {
//
//        System.out.println("Bạn chọn địa chỉ cũ để thanh toán");
//
//        //Thanh toán bằng địa chỉ cũ qua thanh toán momo
//        if ("momo".equals(hinhThucThanhToan)) {
//
//        System.out.println("Bạn chọn thanh toán bằng hình thức momo");
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
//
//        if (hoaDon != null) {
//
//        String tenKhachHangCu = request.getParameter("tenKhachHang");
//        String soDienThoaiCuMomo = request.getParameter("soDienThoai");
//        String quocGiaCuMomo = request.getParameter("quocGia");
//        String thanhPhoCuMomo = request.getParameter("thanhPho");
//        String diaChiCuMomo = request.getParameter("diaChi");
//        String messCuMomo = request.getParameter("mess");
//        String thanhTien = request.getParameter("thanhTien");
//        String phiShip = request.getParameter("ship");
//        String thanhTienTong1 = request.getParameter("tongTien");
//
//        System.out.println("Thành tiền : " + thanhTien);
//        System.out.println("Phí ship : " + phiShip);
//        System.out.println("Tổng tiền : " + thanhTienTong1);
//
////                            BigDecimal thanhTienBigDecimal = new BigDecimal(thanhTienTong1);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
//        String orderId = Integer.toString(code);
//        MomoModel jsonRequest = new MomoModel();
//        jsonRequest.setPartnerCode(Constant.IDMOMO);
//        jsonRequest.setOrderId(orderId);
//        jsonRequest.setStoreId(orderId);
//        jsonRequest.setRedirectUrl(Constant.redirectUrl);
//        jsonRequest.setIpnUrl(Constant.ipnUrl);
//        jsonRequest.setAmount(thanhTienTong1);
//        jsonRequest.setOrderInfo("Thanh toán sản phẩm của hàng Bess Shoes.");
//        jsonRequest.setRequestId(orderId);
//        jsonRequest.setOrderType(Constant.orderType);
//        jsonRequest.setRequestType(Constant.requestType);
//        jsonRequest.setTransId("1");
//        jsonRequest.setResultCode("200");
//        jsonRequest.setMessage("");
//        jsonRequest.setPayType(Constant.payType);
//        jsonRequest.setResponseTime("300000");
//        jsonRequest.setExtraData("");
//
//        String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
//        + jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
//        + jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
//        + Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
//        + Constant.requestType;
//
//
//        String signature = Decode.encode(Constant.serectkey, decode);
//        jsonRequest.setSignature(signature);
//        String json = mapper.writeValueAsString(jsonRequest);
//        HttpClient client = HttpClient.newHttpClient();
//        ResultMoMo res = new ResultMoMo();
//
//        try {
//        HttpRequest requestMomo = HttpRequest.newBuilder().uri(new URI(Constant.Url))
//        .POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
//        .build();
//        HttpResponse<String> response = client.send(requestMomo, HttpResponse.BodyHandlers.ofString());
//        res = mapper.readValue(response.body(), ResultMoMo.class);
//
//        } catch (InterruptedException | URISyntaxException | IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//        }
//        if (res == null) {
//
//        session.setAttribute("error_momo", "Thanh toán thất bại");
//        System.out.println("Thanh toán thất bại");
//        return "redirect:/TrangChu/listGiayTheThao";
//
//
//        } else {
//
//        //Hiện ra trang để quét mã QR
//        System.out.println("Lưu lại thông tin hóa đơn khi thanh toán bằng momo");
//
//        String ship = request.getParameter("ship");
//
//        if (ship.matches("\\d*\\.?\\d*")) {
//        BigDecimal shipBigDecimal = new BigDecimal(ship);
//        System.out.println("Giá trị shipBigDecimal: " + shipBigDecimal);
//        hoaDon.setPhiShip(shipBigDecimal);
//
//        //Tính thành tiền
//        BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienTong1);
//
//        // Trừ giá trị ship từ tổng tiền
//        BigDecimal thanhTienSauShip = thanhTienBigDecemal.subtract(shipBigDecimal);
//        hoaDon.setThanhTien(thanhTienSauShip);
//
//
//        } else {
//
//        System.out.println("Chuỗi không hợp lệ");
//
//        }
//
//        hoaDon.setTrangThai(1);
//        hoaDon.setTrangThaiMoney(1);
//        hoaDon.setHinhThuc(0);
//        hoaDon.setHinhThucThanhToan(2);
//        hoaDon.setGhiChu("Tên khách hàng: "+ tenKhachHangCu+ "," + "Số điện thoại nhận hàng: " + soDienThoaiCuMomo + ", Địa chỉ giao hàng: " + diaChiCuMomo + "," + thanhPhoCuMomo + "," + quocGiaCuMomo);
//        hoaDon.setMess(messCuMomo);
//
//        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
//        model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);
//
//        //Tìm hóa đơn để tính số lượng
//        for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {
//
//        //Số lượng mua của khách hàng
//        String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
//        int soLuongMua = Integer.parseInt(soLuongMuaToString);
//
//        //Số lượng có trong kho
//        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
//        String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
//        int soLuongCo = Integer.parseInt(soLuongCoToString);
//
//        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
//        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);
//
//        }
//
//
//        hoaDonRepository.save(hoaDon);
//        //Xóa giỏ hang chi tiết khi thanh toán
//        String[] idGiayTheTheThaoChiTietArrayMomoCu = request.getParameterValues("idGiayTheTheThaoChiTiet");
//
//        if (idGiayTheTheThaoChiTietArrayMomoCu != null) {
//        for (String idGiayTheTheThaoChiTiet : idGiayTheTheThaoChiTietArrayMomoCu) {
//        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTiet);
//
//        // Tìm giỏ hàng chi tiết để xóa
//        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);
//
//        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
//        if (gioHangChiTiet != null) {
//        // Xóa giỏ hàng chi tiết
//        gioHangChiTietRepository.delete(gioHangChiTiet);
//
//        }
//        }
//        }
//
//        return "redirect:" + res.payUrl;
//
//        }
//
//        }
//
//        }
//
//        //Thanh toán bằng địa chỉ cũ qua thanh toán khi nhận hàng
//        if ("cash".equals(hinhThucThanhToan)) {
//
//        System.out.println("Bạn chọn thanh toán bằng hình thức thanh toán khi nhận hàng");
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
//
//        if (hoaDon != null) {
//
//        String tenKhachHangCu = request.getParameter("tenKhachHang");
//        String soDienThoaiCu = request.getParameter("soDienThoai");
//        String quocGiaCu = request.getParameter("quocGia");
//        String thanhPhoCu = request.getParameter("thanhPho");
//        String diaChiCu = request.getParameter("diaChi");
//        String messCu = request.getParameter("mess");
//        String thanhTienTong1 = request.getParameter("tongTien");
//        String[] idGiayTheTheThaoChiTietArray = request.getParameterValues("idGiayTheTheThaoChiTiet");
//
//        //Todo gọi id để xóa giỏ hàng chi tiết
//        String idGiayTheTheThaoChiTiet = request.getParameter("idGiayTheTheThaoChiTiet");
//
//        // Lấy giá trị ship từ request
//        String ship = request.getParameter("ship");
//
//        // Loại bỏ ký tự không mong muốn
//        ship = ship.replaceAll("[^0-9]", "");
//
//        // Kiểm tra xem chuỗi có giá trị không rỗng
//        if (!ship.isEmpty()) {
//        // Chuyển đổi chuỗi thành số nguyên
//        int shipValue = Integer.parseInt(ship);
//
//        // In giá trị đã chuyển đổi
//        System.out.println("Giá trị ship sau khi chuyển đổi: " + shipValue);
//
//        } else {
//
//        System.out.println("Không có giá trị ship hợp lệ.");
//
//        }
//
//        if (ship.matches("\\d*\\.?\\d*")) {
//        BigDecimal shipBigDecimal = new BigDecimal(ship);
//        System.out.println("Giá trị shipBigDecimal: " + shipBigDecimal);
//        hoaDon.setPhiShip(shipBigDecimal);
//
//        //Tính thành tiền
//        BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienTong1);
//
//        // Trừ giá trị ship từ tổng tiền
//        BigDecimal thanhTienSauShip = thanhTienBigDecemal.subtract(shipBigDecimal);
//        hoaDon.setThanhTien(thanhTienSauShip);
//
//
//        } else {
//
//        System.out.println("Chuỗi không hợp lệ");
//
//        }
//
//
//        hoaDon.setTrangThai(1);
//        hoaDon.setTrangThaiMoney(0);
//        hoaDon.setHinhThuc(0);
//        hoaDon.setHinhThucThanhToan(3);
//
//        hoaDon.setNgayThanhToan(LocalDateTime.now());
//        hoaDon.setNgayTao(LocalDateTime.now());
//        hoaDon.setGhiChu("Tên khách hàng: "+ tenKhachHangCu + "," + "Số điện thoại nhận hàng: " + soDienThoaiCu + ", Địa chỉ giao hàng: " + diaChiCu + "," + thanhPhoCu + "," + quocGiaCu);
//        hoaDon.setMess(messCu);
//
//        //Lấy số lượng hiện tại trừ đi
//
//        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
//        model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);
//
//        //Tìm hóa đơn để tính số lượng
//        for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {
//
//        //Số lượng mua của khách hàng
//        String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
//        int soLuongMua = Integer.parseInt(soLuongMuaToString);
//
//        //Số lượng có trong kho
//        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
//        String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
//        int soLuongCo = Integer.parseInt(soLuongCoToString);
//
//        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
//        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);
//
//        }
//
//        hoaDonRepository.save(hoaDon);
//
//        String[] idGiayTheTheThaoChiTietArrayCashCu = request.getParameterValues("idGiayTheTheThaoChiTiet");
//
//        if (idGiayTheTheThaoChiTietArrayCashCu != null) {
//        for (String idGiayTheTheThaoChiTietCashCu : idGiayTheTheThaoChiTietArrayCashCu) {
//        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTietCashCu);
//
//        // Tìm giỏ hàng chi tiết để xóa
//        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);
//
//        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
//        if (gioHangChiTiet != null) {
//        // Xóa giỏ hàng chi tiết
//        gioHangChiTietRepository.delete(gioHangChiTiet);
//
//        }
//        }
//        }
//
//        model.addAttribute("idKH", hoaDon.getKhachHang().getId());
//        return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";
//
//        }
//
//        }
//
//        //Thanh toán bằng ví điện tử
//        if ("vi".equals(hinhThucThanhToan)) {
//
//        System.out.println("Bạn chọn thanh toán bằng ví điện tử");
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
//        ViTien viTien = viTienServiceImpl.findByIdKhachHang(hoaDon.getKhachHang().getId());
//
//        if(viTien.getThanhTien().intValue() < hoaDon.getThanhTien().intValue()){
//
//        attributes.addFlashAttribute("erVi","Xin lỗi quý khách hiện tại số dư trong ví của quý khách không đủ để thanh toán !");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }else {
//
//        if (hoaDon != null) {
//
//        String tenKhachHangCu = request.getParameter("tenKhachHang");
//        String soDienThoaiCu = request.getParameter("soDienThoai");
//        String quocGiaCu = request.getParameter("quocGia");
//        String thanhPhoCu = request.getParameter("thanhPho");
//        String diaChiCu = request.getParameter("diaChi");
//        String messCu = request.getParameter("mess");
//        String thanhTienTong1 = request.getParameter("tongTien");
//
//        // Lấy giá trị ship từ request
//        String ship = request.getParameter("ship");
//
//        // Loại bỏ ký tự không mong muốn
//        ship = ship.replaceAll("[^0-9]", "");
//
//        // Kiểm tra xem chuỗi có giá trị không rỗng
//        if (!ship.isEmpty()) {
//        // Chuyển đổi chuỗi thành số nguyên
//        int shipValue = Integer.parseInt(ship);
//
//        // In giá trị đã chuyển đổi
//        System.out.println("Giá trị ship sau khi chuyển đổi: " + shipValue);
//
//        } else {
//
//        System.out.println("Không có giá trị ship hợp lệ.");
//
//        }
//
//        if (ship.matches("\\d*\\.?\\d*")) {
//        BigDecimal shipBigDecimal = new BigDecimal(ship);
//        System.out.println("Giá trị shipBigDecimal: " + shipBigDecimal);
//        hoaDon.setPhiShip(shipBigDecimal);
//
//        //Tính thành tiền
//        BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienTong1);
//
//        // Trừ giá trị ship từ tổng tiền
//        BigDecimal thanhTienSauShip = thanhTienBigDecemal.subtract(shipBigDecimal);
//        hoaDon.setThanhTien(thanhTienSauShip);
//
//        //Trừ tiền trong ví điện tử đi và lưu lại lấy mã giao dịch
//
////                                        BigDecimal tongTienVi = viTien.getThanhTien().subtract(hoaDon.getThanhTien());
//        System.out.println("Thành tiền thanhTienBigDecemal "+ thanhTienBigDecemal);
//        System.out.println("Thành tiền request "+ thanhTienTong1);
//
//        BigDecimal tongTienVi = viTien.getThanhTien().subtract(thanhTienBigDecemal);
//
//        ViTien viTienNew = new ViTien();
//
//        viTienNew.setMaViTien(viTien.getMaViTien());
//        viTienNew.setKhachHang(viTien.getKhachHang());
//        viTienNew.setThanhTien(tongTienVi);
//        viTienNew.setTrangThai(1);
//
//        viTienServiceImpl.update(viTien.getId(),viTienNew);
//
//        //Giao dịch ví
//
//        LocalDateTime now = LocalDateTime.now();
//
//        GiaoDichViChiTiet giaoDichViChiTiet = new GiaoDichViChiTiet();
//
//        giaoDichViChiTiet.setMaGiaoDichViChiTiet("GiaoDV"+now.getMonthValue() + now.getDayOfMonth() + now.getHour() + now.getMinute() + now.getSecond());
//        giaoDichViChiTiet.setViTien(viTien);
//        giaoDichViChiTiet.setNgayGiaoDich(LocalDateTime.now());
//
//        BigDecimal thanhTienGiaoDich = hoaDon.getThanhTien();
//        BigDecimal phiShipGiaoDich = hoaDon.getPhiShip();
//
//        //Cộng lại
//        BigDecimal tongTienGiaoDichNew = thanhTienGiaoDich.add(phiShipGiaoDich);
//        giaoDichViChiTiet.setDonGia(tongTienGiaoDichNew);
//        giaoDichViChiTiet.setHinhThuc(2);
//        giaoDichViChiTiet.setTrangThai(1);
//
//        giaoDichViChiTietServiceImpl.add(giaoDichViChiTiet);
//
//
//        } else {
//
//        System.out.println("Chuỗi không hợp lệ");
//
//        }
//
//        hoaDon.setTrangThai(1);
//        hoaDon.setTrangThaiMoney(1);
//        hoaDon.setHinhThuc(0);
//        hoaDon.setHinhThucThanhToan(1);
//
//        hoaDon.setNgayThanhToan(LocalDateTime.now());
//        hoaDon.setNgayTao(LocalDateTime.now());
//        hoaDon.setGhiChu("Tên khách hàng: "+ tenKhachHangCu + "" + ", Số điện thoại nhận hàng: " + soDienThoaiCu + ", Địa chỉ giao hàng: " + diaChiCu + "," + thanhPhoCu + "," + quocGiaCu);
//        hoaDon.setMess(messCu);
//
//        //Lấy số lượng hiện tại trừ đi
//
//        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
//        model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);
//
//        //Tìm hóa đơn để tính số lượng
//        for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {
//
//        //Số lượng mua của khách hàng
//        String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
//        int soLuongMua = Integer.parseInt(soLuongMuaToString);
//
//        //Số lượng có trong kho
//        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
//        String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
//        int soLuongCo = Integer.parseInt(soLuongCoToString);
//
//        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
//        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);
//
//        }
//
//        hoaDonRepository.save(hoaDon);
//
//        String[] idGiayTheTheThaoChiTietArrayCashCu = request.getParameterValues("idGiayTheTheThaoChiTiet");
//
//        //Xóa sản phẩm trong giỏ hàng chi tiết
//        if (idGiayTheTheThaoChiTietArrayCashCu != null) {
//        for (String idGiayTheTheThaoChiTietCashCu : idGiayTheTheThaoChiTietArrayCashCu) {
//        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTietCashCu);
//
//        // Tìm giỏ hàng chi tiết để xóa
//        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);
//
//        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
//        if (gioHangChiTiet != null) {
//        // Xóa giỏ hàng chi tiết
//        gioHangChiTietRepository.delete(gioHangChiTiet);
//
//        }
//        }
//        }
//
//        model.addAttribute("idKH", hoaDon.getKhachHang().getId());
//        return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";
//
//        }
//
//        }
//
//        }
//
//        }
//
//        //Chọn địa chỉ mới để thanh toán
//        if ("diaChiMoi".equals(diaChiChon)) {
//
//        System.out.println("Bạn chọn địa chỉ cũ để thanh toán ");
//
//        if ("momo".equals(hinhThucThanhToan)) {
//
//        System.out.println("Bạn chọn hình thức thanh toán bằng momo cho địa chỉ mới");
//
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
////
//        if (hoaDon != null) {
//
//        String tenKhachHangMoiMomo = request.getParameter("ten1");
//        String emailMoiMomo = request.getParameter("email1");
//        String soDienThoaiMoiMomo = request.getParameter("sdt1");
//        String quocGiaMoiMomo = request.getParameter("quocGia1");
//        String thanhPhoMoiMomo = request.getParameter("thanhPho1");
//        String diaChiMoiMomo = request.getParameter("diaChi1");
//        String messMoiMomo = request.getParameter("messMoi");
//        String thanhTienMomo = request.getParameter("tongTien");
//
//
//        System.out.println("Tổng tiền : " + thanhTienMomo);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
//        String orderId = Integer.toString(code);
//        MomoModel jsonRequest = new MomoModel();
//        jsonRequest.setPartnerCode(Constant.IDMOMO);
//        jsonRequest.setOrderId(orderId);
//        jsonRequest.setStoreId(orderId);
//        jsonRequest.setRedirectUrl(Constant.redirectUrl);
//        jsonRequest.setIpnUrl(Constant.ipnUrl);
//        jsonRequest.setAmount(thanhTienMomo);
//        jsonRequest.setOrderInfo("Thanh toán sản phẩm của hàng Bess Shoes.");
//        jsonRequest.setRequestId(orderId);
//        jsonRequest.setOrderType(Constant.orderType);
//        jsonRequest.setRequestType(Constant.requestType);
//        jsonRequest.setTransId("1");
//        jsonRequest.setResultCode("200");
//        jsonRequest.setMessage("");
//        jsonRequest.setPayType(Constant.payType);
//        jsonRequest.setResponseTime("300000");
//        jsonRequest.setExtraData("");
//
//        String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
//        + jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
//        + jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
//        + Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
//        + Constant.requestType;
//
//
//        String signature = Decode.encode(Constant.serectkey, decode);
//        jsonRequest.setSignature(signature);
//        String json = mapper.writeValueAsString(jsonRequest);
//        HttpClient client = HttpClient.newHttpClient();
//        ResultMoMo res = new ResultMoMo();
//
//        try {
//        HttpRequest requestMomo = HttpRequest.newBuilder().uri(new URI(Constant.Url))
//        .POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
//        .build();
//        HttpResponse<String> response = client.send(requestMomo, HttpResponse.BodyHandlers.ofString());
//        res = mapper.readValue(response.body(), ResultMoMo.class);
//
//        } catch (InterruptedException | URISyntaxException | IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//        }
//        if (res == null) {
//
//        session.setAttribute("error_momo", "Thanh toán thất bại");
//        System.out.println("Thanh toán thất bại");
//        return "redirect:/TrangChu/listGiayTheThao";
//
//
//        } else {
//
//
//        //Hiện ra trang để quét mã QR
//        System.out.println("Lưu lại thông tin hóa đơn khi thanh toán bằng momo");
//
//        hoaDon.setTrangThai(1);
//        hoaDon.setTrangThaiMoney(1);
//        hoaDon.setHinhThuc(0);
//        hoaDon.setHinhThucThanhToan(2);
//
//        String ship = request.getParameter("ship");
//        if (ship.matches("\\d*\\.?\\d*")) {
//        BigDecimal shipBigDecimal = new BigDecimal(ship);
//        System.out.println("Giá trị shipBigDecimal: " + shipBigDecimal);
//        hoaDon.setPhiShip(shipBigDecimal);
//
//        //Tính thành tiền
//        BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienMomo);
//
//        // Trừ giá trị ship từ tổng tiền
//        BigDecimal thanhTienSauShip = thanhTienBigDecemal.subtract(shipBigDecimal);
//        hoaDon.setThanhTien(thanhTienSauShip);
//
//
//        } else {
//
//        System.out.println("Chuỗi không hợp lệ");
//
//        }
//
//
//        hoaDon.setGhiChu("Tên khách hàng: " + tenKhachHangMoiMomo + ";" + "Số điện thoại nhận hàng: " + soDienThoaiMoiMomo + ", Địa chỉ giao hàng: " + diaChiMoiMomo + "," + thanhPhoMoiMomo + "," + quocGiaMoiMomo);
//        hoaDon.setMess(messMoiMomo);
//
//        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
//        model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);
//
//        //Tìm hóa đơn để tính số lượng
//        for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {
//
//        //Số lượng mua của khách hàng
//        String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
//        int soLuongMua = Integer.parseInt(soLuongMuaToString);
//
//        //Số lượng có trong kho
//        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
//        String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
//        int soLuongCo = Integer.parseInt(soLuongCoToString);
//
//        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
//        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);
//
//        }
//
//        hoaDonRepository.save(hoaDon);
//
//        //Xóa giỏ hàng chi tiết
//        String[] idGiayTheTheThaoChiTietArrayMomoMoi = request.getParameterValues("idGiayTheTheThaoChiTiet");
//
//        if (idGiayTheTheThaoChiTietArrayMomoMoi != null) {
//        for (String idGiayTheTheThaoChiTiet : idGiayTheTheThaoChiTietArrayMomoMoi) {
//        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTiet);
//
//        // Tìm giỏ hàng chi tiết để xóa
//        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);
//
//        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
//        if (gioHangChiTiet != null) {
//        // Xóa giỏ hàng chi tiết
//        gioHangChiTietRepository.delete(gioHangChiTiet);
//
//        }
//        }
//        }
//        return "redirect:" + res.payUrl;
//
//        }
//
//        }
//
//        }
//
//        //Bạn chọn thanh toán khi nhận hàng ở địa chỉ mới
//        if ("cash".equals(hinhThucThanhToan)) {
//
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
//
//        if (hoaDon != null) {
//
//        String tenKhachHangMoi = request.getParameter("ten1");
//        String emailMoi = request.getParameter("email1");
//        String soDienThoaiMoi = request.getParameter("sdt1");
//        String quocGiaMoi = request.getParameter("quocGia1");
//        String thanhPhoMoi = request.getParameter("thanhPho1");
//        String diaChiMoi = request.getParameter("diaChi1");
//        String messMoi = request.getParameter("messMoi");
//        String thanhTien = request.getParameter("tongTien");
//
//        //Check trống các trường
//
//        //Check trống tên khách hàng
//        if (tenKhachHangMoi == null
//        || tenKhachHangMoi.trim().length() == 0
//        || tenKhachHangMoi.isEmpty()) {
//
//        attributes.addFlashAttribute("tenKhachHangMoiNull", "Xin lỗi tên khách hàng không được để trống");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//        //Check trống email
//        if (emailMoi.trim().length() == 0
//        || emailMoi == null
//        || emailMoi.isEmpty()) {
//
//        attributes.addFlashAttribute("emailMoiNull", "Xin lỗi tên email không được để trống");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//        //Check trống số điện thoại
//
//        if (soDienThoaiMoi.trim().length() == 0
//        || soDienThoaiMoi.isEmpty()
//        || soDienThoaiMoi == null) {
//
//        attributes.addFlashAttribute("soDienThoaiMoiNull", "Xin lỗi không được để trống số điện thoại !");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//        //check số điện thoại nhập là chữ
//        try {
//
//        int soDienThoaiFomat = Integer.parseInt(soDienThoaiMoi);
//
//        if (soDienThoaiFomat < 0) {
//
//        attributes.addFlashAttribute("erLogSoDienThoai0", "Số điện thoại không được nhỏ hơn 0");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//
//        } catch (NumberFormatException e) {
//
//        e.printStackTrace();
//        attributes.addFlashAttribute("erLogSoDienThoaiChonChu", "Số điện thoại không được là chữ !");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//        // Validate số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số
//        String phoneNumberRegex = "^0\\d{9}$";
//
//        if (!soDienThoaiMoi.matches(phoneNumberRegex)) {
//
//        attributes.addFlashAttribute("erLogSoDienThoaiNumber", "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số.");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//        if (quocGiaMoi == null
//        || quocGiaMoi.trim().length() == 0
//        || quocGiaMoi.isEmpty()) {
//
//        attributes.addFlashAttribute("quocGiaMoiNull", "Không được để trống tỉnh !");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//        if (thanhPhoMoi == null
//        || thanhPhoMoi.trim().length() == 0
//        || thanhPhoMoi.isEmpty()) {
//
//        attributes.addFlashAttribute("thanhPhoMoiNull", "Không được để trống huyện !");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }
//
//        if (diaChiMoi == null
//        || diaChiMoi.isEmpty()
//        || diaChiMoi.trim().length() == 0) {
//
//        attributes.addFlashAttribute("diaChiMoiNull", "Không được để trống xã !");
//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//
//        }
//        //Lưu lại đơn hàng
//
//
//        hoaDon.setNgayThanhToan(LocalDateTime.now());
//        hoaDon.setNgayTao(LocalDateTime.now());
//        hoaDon.setTrangThai(1);
//        hoaDon.setTrangThaiMoney(0);
//        hoaDon.setHinhThuc(0);
//        hoaDon.setHinhThucThanhToan(3);
//
//        hoaDon.setGhiChu("Tên khách hàng: " + tenKhachHangMoi + ", Số điện thoại nhận hàng: " + soDienThoaiMoi + ", Địa chỉ giao hàng: " + diaChiMoi + "," + thanhPhoMoi + "," + quocGiaMoi);
//        hoaDon.setMess(messMoi);
//
//
//        String ship = request.getParameter("ship");
//
//        if (ship.matches("\\d*\\.?\\d*")) {
//        BigDecimal shipBigDecimal = new BigDecimal(ship);
//        System.out.println("Giá trị shipBigDecimal: " + shipBigDecimal);
//        hoaDon.setPhiShip(shipBigDecimal);
//
//        //Tính thành tiền
//        BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTien);
//
//        // Trừ giá trị ship từ tổng tiền
//        BigDecimal thanhTienSauShip = thanhTienBigDecemal.subtract(shipBigDecimal);
//        hoaDon.setThanhTien(thanhTienSauShip);
//
//
//        } else {
//
//        System.out.println("Chuỗi không hợp lệ");
//
//        }
//
//
//        //Lấy số lượng hiện tại trừ đi
//
//        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
//        model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);
//
//        //Tìm hóa đơn để tính số lượng
//        for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {
//
//        //Số lượng mua của khách hàng
//        String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
//        int soLuongMua = Integer.parseInt(soLuongMuaToString);
//
//        //Số lượng có trong kho
//        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
//        String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
//        int soLuongCo = Integer.parseInt(soLuongCoToString);
//
//        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
//        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);
//
//        }
//
//        hoaDonRepository.save(hoaDon);
//
//        String[] idGiayTheTheThaoChiTietArrayCashMoi = request.getParameterValues("idGiayTheTheThaoChiTiet");
//
//        if (idGiayTheTheThaoChiTietArrayCashMoi != null) {
//        for (String idGiayTheTheThaoChiTiet : idGiayTheTheThaoChiTietArrayCashMoi) {
//        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTiet);
//
//        // Tìm giỏ hàng chi tiết để xóa
//        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);
//
//        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
//        if (gioHangChiTiet != null) {
//        // Xóa giỏ hàng chi tiết
//        gioHangChiTietRepository.delete(gioHangChiTiet);
//
//        }
//        }
//        }
//        model.addAttribute("idKH", hoaDon.getKhachHang().getId());
//        return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";
//
//        }
//
//        }
//
//        }
//
//        }
//
//        return "redirect:/TrangChu/listGiayTheThao";
//
//        }
//
//        }else{
//
//        System.out.println("Khách hàng chưa đăng nhập tài khoản");
//
//        return "redirect:/TrangChu/listGiayTheThao";
//
//        }
//
//        }
//
//






