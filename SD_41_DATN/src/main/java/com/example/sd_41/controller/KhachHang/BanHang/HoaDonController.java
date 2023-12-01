package com.example.sd_41.controller.KhachHang.BanHang;

import com.example.sd_41.controller.Momo.MomoModel;
import com.example.sd_41.controller.Momo.ResultMoMo;
import com.example.sd_41.controller.Utils.Constant;
import com.example.sd_41.controller.Utils.Decode;
import com.example.sd_41.model.GiayTheThaoChiTiet;
import com.example.sd_41.model.HoaDon;
import com.example.sd_41.model.HoaDonChiTiet;
import com.example.sd_41.model.KhachHang;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.service.HoaDon.HoaDonChiTietServie;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.HoaDon.HoaDonServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.time.temporal.ChronoUnit;
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
    private GiayTheThaoChiTietRepository giayTheThaoChiTietRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;


    //Todo code view hóa đơn
    //hiện thông tin cho view hóa đơn

    @GetMapping("nguoiDung/HoaDon/{id}")
    public String showViewHoaDon(
            @PathVariable String id,
            Model model,
            HttpSession session) {

        UUID hoaDonId = UUID.fromString(id);
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
        model.addAttribute("hoaDonId", hoaDonId);
        if (hoaDon != null) {

            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);

            //Hiện view thông tin của hóa đơn
            model.addAttribute("hoaDonChiTietList", hoaDonChiTietList);
            model.addAttribute("hoaDon1", hoaDonId);
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("maHoaDon",hoaDon.getMaHoaDon());
            model.addAttribute("idKhacHang", hoaDon.getKhachHang().getId());
            model.addAttribute("tenKhachHang", hoaDon.getKhachHang().getTenKhachHang());
            model.addAttribute("diaChi", hoaDon.getKhachHang().getDiaChi());
            model.addAttribute("soDienThoai", hoaDon.getKhachHang().getSoDienThoai());
            model.addAttribute("email", hoaDon.getKhachHang().getEmail());
            model.addAttribute("thanhPho",hoaDon.getKhachHang().getThanhPho());
            model.addAttribute("huyen",hoaDon.getKhachHang().getHuyen());
            model.addAttribute("xa",hoaDon.getKhachHang().getXa());


        } else {

            System.out.println("Xin lỗi không tìm thấy hóa đơn phù hợp cho khách hàng này");

        }

        return "/templates/Users/Layouts/Shop/viewHoaDon";

    }

    //Todo code thanh toán thành công chờ xác nhận đơn hàng
    @PostMapping("nguoiDung/hoaDon/ThanhToan/{id}")
    public String showEditViewThanhToanHoaDon(Model model,
                                              @PathVariable String id,
                                              HttpSession session,
                                              HttpServletRequest request,
                                              RedirectAttributes attributes) throws JsonProcessingException {
        UUID hoaDonId = UUID.fromString(id);

        //Todo code mã code mới bên controller

        String diaChiChon = request.getParameter("diaChiChon");

        //Địa chỉ chon là null
        if(diaChiChon == null || diaChiChon.isEmpty()){

            attributes.addFlashAttribute("diaChiChonNull","Xin lỗi vui lòng chọn địa chỉ để có thể thanh toán!");
            System.out.println("Vui lòng chọn một địa chỉ để có thể thanh toán");
            return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

        }else{

            //Địa chỉ chọn không null

            String hinhThucThanhToan = request.getParameter("payment");

            //Hình thức thanh toán
            if(hinhThucThanhToan == null || hinhThucThanhToan.isEmpty()) {

                attributes.addFlashAttribute("hinhThucThanhToanNull", "Vui lòng chọn hình thức thanh toán!");
                System.out.println("Vui lòng chọn hình thức thanh toán");
                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

            }else{

                //Chọn địa chỉ mới để thanh toán
                if("diaChiCu".equals(diaChiChon)){

                    System.out.println("Bạn chọn địa chỉ cũ để thanh toán");

                    //Thanh toán bằng địa chỉ cũ qua thanh toán momo
                    if ("momo".equals(hinhThucThanhToan)) {
                        System.out.println("Bạn chọn thanh toán bằng hình thức momo");

                        String thanhTien    = request.getParameter("thanhTien");
                        String phiShip      = request.getParameter("ship");
                        String thanhTienTongPrice = request.getParameter("thanhTienTongPrice");

                        System.out.println("Thành tiền : "+thanhTien);
                        System.out.println("Phí ship : "+phiShip);
                        System.out.println("Tổng tiền : "+thanhTienTongPrice);

                        ObjectMapper mapper = new ObjectMapper();

                        int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
                        String orderId = Integer.toString(code);
                        MomoModel jsonRequest = new MomoModel();
                        jsonRequest.setPartnerCode(Constant.IDMOMO);
                        jsonRequest.setOrderId(orderId);
                        jsonRequest.setStoreId(orderId);
                        jsonRequest.setRedirectUrl(Constant.redirectUrl);
                        jsonRequest.setIpnUrl(Constant.ipnUrl);
                        jsonRequest.setAmount(thanhTienTongPrice);
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

//                            session.setAttribute("error_momo", "Thanh toán thất bại");
//                            return "redirect:/home";
                            System.out.println("Thanh toán thất bại");
                            return "redirect:/TrangChu/listGiayTheThao";


                        } else {
//					return "redirect:/shop";
//				resp.sendRedirect(res.payUrl);
                            System.out.println("Thanh toán thành công");
                            return "redirect:" + res.payUrl;
//                            System.out.println("Thanh toán thất bại");
//                            return "redirect:/TrangChu/listGiayTheThao";

                        }





                    }

                    //Thanh toán bằng địa chỉ cũ qua thanh toán khi nhận hàng
                    if("cash".equals(hinhThucThanhToan)) {

                        System.out.println("Bạn chọn thanh toán bằng hình thức thanh toán khi nhận hàng");
                        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                        if(hoaDon != null){

                            String tenKhachHangCu       = request.getParameter("tenKhachHang");
                            String soDienThoaiCu        = request.getParameter("soDienThoai");
                            String quocGiaCu            = request.getParameter("quocGia");
                            String thanhPhoCu           = request.getParameter("thanhPho");
                            String diaChiCu             = request.getParameter("diaChi");
                            String messCu               = request.getParameter("mess");

                            LocalDate ngayThanhToanCu = LocalDate.now();
                            String ngayTaoCu = ngayThanhToanCu.toString();

                            hoaDon.setTrangThai(1);
                            hoaDon.setNgayThanhToan(ngayTaoCu);
                            hoaDon.setNgayTao(ngayTaoCu);
                            hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoaiCu + ", Địa chỉ giao hàng: " + diaChiCu + "," + thanhPhoCu + "," + quocGiaCu);
                            hoaDon.setMess(messCu);

                            //Lấy số lượng hiện tại trừ đi

                            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
                            model.addAttribute("hoaDonChiTiets",hoaDonChiTiets);

                            //Tìm hóa đơn để tính số lượng
                            for(HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets){

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
                            model.addAttribute("idKH",hoaDon.getKhachHang().getId());
                            return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";

                        }


                    }

                }

                //Chọn địa chỉ mới để thanh toán
                if("diaChiMoi".equals(diaChiChon)){

                    System.out.println("Bạn chọn địa chỉ cũ để thanh toán ");

                    if("momo".equals(hinhThucThanhToan)){

                        System.out.println("Bạn chọn hình thức thanh toán bằng momo");

                    }


                    //Bạn chọn thanh toán khi nhận hàng
                    if("cash".equals(hinhThucThanhToan)){

                        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);

                        if(hoaDon != null){

                            String tenKhachHangMoi      = request.getParameter("ten1");
                            String emailMoi             = request.getParameter("email1");
                            String soDienThoaiMoi       = request.getParameter("sdt1");
                            String quocGiaMoi           = request.getParameter("quocGia1");
                            String thanhPhoMoi          = request.getParameter("thanhPho1");
                            String diaChiMoi            = request.getParameter("diaChi1");
                            String messMoi              = request.getParameter("messMoi");
                            //Check trống các trường

                            //Check trống tên khách hàng
                            if(tenKhachHangMoi == null
                                    || tenKhachHangMoi.trim().length() ==0
                                    || tenKhachHangMoi.isEmpty()){

                                attributes.addFlashAttribute("tenKhachHangMoiNull","Xin lỗi tên khách hàng không được để trống");
                                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                            }

                            //Check trống email
                            if(emailMoi.trim().length() ==0
                                    || emailMoi == null
                                    || emailMoi.isEmpty()){

                                attributes.addFlashAttribute("emailMoiNull","Xin lỗi tên email không được để trống");
                                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                            }

                            //Check trống số điện thoại

                            if(soDienThoaiMoi.trim().length() ==0
                                    || soDienThoaiMoi.isEmpty()
                                    || soDienThoaiMoi == null){

                                attributes.addFlashAttribute("soDienThoaiMoiNull","Xin lỗi không được để trống số điện thoại !");
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

                            if(quocGiaMoi == null
                                    || quocGiaMoi.trim().length() ==0
                                    || quocGiaMoi.isEmpty()){

                                attributes.addFlashAttribute("quocGiaMoiNull", "Không được để trống tỉnh !");
                                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                            }

                            if(thanhPhoMoi  == null
                                    || thanhPhoMoi.trim().length()==0
                                    || thanhPhoMoi.isEmpty()){

                                attributes.addFlashAttribute("thanhPhoMoiNull", "Không được để trống huyện !");
                                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;

                            }

                            if(diaChiMoi == null
                                    || diaChiMoi.isEmpty()
                                    || diaChiMoi.trim().length() == 0){

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
                            //Lấy số lượng hiện tại trừ đi

                            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
                            model.addAttribute("hoaDonChiTiets",hoaDonChiTiets);

                            //Tìm hóa đơn để tính số lượng
                            for(HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiets){

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
                            model.addAttribute("idKH",hoaDon.getKhachHang().getId());
                            return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";

                        }

                    }

                }

            }


        }



//
//        if ("banking".equals(hinhThuc)) {
//
//            //Todo code
//
//            ObjectMapper mapper = new ObjectMapper();
//            int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
//            String orderId = Integer.toString(code);
//            MomoModel jsonRequest = new MomoModel();
//            jsonRequest.setPartnerCode(Constant.IDMOMO);
//            jsonRequest.setOrderId(orderId);
//            jsonRequest.setStoreId(orderId);
//            jsonRequest.setRedirectUrl(Constant.redirectUrl);
//            jsonRequest.setIpnUrl(Constant.ipnUrl);
//            jsonRequest.setAmount(String.valueOf(1000));
//            jsonRequest.setOrderInfo("Thanh toán Male Fashion.");
//            jsonRequest.setRequestId(orderId);
//            jsonRequest.setOrderType(Constant.orderType);
//            jsonRequest.setRequestType(Constant.requestType);
//            jsonRequest.setTransId("1");
//            jsonRequest.setResultCode("200");
//            jsonRequest.setMessage("");
//            jsonRequest.setPayType(Constant.payType);
//            jsonRequest.setResponseTime("300000");
//            jsonRequest.setExtraData("");
//
//            String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
//                    + jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
//                    + jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
//                    + Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
//                    + Constant.requestType;
//
//
//            String signature = Decode.encode(Constant.serectkey, decode);
//            jsonRequest.setSignature(signature);
//            String json = mapper.writeValueAsString(jsonRequest);
//            HttpClient client = HttpClient.newHttpClient();
//            ResultMoMo res = new ResultMoMo();
//
//            try {
//                HttpRequest requestMomo = HttpRequest.newBuilder().uri(new URI(Constant.Url))
//                        .POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
//                        .build();
//                HttpResponse<String> response = client.send(requestMomo, HttpResponse.BodyHandlers.ofString());
//                res = mapper.readValue(response.body(), ResultMoMo.class);
//            } catch (InterruptedException | URISyntaxException | IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            if (res == null) {
//
//                session.setAttribute("error_momo", "Thanh toán thất bại");
//                return "redirect:/home";
//
//            } else {
////					return "redirect:/shop";
////				resp.sendRedirect(res.payUrl);
//                return "redirect:" + res.payUrl;
//
//            }
//
//
////            System.out.println("Bạn đã chọn thanh toán bằng banking");
////            return "Bạn đã chọn thanh toán bằng tiền mặt";
//
//        } else if ("cash".equals(hinhThuc)) {
//
//            System.out.println("Bạn đã chọn thanh toán bằng tiền mặt");
//            String diaChiChon = request.getParameter("diaChiChon");
//
//            if ("diaChiCu".equals(diaChiChon)) {
//
//                HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
//
//                if (hoaDon != null) {
//
//                    String soDienThoaiCu = request.getParameter("hiddenSoDienThoai");
//                    String diaChiCu = request.getParameter("hiddenDiaChi");
//
//                    LocalDate ngayThanhToanCu = LocalDate.now();
//                    String ngayTaoCu = ngayThanhToanCu.toString();
//
//                    hoaDon.setTrangThai(1);
//                    hoaDon.setNgayThanhToan(ngayTaoCu);
//                    hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoaiCu + ", Địa chỉ giao hàng: " + diaChiCu);
//
//                    //Lấy số lượng hiện tại và trừ đi
//
//                    List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDon_Id(hoaDonId);
//                    model.addAttribute("hoaDonChiTiet",hoaDonChiTiet);
//
//                    // Tìm hóa đơn để tính số lượng
//                    for (HoaDonChiTiet hoaDonChiTietList : hoaDonChiTiet) {
//
//                        // Số lượng mua của khách hàng
//                        String soLuongMuaAsString = hoaDonChiTietList.getSoLuong();
//                        int soLuongMua = Integer.parseInt(soLuongMuaAsString);
//
//                        // Số lượng hiện có trong kho
//                        GiayTheThaoChiTiet giayTheThaoChiTiet = hoaDonChiTietList.getGiayTheThaoChiTiet();
//                        String soLuongCoAsString = giayTheThaoChiTiet.getSoLuong();
//                        int soLuongCo = Integer.parseInt(soLuongCoAsString);
//                        giayTheThaoChiTiet.setSoLuong(Integer.toString(soLuongCo - soLuongMua));
//                        giayTheThaoChiTietRepository.save(giayTheThaoChiTiet);
//
//
//                    }
//
//
//
//                    hoaDonRepository.save(hoaDon);
//                    model.addAttribute("idKH", hoaDon.getKhachHang().getId());
////                    return "/templates/Users/Layouts/Shop/viewHoaDonThanhCong";
//                    return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";
//
//                } else {
//
//                    System.out.println("Hóa đơn không tồn tại");
//
//                }
//
//            }else if("diaChiMoi".equals(diaChiChon)){
//
//                HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElse(null);
//
//                if (hoaDon != null) {
//
//                    String soDienThoai = request.getParameter("soDienThoai");
//                    String diaChi = request.getParameter("diaChi");
//
//                    //Check số điện thoại
//                    if(soDienThoai.isEmpty() || soDienThoai.trim().length() ==0 || soDienThoai == null){
//
//                        attributes.addFlashAttribute("erLogSoDienThoaiChon","Xin lỗi không được để trống số điện thoại !");
//                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//                    }
//
//                    //check số điện thoại nhập là chữ
//                    try{
//
//                        int soDienThoaiFomat = Integer.parseInt(soDienThoai);
//
//                        if(soDienThoaiFomat < 0){
//
//                            attributes.addFlashAttribute("erLogSoDienThoai0","Số điện thoại không được nhỏ hơn 0");
//                            return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//                        }
//
//
//                    }catch (NumberFormatException e){
//
//                        e.printStackTrace();
//                        attributes.addFlashAttribute("erLogSoDienThoaiChonChu","Số điện thoại không được là chữ !");
//                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//                    }
//
//                    // Validate số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số
//                    String phoneNumberRegex = "^0\\d{9}$";
//
//                    if (!soDienThoai.matches(phoneNumberRegex)) {
//                        attributes.addFlashAttribute("erLogSoDienThoaiNumber", "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại bắt đầu bằng số 0 và có độ dài là 10 số.");
//                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//                    }
//
//                    //Check địa chỉ nhận hàng
//                    if(diaChi == null || diaChi.isEmpty() || diaChi.trim().length()==0){
//
//                        attributes.addFlashAttribute("erLogDiaChiChon","Không được để trống địa chỉ !");
//                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//                    }
//
//                    if(diaChi.matches("^\\d.*")
//                        || !diaChi.matches(".*[a-zA-Z].*")){
//
//                        attributes.addFlashAttribute("erLogDiaChiChonNo","Xin lỗi tên địa chỉ không hợp lệ");
//                        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//                    }
//
//
//                    LocalDate ngayThanhToanMoi = LocalDate.now();
//                    String ngayTaoMoi = ngayThanhToanMoi.toString();
//
//                    hoaDon.setTrangThai(1);
//                    hoaDon.setNgayThanhToan(ngayTaoMoi);
//                    hoaDon.setGhiChu("Số điện thoại nhận hàng: " + soDienThoai + ", Địa chỉ giao hàng: " + diaChi);
//
//                    hoaDonRepository.save(hoaDon);
//                    model.addAttribute("idKH", hoaDon.getKhachHang().getId());
////                    return "/templates/Users/Layouts/Shop/viewHoaDonThanhCong";
//                    return "redirect:/nguoiDung/hoaDon/thanhToan/ThanhCong";
//
//                } else {
//
//                    System.out.println("Hóa đơn không tồn tại");
//
//                }
//
//            }else if(diaChiChon == null || diaChiChon.isEmpty()){
//
//                attributes.addFlashAttribute("erViewDiaChiChon","Vui lòng chọn địa chỉ để có thể giao hàng !");
//                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//            }else{
//
//                attributes.addFlashAttribute("erViewDiaChiChon","Vui lòng chọn địa chỉ để có thể giao hàng !");
//                return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//            }
//
//        }else if(hinhThuc == null || hinhThuc.isEmpty()){
//
//            System.out.println("Hình thức thanh toán không hợp lệ");
//            attributes.addFlashAttribute("messageNoChonNull","Vui lòng chọn một hình thức để có thể thanh toán !");
//            return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }else{
//
//            System.out.println("Hình thức thanh toán không hợp lệ");
//            attributes.addFlashAttribute("messageNoChon","Vui lòng chọn hình thức để có thể thanh toán !");
//            return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
//
//        }


//        return "redirect:/nguoiDung/HoaDon/" + hoaDonId;
        return "redirect:/TrangChu/listGiayTheThao";

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

    //Todo code trạng thái đơn hàng
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

    //Todo code đang giao hàng bên phía khách hàng
    @GetMapping("/KhachHang/HoaDon/DangGiaoHang/*")
    public ModelAndView dangGiaoHangBenPhiaKhachHang(
            @RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model){

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
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),2,pageNo,5);
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
        hd.setTrangThai(3);

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
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),3,pageNo,5);
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
        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByKhachHangAndTrangThai(khachHang.getId(),4,pageNo,5);
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
         */
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(4);

        hoaDonServiceImpl.update(hoaDon.getId(),hd);

//        return "redirect:/KhachHang/HoaDon/HuyDonHang"+idKH;
//        return "redirect:/KhachHang/HoaDon/HuyDonHang?idKH=" + idKH;

        return "/templates/Users/Layouts/TrangThaiDonHang/KhachHang/hoaDonHuyBenKhachHang";

    }


//////////////////////////////////////////////////////////////////////////


    //Bên phía Admin

    //Todo code xác nhân đơn hàng bên phía admin

    //Todo code All xác nhận bên phía admin
    @GetMapping("/Admin/xacNhanDonHangKhachHangAll")
    public String showViewXacNhanDonHangAllKhachHang(){

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

    //Todo code xác nhân bên khách hàng
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHang")
    public String showHoaDonXacNhanBenKhachHang(HttpServletRequest request){

        String huy = request.getParameter("huy");

        HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(huy));
        HoaDon hd = new HoaDon();

        hd.setMaHoaDon(hoaDon.getMaHoaDon());
        hd.setThanhTien(hoaDon.getThanhTien());
        hd.setNgayTao(hoaDon.getNgayTao());
        hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(2);
        hoaDonServiceImpl.update(hoaDon.getId(),hd);

        return "redirect:/Admin/xacNhanDonHangKhachHangAll";

    }

    //Todo code xác nhận giao hàng thành công bên phía admin
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCong")
    public ModelAndView showFormHoaDonXacNhanGiaoHangThanhCong(

            @RequestParam(value = "pageNo",required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model
    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,2);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/dangGiaoHang");
        mav.addObject("page", page);
        return mav;

    }

    //Todo code xác nhận giao hàng thành công bên phía Admin
    @PostMapping("/Admin/HoaDon/XacNhanHoaDonKhachHangThanhCong")
    public String adminGiaoHangThanhCong(
            HttpServletRequest request
    ){

        String thanhCong = request.getParameter("thanhCong");

        HoaDon hoaDon = hoaDonServiceImpl.findId(UUID.fromString(thanhCong));
        HoaDon hd = new HoaDon();

        hd.setMaHoaDon(hoaDon.getMaHoaDon());
        hd.setThanhTien(hoaDon.getThanhTien());
        hd.setNgayThanhToan(hoaDon.getNgayThanhToan());
        hd.setNgayTao(hoaDon.getNgayTao());
        hd.setKhachHang(hoaDon.getKhachHang());
        hoaDon.setTrangThai(3);
        hd.setGhiChu(hoaDon.getGhiChu());

        hoaDonServiceImpl.update(hoaDon.getId(),hd);

        return "redirect:/Admin/xacNhanDonHangKhachHangAll";

    }


    //Todo code hoàn thành giao hàng
    @GetMapping("/Admin/HoaDon/XacNhanHoaDonGiaoHangThanhCongHoanThanh")
    public ModelAndView showGiaoHangHoanThanh(

            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            HttpServletRequest request,
            Model model

    ){

        Page<HoaDon> page = hoaDonServiceImpl.listHoaDonFindByTrangThai(pageNo,5,3);
        ModelAndView mav = new ModelAndView("/templates/Admin/TrangThaiDonHang/hoanThanh");
        mav.addObject("page", page);
        return mav;

    }


}
