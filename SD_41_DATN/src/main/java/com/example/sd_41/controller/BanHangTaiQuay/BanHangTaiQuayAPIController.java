package com.example.sd_41.controller.BanHangTaiQuay;

import com.example.sd_41.model.*;
import com.example.sd_41.service.GiayTheThao.GiayTheThaoChiTietService;
import com.example.sd_41.service.HoaDon.HoaDonChiTietServie;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.KhachHangService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private GiayTheThaoChiTietService gttctService;

    @GetMapping("/gttct")
    private List<GiayTheThaoChiTiet> getAllGttct() {
        return gttctService.getAll();
    }

    @PostMapping("/gttct/search")
    private List<GiayTheThaoChiTiet> searchByName(@RequestBody String[] name) {
//        System.out.println("Name: " + name);
        List<GiayTheThaoChiTiet> result = gttctService.searchByName(name[0]);
//        System.out.println("GTTCT: "+result.get(0).getGiayTheThao(). getTenGiayTheThao());
        return result;
    }

    @PostMapping("/kh/search")
    private List<KhachHang> searchByTen(@RequestBody String[] name) {
//        System.out.println("Name: " + name);
        List<KhachHang> result = khService.findKhachHang(name[0]);
//        System.out.println(": "+result.get(0).getGiayTheThao().getTenGiayTheThao());
        return result;
    }


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
    public HoaDon createHoaDon(@RequestBody String[] strings, HttpSession session) {
        HoaDon hd = new HoaDon();

        // if (strings[0].length() != 0) {
        // UUID idUser = UUID.fromString(strings[0]);
        // User user = new User();
        // user.setId(idUser);
        // hd.setUser(user);
        // }

        UUID idUser = (UUID) session.getAttribute("idUser");
        User user = new User();
        user.setId(idUser);
        hd.setUser(user);

        if (strings[1].length() != 0) {
            UUID idKH = UUID.fromString(strings[1]);
            KhachHang kh = new KhachHang();
            kh.setId(idKH);
            hd.setKhachHang(kh);
        }

        return hdService.createHD(hd);
    }

    @PostMapping("/hd/pay/{id}")
    public String pay(@PathVariable("id") UUID id) {
        return hdService.thanhToan(id);
    }

@GetMapping("/hd/{id}")
    public HoaDon Hd(@PathVariable("id") UUID id){
        return hdService.findId(id);
}

}
