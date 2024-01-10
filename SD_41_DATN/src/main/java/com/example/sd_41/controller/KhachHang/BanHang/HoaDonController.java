package com.example.sd_41.controller.KhachHang.BanHang;

import com.example.sd_41.controller.Momo.MomoModel;
import com.example.sd_41.controller.Momo.ResultMoMo;
import com.example.sd_41.controller.Utils.Constant;
import com.example.sd_41.controller.Utils.Decode;
import com.example.sd_41.model.*;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.MauSacRepository;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.SizeRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.HoaDon.HoaDonServiceImpl;
import com.example.sd_41.service.impl.ChuongTrinhGiamGiaHoaDonImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.sd_41.service.ViTien.Impl.giaoDichViChiTietServiceImpl;
import com.example.sd_41.service.ViTien.Impl.viTienServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class HoaDonController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private HoaDonServiceImpl hoaDonServiceImpl;

    @Autowired
//    @Qualifier("giayTheThaoChiTietRepository")
    private GiayTheThaoChiTietRepository giayTheThaoChiTietRepository;

    @Autowired
    private GiayTheThaoRepository giayTheThaoRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    ChuongTrinhGiamGiaHoaDonImpl chuongTrinhGiamGiaHoaDonImpl;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    giaoDichViChiTietServiceImpl giaoDichViChiTietServiceImpl;

    @Autowired
    viTienServiceImpl viTienServiceImpl;


    //Todo code view hóa đơn
    //hiện thông tin cho view hóa đơn

    @GetMapping("nguoiDung/HoaDon/{id}")
    public String showViewHoaDon(
            @PathVariable String id,
            Model model,
            RedirectAttributes attributes,
            HttpSession session,
            HttpServletRequest request) {

        if(session.getAttribute("khachHangLog") != null) {

            UUID hoaDonId = UUID.fromString(id);
            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
            model.addAttribute("hoaDonId", hoaDonId);

            if (hoaDon != null) {

                List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);

                //Hiện view thông tin của hóa đơn
                model.addAttribute("hoaDonChiTietList", hoaDonChiTietList);
                model.addAttribute("hoaDon1", hoaDonId);
                model.addAttribute("hoaDon", hoaDon);
                model.addAttribute("maHoaDon", hoaDon.getMaHoaDon());
                model.addAttribute("idKhacHang", hoaDon.getKhachHang().getId());
                model.addAttribute("tenKhachHang", hoaDon.getKhachHang().getTenKhachHang());
                model.addAttribute("diaChi", hoaDon.getKhachHang().getDiaChi());
                model.addAttribute("soDienThoai", hoaDon.getKhachHang().getSoDienThoai());
                model.addAttribute("email", hoaDon.getKhachHang().getEmail());
                model.addAttribute("thanhPho", hoaDon.getKhachHang().getThanhPho());
                model.addAttribute("huyen", hoaDon.getKhachHang().getHuyen());
                model.addAttribute("xa", hoaDon.getKhachHang().getXa());

                //Thành tiền của hóa đơn
                //Todo code thông tin cho giảm hóa đơn

                String sl = hoaDonServiceImpl.tongSl(hoaDonId);
                System.out.println("Thành tiền :" + hoaDon.getThanhTien());
                System.out.println("Số lượng : " + sl);
                System.out.println("Id của hóa đơn :" + hoaDonId);
                String idGiayTheThaoChiTiet = request.getParameter("idGiayTheThaoChiTiet");
                System.out.println("ID giầy thể thao chi tiết: " + idGiayTheThaoChiTiet);


                List<UUID> hoaDonChiTietIds = new ArrayList<>();

                // Duyệt qua danh sách và lấy id của từng hóa đơn chi tiết
                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {

                    hoaDonChiTietIds.add(hoaDonChiTiet.getId());

                }

                ///Hiện ra thông tin của các chương trình giảm giá
                model.addAttribute("CTGG", chuongTrinhGiamGiaHoaDonImpl.findBySlandTt(sl, hoaDon.getThanhTien()));

            } else {

                System.out.println("Xin lỗi không tìm thấy hóa đơn phù hợp cho khách hàng này");

            }

            return "/templates/Users/Layouts/Shop/viewHoaDon";

        }else{

            System.out.println("Chưa đăng nhập tài khoản!");
            return "redirect:/TrangChu/listGiayTheThao";

        }

    }

    //Todo code thanh toán thành công chờ xác nhận đơn hàng
    @PostMapping("nguoiDung/hoaDon/ThanhToan/{id}")
    @Transactional
    public String showEditViewThanhToanHoaDon(Model model,
                                              @PathVariable String id,
                                              HttpSession session,
                                              HttpServletRequest request,
                                              RedirectAttributes attributes) throws JsonProcessingException {

        if(session.getAttribute("khachHangLog") != null) {

            UUID hoaDonId = UUID.fromString(id);
            session.setAttribute("hoaDonId", hoaDonId);

            //Todo code mã code mới bên controller

            String diaChiChon = request.getParameter("diaChiChon");

            //Địa chỉ chon là null
            if (diaChiChon == null || diaChiChon.isEmpty()) {

                attributes.addFlashAttribute("diaChiChonNull", "Xin lỗi vui lòng chọn địa chỉ để có thể thanh toán!");
                System.out.println("Vui lòng chọn một địa chỉ để có thể thanh toán");
                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

            } else {

                //Hình thức thanh thanh toán không được là null
                String hinhThucThanhToan = request.getParameter("payment");

                //Hình thức thanh toán
                if (hinhThucThanhToan == null || hinhThucThanhToan.isEmpty()) {

                    attributes.addFlashAttribute("hinhThucThanhToanNull", "Vui lòng chọn hình thức thanh toán!");
                    System.out.println("Vui lòng chọn hình thức thanh toán");
                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                } else {

                    //Chọn địa chỉ cũ để thanh toán
                    if ("diaChiCu".equals(diaChiChon)) {

                        System.out.println("Bạn chọn địa chỉ cũ để thanh toán");


                        //Thanh toán bằng địa chỉ cũ qua thanh toán khi nhận hàng
                        if ("cash".equals(hinhThucThanhToan)) {

                            System.out.println("Bạn chọn thanh toán bằng hình thức thanh toán khi nhận hàng");
                            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                            if (hoaDon != null) {

                                String tenKhachHangCu = request.getParameter("tenKhachHang");
                                String soDienThoaiCu = request.getParameter("soDienThoai");
                                String quocGiaCu = request.getParameter("quocGia");
                                String thanhPhoCu = request.getParameter("thanhPho");
                                String diaChiCu = request.getParameter("diaChi");
                                String messCu = request.getParameter("mess");
                                String thanhTienTong1 = request.getParameter("tongTien");

                                // Lấy giá trị ship từ request
                                String ship = request.getParameter("ship");

                                // Loại bỏ ký tự không mong muốn
                                ship = ship.replaceAll("[^0-9]", "");

                                // Kiểm tra xem chuỗi có giá trị không rỗng
                                if (!ship.isEmpty()) {
                                    // Chuyển đổi chuỗi thành số nguyên
                                    int shipValue = Integer.parseInt(ship);

                                    // In giá trị đã chuyển đổi
                                    System.out.println("Giá trị ship sau khi chuyển đổi: " + shipValue);

                                } else {

                                    System.out.println("Không có giá trị ship hợp lệ.");

                                }

                                if (ship.matches("\\d*\\.?\\d*")) {
                                    BigDecimal shipBigDecimal = new BigDecimal(ship);
                                    System.out.println("Giá trị shipBigDecimal: " + shipBigDecimal);
                                    hoaDon.setPhiShip(shipBigDecimal);

                                    //Tính thành tiền
                                    BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienTong1);

                                    // Trừ giá trị ship từ tổng tiền
                                    BigDecimal thanhTienSauShip = thanhTienBigDecemal.subtract(shipBigDecimal);
                                    hoaDon.setThanhTien(thanhTienSauShip);


                                } else {

                                    System.out.println("Chuỗi không hợp lệ");

                                }

                                hoaDon.setTrangThai(1);
                                hoaDon.setTrangThaiMoney(0);
                                hoaDon.setHinhThuc(0);
                                hoaDon.setHinhThucThanhToan(3);

                                hoaDon.setNgayThanhToan(LocalDateTime.now());
                                hoaDon.setNgayTao(LocalDateTime.now());
                                hoaDon.setGhiChu("Tên khách hàng: "+ tenKhachHangCu + "," + "Số điện thoại nhận hàng: " + soDienThoaiCu + ", Địa chỉ giao hàng: " + diaChiCu + "," + thanhPhoCu + "," + quocGiaCu);
                                hoaDon.setMess(messCu);

                                //Lấy số lượng hiện tại trừ đi

                                List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
                                model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);

                                //Tìm hóa đơn để tính số lượng
                                for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {

                                    //Số lượng mua của khách hàng
                                    String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
                                    int soLuongMua = Integer.parseInt(soLuongMuaToString);

                                    //Số lượng có trong kho
                                    GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
                                    String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
                                    int soLuongCo = Integer.parseInt(soLuongCoToString);

                                    giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
                                    giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);

                                }

                                hoaDonRepository.save(hoaDon);

//                                String[] idGiayTheTheThaoChiTietArrayCashCu = request.getParameterValues("idGiayTheTheThaoChiTiet");
//
//                                if (idGiayTheTheThaoChiTietArrayCashCu != null) {
//                                    for (String idGiayTheTheThaoChiTietCashCu : idGiayTheTheThaoChiTietArrayCashCu) {
//                                        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTietCashCu);
//
//                                        // Tìm giỏ hàng chi tiết để xóa
//                                        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);
//
//                                        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
//                                        if (gioHangChiTiet != null) {
//                                            // Xóa giỏ hàng chi tiết
//                                            gioHangChiTietRepository.delete(gioHangChiTiet);
//
//                                        }
//                                    }
//                                }



                                model.addAttribute("idKH", hoaDon.getKhachHang().getId());
                                return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";


                            }

                        }



                    }


                }

                System.out.println("Thanh toán thành công !");
                return "redirect:/TrangChu/listGiayTheThao";

            }

        }else{

            System.out.println("Khách hàng chưa đăng nhập tài khoản");

            return "redirect:/TrangChu/listGiayTheThao";

        }

    }




    //Todo code xóa giỏ hàng chi tiết khi thanh toán
    private void xoaGioHangChiTiet(UUID idGiayTheThaoChiTiet) {
        try {
            hoaDonChiTietRepository.deleteById(idGiayTheThaoChiTiet);
            System.out.println("Xóa giỏ hàng chi tiết thành công");
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa giỏ hàng chi tiết: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Todo code thanh toán bằng momo
    @GetMapping("paywithmomo")
    public String PayWithMomoGet(@ModelAttribute("message") String message,
                                 Model model,
                                 @RequestParam(value = "orderId", required = false) String orderId,
                                 HttpSession session) {

        System.out.println("OrderId là: " + orderId);

        if (orderId.contains("GiaoDV")) {

                //Tìm theo mã giao dịch
                GiaoDichViChiTiet giaoDichViChiTietNew = giaoDichViChiTietServiceImpl.findByMa(orderId);

                if(message.equals("Successful.")) {

                    System.out.println("Nạp thành công!");

                    ViTien viTien = giaoDichViChiTietNew.getViTien();

                    BigDecimal tongTien = viTien.getThanhTien().add(giaoDichViChiTietNew.getDonGia());

                    ViTien viTienNew = new ViTien();

                    viTienNew.setMaViTien(viTien.getMaViTien());
                    viTienNew.setKhachHang(viTien.getKhachHang());
                    viTienNew.setThanhTien(tongTien);
                    viTienNew.setTrangThai(1);

                    //Lưu lại thông tin của ví tiền
                    viTienServiceImpl.update(viTien.getId(),viTienNew);

                    LocalDate ngayGiaoDich = LocalDate.now();
                    String ngayGiaoDichToDate = ngayGiaoDich.toString();
                    LocalDateTime now = LocalDateTime.now();

                    GiaoDichViChiTiet giaoDichViChiTiet = new GiaoDichViChiTiet();

                    giaoDichViChiTiet.setMaGiaoDichViChiTiet(giaoDichViChiTietNew.getMaGiaoDichViChiTiet());
                    giaoDichViChiTiet.setViTien(viTien);
                    giaoDichViChiTiet.setNgayGiaoDich(LocalDateTime.now());
                    giaoDichViChiTiet.setDonGia(giaoDichViChiTietNew.getDonGia());
                    giaoDichViChiTiet.setHinhThuc(1);
                    giaoDichViChiTiet.setTrangThai(1);

                    //Lưu lại thông tin giao dịch
                    giaoDichViChiTietServiceImpl.update(giaoDichViChiTietNew.getId(),giaoDichViChiTiet);
                    session.setAttribute("napThanhCong","2");

                    return "redirect:/KhachHang/ViDienTu/ViewViDienTu/" + session.getAttribute("maKH");

                }else{

                    System.out.println("Thanh toán thất bại");
                    session.setAttribute("napThatBai","2");
                    return "redirect:/KhachHang/ViDienTu/ViewViDienTu/" + session.getAttribute("maKH");

                }
        }

        if (!message.equals("Successful.")) {

            session.setAttribute("error_momo", "Thanh toán không thành công!");
            return "redirect:/TrangChu/listGiayTheThao";

        } else {

            System.out.println("Thanh toán momo thành công");
            return "redirect:/TrangChu/listGiayTheThao";

        }


    }



    //Todo code log swel thông báo cho thanh toán thành công cho đơn hàng
    @GetMapping("nguoiDung/hoaDon/thanhToan/ThanhCong")
    public String showViewThanhToanThanhCong(Model model){

        System.out.println("Hiện thông báo trước khi chuyển hướng");

        return "/templates/Users/Layouts/Log/XuatHoaDonLog";

    }

    //Todo code trạng thái đơn hàng cho cả Admin và khách hàng

    //Bên phía khách hàng

    //Todo code view tổng quan trong thái
    @GetMapping("/KhachHang/thongTinHoaDonAll/*")
    public String viewThongTin(HttpServletRequest request, Model model) {
        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/thongTinHoaDonAll/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());

        } catch (Exception e) {

            model.addAttribute("maKH", "2");

        }

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/viewHoaDonTrangThaiAll";

    }

    //Todo code trạng thái đơn hàng cho hóa đơn
    //////////////////////////////////////////////////////////////////////////////
    //Bên khách hàng

    //Todo code chờ thanh toán bên phía khách hàng(Tức là khách hàng nhấn vào thanh toán xong lại không thanh toán nữa)

    @GetMapping("/KhachHang/HoaDon/ChoThanhToan/*")
    public ModelAndView hoaDonChoThanhToan(
            @RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/ChoThanhToan/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("maKH","2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),0,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/choThanhToanBenKhachHang");
        mav.addObject("page",page);

        return  mav;

    }


   //Todo code chờ xác nhận bên phía khác hàng

    @GetMapping("/KhachHang/HoaDon/ChoXacNhan/*")
    public ModelAndView hoaDonChoXacNhan(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request, Model model) {

        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/HoaDon/ChoXacNhan/");
        String ma = parts[1];

        try {
            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());
        } catch (Exception e) {
            model.addAttribute("maKH", "2");
        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);

        // Sắp xếp danh sách đơn hàng theo ngày thanh toán tăng dần (mới nhất lên đầu tiên)
        List<HoaDon> sortedList = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(), 1, pageNo, 5)
                .getContent()
                .stream()
                .sorted(Comparator.comparing(HoaDon::getNgayThanhToan))
                .collect(Collectors.toList());

        model.addAttribute("page", new PageImpl<>(sortedList));

        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/choXacNhanBenKhachHang");
        return mav;

    }




    //Todo code view thông tin sản phẩm khách hàng mua bên phía khách hàng
    @PostMapping("/KhachHang/viewThongTinSanPhamMuaChoXacNhan")
    public String viewThongTinSanPhamMuaKhachHangChoXacNhan(Model model,
                                                            HttpServletRequest request,
                                                            HttpSession session){

        if(session.getAttribute("maKH") != null){

            model.addAttribute("maKhachHang",session.getAttribute("maKH"));

            String idHoaDonViewThongTinSanPhamChoXacNhan = request.getParameter("idHoaDonViewThongTinSanPhamChoXacNhan");

            System.out.println("Id của hóa đơn là : "+idHoaDonViewThongTinSanPhamChoXacNhan);

            HoaDon hoaDonView = hoaDonRepository.findById(UUID.fromString(idHoaDonViewThongTinSanPhamChoXacNhan)).orElse(null);

            model.addAttribute("hoaDonView",hoaDonView);

            //Tìm kiếm hóa đơn chi tiết theo id của hóa đơn

            //Từ hóa đơn tìm ra hóa đơn chi tiết
            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findByHoaDon_Id(UUID.fromString(idHoaDonViewThongTinSanPhamChoXacNhan));
            model.addAttribute("hoaDonChiTietList",hoaDonChiTietList);


        }else{

            System.out.println("Chưa đăng nhập tài khoản !");
            return "redirect:/TrangChu/listGiayTheThao";

        }

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/viewThongTinSanPhamKhachHangMuaChoXacNhan";

    }


    //Todo code khách hàng chờ đóng gói sản phẩm là không được hủy nữa
    @GetMapping("/KhachHang/HoaDon/ChoDongGoi/*")
    public ModelAndView choDongGoiPhiaKhachHang(

            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/ChoDongGoi/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("maKH","2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),2,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/choDongGoiBenKhachHang");
        mav.addObject("page",page);

        return mav;


    }

    //Todo code đang giao hàng bên phía khách hàng
    @GetMapping("/KhachHang/HoaDon/DangGiaoHang/*")
    public ModelAndView dangGiaoHangBenPhiaKhachHang(
            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model
    ){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/DangGiaoHang/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("maKH","2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),3,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/dangGiaoHangBenPhiaKhachHang");
        mav.addObject("page",page);
        return mav;

    }

    //Todo code giao hàng thành công
    @PostMapping("/KhachHang/HoaDon/XacNhanGiaoHangThanhCong")
    public String xacNhanGiaoHangThanhCong(
            Model model,
            HttpServletRequest request
    ){

        String thanhCong  = request.getParameter("thanhCong");
        String idKH = request.getParameter("idKH");

        HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(thanhCong));

        HoaDon hd = new HoaDon();

        hd.setMaHoaDon(hoaDon.getMaHoaDon());
        hd.setThanhTien(hoaDon.getThanhTien());
        hd.setKhachHang(hoaDon.getKhachHang());
        hd.setNgayTao(hoaDon.getNgayTao());
        hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(4);

        hoaDonServiceImpl.update(hoaDon.getId(),hd);

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/giaoHangThanhCongBenPhiaKhachHang";

    }

    //Todo code giao hàng thành công
    @GetMapping("/KhachHang/HoaDon/GiaoHangThanhCong/*")
    public ModelAndView giaoHangThanhCong(
            Model model,
            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request

    ){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/GiaoHangThanhCong/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e) {

            model.addAttribute("maKH", "2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),4,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/giaoHangThanhCongBenPhiaKhachHang");

        mav.addObject("page", page);
        return mav;



    }


    //Todo code hủy đơn hàng bên phía khách hàng

    @GetMapping("/KhachHang/HoaDon/HuyDonHang/*")
    public ModelAndView hoaDonHuy(@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                  HttpServletRequest request,
                                  Model model){

        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/HoaDon/HuyDonHang/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());

        }catch (Exception e){

            model.addAttribute("maKH", "2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),5,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/hoaDonHuyBenKhachHang");

        mav.addObject("page", page);
        return mav;
    }

    @PostMapping("/KhachHang/HoaDon/HuyDonHang")
    public String huyDonHang(HttpServletRequest request,
                             HttpSession session,
                             Model model){

        if(session.getAttribute("maKH") != null) {

            String huyDonHang = request.getParameter("huyDonHang");
            String idKH = request.getParameter("idKH");

            HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huyDonHang));
            //Tạo mới hóa đơn để lưu

            System.out.println("Id của hóa đơn hủy là: "+huyDonHang);
            System.out.println("Id của khách hàng hủy: "+ idKH);


            HoaDon hd = new HoaDon();

            hd.setMaHoaDon(hoaDon.getMaHoaDon());
            hd.setThanhTien(hoaDon.getThanhTien());
            hd.setKhachHang(hoaDon.getKhachHang());
            hd.setNgayThanhToan(LocalDateTime.now());

        /*

            0: là chưa thanh toán xong
            1: là chờ xác nhận
            2: là đang đóng gói hàng
            3: là đang giao
            4: là giao hàng thành công
            5: là hủy đơn hàng

         */
            hd.setGhiChu(hoaDon.getGhiChu());
            hd.setTrangThai(5);

            hoaDonServiceImpl.update(hoaDon.getId(), hd);

            //Nếu mà hủy đơn hàng thí số lượng của giầy thể thao đó sẽ được trả về


            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findByHoaDon_Id(UUID.fromString(huyDonHang));
            model.addAttribute("hoaDonChiTietList",hoaDonChiTietList);

            //Tìm hóa đơn để tính số lượng

            for(HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList){

                //Số lượng khách hàng mua
                String soLuongKhachHangMua = hoaDonChiTiet.getSoLuong();
                int soLuongMuaConvertInt = Integer.parseInt(soLuongKhachHangMua);

                System.out.println("Số lượng khách hàng mua: "+ soLuongMuaConvertInt);


                //Số lượng trong kho có
                GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTiet.getGiayTheThaoChiTiet();
                String soLuongCo = giayTheThaoChiTiet.getSoLuong();
                int soLuongCoConvertInt = Integer.parseInt(soLuongCo);

                System.out.println("Số lượng trong kho có: "+ soLuongCoConvertInt);

                giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongMuaConvertInt + soLuongCoConvertInt));


                giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);

                int tongSoLuongCong  = soLuongMuaConvertInt + soLuongCoConvertInt;

                System.out.println("Số lượng sau khi trả lại: "+ tongSoLuongCong);

            }

        }else{

            System.out.println("Chưa đăng nhập tài khoản !");
            return "redirect:/TrangChu/listGiayTheThao";

        }

        return "redirect:/KhachHang/HoaDon/HuyDonHang/"+session.getAttribute("maKH");

    }


//////////////////////////////////////////////////////////////////////////


    //Bên phía Admin

    //Todo code xác nhân đơn hàng bên phía admin

    //Todo code All xác nhận bên phía admin
    @GetMapping("/Admin/xacNhanDonHangKhachHangAll")
    public String showViewXacNhanDonHangAllKhachHang(Model model){


        model.addAttribute("listHoaDon",hoaDonRepository.findAll());

        return "/templates/Admin/TrangThaiDonHang/viewHoaDonTrangThaiAll";


    }

    //Todo code xác nhân đơn hàng bên phía admin

//    @GetMapping("/Admin/xacNhanDonHangKhachHang")
//    public ModelAndView adminXacNhanDonHangChoKhachHang(
//            Model model,
//            @RequestParam(value = "pageNo",required = false, defaultValue = "0") Integer pageNo,
//            HttpServletRequest request
//
//    ){
//
//        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,1);
//        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/choXacNhan");
//        mav.addObject("page",page);
//
//        return mav;
//
//    }

    @GetMapping("/Admin/xacNhanDonHangKhachHang")
    public ModelAndView adminXacNhanDonHangChoKhachHang(
            Model model,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request
    ) {

        // Sắp xếp danh sách đơn hàng theo ngày thanh toán tăng dần (cũ nhất lên đầu tiên)
        List<HoaDon> sortedList = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo, 5, 1)
                .getContent()
                .stream()
                .sorted(Comparator.comparing(HoaDon::getNgayThanhToan))
                .collect(Collectors.toList());

        Page<HoaDon> page = new PageImpl<>(sortedList);

        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/choXacNhan");
        mav.addObject("page", page);

        return mav;
    }



    //Todo code xác nhân bên đơn bên admin
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHang")
    public String showHoaDonXacNhanBenKhachHang(HttpServletRequest request,
                                                HttpSession session){



            if (session.getAttribute("userLog") != null) {

                User user = (User) session.getAttribute("userLog");

                String huy = request.getParameter("huy");

                HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huy));
                HoaDon hd = new HoaDon();

                hd.setUser(user);
                hd.setMaHoaDon(hoaDon.getMaHoaDon());
                hd.setThanhTien(hoaDon.getThanhTien());
                hd.setNgayTao(hoaDon.getNgayTao());
                hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
                hd.setGhiChu(hoaDon.getGhiChu());
                //Xác nhận đơn hàng sang đang giao hàng
                hd.setTrangThai(2);
                hoaDonServiceImpl.update(hoaDon.getId(), hd);

            }

        return "redirect:/Admin/logXacNhanDonHang";

    }

    //Todo code xác nhận giao hàng thành công bên phía admin
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonDangDongGoi")
    public ModelAndView showFormHoaDonXacNhanGiaoHangThanhCong(

            @RequestParam(value = "pageNo",required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model
    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,2);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/dangDongGoi");
        mav.addObject("page", page);
        return mav;

    }



    //Todo code xác nhận giao hàng thành công bên phía Admin
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHangDangGiao")
    public String adminDangGiaoHang(

            HttpServletRequest request,
            HttpSession session
    ){

        if (session.getAttribute("userLog") != null) {

            User user = (User) session.getAttribute("userLog");

            String thanhCong = request.getParameter("thanhCong");

            HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(thanhCong));
            HoaDon hd = new HoaDon();

            System.out.println("Đơn hàng chuyển sang trạng thái giao hàng");
            hd.setUser(user);
            hd.setMaHoaDon(hoaDon.getMaHoaDon());
            hd.setThanhTien(hoaDon.getThanhTien());
            hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
            hd.setNgayTao(hoaDon.getNgayTao());
            hd.setKhachHang(hoaDon.getKhachHang());
            hd.setGhiChu(hoaDon.getGhiChu());
            hd.setTrangThai(3);

            hoaDonServiceImpl.update(hoaDon.getId(), hd);
        }

          return "redirect:/Admin/dongGoiThanhCong";

    }

    //Todo code đang giao hàng
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonKhachHangDangGiao")
    public ModelAndView showDangGiaoHang(

            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,3);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/dangGiaoHang");
        mav.addObject("page", page);
        return mav;

    }

    //Todo code hoàn thành giao hàng
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh")
    public ModelAndView showGiaoHangHoanThanh(

            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,4);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/hoanThanh");
        mav.addObject("page", page);
        return mav;

    }

    //Todo code giao hàng thanh công admin xác nhận giao hàng thành công
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHangThanhCong")
    public String adminXacNhanGiaoHangThanhCong(

            HttpServletRequest request,
            HttpSession session
    ){

        if (session.getAttribute("userLog") != null) {

            User user = (User) session.getAttribute("userLog");


            String thanhCong = request.getParameter("thanhCong");

            HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(thanhCong));
            HoaDon hd = new HoaDon();

            System.out.println("Đơn hàng chuyển sang trạng thái giao hàng");
            hd.setUser(user);
            hd.setMaHoaDon(hoaDon.getMaHoaDon());
            hd.setThanhTien(hoaDon.getThanhTien());
            hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
            hd.setNgayTao(hoaDon.getNgayTao());
            hd.setKhachHang(hoaDon.getKhachHang());
            hd.setGhiChu(hoaDon.getGhiChu());
            hd.setTrangThai(4);

            hoaDonServiceImpl.update(hoaDon.getId(), hd);

        }

//        return "redirect:/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh";
        return "redirect:/Admin/giaoHangThanhCong";

    }


    //Todo code đơn hàng bị hủy
    @GetMapping("/Admin/HoaDon/DonHangBiHuy")
    public ModelAndView donHangBiHuy(

            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,5);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/donHangBiHuy");
        mav.addObject("page", page);
        return mav;

    }


    @GetMapping("/XoaDonHangBiHuyThanhCong")
    public String xoaDonHangBiHuyThanhCong(){

        return "/templates/Users/Layouts/Log/xoaDonHangBiHuyThanhCong";

    }
    //Todo code xóa đơn hàng bị hủy

    @PostMapping("/XoaHoaDon")
    public String xoaHoaDon(@RequestParam("hoaDonId") UUID hoaDonId) {

        hoaDonService.deleteHoaDon(hoaDonId);

        return "redirect:/XoaDonHangBiHuyThanhCong";

    }


    //Todo code xác nhận hủy đơn hàng bên admin
    @PostMapping("/Admin/HoaDon/HuyDonHangCuaKhachHang")
    public String adminXacNhanHuyDonHangCuaKhachHang(

            HttpServletRequest request,
            HttpSession session,
            Model model
    ){

        if (session.getAttribute("userLog") != null) {

            User user = (User) session.getAttribute("userLog");

            String huyDonHang = request.getParameter("huyDonHang");
            String lyDo       = request.getParameter("lyDo");

            System.out.println("Lý do hủy đơn : "+ lyDo);
            System.out.println("Hủy đơn hàng : "+ huyDonHang);

            HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huyDonHang));

            //Cộng tiền vào ví

            if(hoaDon.getHinhThucThanhToan() == 1 || hoaDon.getHinhThucThanhToan() == 2){

                ViTien viTien = viTienServiceImpl.findByIdKhachHang(hoaDon.getKhachHang().getId());

                BigDecimal tongTien = viTien.getThanhTien().add(hoaDon.getThanhTien());

                //Lưu tiền lại vào ví điện tử

                ViTien viTienNew = new ViTien();

                viTienNew.setMaViTien(viTien.getMaViTien());
                viTienNew.setKhachHang(viTien.getKhachHang());
                viTienNew.setThanhTien(tongTien);
                viTienNew.setTrangThai(1);

                //Lưu lại vào ví tiền thành công
                viTienServiceImpl.update(viTien.getId(),viTienNew);

                //Lưu lại vào lịch sử giao dịch

                LocalDateTime now = LocalDateTime.now();

                GiaoDichViChiTiet giaoDichViChiTiet = new GiaoDichViChiTiet();

                giaoDichViChiTiet.setMaGiaoDichViChiTiet("GiaoDV"+now.getMonthValue() + now.getDayOfMonth() + now.getHour() + now.getMinute() + now.getSecond());
                giaoDichViChiTiet.setViTien(viTien);
                giaoDichViChiTiet.setUser(user);
                giaoDichViChiTiet.setNgayGiaoDich(LocalDateTime.now());

                BigDecimal donGia = hoaDon.getThanhTien();
                BigDecimal phiShip = hoaDon.getPhiShip();
//                BigDecimal thanhTien = donGia.add(phiShip);

                giaoDichViChiTiet.setDonGia(donGia.add(phiShip));
//                1: là nạp tiền
//                2: thanh toán đơn hàng
//                3: hoàn tiền

                giaoDichViChiTiet.setHinhThuc(3);
                giaoDichViChiTiet.setTrangThai(1);

                //Lưu lại vào mã giao dịch
                giaoDichViChiTietServiceImpl.add(giaoDichViChiTiet);

            }

            HoaDon hd = new HoaDon();

            System.out.println("Đơn hàng bị hủy vì có một số lý do");
            hd.setUser(user);
            hd.setMaHoaDon(hoaDon.getMaHoaDon());
            hd.setThanhTien(hoaDon.getThanhTien());
            hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
            hd.setNgayTao(hoaDon.getNgayTao());
            hd.setKhachHang(hoaDon.getKhachHang());
            hd.setGhiChu(hoaDon.getGhiChu());
            hd.setMess(lyDo);
            hd.setTrangThai(5);

            String email = request.getParameter("emailView");
            String maHoaDon = request.getParameter("maHoaDonView");
            String tenKhachHang = request.getParameter("khachHangView");
            String ngayThanhToan = request.getParameter("ngayThanhToanView");
            String thanhTien = request.getParameter("tongTienView");
            String thongTinNhanHang = request.getParameter("thongTienNhanHangView");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);

            message.setSubject("Cửa hàng bán giầy thể thao bóng đá BeeShoes xin thông báo");
            message.setText(
                    "Mã hóa đơn : " + maHoaDon +
                            "\n" +
                            "Ngày thanh toán :" + ngayThanhToan +
                            "\n" +
                            "Thành tiền : " + thanhTien + "VNĐ" +
                            "\n" +
                            "Thông tin địa chỉ nhận hàng :" +thongTinNhanHang +
                            "\n" +
                            "\n" +
                            "Xin chào khách hàng : "+ tenKhachHang + "\n" +
                            "Có địa chỉ email là: "+ email +"\n"+
                            "Cảm ơn bạn đã quan tâm đến sản phẩm của shop, đơn hàng của bạn bị hủy do: " + lyDo +
                            "" +
                            "\n"+
                            "\n"+
                            "Cảm ơn quý khách hàng đã quan tâm!" + "\n" +
                            "Xin cảm ơn,và hân hạnh phục phục lần sau!"

            );


            mailSender.send(message);

            hoaDonServiceImpl.update(hoaDon.getId(), hd);

            //Trả lại số lượng cho bên sản phẩm

            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findByHoaDon_Id(UUID.fromString(huyDonHang));
            model.addAttribute("hoaDonChiTietList",hoaDonChiTietList);

            //Tìm hóa đơn để tính số lượng

            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList){

                //Số lượng khách hàng mua

                String soLuongKhachHangMuaToString = hoaDonChiTiet.getSoLuong();
                int soLuongKhachHangMuaConvertInt = Integer.parseInt(soLuongKhachHangMuaToString);
                System.out.println("Số lượng khách hàng mua: "+ soLuongKhachHangMuaConvertInt);

                //Số lượng có trong kho
                GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTiet.getGiayTheThaoChiTiet();
                String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
                int soLuongCoConvertInt = Integer.parseInt(soLuongCoToString);

                System.out.println("Số lượng sản phẩm có: "+ soLuongCoConvertInt);

                giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongKhachHangMuaConvertInt + soLuongCoConvertInt));

                giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);

                System.out.println("Số lượng sau khi hoàn: "+ soLuongKhachHangMuaConvertInt + soLuongCoConvertInt);


            }


        }

        return "redirect:/Admin/HuyDonHangCuaKhachHangLog";

    }


    //Todo code log bên phía admin

    //Admin
    //Todo code log xác nhận đơn hàng thành công sang bên phía đang đóng gói
    @GetMapping("/Admin/logXacNhanDonHang")
    public String showLogXacNhanDonHangAlert(){

        return "/templates/Users/Layouts/Log/logXacNhanDonHangThanhCong";

    }

    //Todo code log đóng gói sang đang giao
    @GetMapping("/Admin/dongGoiThanhCong")
    public String showLogDongGoiThanhCongAlert(){

        return "/templates/Users/Layouts/Log/logDongGoiDonHangThanhCong";

    }

    //Todo code từ đang giao hàng sang nhận hàng thành công!
    @GetMapping("/Admin/giaoHangThanhCong")
    public String showLogGiaoHangThanhCongAlert(){

        return "/templates/Users/Layouts/Log/logGiaoHangThanhCong";

    }

    @GetMapping("/Admin/HuyDonHangCuaKhachHangLog")
    public String showHuyDonHangCuaKhachHangAlert(){

        return "/templates/Users/Layouts/Log/logDonHangKhachHangBiHuy";

    }
    //Khách hàng

    //Todo code log hủy đơn hàng bên khách hàng
    @GetMapping("/KhachHang/huyDonHang")
    public String showLogHuyDonHangAlert(){

        return "/templates/Users/Layouts/Log/logHuyDonHangThanhCongBenKhachHang";

    }

    @PostMapping("/HoaDon/ThongTinChiTietHoaDon")
    public String showThongTinHoaDonChiTiet(

            Model model,
            HttpServletRequest request

    ){

        String idHoaDon = request.getParameter("idHoaDon");
        UUID idHoaDonConvert = UUID.fromString(idHoaDon);

        System.out.println("ID hóa đơn "+ idHoaDon);
        System.out.println("Id hóa đơn sau khi convert: "+idHoaDonConvert);

        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(idHoaDonConvert);

        if (!hoaDonChiTiets.isEmpty()) {
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {

                //Thông tin cần in ra
                System.out.println();
                model.addAttribute("idHoaDonConvert",idHoaDonConvert);
                model.addAttribute("maHoaDonView",hoaDonChiTiet.getHoaDon().getMaHoaDon());
                model.addAttribute("emailView",hoaDonChiTiet.getHoaDon().getKhachHang().getEmail());
                model.addAttribute("khachHangView",hoaDonChiTiet.getHoaDon().getKhachHang().getTenKhachHang());
                model.addAttribute("ngayThanhToanView",hoaDonChiTiet.getHoaDon().getNgayThanhToan());
                model.addAttribute("tongTienView",hoaDonChiTiet.getHoaDon().getThanhTien());
                model.addAttribute("thongTienNhanHangView",hoaDonChiTiet.getHoaDon().getGhiChu());
                model.addAttribute("messView",hoaDonChiTiet.getHoaDon().getMess());
                model.addAttribute("hinhThucView",hoaDonChiTiet.getHoaDon().getHinhThuc());
                model.addAttribute("tenGiayTheThaoView",hoaDonChiTiet.getGiayTheThaoChiTiet().getGiayTheThao().getTenGiayTheThao());
                model.addAttribute("trangThaiView",hoaDonChiTiet.getHoaDon().getTrangThai());
                model.addAttribute("trangThaiMoney",hoaDonChiTiet.getHoaDon().getTrangThaiMoney());
                model.addAttribute("hinhThucView",hoaDonChiTiet.getHoaDon().getHinhThuc());
                model.addAttribute("hinhThucThanhToan",hoaDonChiTiet.getHoaDon().getHinhThucThanhToan());
                model.addAttribute("phiShipView",hoaDonChiTiet.getHoaDon().getPhiShip());

            }
        }

        model.addAttribute("hoaDonChiTiets",hoaDonChiTiets);

        return "/templates/Users/Layouts/ChiTiet/ChiTietHoaDon";

    }


    //Todo code trạng thái đơn hàng All bên Admin
    @PostMapping("/HoaDon/Admin/TrangThaiDonHangAllView")
    public String showTrangThaiDonHangAllView(HttpServletRequest request,Model model) {

        String selectedIdHoaDon = request.getParameter("selectedIdHoaDon");
        UUID selectedIdHoaDonConvert = UUID.fromString(selectedIdHoaDon);

        System.out.println("Id là: " + selectedIdHoaDon);

        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(selectedIdHoaDonConvert);

        if (!hoaDonChiTiets.isEmpty()) {
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {

                //Thông tin cần in ra
                System.out.println();
                model.addAttribute("idHoaDonConvert",selectedIdHoaDonConvert);
                model.addAttribute("maHoaDonView",hoaDonChiTiet.getHoaDon().getMaHoaDon());
//                model.addAttribute("khachHangView",hoaDonChiTiet.getHoaDon().getKhachHang().getTenKhachHang());

                String khachHangView = hoaDonChiTiet.getHoaDon().getKhachHang() != null ?
                        hoaDonChiTiet.getHoaDon().getKhachHang().getTenKhachHang() : "N/A";

                model.addAttribute("khachHangView", khachHangView);

                model.addAttribute("ngayThanhToanView",hoaDonChiTiet.getHoaDon().getNgayThanhToan());
                model.addAttribute("tongTienView",hoaDonChiTiet.getHoaDon().getThanhTien());
                model.addAttribute("thongTienNhanHangView",hoaDonChiTiet.getHoaDon().getGhiChu());
                model.addAttribute("messView",hoaDonChiTiet.getHoaDon().getMess());
                model.addAttribute("hinhThucView",hoaDonChiTiet.getHoaDon().getHinhThuc());
                model.addAttribute("tenGiayTheThaoView",hoaDonChiTiet.getGiayTheThaoChiTiet().getGiayTheThao().getTenGiayTheThao());
                model.addAttribute("trangThaiMoney",hoaDonChiTiet.getHoaDon().getTrangThaiMoney());
                model.addAttribute("hinhThucThanhToan",hoaDonChiTiet.getHoaDon().getHinhThucThanhToan());
                model.addAttribute("trangThaiView",hoaDonChiTiet.getHoaDon().getTrangThai());
                model.addAttribute("phiShipView",hoaDonChiTiet.getHoaDon().getPhiShip());

            }
        }

        model.addAttribute("hoaDonChiTiets",hoaDonChiTiets);

        return "/templates/Users/Layouts/ChiTiet/ChiTietHoaDonAllTrangThaiHoaDon";

    }


    @GetMapping("/Admin/ThemSanPhamKhachHangThanhCong")
    public String saveThemSanPhamChoKhachHangThanhCong(){

        return "/templates/Users/Layouts/Log/themSanPhamChoKhachHangThanhCong";

    }


    @PostMapping("/Admin/HoaDon/ThemThongTinSanPham")
    public String saveThemThongTinSanPham(
            HttpServletRequest request,
            Model model
    ) {

        String idHoaDonConvert = request.getParameter("idHoaDonConvert");
        System.out.println("ID của hóa đơn là : " + idHoaDonConvert);
        model.addAttribute("idHoaDonConvert", idHoaDonConvert);

        //Id
        if(idHoaDonConvert != null){

            String giayTheThaoChiTietId = request.getParameter("giayTheThaoChiTietId");
            System.out.println("Id của giầy thể thao là: " + giayTheThaoChiTietId);

            String sizeId = request.getParameter("sizeId");
            System.out.println("Id của size là: "+ sizeId);

            String mauSacId = request.getParameter("mauSacId");
            System.out.println("Id của màu sắc là: "+ mauSacId);

            String soLuong = request.getParameter("soLuong");
            System.out.println("Số lượng mua: "+soLuong);

            if(giayTheThaoChiTietId != null && !giayTheThaoChiTietId.isEmpty()
                && sizeId != null && !sizeId.isEmpty()
                && mauSacId != null && !mauSacId.isEmpty()) {

                UUID idGiayTheThaoConvert = UUID.fromString(giayTheThaoChiTietId);
                UUID idSizeConvert = UUID.fromString(sizeId);
                UUID idMauSacConvert = UUID.fromString(mauSacId);

                UUID idGiayTheThaoChiTiet = giayTheThaoChiTietRepository.findIdByGiayTheThaoAndSizeAndMauSac(idGiayTheThaoConvert, idSizeConvert, idMauSacConvert);

                System.out.println("Id giầy thể thao chi tiết : "+ idGiayTheThaoConvert);
                System.out.println("Id của size: "+ idSizeConvert);
                System.out.println("Id của màu săc: "+idMauSacConvert);

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

                HoaDon hoaDon = hoaDonRepository.findById(UUID.fromString(idHoaDonConvert)).orElse(null);
                GiayTheThaoChiTiet giayTheThaoChiTiet= giayTheThaoChiTietRepository.findById(idGiayTheThaoChiTiet).orElse(null);

                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTiet);
                hoaDonChiTiet.setGhiChu("Khách hàng yêu cầu thêm sản phẩm");
                hoaDonChiTiet.setSoLuong(soLuong);

                String giaBanString = giayTheThaoChiTiet.getGiayTheThao().getGiaBan();

                BigDecimal giaBan = new BigDecimal(giaBanString);

                int soLuongInt = Integer.parseInt(soLuong);
                // Tính toán đơn giá
                BigDecimal donGia = giaBan.multiply(BigDecimal.valueOf(soLuongInt));
                hoaDonChiTiet.setDonGia(donGia);
                hoaDonChiTiet.setTrangThai(1);

                hoaDonChiTietRepository.save(hoaDonChiTiet);

                BigDecimal totalDonGia = hoaDonRepository.calculateTotalDonGiaByHoaDonId(UUID.fromString(idHoaDonConvert));
                System.out.println("Tổng đơn giá của hóa đơn có ID " + idHoaDonConvert + " là: " + totalDonGia);

                HoaDon hoaDonToUpdate = hoaDonRepository.findById(UUID.fromString(idHoaDonConvert)).orElse(null);
                hoaDonToUpdate.setThanhTien(totalDonGia);
                hoaDonRepository.save(hoaDonToUpdate);

                return "redirect:/Admin/ThemSanPhamKhachHangThanhCong";

            }

        }else{

            System.out.println("Mã hóa đơn không được là null");

        }

        return "/templates/Users/Layouts/ChiTiet/ThemThongTinSanPham";

    }









    //Todo code list giầy thể thao chi tiết
    @ModelAttribute("giayTheThaoChiTiet")
    public List<GiayTheThaoChiTiet> getListGiayTheThaoChiTiet(){

        return giayTheThaoChiTietRepository.findAll();

    }

    @ModelAttribute("size")
    public List<Size> getListSize(){

        return sizeRepository.findAll();
    }

    @ModelAttribute("mauSac")
    public List<MauSac> getListMauSac(){

        return mauSacRepository.findAll();

    }

}
