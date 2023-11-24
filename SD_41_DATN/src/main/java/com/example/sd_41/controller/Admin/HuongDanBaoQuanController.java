package com.example.sd_41.controller.Admin;

import com.example.sd_41.model.HuongDanBaoQuan;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.HuongDanBaoQuanRepository;
import com.example.sd_41.service.admin.HuongDanBaoQuanService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
//@RequestMapping("HuongDanBaoQuan")
public class HuongDanBaoQuanController {
    @Autowired
        private HuongDanBaoQuanService huongDanBaoQuanService;

    @Autowired
    private HuongDanBaoQuanRepository huongDanBaoQuanRepository;

    @Autowired
    ServletContext context;

    @GetMapping("/HuongDanBaoQuan/list")
    public String listShowViewHuongDanBaoQuan(Model model,
                                     HttpSession session,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {

        //Todo code tab trạng thái và phân trang
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<HuongDanBaoQuan> page = huongDanBaoQuanRepository.findAll(pageable);
        //listPage sẽ dùng cho trạng thái giầy thể thao kích hoạt hay chưa
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        List<Integer> pageNumbers = getPageNumbers(page, pageNum);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", pageNum);


        // Lấy danh sách giày thể thao từ cơ sở dữ liệu
        List<HuongDanBaoQuan> listPageFind = huongDanBaoQuanRepository.findAll();
        model.addAttribute("listPageFind", listPageFind);

        return "/HuongDanBaoQuan/list";
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

    @GetMapping("/HuongDanBaoQuan/create")
    public String createPage(Model model){
        model.addAttribute("huongDanBaoQuan", new HuongDanBaoQuan());
        return "/HuongDanBaoQuan/create";
    }

    @PostMapping("/HuongDanBaoQuan/create")
    public String create(Model model, @ModelAttribute("huongDanBaoQuan") HuongDanBaoQuan huongDanBaoQuan, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "/HuongDanBaoQuan/create";
        }

        if(huongDanBaoQuan.getTenHuongDanBaoQuan() == null || huongDanBaoQuan.getTenHuongDanBaoQuan().isEmpty() || huongDanBaoQuan.getTenHuongDanBaoQuan().trim().length() == 0){
            model.addAttribute("checkHuongDanBaoQuanNotNull", "Tên hướng dẫn bảo quản không được để trống");
            return "/HuongDanBaoQuan/create";
        }

        if(huongDanBaoQuan.getTenHuongDanBaoQuan().matches("^\\d.*") || !huongDanBaoQuan.getTenHuongDanBaoQuan().matches(".*[a-zA-Z].*")){
            model.addAttribute("checkHuongDanBaoQuanHopLe", "Tên hướng dẫn bảo quản không hợp lệ");
            return "/HuongDanBaoQuan/create";
        }

        LocalDate ngayTao = LocalDate.now();
        LocalDate ngaySua = LocalDate.now();

        String ngayTaoDate = ngayTao.toString();
        String ngaySuaDate = ngaySua.toString();

        huongDanBaoQuanRepository.save(huongDanBaoQuan);
        attributes.addFlashAttribute("message", "Thêm thành công!");
        return "redirect:/HuongDanBaoQuan/list";
    }

    @GetMapping("/HuongDanBaoQuan/edit/{id}")
    public String editPage(Model model, @PathVariable UUID id){
        HuongDanBaoQuan huongDanBaoQuan = huongDanBaoQuanRepository.findById(id).orElse(null);
        if(huongDanBaoQuan == null){
            model.addAttribute("messageFind", "Không tìm thấy id có mã: " +id);
            return "/HuongDanBaoQuan/list";
        }

        model.addAttribute("huongDanBaoQuan", huongDanBaoQuanRepository.findById(id).orElse(null));
        return "/HuongDanBaoQuan/edit";
    }

//    @GetMapping("/search")
//    public String searchMauSac(@RequestParam(value = "tenMauSac", required = false) String tenMauSac, Model model){
//        List<MauSac> listPage;
//        if(tenMauSac != null){
//            listPage =mauSacRepository.findMauSacByTen(tenMauSac);
//        }else {
//            listPage = mauSacRepository.findAll();
//        }
//
//        if (!listPage.isEmpty()){
//            model.addAttribute("listPage", listPage);
//        }else {
//            model.addAttribute("message", "Không tìm thấy kết quả");
//            listPage = mauSacRepository.findAll();
//        }
//
//        model.addAttribute("listPage", listPage);
//        return "/MauSac/list";
//    }

    @GetMapping("/HuongDanBaoQuan/delete/{id}")
    public String delete(@PathVariable("id") HuongDanBaoQuan huongDanBaoQuan){
        huongDanBaoQuanRepository.delete(huongDanBaoQuan);
        return "redirect:/HuongDanBaoQuan/list";
    }

//    @GetMapping("/HuongDanBaoQuan/search")
//    public String searchHuongDanBaoQuan(@RequestParam(value = "tenHuongDanBaoQuan", required = false) String tenHuongDanBaoQuan, Model model) {
//        List<HuongDanBaoQuan> listPageFind;
//        if (tenHuongDanBaoQuan != null) {
//            listPageFind = huongDanBaoQuanService.findHuongDanBaoQuan(tenHuongDanBaoQuan);
//            if (listPageFind.size()!=0) {
//                model.addAttribute("listPage", listPageFind);
//                model.addAttribute("messageFindDone", "Tìm thấy dữ liệu");
//                return "/HuongDanBaoQuan/list";
//            } else {
//                model.addAttribute("messageFindError", "Không tìm thấy dữ liệu");
//            }
//        } else {
//            model.addAttribute("messageFind", "Bạn hãy nhập tên hướng dẫn bảo quản muốn tìm kiếm!");
//        }
//
//        return "/HuongDanBaoQuan/list";
//    }

    @ModelAttribute("tenHuongDanBaoQuan")
    public List<HuongDanBaoQuan> getListHuongDanBaoQuan() {
        return huongDanBaoQuanRepository.findAll();
    }
}
