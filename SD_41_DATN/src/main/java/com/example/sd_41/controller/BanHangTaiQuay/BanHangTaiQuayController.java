package com.example.sd_41.controller.BanHangTaiQuay;

import com.example.sd_41.service.GiayTheThao.GiayTheThaoChiTietService;
import com.example.sd_41.service.GiayTheThao.GiayTheThaoService;
import com.example.sd_41.service.HoaDon.HoaDonChiTietServie;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("BanHangTaiQuay")
public class BanHangTaiQuayController {
    @Autowired
    private GiayTheThaoService service;

    @Autowired
    private GiayTheThaoChiTietService gttctService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietServie hoaDonChiTietservie;

    @Autowired
    private KhachHangService khachHangService;

    @GetMapping()
    public String getView(Model model) {
        model.addAttribute("list", gttctService.getAll());
        model.addAttribute("listHDC", hoaDonService.hoaDonCho());
        model.addAttribute("listKH", khachHangService.getAll());
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @GetMapping("thanhToan/{id}")
    public String getViewPay(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("list", hoaDonChiTietservie.getListByID(id));
        model.addAttribute("id", id);
        return "BanHangTaiQuay/thanhToan";
    }

    // @PostMapping("pay/{id}")
    // public String pay(@PathVariable("id") UUID id, Model model) {
    //     HoaDon hd = hoaDonService.thanhToan(id);        
    //     return "redirect:/BanHangTaiQuay";
    // }

}


