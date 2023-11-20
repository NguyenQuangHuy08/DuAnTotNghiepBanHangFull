package com.example.sd_41.controller.Admin;

import com.example.sd_41.model.CoGiay;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.CoGiayRepository;
import com.example.sd_41.service.admin.CoGiayService;
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
//@RequestMapping(value = "CoGiay")
public class CoGiayController {

    @Autowired
    private CoGiayService coGiayService;

    @Autowired
    private CoGiayRepository coGiayRepository;

    @Autowired
    ServletContext context;

    @GetMapping("CoGiay/list")
    public String listShowViewMauSac(Model model,
                                     HttpSession session,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {

        //Todo code tab trạng thái và phân trang
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<CoGiay> page = coGiayRepository.findAll(pageable);
        //listPage sẽ dùng cho trạng thái giầy thể thao kích hoạt hay chưa
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        List<Integer> pageNumbers = getPageNumbers(page, pageNum);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", pageNum);


        // Lấy danh sách giày thể thao từ cơ sở dữ liệu
        List<CoGiay> listPageFind = coGiayRepository.findAll();
        model.addAttribute("listPageFind", listPageFind);

        return "/CoGiay/list";
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

    @GetMapping("/CoGiay/view-create")
    public String createPage(Model model) {
        model.addAttribute("coGiay", new CoGiay());
        return "/CoGiay/create";
    }

    @PostMapping("/CoGiay/create")
    public String create(Model model, @ModelAttribute("coGiay") CoGiay coGiay, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/CoGiay/create";
        }

        if (coGiay.getTenCoGiay() == null || coGiay.getTenCoGiay().isEmpty() || coGiay.getTenCoGiay().trim().length() == 0) {
            model.addAttribute("checkCoGiayNotNull", "Tên cỡ giầy không được để trống");
            return "/CoGiay/create";
        }

        if (coGiay.getTenCoGiay().matches("^\\d.*") || !coGiay.getTenCoGiay().matches(".*[a-zA-Z].*")) {
            model.addAttribute("checkCoGiayHopLe", "Tên cỡ giầy không hợp lệ");
            return "/CoGiay/create";
        }

        LocalDate ngayTao = LocalDate.now();
        LocalDate ngaySua = LocalDate.now();

        String ngayTaoDate = ngayTao.toString();
        String ngaySuaDate = ngaySua.toString();

        coGiay.setNgaySua(ngaySuaDate);
        coGiay.setNgayTao(ngayTaoDate);

        coGiayRepository.save(coGiay);
        attributes.addFlashAttribute("message", "Thêm thành công!");
        return "redirect:/CoGiay/list";
    }

    @GetMapping("/CoGiay/edit/{id}")
    public String editPage(Model model, @PathVariable UUID id) {
        CoGiay coGiay = coGiayRepository.findById(id).orElse(null);
        if (coGiay == null) {
            model.addAttribute("messageFind", "Không tìm thấy id có mã: " + id);
            return "/CoGiay/list";
        }

        model.addAttribute("coGiay", coGiayRepository.findById(id).orElse(null));
        return "/CoGiay/edit";
    }

    @GetMapping("CoGiay/search")
    public String searchCoGiay(@RequestParam(value = "tenCoGiay", required = false) String tenCoGiay, Model model) {
        List<CoGiay> listPageFind;
        if (tenCoGiay != null) {
            listPageFind = coGiayService.findCoGiay(tenCoGiay);

            if (listPageFind.size()!=0) {
                model.addAttribute("listPage", listPageFind);
                model.addAttribute("messageFindDone", "Tìm thấy dữ liệu");
                return "/CoGiay/list";
            } else {
                model.addAttribute("messageFindError", "Không tìm thấy dữ liệu");
            }
        } else {
            model.addAttribute("messageFind", "Bạn hãy nhập tên cổ giày muốn tìm kiếm!");
        }

        return "/CoGiay/list";
    }

    @RequestMapping("/CoGiay/delete/{id}")
    public String delete(@PathVariable("id") CoGiay coGiay) {
        coGiayRepository.delete(coGiay);
        return "redirect:/CoGiay/list";
    }

    @ModelAttribute("tenCoGiay")
    public List<CoGiay> getListCoGiay() {
        return coGiayRepository.findAll();
    }
}
