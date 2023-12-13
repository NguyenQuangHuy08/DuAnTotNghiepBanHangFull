package com.example.sd_41.controller.BanHangTaiQuay;

import com.example.sd_41.model.HoaDon;
import com.example.sd_41.model.HoaDonChiTiet;
import com.example.sd_41.model.KhachHang;
import com.example.sd_41.model.User;
import com.example.sd_41.service.HoaDon.HoaDonChiTietServie;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class BanHangTaiQuayAPIController {
    @Autowired
    private HoaDonChiTietServie hoaDonChiTietservie;

    @Autowired
    private HoaDonService hdService;

    @Autowired
    private KhachHangService khService;

    @GetMapping("/kh")
    public List<KhachHang> getAllKH() {

        return khService.getAll();

    }

    @GetMapping("/hdct/{id}")
    public List<HoaDonChiTiet> getAllHDCTByid(@PathVariable("id") UUID id) {
        return hoaDonChiTietservie.getListByID(id);
    }

    @PutMapping("hdct/{id}")
    public HoaDonChiTiet putHDCTByid(@PathVariable("id") UUID id, @RequestBody int soLuong) {
        return hoaDonChiTietservie.updateHDCT(soLuong, id);
    }

    @DeleteMapping("/hdct/{id}")
    public void deleteHDCTByid(@PathVariable("id") UUID id) {
        hoaDonChiTietservie.removeHDCTByID(id);
    }

    @GetMapping("/hd/unpaid")
    public List<HoaDon> getAllHDC() {

        return hdService.hoaDonCho();

    }

    @DeleteMapping("/hd/{id}")
    public void removeHDCByID(@PathVariable("id") UUID id) {

        hdService.removeHDCByID(id);

    }

    @PostMapping("/hdct")
    public HoaDonChiTiet addHDCT(@RequestBody HoaDonChiTiet hdct) {
        return hoaDonChiTietservie.addHDCT(hdct);
    }

    @PostMapping("/hdct/check/{id}")
    public boolean check(@PathVariable("id") UUID id, @RequestBody int soLuong) {
        return hoaDonChiTietservie.checkSoLuongGTTCT(id, soLuong);
    }

    @PostMapping("/hd")
    public HoaDon createHoaDon(@RequestBody String[] strings) {
        HoaDon hd = new HoaDon();

        if (strings[0].length() != 0) {
            UUID idUser = UUID.fromString(strings[0]);
            User user = new User();
            user.setId(idUser);
            hd.setUser(user);
        }

        if (strings[1].length() != 0) {
            UUID idKH = UUID.fromString(strings[1]);
            KhachHang kh = new KhachHang();
            kh.setId(idKH);
            hd.setKhachHang(kh);
        }

        return hdService.createHD(hd);
    }

    @PostMapping("/hd/pay/{id}")
    public HoaDon pay(@PathVariable("id") UUID id) {

        return hdService.thanhToan(id);

    }




}
