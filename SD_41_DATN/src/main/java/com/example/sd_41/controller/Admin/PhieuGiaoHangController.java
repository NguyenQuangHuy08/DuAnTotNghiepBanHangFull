package com.example.sd_41.controller.Admin;

import com.example.sd_41.model.HoaDon;
import com.example.sd_41.model.KhachHang;
import com.example.sd_41.model.PhieuGiaoHang;
import com.example.sd_41.model.User;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.UserRepository;
import com.example.sd_41.repository.PhieuGiaoHang.PhieuGiaoHangRepository;
import com.example.sd_41.service.PhieuGiaoHang.PhieuGiaoHangService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller

public class PhieuGiaoHangController {

    @Autowired
    ServletContext context;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private PhieuGiaoHangRepository phieuGiaoHangRepository;

    @Autowired
    private PhieuGiaoHangService phieuGiaoHangService;

    @GetMapping("PhieuGiaoHang/listPhieuGiaoHang")
    public String listShowViewPhieuGiaoHang(Model model,
                                          HttpSession session,
                                          @RequestParam(name = "tab", required = false, defaultValue = "active") String tab,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize) {


        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<PhieuGiaoHang> page = phieuGiaoHangRepository.findAll(pageable);

        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        List<Integer> pageNumbers = getPageNumbers(page, pageNum);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", pageNum);

        return "/PhieuGiaoHang/list";
    }

    private List<Integer> getPageNumbers(Page<?> page, int currentPage) {
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int startPage, endPage;

        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 2) {
                startPage = 1;
                endPage = 5;
            } else if (currentPage + 2 >= totalPages) {
                startPage = totalPages - 4;
                endPage = totalPages;
            } else {
                startPage = currentPage - 2;
                endPage = currentPage + 2;
            }
        }

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        return pageNumbers;
    }

    //Todo code create bảng phiếu giao hàng
    private boolean tenPhieuGiaoHangCheckTrung(String tenPhieuGiaoHang){

        for(PhieuGiaoHang phieuGiaoHang: phieuGiaoHangRepository.findAll()){

            if(phieuGiaoHang.getTenNguoiNhan().equalsIgnoreCase(tenPhieuGiaoHang)){

                return true;//Tên phiếu giao hàng đã tồn tại trong list

            }

        }

        return false;
    }


    //Todo code bảng phiếu giao hàng
    @GetMapping("PhieuGiaoHang/create")
    public String createShow(Model model){

        model.addAttribute("phieuGiaoHang",new PhieuGiaoHang());
        return "/PhieuGiaoHang/create";

    }

    @PostMapping(value = "PhieuGiaoHang/create")
    public String createSavePhieuGiaoHang(Model model,
                                        @Valid
                                        @ModelAttribute("phieuGiaoHang") PhieuGiaoHang phieuGiaoHang,
                                        BindingResult result,
                                        RedirectAttributes attributes,
                                        HttpSession session

    ){

        if(result.hasErrors()){

            model.addAttribute("viewEr","Lỗi All");
            return "/PhieuGiaoHang/create";

        }

        //Todo code check

        //Check tên sản phẩm trống
        if(phieuGiaoHang.getTenNguoiNhan() == null
                ||  phieuGiaoHang.getTenNguoiNhan().trim().length() ==0
                ||   phieuGiaoHang.getTenNguoiNhan().isEmpty()){

            model.addAttribute("erTenNguoiNhanIsEmty","Tên người nhận không được để trống !");
            return "/PhieuGiaoHang/create";

        }
        //Check tên sản phẩm nhập kí tự số đầu tiên
        if (phieuGiaoHang.getTenNguoiNhan().matches("^\\d.*") ||
                !phieuGiaoHang.getTenNguoiNhan().matches(".*[a-zA-Z].*")) {
            model.addAttribute("erCheckTenNguoiNhanSo", "Tên người nhận không hợp lệ!, Phải bắt đầu bằng chữ cái đầu tiên!");
            return "/PhieuGiaoHang/create";

        }

        Pattern pattern = Pattern.compile("^[^-0-9].*");
        Matcher matcher = pattern.matcher(phieuGiaoHang.getTenNguoiNhan());

        if (!matcher.matches()) {
            model.addAttribute("erCheckTenNguoiNhanSo", "Tên người nhận không hợp lệ!");
            return "/PhieuGiaoHang/create";
        }



        //Check tên sản phẩm trùng
        if(tenPhieuGiaoHangCheckTrung(phieuGiaoHang.getTenNguoiNhan())){

            model.addAttribute("erCheckTenPhieuGiaoHangTrung","Tên phiếu giao hàng đã có trong list !");
            return "/PhieuGiaoHang/create";

        }

        //Check phí ship

        if(phieuGiaoHang.getPhiShip() == null || phieuGiaoHang.getPhiShip().trim().length()==0 || phieuGiaoHang.getPhiShip().isEmpty()){

            model.addAttribute("erCheckPhiShipNumber","Không được để trống phí ship");
            return "/PhieuGiaoHang/create";


        }

        phieuGiaoHang.setTrangThai(0);
        phieuGiaoHangRepository.save(phieuGiaoHang);
        attributes.addFlashAttribute("messageDone","Create phiếu giao hàng thành công !");

        return "redirect:/PhieuGiaoHang/listPhieuGiaoHang";

    }



    @GetMapping("PhieuGiaoHang/detail/{id}")
    public String showDetail(@PathVariable UUID id, Model model){

        PhieuGiaoHang phieuGiaoHang = phieuGiaoHangService.getOne(id);
        model.addAttribute("phieuGiaoHang",phieuGiaoHang);
        return "/PhieuGiaoHang/update";

    }

    @GetMapping("PhieuGiaoHang/update/{id}")
    public String showUpdatePhieuGiaoHang(Model model,
                                          @PathVariable UUID id
    ){


        model.addAttribute("phieuGiaoHang",phieuGiaoHangRepository.findById(id).orElse(null));
        System.out.println("Lỗi : "+ phieuGiaoHangRepository.findById(id).orElse(null));


        return "/PhieuGiaoHang/update";


    }



    @ModelAttribute("khachHang")
    public List<KhachHang> getListKhachHang(){

        return khachHangRepository.findAll();

    }

    @ModelAttribute("user")
    public List<User> getListUser(){

        return userRepository.findAll();

    }

    @ModelAttribute("hoaDon")
    public List<HoaDon> getListHoaDon(){

        return hoaDonRepository.findAll();
    }

    @ModelAttribute("tenKhachHang")
    public List<KhachHang> getListTenKhachHang() {
        return khachHangRepository.findAll();
    }

    @ModelAttribute("tenUser")
    public List<User> getListTenUser() {
        return userRepository.findAll();
    }
}
