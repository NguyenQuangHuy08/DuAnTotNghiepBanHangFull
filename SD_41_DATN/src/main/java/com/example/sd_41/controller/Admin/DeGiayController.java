package com.example.sd_41.controller.Admin;

import com.example.sd_41.model.DeGiay;
import com.example.sd_41.service.admin.DeGiayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("deGiay")
public class DeGiayController {

    @Autowired
    private DeGiayService service;

    private DeGiay deGiay = new DeGiay();

    @GetMapping()
    public String getIndex(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        model.addAttribute("list", this.service.pagination(pageable));
        model.addAttribute("read", "deGiay?");
        return "/deGiay/index";
    }

    @GetMapping("search")
    public String search(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
                         @RequestParam("name") String name) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        model.addAttribute("list", this.service.search(pageable, name));
        model.addAttribute("read", "deGiay/search?name=" + name + "&");
        return "deGiay/index";
    }

    @GetMapping("create")
    public String getFormCreate(Model model) {

        deGiay.setNgayTao(LocalDate.now().toString());
        deGiay.setNgaySua(LocalDate.now().toString());
        deGiay.setTrangThai(1);
        model.addAttribute("dg", deGiay);
        return "deGiay/create";
    }

    // @GetMapping("{id}")
    // public String delete(@PathVariable("id") UUID id){
    // this.service.delete(id);
    // return "redirect:/deGiay";
    // }

    @PostMapping("create")
    public String add(@Valid @ModelAttribute("dg") DeGiay de, BindingResult result) {
        if (result.hasErrors()) {

            return "deGiay/create";
        }

        de.setNgayTao(deGiay.getNgayTao());
        de.setNgaySua(deGiay.getNgaySua());
        this.service.add(de);

        return "redirect:/deGiay";
    }

    @GetMapping("edit/{id}")
    public String getFormUpdate(Model model, @PathVariable("id") DeGiay de) {
        de.setNgaySua(LocalDate.now().toString());
        model.addAttribute("dg", de);
        model.addAttribute("action", "update/" + de.getId());
        return "deGiay/edit";
    }

    @PostMapping("/edit/update/{id}")
    public String update(@ModelAttribute("dg") DeGiay de, @PathVariable("id") UUID id) {

        this.service.update(de, id);

        return "redirect:/deGiay";
    }

}
