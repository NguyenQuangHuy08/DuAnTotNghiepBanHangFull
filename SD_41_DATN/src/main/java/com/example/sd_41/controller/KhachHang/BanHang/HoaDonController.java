package com.example.sd_41.controller.KhachHang.BanHang;

import com.example.sd_41.controller.Momo.MomoModel;
import com.example.sd_41.controller.Momo.ResultMoMo;
import com.example.sd_41.controller.Utils.Constant;
import com.example.sd_41.controller.Utils.Decode;
import com.example.sd_41.model.*;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.MauSacRepository;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.SizeRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.HoaDon.HoaDonServiceImpl;
import com.example.sd_41.service.impl.ChuongTrinhGiamGiaHoaDonImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class HoaDonController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private HoaDonServiceImpl hoaDonServiceImpl;

    @Autowired
//    @Qualifier("giayTheThaoChiTietRepository")
    private GiayTheThaoChiTietRepository giayTheThaoChiTietRepository;

    @Autowired
    private GiayTheThaoRepository giayTheThaoRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    ChuongTrinhGiamGiaHoaDonImpl chuongTrinhGiamGiaHoaDonImpl;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private MauSacRepository mauSacRepository;



    //Todo code view hóa đơn
    //hiện thông tin cho view hóa đơn

    @GetMapping("nguoiDung/HoaDon/{id}")
    public String showViewHoaDon(
            @PathVariable String id,
            Model model,
            RedirectAttributes attributes,
            HttpSession session,
            HttpServletRequest request) {

        if(session.getAttribute("khachHangLog") != null) {

            UUID hoaDonId = UUID.fromString(id);
            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
            model.addAttribute("hoaDonId", hoaDonId);

            if (hoaDon != null) {

                List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);

                //Hiện view thông tin của hóa đơn
                model.addAttribute("hoaDonChiTietList", hoaDonChiTietList);
                model.addAttribute("hoaDon1", hoaDonId);
                model.addAttribute("hoaDon", hoaDon);
                model.addAttribute("maHoaDon", hoaDon.getMaHoaDon());
                model.addAttribute("idKhacHang", hoaDon.getKhachHang().getId());
                model.addAttribute("tenKhachHang", hoaDon.getKhachHang().getTenKhachHang());
                model.addAttribute("diaChi", hoaDon.getKhachHang().getDiaChi());
                model.addAttribute("soDienThoai", hoaDon.getKhachHang().getSoDienThoai());
                model.addAttribute("email", hoaDon.getKhachHang().getEmail());
                model.addAttribute("thanhPho", hoaDon.getKhachHang().getThanhPho());
                model.addAttribute("huyen", hoaDon.getKhachHang().getHuyen());
                model.addAttribute("xa", hoaDon.getKhachHang().getXa());

                //Thành tiền của hóa đơn
                //Todo code thông tin cho giảm hóa đơn

                String sl = hoaDonServiceImpl.tongSl(hoaDonId);
                System.out.println("Thành tiền :" + hoaDon.getThanhTien());
                System.out.println("Số lượng : " + sl);
                System.out.println("Id của hóa đơn :" + hoaDonId);
                String idGiayTheThaoChiTiet = request.getParameter("idGiayTheThaoChiTiet");
                System.out.println("ID giầy thể thao chi tiết: " + idGiayTheThaoChiTiet);


                List<UUID> hoaDonChiTietIds = new ArrayList<>();

                // Duyệt qua danh sách và lấy id của từng hóa đơn chi tiết
                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {

                    hoaDonChiTietIds.add(hoaDonChiTiet.getId());

                }


                ///Hiện ra thông tin của các chương trình giảm giá
                model.addAttribute("CTGG", chuongTrinhGiamGiaHoaDonImpl.findBySlandTt(sl, hoaDon.getThanhTien()));

            } else {

                System.out.println("Xin lỗi không tìm thấy hóa đơn phù hợp cho khách hàng này");

            }

            return "/templates/Users/Layouts/Shop/viewHoaDon";

        }else{

            System.out.println("Chưa đăng nhập tài khoản!");
            return "redirect:/TrangChu/listGiayTheThao";

        }

    }

    //Todo code thanh toán thành công chờ xác nhận đơn hàng
    @PostMapping("nguoiDung/hoaDon/ThanhToan/{id}")
    @Transactional
    public String showEditViewThanhToanHoaDon(Model model,
                                              @PathVariable String id,
                                              HttpSession session,
                                              HttpServletRequest request,
                                              RedirectAttributes attributes) throws JsonProcessingException {

        if(session.getAttribute("khachHangLog") != null) {

            UUID hoaDonId = UUID.fromString(id);
            session.setAttribute("hoaDonId", hoaDonId);

            //Todo code mã code mới bên controller

            String diaChiChon = request.getParameter("diaChiChon");

            //Địa chỉ chon là null
            if (diaChiChon == null || diaChiChon.isEmpty()) {

                attributes.addFlashAttribute("diaChiChonNull", "Xin lỗi vui lòng chọn địa chỉ để có thể thanh toán!");
                System.out.println("Vui lòng chọn một địa chỉ để có thể thanh toán");
                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

            } else {

                //Địa chỉ chọn không null
                String hinhThucThanhToan = request.getParameter("payment");

                //Hình thức thanh toán
                if (hinhThucThanhToan == null || hinhThucThanhToan.isEmpty()) {

                    attributes.addFlashAttribute("hinhThucThanhToanNull", "Vui lòng chọn hình thức thanh toán!");
                    System.out.println("Vui lòng chọn hình thức thanh toán");
                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                } else {

                    //Chọn địa chỉ cũ để thanh toán
                    if ("diaChiCu".equals(diaChiChon)) {

                        System.out.println("Bạn chọn địa chỉ cũ để thanh toán");

                        //Thanh toán bằng địa chỉ cũ qua thanh toán momo
                        if ("momo".equals(hinhThucThanhToan)) {

                            System.out.println("Bạn chọn thanh toán bằng hình thức momo");
                            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                            if (hoaDon != null) {

                                String tenKhachHangCu = request.getParameter("tenKhachHang");
                                String soDienThoaiCuMomo = request.getParameter("soDienThoai");
                                String quocGiaCuMomo = request.getParameter("quocGia");
                                String thanhPhoCuMomo = request.getParameter("thanhPho");
                                String diaChiCuMomo = request.getParameter("diaChi");
                                String messCuMomo = request.getParameter("mess");
                                String thanhTien = request.getParameter("thanhTien");
                                String phiShip = request.getParameter("ship");
                                String thanhTienTong1 = request.getParameter("tongTien");

                                System.out.println("Thành tiền : " + thanhTien);
                                System.out.println("Phí ship : " + phiShip);
                                System.out.println("Tổng tiền : " + thanhTienTong1);

//                            BigDecimal thanhTienBigDecimal = new BigDecimal(thanhTienTong1);

                                ObjectMapper mapper = new ObjectMapper();

                                int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
                                String orderId = Integer.toString(code);
                                MomoModel jsonRequest = new MomoModel();
                                jsonRequest.setPartnerCode(Constant.IDMOMO);
                                jsonRequest.setOrderId(orderId);
                                jsonRequest.setStoreId(orderId);
                                jsonRequest.setRedirectUrl(Constant.redirectUrl);
                                jsonRequest.setIpnUrl(Constant.ipnUrl);
                                jsonRequest.setAmount(thanhTienTong1);
                                jsonRequest.setOrderInfo("Thanh toán sản phẩm của hàng Bess Shoes.");
                                jsonRequest.setRequestId(orderId);
                                jsonRequest.setOrderType(Constant.orderType);
                                jsonRequest.setRequestType(Constant.requestType);
                                jsonRequest.setTransId("1");
                                jsonRequest.setResultCode("200");
                                jsonRequest.setMessage("");
                                jsonRequest.setPayType(Constant.payType);
                                jsonRequest.setResponseTime("300000");
                                jsonRequest.setExtraData("");

                                String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
                                        + jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
                                        + jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
                                        + Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
                                        + Constant.requestType;


                                String signature = Decode.encode(Constant.serectkey, decode);
                                jsonRequest.setSignature(signature);
                                String json = mapper.writeValueAsString(jsonRequest);
                                HttpClient client = HttpClient.newHttpClient();
                                ResultMoMo res = new ResultMoMo();

                                try {
                                    HttpRequest requestMomo = HttpRequest.newBuilder().uri(new URI(Constant.Url))
                                            .POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
                                            .build();
                                    HttpResponse<String> response = client.send(requestMomo, HttpResponse.BodyHandlers.ofString());
                                    res = mapper.readValue(response.body(), ResultMoMo.class);

                                } catch (InterruptedException | URISyntaxException | IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                if (res == null) {

                                    session.setAttribute("error_momo", "Thanh toán thất bại");
                                    System.out.println("Thanh toán thất bại");
                                    return "redirect:/TrangChu/listGiayTheThao";


                                } else {

                                    //Hiện ra trang để quét mã QR
                                    System.out.println("Lưu lại thông tin hóa đơn khi thanh toán bằng momo");
                                    BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienTong1);

                                    hoaDon.setTrangThai(1);
                                    hoaDon.setThanhTien(thanhTienBigDecemal);
                                    hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoaiCuMomo + ", Địa chỉ giao hàng: " + diaChiCuMomo + "," + thanhPhoCuMomo + "," + quocGiaCuMomo);
                                    hoaDon.setMess(messCuMomo);

                                    List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
                                    model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);

                                    //Tìm hóa đơn để tính số lượng
                                    for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {

                                        //Số lượng mua của khách hàng
                                        String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
                                        int soLuongMua = Integer.parseInt(soLuongMuaToString);

                                        //Số lượng có trong kho
                                        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
                                        String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
                                        int soLuongCo = Integer.parseInt(soLuongCoToString);

                                        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
                                        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);

                                    }


                                    hoaDonRepository.save(hoaDon);
                                    //Xóa giỏ hang chi tiết khi thanh toán
                                    String[] idGiayTheTheThaoChiTietArrayMomoCu = request.getParameterValues("idGiayTheTheThaoChiTiet");

                                    if (idGiayTheTheThaoChiTietArrayMomoCu != null) {
                                        for (String idGiayTheTheThaoChiTiet : idGiayTheTheThaoChiTietArrayMomoCu) {
                                            UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTiet);

                                            // Tìm giỏ hàng chi tiết để xóa
                                            GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);

                                            // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
                                            if (gioHangChiTiet != null) {
                                                // Xóa giỏ hàng chi tiết
                                                gioHangChiTietRepository.delete(gioHangChiTiet);

                                            }
                                        }
                                    }

                                    return "redirect:" + res.payUrl;

                                }

                            }

                        }

                        //Thanh toán bằng địa chỉ cũ qua thanh toán khi nhận hàng
                        if ("cash".equals(hinhThucThanhToan)) {

                            System.out.println("Bạn chọn thanh toán bằng hình thức thanh toán khi nhận hàng");
                            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                            if (hoaDon != null) {

                                String tenKhachHangCu = request.getParameter("tenKhachHang");
                                String soDienThoaiCu = request.getParameter("soDienThoai");
                                String quocGiaCu = request.getParameter("quocGia");
                                String thanhPhoCu = request.getParameter("thanhPho");
                                String diaChiCu = request.getParameter("diaChi");
                                String messCu = request.getParameter("mess");
                                String thanhTienTong1 = request.getParameter("tongTien");
//                            String idGiayTheThaoChiTiet = request.getParameter("idGiayTheTheThaoChiTiet");
                                String[] idGiayTheTheThaoChiTietArray = request.getParameterValues("idGiayTheTheThaoChiTiet");

                                String ship = request.getParameter("ship");

                                //Todo gọi id để xóa giỏ hàng chi tiết
                                String idGiayTheTheThaoChiTiet = request.getParameter("idGiayTheTheThaoChiTiet");

                                System.out.println("Giá trị ship ban đầu: " + request.getParameter("ship"));
                                ship = ship.trim();
                                System.out.println("Giá trị ship sau khi cắt bỏ khoảng trắng: " + ship);

//                            System.out.println("Id của giầy thể thao chi tiết: "+ idGiayTheThaoChiTiet);

                                if (ship.matches("\\d*\\.?\\d*")) {
                                    BigDecimal shipBigDecimal = new BigDecimal(ship);
                                    System.out.println("Giá trị shipBigDecimal: " + shipBigDecimal);
                                    hoaDon.setPhiShip(shipBigDecimal);
                                } else {
                                    System.out.println("Chuỗi không hợp lệ");
                                }

                                LocalDate ngayThanhToanCu = LocalDate.now();
                                String ngayTaoCu = ngayThanhToanCu.toString();

                                hoaDon.setTrangThai(1);
                                hoaDon.setNgayThanhToan(ngayTaoCu);
                                hoaDon.setNgayTao(ngayTaoCu);
                                BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienTong1);
                                hoaDon.setThanhTien(thanhTienBigDecemal);
                                hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoaiCu + ", Địa chỉ giao hàng: " + diaChiCu + "," + thanhPhoCu + "," + quocGiaCu);
                                hoaDon.setMess(messCu);

                                //Lấy số lượng hiện tại trừ đi

                                List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
                                model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);

                                //Tìm hóa đơn để tính số lượng
                                for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {

                                    //Số lượng mua của khách hàng
                                    String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
                                    int soLuongMua = Integer.parseInt(soLuongMuaToString);

                                    //Số lượng có trong kho
                                    GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
                                    String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
                                    int soLuongCo = Integer.parseInt(soLuongCoToString);

                                    giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
                                    giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);

                                }

                                hoaDonRepository.save(hoaDon);

                                String[] idGiayTheTheThaoChiTietArrayCashCu = request.getParameterValues("idGiayTheTheThaoChiTiet");

                                if (idGiayTheTheThaoChiTietArrayCashCu != null) {
                                    for (String idGiayTheTheThaoChiTietCashCu : idGiayTheTheThaoChiTietArrayCashCu) {
                                        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTietCashCu);

                                        // Tìm giỏ hàng chi tiết để xóa
                                        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);

                                        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
                                        if (gioHangChiTiet != null) {
                                            // Xóa giỏ hàng chi tiết
                                            gioHangChiTietRepository.delete(gioHangChiTiet);

                                        }
                                    }
                                }

                                model.addAttribute("idKH", hoaDon.getKhachHang().getId());
                                return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";

                            }

                        }

                    }

                    //Chọn địa chỉ mới để thanh toán
                    if ("diaChiMoi".equals(diaChiChon)) {

                        System.out.println("Bạn chọn địa chỉ cũ để thanh toán ");

                        if ("momo".equals(hinhThucThanhToan)) {

                            System.out.println("Bạn chọn hình thức thanh toán bằng momo cho địa chỉ mới");

                            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
//
                            if (hoaDon != null) {

                                String tenKhachHangMoiMomo = request.getParameter("ten1");
                                String emailMoiMomo = request.getParameter("email1");
                                String soDienThoaiMoiMomo = request.getParameter("sdt1");
                                String quocGiaMoiMomo = request.getParameter("quocGia1");
                                String thanhPhoMoiMomo = request.getParameter("thanhPho1");
                                String diaChiMoiMomo = request.getParameter("diaChi1");
                                String messMoiMomo = request.getParameter("messMoi");
                                String thanhTienMomo = request.getParameter("tongTien");


                                System.out.println("Tổng tiền : " + thanhTienMomo);

                                ObjectMapper mapper = new ObjectMapper();

                                int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
                                String orderId = Integer.toString(code);
                                MomoModel jsonRequest = new MomoModel();
                                jsonRequest.setPartnerCode(Constant.IDMOMO);
                                jsonRequest.setOrderId(orderId);
                                jsonRequest.setStoreId(orderId);
                                jsonRequest.setRedirectUrl(Constant.redirectUrl);
                                jsonRequest.setIpnUrl(Constant.ipnUrl);
                                jsonRequest.setAmount(thanhTienMomo);
                                jsonRequest.setOrderInfo("Thanh toán sản phẩm của hàng Bess Shoes.");
                                jsonRequest.setRequestId(orderId);
                                jsonRequest.setOrderType(Constant.orderType);
                                jsonRequest.setRequestType(Constant.requestType);
                                jsonRequest.setTransId("1");
                                jsonRequest.setResultCode("200");
                                jsonRequest.setMessage("");
                                jsonRequest.setPayType(Constant.payType);
                                jsonRequest.setResponseTime("300000");
                                jsonRequest.setExtraData("");

                                String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
                                        + jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
                                        + jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
                                        + Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
                                        + Constant.requestType;


                                String signature = Decode.encode(Constant.serectkey, decode);
                                jsonRequest.setSignature(signature);
                                String json = mapper.writeValueAsString(jsonRequest);
                                HttpClient client = HttpClient.newHttpClient();
                                ResultMoMo res = new ResultMoMo();

                                try {
                                    HttpRequest requestMomo = HttpRequest.newBuilder().uri(new URI(Constant.Url))
                                            .POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
                                            .build();
                                    HttpResponse<String> response = client.send(requestMomo, HttpResponse.BodyHandlers.ofString());
                                    res = mapper.readValue(response.body(), ResultMoMo.class);

                                } catch (InterruptedException | URISyntaxException | IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                if (res == null) {

                                    session.setAttribute("error_momo", "Thanh toán thất bại");
                                    System.out.println("Thanh toán thất bại");
                                    return "redirect:/TrangChu/listGiayTheThao";


                                } else {


                                    //Hiện ra trang để quét mã QR
                                    System.out.println("Lưu lại thông tin hóa đơn khi thanh toán bằng momo");
                                    BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTienMomo);

                                    hoaDon.setTrangThai(1);
                                    hoaDon.setThanhTien(thanhTienBigDecemal);
                                    hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoaiMoiMomo + ", Địa chỉ giao hàng: " + diaChiMoiMomo + "," + thanhPhoMoiMomo + "," + quocGiaMoiMomo);
                                    hoaDon.setMess(messMoiMomo);

                                    List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
                                    model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);

                                    //Tìm hóa đơn để tính số lượng
                                    for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {

                                        //Số lượng mua của khách hàng
                                        String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
                                        int soLuongMua = Integer.parseInt(soLuongMuaToString);

                                        //Số lượng có trong kho
                                        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
                                        String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
                                        int soLuongCo = Integer.parseInt(soLuongCoToString);

                                        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
                                        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);

                                    }

                                    hoaDonRepository.save(hoaDon);

                                    //Xóa giỏ hàng chi tiết
                                    String[] idGiayTheTheThaoChiTietArrayMomoMoi = request.getParameterValues("idGiayTheTheThaoChiTiet");

                                    if (idGiayTheTheThaoChiTietArrayMomoMoi != null) {
                                        for (String idGiayTheTheThaoChiTiet : idGiayTheTheThaoChiTietArrayMomoMoi) {
                                            UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTiet);

                                            // Tìm giỏ hàng chi tiết để xóa
                                            GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);

                                            // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
                                            if (gioHangChiTiet != null) {
                                                // Xóa giỏ hàng chi tiết
                                                gioHangChiTietRepository.delete(gioHangChiTiet);

                                            }
                                        }
                                    }
                                    return "redirect:" + res.payUrl;

                                }

                            }

                        }

                        //Bạn chọn thanh toán khi nhận hàng ở địa chỉ mới
                        if ("cash".equals(hinhThucThanhToan)) {

                            HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                            if (hoaDon != null) {

                                String tenKhachHangMoi = request.getParameter("ten1");
                                String emailMoi = request.getParameter("email1");
                                String soDienThoaiMoi = request.getParameter("sdt1");
                                String quocGiaMoi = request.getParameter("quocGia1");
                                String thanhPhoMoi = request.getParameter("thanhPho1");
                                String diaChiMoi = request.getParameter("diaChi1");
                                String messMoi = request.getParameter("messMoi");
                                String thanhTien = request.getParameter("tongTien");

                                //Check trống các trường

                                //Check trống tên khách hàng
                                if (tenKhachHangMoi == null
                                        || tenKhachHangMoi.trim().length() == 0
                                        || tenKhachHangMoi.isEmpty()) {

                                    attributes.addFlashAttribute("tenKhachHangMoiNull", "Xin lỗi tên khách hàng không được để trống");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                }

                                //Check trống email
                                if (emailMoi.trim().length() == 0
                                        || emailMoi == null
                                        || emailMoi.isEmpty()) {

                                    attributes.addFlashAttribute("emailMoiNull", "Xin lỗi tên email không được để trống");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                }

                                //Check trống số điện thoại

                                if (soDienThoaiMoi.trim().length() == 0
                                        || soDienThoaiMoi.isEmpty()
                                        || soDienThoaiMoi == null) {

                                    attributes.addFlashAttribute("soDienThoaiMoiNull", "Xin lỗi không được để trống số điện thoại !");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                }

                                //check số điện thoại nhập là chữ
                                try {

                                    int soDienThoaiFomat = Integer.parseInt(soDienThoaiMoi);

                                    if (soDienThoaiFomat < 0) {

                                        attributes.addFlashAttribute("erLogSoDienThoai0", "Số điện thoại không được nhỏ hơn 0");
                                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                    }


                                } catch (NumberFormatException e) {

                                    e.printStackTrace();
                                    attributes.addFlashAttribute("erLogSoDienThoaiChonChu", "Số điện thoại không được là chữ !");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                }

                                // Validate số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số
                                String phoneNumberRegex = "^0\\d{9}$";

                                if (!soDienThoaiMoi.matches(phoneNumberRegex)) {

                                    attributes.addFlashAttribute("erLogSoDienThoaiNumber", "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số.");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                }

                                if (quocGiaMoi == null
                                        || quocGiaMoi.trim().length() == 0
                                        || quocGiaMoi.isEmpty()) {

                                    attributes.addFlashAttribute("quocGiaMoiNull", "Không được để trống tỉnh !");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                }

                                if (thanhPhoMoi == null
                                        || thanhPhoMoi.trim().length() == 0
                                        || thanhPhoMoi.isEmpty()) {

                                    attributes.addFlashAttribute("thanhPhoMoiNull", "Không được để trống huyện !");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                                }

                                if (diaChiMoi == null
                                        || diaChiMoi.isEmpty()
                                        || diaChiMoi.trim().length() == 0) {

                                    attributes.addFlashAttribute("diaChiMoiNull", "Không được để trống xã !");
                                    return "redirect:/nguoiDung/HoaDon/" + hoaDonId;


                                }
                                //Lưu lại đơn hàng
                                LocalDate ngayThanhToanMoi = LocalDate.now();
                                String ngayThanhToanToString = ngayThanhToanMoi.toString();

                                hoaDon.setNgayThanhToan(ngayThanhToanToString);
                                hoaDon.setNgayTao(ngayThanhToanToString);
                                hoaDon.setTrangThai(1);
                                hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoaiMoi + ", Địa chỉ giao hàng: " + diaChiMoi + "," + thanhPhoMoi + "," + quocGiaMoi);
                                hoaDon.setMess(messMoi);
                                BigDecimal thanhTienBigDecemal = new BigDecimal(thanhTien);
                                hoaDon.setThanhTien(thanhTienBigDecemal);
                                //Lấy số lượng hiện tại trừ đi

                                List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
                                model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);

                                //Tìm hóa đơn để tính số lượng
                                for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets) {

                                    //Số lượng mua của khách hàng
                                    String soLuongMuaToString = hoaDonChiTietList.getSoLuong();
                                    int soLuongMua = Integer.parseInt(soLuongMuaToString);

                                    //Số lượng có trong kho
                                    GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
                                    String soLuongCoToString = giayTheThaoChiTiet.getSoLuong();
                                    int soLuongCo = Integer.parseInt(soLuongCoToString);

                                    giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
                                    giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);

                                }

                                hoaDonRepository.save(hoaDon);
                                String[] idGiayTheTheThaoChiTietArrayCashMoi = request.getParameterValues("idGiayTheTheThaoChiTiet");

                                if (idGiayTheTheThaoChiTietArrayCashMoi != null) {
                                    for (String idGiayTheTheThaoChiTiet : idGiayTheTheThaoChiTietArrayCashMoi) {
                                        UUID giayTheThaoChiTietId = UUID.fromString(idGiayTheTheThaoChiTiet);

                                        // Tìm giỏ hàng chi tiết để xóa
                                        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(giayTheThaoChiTietId);

                                        // Kiểm tra xem giỏ hàng chi tiết có tồn tại hay không
                                        if (gioHangChiTiet != null) {
                                            // Xóa giỏ hàng chi tiết
                                            gioHangChiTietRepository.delete(gioHangChiTiet);

                                        }
                                    }
                                }
                                model.addAttribute("idKH", hoaDon.getKhachHang().getId());
                                return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";

                            }

                        }

                    }

                }

                return "redirect:/TrangChu/listGiayTheThao";

            }

        }else{

            System.out.println("Khách hàng chưa đăng nhập tài khoản");
            return "redirect:/TrangChu/listGiayTheThao";

        }

    }

    //Todo code xóa giỏ hàng chi tiết khi thanh toán
    private void xoaGioHangChiTiet(UUID idGiayTheThaoChiTiet) {
        try {
            hoaDonChiTietRepository.deleteById(idGiayTheThaoChiTiet);
            System.out.println("Xóa giỏ hàng chi tiết thành công");
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa giỏ hàng chi tiết: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Todo code thanh toán bằng momo
    @GetMapping("paywithmomo")
    public String PayWithMomoGet(@ModelAttribute("message") String message,
                                 Model model,
                                 HttpSession session) {

        if (!message.equals("Successful.")) {

            session.setAttribute("error_momo", "Thanh toán không thành công!");
            return "redirect:/TrangChu/listGiayTheThao";

        } else {

            System.out.println("Thanh toán momo thành công");
            return "redirect:/TrangChu/listGiayTheThao";

        }
    }



    //Todo code log swel thông báo cho thanh toán thành công cho đơn hàng
    @GetMapping("nguoiDung/hoaDon/thanhToan/ThanhCong")
    public String showViewThanhToanThanhCong(Model model){

        System.out.println("Hiện thông báo trước khi chuyển hướng");

        return "/templates/Users/Layouts/Log/XuatHoaDonLog";

    }

    //Todo code trạng thái đơn hàng cho cả Admin và khách hàng

    //Bên phía khách hàng

    //Todo code view tổng quan trong thái
    @GetMapping("/KhachHang/thongTinHoaDonAll/*")
    public String viewThongTin(HttpServletRequest request, Model model) {
        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/thongTinHoaDonAll/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());

        } catch (Exception e) {

            model.addAttribute("maKH", "2");

        }

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/viewHoaDonTrangThaiAll";

    }

    //Todo code trạng thái đơn hàng cho hóa đơn
//////////////////////////////////////////////////////////////////////////////
    //Bên khách hàng

    //Todo code chờ thanh toán bên phía khách hàng(Tức là khách hàng nhấn vào thanh toán xong lại không thanh toán nữa)

    @GetMapping("/KhachHang/HoaDon/ChoThanhToan/*")
    public ModelAndView hoaDonChoThanhToan(
            @RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/ChoThanhToan/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("maKH","2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),0,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/choThanhToanBenKhachHang");
        mav.addObject("page",page);

        return  mav;

    }


   //Todo code chờ xác nhận bên phía khác hàng

    @GetMapping("/KhachHang/HoaDon/ChoXacNhan/*")
    public ModelAndView hoaDonChoXacNhan(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request, Model model){

        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/HoaDon/ChoXacNhan/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());

        }catch (Exception e){
            model.addAttribute("maKH", "2");
        }
        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),1,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/choXacNhanBenKhachHang");

        mav.addObject("page", page);
        return mav;

    }

    //Todo code khách hàng chờ đóng gói sản phẩm là không được hủy nữa
    @GetMapping("/KhachHang/HoaDon/ChoDongGoi/*")
    public ModelAndView choDongGoiPhiaKhachHang(

            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/ChoDongGoi/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("maKH","2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),2,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/choDongGoiBenKhachHang");
        mav.addObject("page",page);
        return mav;


    }

    //Todo code đang giao hàng bên phía khách hàng
    @GetMapping("/KhachHang/HoaDon/DangGiaoHang/*")
    public ModelAndView dangGiaoHangBenPhiaKhachHang(
            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model
    ){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/DangGiaoHang/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("maKH","2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),3,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/dangGiaoHangBenPhiaKhachHang");
        mav.addObject("page",page);
        return mav;

    }

    //Todo code giao hàng thành công
    @PostMapping("/KhachHang/HoaDon/XacNhanGiaoHangThanhCong")
    public String xacNhanGiaoHangThanhCong(
            Model model,
            HttpServletRequest request
    ){

        String thanhCong  = request.getParameter("thanhCong");
        String idKH = request.getParameter("idKH");

        HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(thanhCong));

        HoaDon hd = new HoaDon();

        hd.setMaHoaDon(hoaDon.getMaHoaDon());
        hd.setThanhTien(hoaDon.getThanhTien());
        hd.setKhachHang(hoaDon.getKhachHang());
        hd.setNgayTao(hoaDon.getNgayTao());
        hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(4);

        hoaDonServiceImpl.update(hoaDon.getId(),hd);

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/giaoHangThanhCongBenPhiaKhachHang";

    }

    //Todo code giao hàng thành công
    @GetMapping("/KhachHang/HoaDon/GiaoHangThanhCong/*")
    public ModelAndView giaoHangThanhCong(
            Model model,
            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request

    ){

        String url = request.getRequestURI();
        String [] parts = url.split("/KhachHang/HoaDon/GiaoHangThanhCong/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH",khachHang.getMaKhachHang());

        }catch (Exception e) {

            model.addAttribute("maKH", "2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),4,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/giaoHangThanhCongBenPhiaKhachHang");

        mav.addObject("page", page);
        return mav;



    }


    //Todo code hủy đơn hàng bên phía khách hàng

    @GetMapping("/KhachHang/HoaDon/HuyDonHang/*")
    public ModelAndView hoaDonHuy(@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                  HttpServletRequest request,
                                  Model model){

        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/HoaDon/HuyDonHang/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());

        }catch (Exception e){

            model.addAttribute("maKH", "2");

        }

        KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),5,pageNo,5);
        ModelAndView mav = new ModelAndView("/templates/Users/Layouts/TrangThaiDonHang/KhachHang/hoaDonHuyBenKhachHang");

        mav.addObject("page", page);
        return mav;
    }

    @PostMapping("/KhachHang/HoaDon/HuyDonHang")
    public String huyDonHang(HttpServletRequest request){

        String huyDonHang = request.getParameter("huyDonHang");
        String idKH = request.getParameter("idKH");

        HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huyDonHang));
        //Tạo mới hóa đơn để lưu
        HoaDon hd = new HoaDon();
        LocalDate huyDon = LocalDate.now();
        String huyDonHang4 = huyDon.toString();

        hd.setMaHoaDon(hoaDon.getMaHoaDon());
        hd.setThanhTien(hoaDon.getThanhTien());
        hd.setKhachHang(hoaDon.getKhachHang());
        hd.setNgayThanhToan(huyDonHang4);

        /*
            0: là chưa thanh toán xong
            1: là chờ xác nhận
            2: là đang giao
            3: là giao hàng thành công
            4: là hủy đơn hàng

            0: là chưa thanh toán xong
            1: là chờ xác nhận
            2: là đang đóng gói hàng
            3: là đang giao
            4: là giao hàng thành công
            5: là hủy đơn hàng

         */
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(5);

        hoaDonServiceImpl.update(hoaDon.getId(),hd);

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/hoaDonHuyBenKhachHang";

    }


//////////////////////////////////////////////////////////////////////////


    //Bên phía Admin

    //Todo code xác nhân đơn hàng bên phía admin

    //Todo code All xác nhận bên phía admin
    @GetMapping("/Admin/xacNhanDonHangKhachHangAll")
    public String showViewXacNhanDonHangAllKhachHang(Model model){


        model.addAttribute("listHoaDon",hoaDonRepository.findAll());

        return "/templates/Admin/TrangThaiDonHang/viewHoaDonTrangThaiAll";


    }

    //Todo code xác nhân đơn hàng bên phía admin

    @GetMapping("/Admin/xacNhanDonHangKhachHang")
    public ModelAndView adminXacNhanDonHangChoKhachHang(
            Model model,
            @RequestParam(value = "pageNo",required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,1);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/choXacNhan");
        mav.addObject("page",page);

        return mav;

    }

    //Todo code xác nhân bên đơn bên admin
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHang")
    public String showHoaDonXacNhanBenKhachHang(HttpServletRequest request,
                                                HttpSession session){



            if (session.getAttribute("userLog") != null) {

                User user = (User) session.getAttribute("userLog");

                String huy = request.getParameter("huy");

                HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huy));
                HoaDon hd = new HoaDon();

                hd.setUser(user);
                hd.setMaHoaDon(hoaDon.getMaHoaDon());
                hd.setThanhTien(hoaDon.getThanhTien());
                hd.setNgayTao(hoaDon.getNgayTao());
                hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
                hd.setGhiChu(hoaDon.getGhiChu());
                //Xác nhận đơn hàng sang đang giao hàng
                hd.setTrangThai(2);
                hoaDonServiceImpl.update(hoaDon.getId(), hd);

            }

        return "redirect:/Admin/logXacNhanDonHang";

    }

    //Todo code xác nhận giao hàng thành công bên phía admin
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonDangDongGoi")
    public ModelAndView showFormHoaDonXacNhanGiaoHangThanhCong(

            @RequestParam(value = "pageNo",required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model
    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,2);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/dangDongGoi");
        mav.addObject("page", page);
        return mav;

    }



    //Todo code xác nhận giao hàng thành công bên phía Admin
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHangDangGiao")
    public String adminDangGiaoHang(

            HttpServletRequest request,
            HttpSession session
    ){

        if (session.getAttribute("userLog") != null) {

            User user = (User) session.getAttribute("userLog");

            String thanhCong = request.getParameter("thanhCong");

            HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(thanhCong));
            HoaDon hd = new HoaDon();

            System.out.println("Đơn hàng chuyển sang trạng thái giao hàng");
            hd.setUser(user);
            hd.setMaHoaDon(hoaDon.getMaHoaDon());
            hd.setThanhTien(hoaDon.getThanhTien());
            hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
            hd.setNgayTao(hoaDon.getNgayTao());
            hd.setKhachHang(hoaDon.getKhachHang());
            hd.setGhiChu(hoaDon.getGhiChu());
            hd.setTrangThai(3);

            hoaDonServiceImpl.update(hoaDon.getId(), hd);
        }

          return "redirect:/Admin/dongGoiThanhCong";

    }

    //Todo code đang giao hàng
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonKhachHangDangGiao")
    public ModelAndView showDangGiaoHang(

            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,3);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/dangGiaoHang");
        mav.addObject("page", page);
        return mav;

    }

    //Todo code hoàn thành giao hàng
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh")
    public ModelAndView showGiaoHangHoanThanh(

            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,4);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/hoanThanh");
        mav.addObject("page", page);
        return mav;

    }

    //Todo code giao hàng thanh công admin xác nhận giao hàng thành công
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHangThanhCong")
    public String adminXacNhanGiaoHangThanhCong(

            HttpServletRequest request,
            HttpSession session
    ){

        if (session.getAttribute("userLog") != null) {

            User user = (User) session.getAttribute("userLog");


            String thanhCong = request.getParameter("thanhCong");

            HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(thanhCong));
            HoaDon hd = new HoaDon();

            System.out.println("Đơn hàng chuyển sang trạng thái giao hàng");
            hd.setUser(user);
            hd.setMaHoaDon(hoaDon.getMaHoaDon());
            hd.setThanhTien(hoaDon.getThanhTien());
            hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
            hd.setNgayTao(hoaDon.getNgayTao());
            hd.setKhachHang(hoaDon.getKhachHang());
            hd.setGhiChu(hoaDon.getGhiChu());
            hd.setTrangThai(4);

            hoaDonServiceImpl.update(hoaDon.getId(), hd);

        }

//        return "redirect:/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh";
        return "redirect:/Admin/giaoHangThanhCong";

    }


    //Todo code đơn hàng bị hủy
    @GetMapping("/Admin/HoaDon/DonHangBiHuy")
    public ModelAndView donHangBiHuy(

            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,5);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/donHangBiHuy");
        mav.addObject("page", page);
        return mav;

    }


    @GetMapping("/XoaDonHangBiHuyThanhCong")
    public String xoaDonHangBiHuyThanhCong(){

        return "/templates/Users/Layouts/Log/xoaDonHangBiHuyThanhCong";

    }
    //Todo code xóa đơn hàng bị hủy

    @PostMapping("/XoaHoaDon")
    public String xoaHoaDon(@RequestParam("hoaDonId") UUID hoaDonId) {

        hoaDonService.deleteHoaDon(hoaDonId);

        return "redirect:/XoaDonHangBiHuyThanhCong";

    }

    //Todo code xác nhận hủy đơn hàng bên admin
    @PostMapping("/Admin/HoaDon/HuyDonHangCuaKhachHang")
    public String adminXacNhanHuyDonHangCuaKhachHang(

            HttpServletRequest request,
            HttpSession session
    ){

        if (session.getAttribute("userLog") != null) {

            User user = (User) session.getAttribute("userLog");

            String huyDonHang = request.getParameter("huyDonHang");

            HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huyDonHang));
            HoaDon hd = new HoaDon();

            System.out.println("Đơn hàng bị hủy vì có một số lý do");
            hd.setUser(user);
            hd.setMaHoaDon(hoaDon.getMaHoaDon());
            hd.setThanhTien(hoaDon.getThanhTien());
            hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
            hd.setNgayTao(hoaDon.getNgayTao());
            hd.setKhachHang(hoaDon.getKhachHang());
            hd.setGhiChu(hoaDon.getGhiChu());
            hd.setTrangThai(5);

            String email = request.getParameter("emailView");
            String maHoaDon = request.getParameter("maHoaDonView");
            String tenKhachHang = request.getParameter("khachHangView");
            String ngayThanhToan = request.getParameter("ngayThanhToanView");
            String thanhTien = request.getParameter("tongTienView");
            String thongTinNhanHang = request.getParameter("thongTienNhanHangView");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);

            message.setSubject("Cửa hàng bán giầy thể thao bóng đá BeeShoes xin thông báo");
            message.setText(
                            "Mã hóa đơn : " + maHoaDon +
                            "\n" +
                            "Ngày thanh toán :" + ngayThanhToan +
                            "\n" +
                            "Thành tiền : " + thanhTien + "VNĐ" +
                            "\n" +
                            "Thông tin địa chỉ nhận hàng :" +thongTinNhanHang +
                            "\n" +
                            "\n" +
                            "Xin chào khách hàng : "+ tenKhachHang + "\n" +
                            ".Có địa chỉ email là: "+ email +"\n"+
                            "Cảm ơn bạn đã quan tâm đến sản phẩm của shop, nhưng shop rất lấy làm tiếc vì sự chuẩn bị không chu đáo này xin quý khách thông cảm!" +
                            "Do số sản phẩm trong kho đã hết shop thông báo để quý khách biết và đơn hàng này shop xin hủy đơn!" +
                            "\n"+
                            "\n"+
                            "Cảm ơn quý khách hàng đã quan tâm!" + "\n" +
                            "Xin cảm ơn,và hân hạnh phục phục lần sau!"

            );


            mailSender.send(message);

            hoaDonServiceImpl.update(hoaDon.getId(), hd);

        }

        return "redirect:/Admin/HuyDonHangCuaKhachHangLog";

    }


    //Todo code log bên phía admin

    //Admin
    //Todo code log xác nhận đơn hàng thành công sang bên phía đang đóng gói
    @GetMapping("/Admin/logXacNhanDonHang")
    public String showLogXacNhanDonHangAlert(){

        return "/templates/Users/Layouts/Log/logXacNhanDonHangThanhCong";

    }

    //Todo code log đóng gói sang đang giao
    @GetMapping("/Admin/dongGoiThanhCong")
    public String showLogDongGoiThanhCongAlert(){

        return "/templates/Users/Layouts/Log/logDongGoiDonHangThanhCong";

    }

    //Todo code từ đang giao hàng sang nhận hàng thành công!
    @GetMapping("/Admin/giaoHangThanhCong")
    public String showLogGiaoHangThanhCongAlert(){

        return "/templates/Users/Layouts/Log/logGiaoHangThanhCong";

    }

    @GetMapping("/Admin/HuyDonHangCuaKhachHangLog")
    public String showHuyDonHangCuaKhachHangAlert(){

        return "/templates/Users/Layouts/Log/logDonHangKhachHangBiHuy";

    }
    //Khách hàng

    //Todo code log hủy đơn hàng bên khách hàng
    @GetMapping("/KhachHang/huyDonHang")
    public String showLogHuyDonHangAlert(){

        return "/templates/Users/Layouts/Log/logHuyDonHangThanhCongBenKhachHang";

    }

    @PostMapping("/HoaDon/ThongTinChiTietHoaDon")
    public String showThongTinHoaDonChiTiet(

            Model model,
            HttpServletRequest request

    ){

        String idHoaDon = request.getParameter("idHoaDon");
        UUID idHoaDonConvert = UUID.fromString(idHoaDon);

        System.out.println("ID hóa đơn "+ idHoaDon);
        System.out.println("Id hóa đơn sau khi convert: "+idHoaDonConvert);

        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(idHoaDonConvert);
//        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_IdAndTrangThai(idHoaDonConvert,1);

        if (!hoaDonChiTiets.isEmpty()) {
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {

                //Thông tin cần in ra
                System.out.println();
                model.addAttribute("idHoaDonConvert",idHoaDonConvert);
                model.addAttribute("maHoaDonView",hoaDonChiTiet.getHoaDon().getMaHoaDon());
                model.addAttribute("emailView",hoaDonChiTiet.getHoaDon().getKhachHang().getEmail());
                model.addAttribute("khachHangView",hoaDonChiTiet.getHoaDon().getKhachHang().getTenKhachHang());
                model.addAttribute("ngayThanhToanView",hoaDonChiTiet.getHoaDon().getNgayThanhToan());
                model.addAttribute("tongTienView",hoaDonChiTiet.getHoaDon().getThanhTien());
                model.addAttribute("thongTienNhanHangView",hoaDonChiTiet.getHoaDon().getGhiChu());
                model.addAttribute("messView",hoaDonChiTiet.getHoaDon().getMess());
                model.addAttribute("hinhThucView",hoaDonChiTiet.getHoaDon().getHinhThuc());

                model.addAttribute("tenGiayTheThaoView",hoaDonChiTiet.getGiayTheThaoChiTiet().getGiayTheThao().getTenGiayTheThao());


            }
        }

        model.addAttribute("hoaDonChiTiets",hoaDonChiTiets);

        return "/templates/Users/Layouts/ChiTiet/ChiTietHoaDon";

    }

    @GetMapping("/Admin/ThemSanPhamKhachHangThanhCong")
    public String saveThemSanPhamChoKhachHangThanhCong(){

        return "/templates/Users/Layouts/Log/themSanPhamChoKhachHangThanhCong";

    }


    @PostMapping("/Admin/HoaDon/ThemThongTinSanPham")
    public String saveThemThongTinSanPham(
            HttpServletRequest request,
            Model model
    ) {

        String idHoaDonConvert = request.getParameter("idHoaDonConvert");
        System.out.println("ID của hóa đơn là : " + idHoaDonConvert);
        model.addAttribute("idHoaDonConvert", idHoaDonConvert);

        //Id
        if(idHoaDonConvert != null){

            String giayTheThaoChiTietId = request.getParameter("giayTheThaoChiTietId");
            System.out.println("Id của giầy thể thao là: " + giayTheThaoChiTietId);

            String sizeId = request.getParameter("sizeId");
            System.out.println("Id của size là: "+ sizeId);

            String mauSacId = request.getParameter("mauSacId");
            System.out.println("Id của màu sắc là: "+ mauSacId);

            String soLuong = request.getParameter("soLuong");
            System.out.println("Số lượng mua: "+soLuong);

            if(giayTheThaoChiTietId != null && !giayTheThaoChiTietId.isEmpty()
                && sizeId != null && !sizeId.isEmpty()
                && mauSacId != null && !mauSacId.isEmpty()) {

                UUID idGiayTheThaoConvert = UUID.fromString(giayTheThaoChiTietId);
                UUID idSizeConvert = UUID.fromString(sizeId);
                UUID idMauSacConvert = UUID.fromString(mauSacId);

                UUID idGiayTheThaoChiTiet = giayTheThaoChiTietRepository.findIdByGiayTheThaoAndSizeAndMauSac(idGiayTheThaoConvert, idSizeConvert, idMauSacConvert);

                System.out.println("Id giầy thể thao chi tiết : "+ idGiayTheThaoConvert);
                System.out.println("Id của size: "+ idSizeConvert);
                System.out.println("Id của màu săc: "+idMauSacConvert);

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

                HoaDon hoaDon = hoaDonRepository.findById(UUID.fromString(idHoaDonConvert)).orElse(null);
                GiayTheThaoChiTiet giayTheThaoChiTiet= giayTheThaoChiTietRepository.findById(idGiayTheThaoChiTiet).orElse(null);

                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTiet);
                hoaDonChiTiet.setGhiChu("Khách hàng yêu cầu thêm sản phẩm");
                hoaDonChiTiet.setSoLuong(soLuong);

                String giaBanString = giayTheThaoChiTiet.getGiayTheThao().getGiaBan();

                BigDecimal giaBan = new BigDecimal(giaBanString);

                int soLuongInt = Integer.parseInt(soLuong);
                // Tính toán đơn giá
                BigDecimal donGia = giaBan.multiply(BigDecimal.valueOf(soLuongInt));
                hoaDonChiTiet.setDonGia(donGia);
                hoaDonChiTiet.setTrangThai(1);

                hoaDonChiTietRepository.save(hoaDonChiTiet);

                BigDecimal totalDonGia = hoaDonRepository.calculateTotalDonGiaByHoaDonId(UUID.fromString(idHoaDonConvert));
                System.out.println("Tổng đơn giá của hóa đơn có ID " + idHoaDonConvert + " là: " + totalDonGia);

                HoaDon hoaDonToUpdate = hoaDonRepository.findById(UUID.fromString(idHoaDonConvert)).orElse(null);
                hoaDonToUpdate.setThanhTien(totalDonGia);
                hoaDonRepository.save(hoaDonToUpdate);

                return "redirect:/Admin/ThemSanPhamKhachHangThanhCong";

            }

        }else{

            System.out.println("Mã hóa đơn không được là null");

        }

        return "/templates/Users/Layouts/ChiTiet/ThemThongTinSanPham";

    }









    //Todo code list giầy thể thao chi tiết
    @ModelAttribute("giayTheThaoChiTiet")
    public List<GiayTheThaoChiTiet> getListGiayTheThaoChiTiet(){

        return giayTheThaoChiTietRepository.findAll();

    }

    @ModelAttribute("size")
    public List<Size> getListSize(){

        return sizeRepository.findAll();
    }

    @ModelAttribute("mauSac")
    public List<MauSac> getListMauSac(){

        return mauSacRepository.findAll();

    }

}
