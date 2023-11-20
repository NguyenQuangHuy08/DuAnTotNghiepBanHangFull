package com.example.sd_41.controller.Admin;

import com.example.sd_41.model.TrongLuong;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.TrongLuongRepository;
import com.example.sd_41.service.admin.TrongLuongService;
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
@RequestMapping("/trongLuong")
public class TrongLuongController {

    @Autowired
    private TrongLuongService trongLuongService;

    @Autowired
    private TrongLuongRepository trongLuongRepository;

    @GetMapping()
    public String form(){
        return "TrongLuong/index";
    }

    @GetMapping("/hien-thi")
    public String hienThi(Model model, @RequestParam(name = "num",defaultValue = "0") Integer num) {
        Page<TrongLuong> trongLuongPage = trongLuongRepository.findAll(PageRequest.of(num,3));
        model.addAttribute("list", trongLuongPage.getContent());
        model.addAttribute("next",num);
        model.addAttribute("totalPages", trongLuongPage.getTotalPages());
        return "/TrongLuong/index";
    }
    @GetMapping("/search")
    public String searchByName(@RequestParam("tenTrongLuong") String tenTrongLuong, Model model) {
        List<TrongLuong> searchResults = trongLuongService.searchByTen(tenTrongLuong);
        model.addAttribute("list", searchResults);
        return "/TrongLuong/index";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam("trangThai") int trangThai, Model model) {
        List<TrongLuong> searchResults = trongLuongService.getAll();
        List<TrongLuong> list = new ArrayList<>();
        if(trangThai!=2){
            for (TrongLuong kieu : searchResults) {
                if(kieu.getTrangThai()==trangThai){
                    list.add(kieu);
                }

            }
        } else {
            for (TrongLuong kieu : searchResults) {
                list.add(kieu);
            }
        }
        model.addAttribute("list", list);
        return "/TrongLuong/index";
    }
    @GetMapping("/view-add")
    public String viewadd(Model model){
        model.addAttribute("trongLuong",new TrongLuong());
        return "TrongLuong/view-add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("trongLuong") @Valid TrongLuong trongLuong,
                      BindingResult bindingResult,
                      Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("trongLuong",trongLuong);
            return "/TrongLuong/view-add";
        }else {
            trongLuongService.save(trongLuong);
            return "redirect:/trongLuong/hien-thi";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id){
        trongLuongService.delete(id);
        return "redirect:/trongLuong/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable UUID id, Model model){
        TrongLuong trongLuong = trongLuongService.getOne(id);
        model.addAttribute("trongLuong",trongLuong);
        return "TrongLuong/detail";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") UUID id,TrongLuong trongLuong){
        trongLuongService.save(trongLuong);
        return "redirect:/trongLuong/hien-thi";
    }


}
