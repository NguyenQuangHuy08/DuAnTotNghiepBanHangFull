package com.example.sd_41.controller.KhachHang.BanHang;

import com.example.sd_41.model.HoaDon;
import com.example.sd_41.model.HoaDonChiTiet;
import com.example.sd_41.model.KhachHang;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.service.HoaDon.HoaDonChiTietServie;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.HoaDon.HoaDonServiceImpl;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

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


    //Todo code view hóa đơn
    //hiện thông tin cho view hóa đơn

    @GetMapping("nguoiDung/HoaDon/{id}")
    public String showViewHoaDon(
            @PathVariable String id,
            Model model,
            HttpSession session) {

        UUID hoaDonId = UUID.fromString(id);
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
        model.addAttribute("hoaDonId", hoaDonId);
        if (hoaDon != null) {
            // Thực hiện xử lý với đối tượng hoaDon
//            System.out.println("Id của hóa đơn là: " + id);
//            System.out.println("Id của hóa đơn là: " + hoaDonId);

            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);

            //Hiện view thông tin của hóa đơn
            model.addAttribute("hoaDonChiTietList", hoaDonChiTietList);
            model.addAttribute("hoaDon1", hoaDonId);
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("maHoaDon",hoaDon.getMaHoaDon());
            model.addAttribute("idKhacHang", hoaDon.getKhachHang().getId());
            model.addAttribute("tenKhachHang", hoaDon.getKhachHang().getTenKhachHang());
            model.addAttribute("diaChi", hoaDon.getKhachHang().getDiaChi());
            model.addAttribute("soDienThoai", hoaDon.getKhachHang().getSoDienThoai());

        } else {

            System.out.println("Xin lỗi không tìm thấy hóa đơn phù hợp cho khách hàng này");

        }

        return "/templates/Users/Layouts/Shop/viewHoaDon";

    }

    //Todo code thanh toán thành công chờ xác nhận đơn hàng
    @PostMapping("nguoiDung/hoaDon/ThanhToan/{id}")
    public String showEditViewThanhToanHoaDon(Model model,
                                              @PathVariable String id,
                                              HttpSession session,
                                              HttpServletRequest request,
                                              RedirectAttributes attributes) {
        UUID hoaDonId = UUID.fromString(id);
        String hinhThuc = request.getParameter("payment-method");

        if ("banking".equals(hinhThuc)) {

            System.out.println("Bạn đã chọn thanh toán bằng banking");
            return "Bạn đã chọn thanh toán bằng tiền mặt";

        } else if ("cash".equals(hinhThuc)) {

            System.out.println("Bạn đã chọn thanh toán bằng tiền mặt");
            String diaChiChon = request.getParameter("diaChiChon");

            if ("diaChiCu".equals(diaChiChon)) {

                HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                if (hoaDon != null) {

                    String soDienThoaiCu = request.getParameter("hiddenSoDienThoai");
                    String diaChiCu = request.getParameter("hiddenDiaChi");

                    LocalDate ngayThanhToanCu = LocalDate.now();
                    String ngayTaoCu = ngayThanhToanCu.toString();

                    hoaDon.setTrangThai(1);
                    hoaDon.setNgayThanhToan(ngayTaoCu);

                    hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoaiCu + ", Địa chỉ giao hàng: " + diaChiCu);

                    hoaDonRepository.save(hoaDon);
                    model.addAttribute("idKH", hoaDon.getKhachHang().getId());
//                    return "/templates/Users/Layouts/Shop/viewHoaDonThanhCong";
                    return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";

                } else {

                    System.out.println("Hóa đơn không tồn tại");

                }

            }else if("diaChiMoi".equals(diaChiChon)){

                HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                if (hoaDon != null) {

                    String soDienThoai = request.getParameter("soDienThoai");
                    String diaChi = request.getParameter("diaChi");

                    //Check số điện thoại
                    if(soDienThoai.isEmpty() || soDienThoai.trim().length() ==0 || soDienThoai == null){

                        attributes.addFlashAttribute("erLogSoDienThoaiChon","Xin lỗi không được để trống số điện thoại !");
                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                    }

                    //check số điện thoại nhập là chữ
                    try{

                        int soDienThoaiFomat = Integer.parseInt(soDienThoai);

                        if(soDienThoaiFomat < 0){

                            attributes.addFlashAttribute("erLogSoDienThoai0","Số điện thoại không được nhỏ hơn 0");
                            return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                        }


                    }catch (NumberFormatException e){

                        e.printStackTrace();
                        attributes.addFlashAttribute("erLogSoDienThoaiChonChu","Số điện thoại không được là chữ !");
                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                    }

                    // Validate số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số
                    String phoneNumberRegex = "^0\\d{9}$";

                    if (!soDienThoai.matches(phoneNumberRegex)) {
                        attributes.addFlashAttribute("erLogSoDienThoaiNumber", "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số.");
                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
                    }

                    //Check địa chỉ nhận hàng
                    if(diaChi == null || diaChi.isEmpty() || diaChi.trim().length()==0){

                        attributes.addFlashAttribute("erLogDiaChiChon","Không được để trống địa chỉ !");
                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                    }

                    if(diaChi.matches("^\\d.*")
                        || !diaChi.matches(".*[a-zA-Z].*")){

                        attributes.addFlashAttribute("erLogDiaChiChonNo","Xin lỗi tên địa chỉ không hợp lệ");
                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                    }


                    LocalDate ngayThanhToanMoi = LocalDate.now();
                    String ngayTaoMoi = ngayThanhToanMoi.toString();

                    hoaDon.setTrangThai(1);
                    hoaDon.setNgayThanhToan(ngayTaoMoi);
                    hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoai + ", Địa chỉ giao hàng: " + diaChi);

                    hoaDonRepository.save(hoaDon);
                    model.addAttribute("idKH", hoaDon.getKhachHang().getId());
//                    return "/templates/Users/Layouts/Shop/viewHoaDonThanhCong";
                    return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";

                } else {

                    System.out.println("Hóa đơn không tồn tại");

                }

            }else if(diaChiChon == null || diaChiChon.isEmpty()){

                attributes.addFlashAttribute("erViewDiaChiChon","Vui lòng chọn địa chỉ để có thể giao hàng !");
                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

            }else{

                attributes.addFlashAttribute("erViewDiaChiChon","Vui lòng chọn địa chỉ để có thể giao hàng !");
                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

            }

        }else if(hinhThuc == null || hinhThuc.isEmpty()){

            System.out.println("Hình thức thanh toán không hợp lệ");
            attributes.addFlashAttribute("messageNoChonNull","Vui lòng chọn một hình thức để có thể thanh toán !");
            return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

        }else{

            System.out.println("Hình thức thanh toán không hợp lệ");
            attributes.addFlashAttribute("messageNoChon","Vui lòng chọn hình thức để có thể thanh toán !");
            return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

        }


        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

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

    //Todo code trạng thái đơn hàng
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
            HttpServletRequest request, Model model){

        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/HoaDon/ChoXacNhan/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());

        }catch (Exception e){
            model.addAttribute("maKH", "2");
        }
        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),1,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/choXacNhanBenKhachHang");

        mav.addObject("page", page);
        return mav;
    }

    //Todo code đang giao hàng bên phía khách hàng
    @GetMapping("/KhachHang/HoaDon/DangGiaoHang/*")
    public ModelAndView dangGiaoHangBenPhiaKhachHang(
            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model){

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
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),2,pageNo,5);
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
        hd.setTrangThai(3);

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
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),3,pageNo,5);
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
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),4,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/hoaDonHuyBenKhachHang");

        mav.addObject("page", page);
        return mav;
    }

    @PostMapping("/KhachHang/HoaDon/HuyDonHang")
    public String huyDonHang(HttpServletRequest request){

        String huyDonHang = request.getParameter("huyDonHang");
        String idKH = request.getParameter("idKH");

        HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huyDonHang));
        //Tạo mới hóa đơn để lưu
        HoaDon hd = new HoaDon();
        LocalDate huyDon = LocalDate.now();
        String huyDonHang4 = huyDon.toString();

        hd.setMaHoaDon(hoaDon.getMaHoaDon());
        hd.setThanhTien(hoaDon.getThanhTien());
        hd.setKhachHang(hoaDon.getKhachHang());
        hd.setNgayThanhToan(huyDonHang4);

        /*
            0: là chưa thanh toán xong
            1: là chờ xác nhận
            2: là đang giao
            3: là giao hàng thành công
            4: là hủy đơn hàng
         */
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(4);

        hoaDonServiceImpl.update(hoaDon.getId(),hd);

//        return "redirect:/KhachHang/HoaDon/HuyDonHang"+idKH;
//        return "redirect:/KhachHang/HoaDon/HuyDonHang?idKH=" + idKH;

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/hoaDonHuyBenKhachHang";

    }


//////////////////////////////////////////////////////////////////////////


    //Bên phía Admin

    //Todo code xác nhân đơn hàng bên phía admin

    @GetMapping("/Admin/xacNhanDonHangKhachHang")
    public ModelAndView adminXacNhanDonHangChoKhachHang(
            Model model,
            @RequestParam(value = "pageNo",required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,1);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/choXacNhan");

        mav.addObject("page",page);
        return mav;

    }

    //Todo code xác nhân bên khách hàng
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHang")
    public String showHoaDonXacNhanBenKhachHang(HttpServletRequest request){

        String huy = request.getParameter("huy");

        HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huy));
        HoaDon hd = new HoaDon();

        hd.setMaHoaDon(hoaDon.getMaHoaDon());
        hd.setThanhTien(hoaDon.getThanhTien());
        hd.setNgayTao(hoaDon.getNgayTao());
        hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(2);
        hoaDonServiceImpl.update(hoaDon.getId(),hd);

        return "";

    }



}
