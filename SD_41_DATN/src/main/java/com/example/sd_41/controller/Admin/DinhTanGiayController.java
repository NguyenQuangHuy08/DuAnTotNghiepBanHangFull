package com.example.sd_41.controller.Admin;

import com.example.sd_41.model.DinhTanGiay;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.DinhTanGiayRepository;
import com.example.sd_41.service.admin.DinhTanGiayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/dinhTanGiay")

public class DinhTanGiayController {

    @Autowired
    private DinhTanGiayRepository dinhTanGiayRepository;

    @Autowired
    private DinhTanGiayService dinhTanGiayService;

    @GetMapping()
    public String form(){
        return "KieuBuoc/index";
    }

    @GetMapping("/hien-thi")
    public String hienThi(Model model, @RequestParam(name = "num",defaultValue = "0") Integer num) {
        Page<DinhTanGiay> dtgPage = dinhTanGiayRepository.findAll(PageRequest.of(num,3));
        model.addAttribute("list", dtgPage.getContent());
        model.addAttribute("next",num);
        model.addAttribute("totalPages", dtgPage.getTotalPages());
        return "/DinhTanGiay/index";
    }
    @GetMapping("/search")
    public String searchByName(@RequestParam("tenDinhTanGiay") String tenDinhTanGiay, Model model) {
        List<DinhTanGiay> searchResults = dinhTanGiayService.searchByTen(tenDinhTanGiay);
        model.addAttribute("list", searchResults);
        return "/DinhTanGiay/index";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam("trangThai") int trangThai, Model model) {
        List<DinhTanGiay> searchResults = dinhTanGiayService.getAll();
        List<DinhTanGiay> list = new ArrayList<>();
        if(trangThai!=2){
            for (DinhTanGiay kieu : searchResults) {
                if(kieu.getTrangThai()==trangThai){
                    list.add(kieu);
                }

            }
        } else {
            for (DinhTanGiay kieu : searchResults) {
                list.add(kieu);
            }
        }
        model.addAttribute("list", list);
        return "/DinhTanGiay/index";
    }
    @GetMapping("/view-add")
    public String viewadd(Model model){
        model.addAttribute("dinhTanGiay",new DinhTanGiay());
        return "DinhTanGiay/view-add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("dinhTanGiay") @Valid DinhTanGiay dinhTanGiay,
                      BindingResult bindingResult,
                      Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("dinhTanGiay",dinhTanGiay);
            return "/DinhTanGiay/view-add";
        }else {
            dinhTanGiayService.save(dinhTanGiay);
            return "redirect:/dinhTanGiay/hien-thi";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id){
        dinhTanGiayService.delete(id);
        return "redirect:/dinhTanGiay/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable UUID id, Model model){
        DinhTanGiay dinhTanGiay = dinhTanGiayService.getOne(id);
        model.addAttribute("dinhTanGiay",dinhTanGiay);
        return "DinhTanGiay/detail";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") String id, DinhTanGiay dinhTanGiay){
        dinhTanGiayService.save(dinhTanGiay);
        return "redirect:/dinhTanGiay/hien-thi";
    }

}
