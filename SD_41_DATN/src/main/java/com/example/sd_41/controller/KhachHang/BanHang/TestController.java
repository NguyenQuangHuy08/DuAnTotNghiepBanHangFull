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
