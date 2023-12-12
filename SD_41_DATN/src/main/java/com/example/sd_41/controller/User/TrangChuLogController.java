package com.example.sd_41.controller.User;

import com.example.sd_41.model.User;
import com.example.sd_41.service.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.GetExchange;

@Controller
public class TrangChuLogController {

    //Todo code trang chủ bên admin

    @Autowired
    ServletContext context;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private UserServiceImpl userServiceImpl;



    @RequestMapping(value = "/TrangChu/Admin/home")
    public String homeAdmin(){

        return "/TrangChuAdmin/home";

    }

   //Todo code thông tin tài khoản bên Admin

    //Todo code log sweler thay đổi mật khẩu thành công
    @GetMapping("User/ThayDoiMatKhauThanhCong")
    public String showViewThayDoiMatKhauThanhCong(){

        System.out.println("Thay đổi mật khẩu thành công !");
        return "/templates/Users/Layouts/Log/thayDoiMatKhauUserThanhCong";

    }



    //Todo code thay đổi mật khẩu bên admin khi quên

    @GetMapping("/Admin/viewQuenMatKhau/*")
    public String showviewQuenMatKhau(

            HttpServletRequest request,
            Model model
    ){

        String url = request.getRequestURI();
        String[] p = url.split("/Admin/viewQuenMatKhau/");
        String idStr = p[1];

        if (idStr.equalsIgnoreCase("2")) {

            model.addAttribute("idUser", "2");

        }

        return "/templates/Admin/Layouts/DangNhap/quenMatKhau";

    }

    //Todo code gửi email và save lại dữ liệu

    @PostMapping("/Admin/QuenMatKhau")
    public String saveAdminQuenMatKhau(

            Model model,
            HttpServletRequest request

    ){

        String email = request.getParameter("email");
        System.out.println("Email : "+ email);

        if(email == null || email.isEmpty() || email.trim().length() ==0){

            System.out.println("Xin lỗi vui lòng nhập địa chỉ email !");
            model.addAttribute("loiEmailNull","Vui lòng nhập địa chỉ email !");
            return "/templates/Admin/Layouts/DangNhap/quenMatKhau";

        }

        try{

            User user = userServiceImpl.findByEmail(email);

            int min = 100000;
            int max = 999999;
            int randomNumber = (int) (Math.random() * (max - min + 1) + min);

            User us = new User();
            // Thay đổi thông tin user
            us.setMaUser(user.getMaUser());
            us.setEmail(user.getEmail());
            us.setMatKhau(String.valueOf(randomNumber));
            us.setTenUser(user.getTenUser());
            us.setGioiTinh(user.getGioiTinh());
            us.setNgaySinh(user.getNgaySinh());
            us.setMaCCCD(user.getMaCCCD());
            us.setHoKhau(user.getHoKhau());
            us.setSoDienThoai(user.getSoDienThoai());
            us.setDiaChi(user.getDiaChi());
            us.setGhiChu(user.getGhiChu());
            us.setNgayTao(user.getNgayTao());
            us.setNgaySua(user.getNgaySua());
            us.setTrangThai(user.getTrangThai());
            us.setRole(user.getRole());

            userServiceImpl.update(user.getId(),us);


            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("Mật khẩu mới của bạn là ");
            message.setText("Mật khẩu mới của bạn là :" + randomNumber);

            mailSender.send(message);
            System.out.println("Thay đổi mật khẩu khách hàng thành công !");

            return "redirect:/User/ThayDoiMatKhauThanhCong";

        }catch (Exception e){

            e.printStackTrace();
            e.printStackTrace();
            System.out.println("Lỗi thay đổi tài khoản mật khẩu");
            model.addAttribute("idUser", "2");
            model.addAttribute("loi", "Xin lỗi email không hợp lệ hoặc không có tài khoản nào áp dụng cho email này");
            return "/templates/Admin/Layouts/DangNhap/quenMatKhau";

        }

    }





}
