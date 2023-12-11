package com.example.sd_41.controller.KhachHang;

import com.example.sd_41.model.GioHang;
import com.example.sd_41.model.KhachHang;
import com.example.sd_41.repository.BanHang.GioHangRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.service.KhachHangService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class KhachHangController {

    @Autowired
    ServletContext context;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Data
    public static class SearchKH{
        String keyword;
        String tenKhachHang;
        String email;
        String matKhau;
        String gioiTinh;
        String ngaySinh;
        String soDienThoai;
        String diaChi;
    }

    @RequestMapping(value = "/KhachHang/list")
    public String viewKhachHang(Model model,
                                @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                                @ModelAttribute("searchKH") SearchKH searchKH)
    {
        List<KhachHang> listKH = khachHangRepository.findAll();
        model.addAttribute("listKH", listKH);

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<KhachHang> page = khachHangService.searchKH(searchKH.keyword, searchKH.tenKhachHang, searchKH.email, searchKH.matKhau, searchKH.gioiTinh, searchKH.ngaySinh, searchKH.soDienThoai, searchKH.diaChi, pageable);

        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        return "/KhachHang/list";
    }

    //Todo code check trùng email của khách hàng
    private  boolean checkTrungEmailKhachHang(String emailKhachHang){

            for(KhachHang khachHang : khachHangRepository.findAll()){

                 if(khachHang.getEmail().equalsIgnoreCase(emailKhachHang)){

                     return true;

                 }

            }

            return false;

    }

    //Todo code đăng ký tài khoản khách hàng
    @RequestMapping(value = "/KhachHang/view-createDanngKy")
    public String create(Model model){

        model.addAttribute("khachHang", new KhachHang());
        return "/templates/Users/Layouts/DangNhap/Register";

    }

    @RequestMapping(value = "/KhachHang/create")
    public String create(@Valid
                         @ModelAttribute("khachHang") KhachHang khachHang,
                         @RequestParam("quocGia1") String quocGia1,
                         @RequestParam("thanhPho1") String thanhPho1,
                         @RequestParam("diaChi1") String diaChi1  ,
                         BindingResult result,
                         Model model,
                         RedirectAttributes attributes,
                         HttpSession session){

        if (result.hasErrors()){

            return "/templates/Users/Layouts/DangNhap/Register";

        }

        //Check trô email
        if(khachHang.getEmail() == null
            || khachHang.getEmail().isEmpty()
                || khachHang.getEmail().trim().length()==0){

            model.addAttribute("erCheckEmailKhachHangNull","Không được để trống Email !");
            return "/templates/Users/Layouts/DangNhap/Register";

        }

        //Check email phải có đuôi @gmail.com
        if(!khachHang.getEmail().endsWith("@gmail.com")){

            model.addAttribute("erMail@gmail","Email của bạn không đúng định dạng !");
            return "/templates/Users/Layouts/DangNhap/Register";

        }

        //Check tên email nhập kí tự số đầu tiên
        if (khachHang.getEmail().matches("^\\d.*") ||
                !khachHang.getEmail().matches(".*[a-zA-Z].*")) {
            model.addAttribute("erCheckEmailSo", "Tên Email  không hợp lệ!, Phải bắt đầu bằng chữ cái đầu tiên!");
            return "/templates/Users/Layouts/DangNhap/Register";

        }

        Pattern pattern = Pattern.compile("^[^-0-9].*");
        Matcher matcher = pattern.matcher(khachHang.getEmail());

        if (!matcher.matches()) {

            model.addAttribute("erCheckEmail", "Email thao không hợp lệ!");
            return "/templates/Users/Layouts/DangNhap/Register";

        }


        //Check trùng tên Email
        if(checkTrungEmailKhachHang(khachHang.getEmail())){

            model.addAttribute("erCheckTrungEmailKhachHang","Xin lỗi tên email này đã tồn tại trong hệ thống !");
            return "/templates/Users/Layouts/DangNhap/Register";

        }

        if (khachHang.getTenKhachHang() == null || khachHang.getTenKhachHang().isEmpty() || khachHang.getTenKhachHang().trim().length()==0){
            model.addAttribute("tenKhachHangNotNull", "Tên khách hàng không được để trống");
            return "/templates/Users/Layouts/DangNhap/Register";

        }

        if (khachHang.getTenKhachHang().matches("^\\d.*") || !khachHang.getTenKhachHang().matches(".*[a-zA-Z].*")){
            model.addAttribute("tenKhachHangHopLe", "Tên khách hàng không hợp lệ");
            return "/templates/Users/Layouts/DangNhap/Register";

        }

        if (khachHang.getEmail() == null || khachHang.getEmail().isEmpty() || khachHang.getEmail().trim().length()==0){
            model.addAttribute("emailNotNull", "Email không được để trống");

            return "/templates/Users/Layouts/DangNhap/Register";

        }


        if (khachHang.getMatKhau() == null || khachHang.getMatKhau().isEmpty() || khachHang.getMatKhau().trim().length()==0){
            model.addAttribute("matKhauNotNull", "Mật khẩu không được để trống");

            return "/templates/Users/Layouts/DangNhap/Register";

        }

        if (khachHang.getDiaChi() == null || khachHang.getDiaChi().isEmpty() || khachHang.getDiaChi().trim().length()==0){

            model.addAttribute("diaChiNotNull", "Địa chỉ không được để trống");
            return "/templates/Users/Layouts/DangNhap/Register";

        }

        if (khachHang.getDiaChi().matches("^\\d.*") || !khachHang.getDiaChi().matches(".*[a-zA-Z].*")){
            model.addAttribute("diaChiHopLe", "Địa chỉ không hợp lệ");

            return "/templates/Users/Layouts/DangNhap/Register";

        }

        //Check thành phố
        if (quocGia1 == null || quocGia1.isEmpty()) {
            result.rejectValue("quocGia1", "Thành phố không được để trống!");
            return "/templates/Users/Layouts/DangNhap/Register";
        }

        if (thanhPho1 == null || thanhPho1.isEmpty()) {
            result.rejectValue("thanhPho1", "Huyện không được để trống!");
            return "/templates/Users/Layouts/DangNhap/Register";
        }

        if (diaChi1 == null || diaChi1.isEmpty()) {
            result.rejectValue("diaChi1", "Xã không được để trống!");
            return "/templates/Users/Layouts/DangNhap/Register";
        }
        //check huyện


        //check xã


        LocalDate ngayTao = LocalDate.now();
        LocalDate ngaySua = LocalDate.now();

        //Tạo mã tự sinh cho khách hàng
        LocalTime localTime = LocalTime.now();

        String ngayTaoDate = ngayTao.toString();
        String ngaySuaDate = ngaySua.toString();

        khachHang.setNgayTao(ngayTaoDate);
        khachHang.setNgaySua(ngaySuaDate);
        khachHang.setMaKhachHang("MaKH" + localTime.getHour() + localTime.getMinute() + localTime.getSecond());
        khachHang.setThanhPho(quocGia1);
        khachHang.setHuyen(thanhPho1);
        khachHang.setXa(diaChi1);
        khachHang.setDiaChi(khachHang.getDiaChi());

        khachHangRepository.save(khachHang);

        //Tạo mới giỏ hàng cho khách hàng luôn
        GioHang gioHang = new GioHang();

        gioHang.setKhachHang(khachHang);
        session.setAttribute("gioHang",gioHang);
        gioHangRepository.save(gioHang);
        attributes.addFlashAttribute("message", "Đăng kí tài khoản thành công");

        return "redirect:/KhachHang/loginViewDangNhap";

    }




    //Todo code edit khách hàng
    @RequestMapping("/KhachHang/edit/{id}")
    public String edit(Model model, @PathVariable UUID id){
        KhachHang khachHang = khachHangRepository.findById(id).orElse(null);

        if (khachHang == null){
            model.addAttribute("messageFind", "Không tìm thấy khách hàng có id: "+id);
            return "/KhachHang/list";
        }

        model.addAttribute("khachHang", khachHangRepository.findById(id));
        return "/KhachHang/edit";

    }

    //Todo code xóa khách hàng
    @RequestMapping("/KhachHang/delete/{id}")
    public String delete(@PathVariable("id") KhachHang khachHang){
        khachHangRepository.delete(khachHang);
        return "redirect:/KhachHang/list";
    }


    //Todo code update login
    @GetMapping("KhachHang/loginViewDangNhap")
    public String loginKhachHang(Model model){

        model.addAttribute("khachHang", new KhachHang());
        return "/templates/Users/Layouts/DangNhap/Login";

    }

    //Todo code log login
    @GetMapping("/KhachHang/showSweetAlertLoginSuccess")
    public String showSweetAlertLogLogin() {

        return "/templates/Users/Layouts/Log/DangNhapLog";

    }

    //Todo code check login
    @PostMapping("KhachHang/loginViewDangNhap")
    public String checkLoginKhachHang(Model model,
                                      @ModelAttribute("khachHang")KhachHang khachHang,
                                      @Valid
                                      BindingResult result,
                                      HttpSession session) {

        if (result.hasErrors()) {

            System.out.println("Đăng nhập thất bại !");
            model.addAttribute("messErLogin", "Đăng nhập thất bại");
            return "/templates/Users/Layouts/DangNhap/Login";

        }

        KhachHang khachHangData = khachHangRepository.findByEmailAndMatKhau(khachHang.getEmail(), khachHang.getMatKhau());

        //Đăng nhập thành công

        if (khachHangData != null) {

            UUID idKhachHang = khachHangData.getId(); //tìm kiếm mã khách hàng
            String maKH = khachHangData.getMaKhachHang();

            session.setAttribute("khachHangLog", khachHangData);
            session.setAttribute("maKH", maKH);
            session.setAttribute("idKhachHang", idKhachHang);//Lưu lại mã trong quá trình làm việc

//            return "redirect:/TrangChu/listGiayTheThao";
            return "redirect:/KhachHang/showSweetAlertLoginSuccess";


        } else if (khachHangData == null) {

            session.setAttribute("KhachHangNull", "Xin lỗi tài khoản này không tồn tại !");
            System.out.println("Xin lỗi tài khoản này không tồn tại !");
            return "/templates/Users/Layouts/DangNhap/Login";

        }

        model.addAttribute("erLogLogin", "Email hoặc mật khẩu không đúng !");
        System.out.println("Lỗi dữ liệu !");
        return "/templates/Users/Layouts/DangNhap/Login";



    }




    //Todo code đăng xuất tài khoản khách hàng
    @GetMapping("KhachHang/dangXuat")
    public String logout(Model model,
                         HttpSession session,
                         RedirectAttributes attributes){

     session.invalidate();//Hủy toàn bộ quá trình phiên làm việc
     attributes.addFlashAttribute("messageLogout","Đăng xuất tài khoản thành công !");

        return "redirect:/KhachHang/loginViewDangNhap";

    }


    @GetMapping("KhachHang/search")
    public String searchCoGiay(@RequestParam(value = "tenKhachHang", required = false) String tenKhachHang, Model model) {
        List<KhachHang> searchResults;
        if (tenKhachHang != null) {
            searchResults  = khachHangService.findKhachHang(tenKhachHang);
            model.addAttribute("searchResults ", searchResults );
            if (!searchResults .isEmpty()) {
                model.addAttribute("messageFindDone", "Tìm thấy dữ liệu");
            } else {
                model.addAttribute("messageFindError", "Không tìm thấy dữ liệu");
            }
        } else {
            model.addAttribute("messageFind", "Bạn hãy nhập tên khách hàng muốn tìm kiếm!");
        }
        return "/KhachHang/list";
    }

    //Todo codo log Khách hàng chưa đăng nhập
    @GetMapping("KhachHang/showSweetAlertLogin")
    public String showLogErNotLogin(){

        System.out.println("Bạn chưa đăng nhập tài khoản cho người dùng");
        return "/templates/Users/Layouts/Log/AddToCartLogNotLogin";

    }

//    //Todo code Trang chủ khách hàng
//    @GetMapping("TrangChu/ThongTinCaNhan")
//    public String thongTinKhachHang(HttpSession session,
//                                    Model model,
//                                    RedirectAttributes attributes){
//
//        //Đã đăng nhập tài khoản
//        if(session.getAttribute("khachHangLog") != null){
//
//            UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
//
//            if(idKhachHang != null){
//
//                System.out.println("Đăng nhập tài khoản thành công !");
//                KhachHang khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
//
//                model.addAttribute("khachHang",khachHang);
//
//                System.out.println("Email "+ khachHang.getEmail());
//                System.out.println("Tên " + khachHang.getTenKhachHang());
//                System.out.println("Giới tính "+ khachHang.getGioiTinh());
//                System.out.println("Số điện thoại "+ khachHang.getSoDienThoai());
//                System.out.println("Địa chỉ "+ khachHang.getDiaChi());
//
//                //Edit thông tin của khách hàng
//
//            }
//
//            return "/templates/Users/Layouts/DangNhap/CaiDat";
//
//        }else{
//
//            //Chưa đăng nhập tài khoản
//            System.out.println("Khách hàng chưa đăng nhập tài khoản !");
//            return "redirect:/KhachHang/showSweetAlertLogin";
//
//        }
//
//
//    }
//
//    //Thay đổi thông tin của khách hàng
//    @PostMapping("/TrangChu/ThongTinCaNhan/Luu")
//    public String luuThongTin(@ModelAttribute("khachHang")
//                                          KhachHang khachHang,
//                                          HttpSession session) {
//
//        // Lấy thông tin khách hàng từ session
////        KhachHang khachHangSession = (KhachHang) session.getAttribute("khachHang");
//
//        if(session.getAttribute("khachHangLog") != null){
//
//            System.out.println("Lấy được thông tin của khách hàng !");
//            UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
//
//            if(idKhachHang != null) {
//
//                System.out.println("Đăng nhập tài khoản thành công !");
//                KhachHang khachHangDangNhap = khachHangRepository.findById(idKhachHang).orElse(null);
//
//
//
//                khachHangRepository.save(khachHangDangNhap);
//                System.out.println("Lưu thành công !");
//
//            }
//
//        }
//
//        return "/templates/Users/Layouts/DangNhap/CaiDat";
//
//    }


    @GetMapping("/TrangChu/ThongTinCaNhan")
    public String thongTinKhachHang(HttpSession session, Model model, RedirectAttributes attributes) {
        if (session.getAttribute("khachHangLog") != null) {

            UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
            if (idKhachHang != null) {

                System.out.println("Đăng nhập tài khoản thành công !");
                KhachHang khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
                model.addAttribute("khachHang", khachHang);

            }

            return "/templates/Users/Layouts/DangNhap/CaiDat";

        } else {

            System.out.println("Khách hàng chưa đăng nhập tài khoản !");
            return "redirect:/KhachHang/showSweetAlertLogin";

        }
    }

    @PostMapping("/TrangChu/ThongTinCaNhan/Luu")
    public String luuThongTin(@ModelAttribute("khachHang") KhachHang khachHang, HttpSession session) {
        if (session.getAttribute("khachHangLog") != null) {
            UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
            if (idKhachHang != null) {
                System.out.println("Đăng nhập tài khoản thành công !");
                KhachHang khachHangDangNhap = khachHangRepository.findById(idKhachHang).orElse(null);

                // Cập nhật thông tin từ form
                khachHangDangNhap.setEmail(khachHang.getEmail());
                khachHangDangNhap.setLink(khachHang.getLink());
                khachHang.setMatKhau(khachHang.getMatKhau());
                khachHangDangNhap.setTenKhachHang(khachHang.getTenKhachHang());
                khachHangDangNhap.setGioiTinh(khachHang.getGioiTinh());
                khachHangDangNhap.setSoDienThoai(khachHang.getSoDienThoai());
                khachHangDangNhap.setDiaChi(khachHang.getDiaChi());
                khachHangDangNhap.setThanhPho(khachHang.getThanhPho());
                khachHang.setHuyen(khachHang.getHuyen());
                khachHang.setXa(khachHang.getXa());

                khachHangRepository.save(khachHangDangNhap);
                System.out.println("Lưu thành công !");
            }
        }
        return "/templates/Users/Layouts/DangNhap/CaiDat";
    }


}
