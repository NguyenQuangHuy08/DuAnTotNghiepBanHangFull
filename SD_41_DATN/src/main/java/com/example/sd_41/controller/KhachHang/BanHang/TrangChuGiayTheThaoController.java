package com.example.sd_41.controller.KhachHang.BanHang;

import com.example.sd_41.model.*;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import com.example.sd_41.repository.BanHang.GioHangRepository;
import com.example.sd_41.repository.ChuongTrinhGiamGia.ChuongTrinhGiamGiaChiTietGiayTheThaoRepository;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.ImageRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.*;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;

import com.example.sd_41.service.GioHang.GioHangChiTietImpl;
import com.example.sd_41.service.GioHang.GioHangChiTietService;
import com.example.sd_41.service.KhachHangService;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class TrangChuGiayTheThaoController {

    @Autowired
    ServletContext context;

     @Autowired
     private GiayTheThaoChiTietRepository giayTheThaoChiTietRepository;

     @Autowired
     private GiayTheThaoRepository giayTheThaoRepository;

     @Autowired
     private ImageRepository imageRepository;

     @Autowired
     private GioHangRepository gioHangRepository;

     @Autowired
     private GioHangChiTietRepository gioHangChiTietRepository;

     @Autowired
     private HoaDonRepository hoaDonRepository;

     @Autowired
     private HoaDonChiTietRepository hoaDonChiTietRepository;

     @Autowired
     private GioHangChiTietImpl gioHangChiTietImpl;

     @Autowired
     private KhachHangRepository khachHangRepository;

     @Autowired
     private GioHangChiTietService gioHangChiTietService;

     @Autowired
     private ThuongHieuRepository thuongHieuRepository;

     @Autowired
     private CongDungRepository congDungRepository;

     @Autowired
     private TrongLuongRepository trongLuongRepository;

     @Autowired
     private FormRepository formRepository;

     @Autowired
     private SizeRepository sizeRepository;

     @Autowired
     private MauSacRepository mauSacRepository;

     @Autowired
     private ChatLieuRepository chatLieuRepository;

     @Autowired
     private ChuongTrinhGiamGiaChiTietGiayTheThaoRepository chuongTrinhGiamGiaChiTietGiayTheThaoRepository;

    //Todo code view giầy thể thao cho người dùng mua hàng phân trang cho người dùng ở luôn trang index by Giầy thể thao

    private void giaoDienTrangChuListGiayTheThao(Model model, Integer pageNum, Integer pageSize, GiayTheThaoRepository giayTheThaoRepository) {
        List<GiayTheThao> lstGiayTheThao = giayTheThaoRepository.findAll();
        model.addAttribute("lstGiayTheThao", lstGiayTheThao);

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<GiayTheThao> page = giayTheThaoRepository.findAll(pageable);

        // Hiện thông tin sản phẩm
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        // List danh sách các trang để hiện ra giao diện
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int currentPage = pageNum;
        int startPage, endPage;

        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 3) {
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

        //Todo code sử lý dữ liệu size và màu sắc để lọc thông tin


        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);

        //Todo code list các sản phẩm giầy thể thao có chương trình khuyến mãi

      List<ChuongTrinhGiamGiaChiTietGiayTheThao> listSale = chuongTrinhGiamGiaChiTietGiayTheThaoRepository.findAll();
      model.addAttribute("listSale",listSale);


    }

    private void giaoDienTrangChuListGiayTheThaoDetails(Model model, Integer pageNum, Integer pageSize, GiayTheThaoRepository giayTheThaoRepository) {
        // Số sản phẩm hiển thị trên mỗi trang
        int size = 25;

        // Lấy tất cả sản phẩm
        List<GiayTheThao> lstGiayTheThao = giayTheThaoRepository.findAll();
        model.addAttribute("lstGiayTheThao", lstGiayTheThao);

        // Thiết lập Pageable với số lượng sản phẩm trên mỗi trang
        Pageable pageable = PageRequest.of(pageNum - 1, size);

        // Lấy trang dữ liệu
        Page<GiayTheThao> page = giayTheThaoRepository.findAll(pageable);

        // Hiển thị thông tin sản phẩm
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        // Danh sách các trang để hiển thị ra giao diện
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int currentPage = pageNum;
        int startPage, endPage;

        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 3) {
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

        // Todo code sử lý dữ liệu size và màu sắc để lọc thông tin

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);
    }

    //Todo code list giầy thể thao trang chủ
    @RequestMapping(value = "TrangChu/listGiayTheThao")
    public String showListViewGiayTheThao(Model model,
                                          HttpSession session,
                                          @RequestParam(name = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                          @RequestParam(name = "pageSize",required = false,defaultValue = "9")Integer pageSize){

        if(session.getAttribute("khachHangLog") != null) {

            System.out.println("Tài khoản khách hàng đã được đăng nhập");
            model.addAttribute("maKH",session.getAttribute("maKH"));
            giaoDienTrangChuListGiayTheThao(model,pageNum,pageSize,giayTheThaoRepository);

            return "/templates/Users/indexLogin";

        }else {

            System.out.println("Tài khoản khách hàng chưa được đăn nhập");
            giaoDienTrangChuListGiayTheThao(model,pageNum,pageSize,giayTheThaoRepository);
            return "/templates/Users/index";

        }

    }

    //Todo code list giầy thể thao có bộ lọc
    @RequestMapping(value = "TrangChu/listGiayTheThao/Details")
    public String showListViewGiayTheThaoDetailsAndLoc(Model model,
                                                       HttpSession session,
                                                       @RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                                       @RequestParam(name = "pageSize",required = false,defaultValue = "25") Integer pageSize
                                                       ){


        if(session.getAttribute("khachHangLog") != null){

            System.out.println("Tài khoản của khách hàng đã được đang nhập");
            model.addAttribute("maKH",session.getAttribute("maKH"));
            giaoDienTrangChuListGiayTheThaoDetails(model,pageNum,pageSize,giayTheThaoRepository);

            return "/templates/Users/indexLoginDetails";

        }else {

            System.out.println("Khách hàng chưa đăng nhập tài khoản !");
            giaoDienTrangChuListGiayTheThao(model,pageNum,pageSize,giayTheThaoRepository);

            return "/templates/Users/index";

        }

    }


    //Todo code detail thông tin sản phẩm chi tiết và thông tin sản phẩm
    //id ở đây là id của giầy thể thao
    private void detailGiayTheThaoChiTietTrangChu(Model model, UUID id, Integer pageNum, Integer pageSize) {
        GiayTheThao giayTheThao = giayTheThaoRepository.findById(id).orElse(null);
        model.addAttribute("giayTheThao", giayTheThao);

        if (giayTheThao != null) {
            List<GiayTheThaoChiTiet> giayTheThaoChiTiet = giayTheThaoChiTietRepository.findByGiayTheThao(giayTheThao);
            model.addAttribute("giayTheThaoChiTiet", giayTheThaoChiTiet);

            List<Size> uniqueSizes = new ArrayList<>();
            List<MauSac> uniqueMauSac = new ArrayList();

            for (GiayTheThaoChiTiet chiTiet : giayTheThaoChiTiet) {
                Size size = chiTiet.getSize();

                if (!uniqueSizes.contains(size)) {
                    uniqueSizes.add(size);
                }
            }

            for (GiayTheThaoChiTiet chiTiet : giayTheThaoChiTiet) {
                MauSac mauSac = chiTiet.getMauSac();
                if (!uniqueMauSac.contains(mauSac)) {
                    uniqueMauSac.add(mauSac);
                }
            }

            int totalSoLuong = 0;

            for (GiayTheThaoChiTiet chiTiet : giayTheThaoChiTiet) {

                totalSoLuong += Integer.parseInt(chiTiet.getSoLuong());

            }

            model.addAttribute("totalSoLuong", totalSoLuong);
            model.addAttribute("uniqueSizes", uniqueSizes);
            model.addAttribute("uniqueMauSac", uniqueMauSac);

            List<Image> images = imageRepository.findImageByGiayTheThao(giayTheThao);
            model.addAttribute("listImage", images);
        }

        // Phân trang dữ liệu
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<GiayTheThao> page = giayTheThaoRepository.findAll(pageable);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int currentPage = pageNum;
        int startPage, endPage;

        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 3) {
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

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);

        //Todo code giảm giá bên details
        try{

            List<ChuongTrinhGiamGiaChiTietGiayTheThao> listSale = chuongTrinhGiamGiaChiTietGiayTheThaoRepository.findByGiayTheThao_Id(id);

            for(ChuongTrinhGiamGiaChiTietGiayTheThao sale : listSale){

                //Trạng thái đã kích hoạt
                //Trạng thái 1 là đã được kích hoạt
                if(sale.getTrangThai() == 1){

                    model.addAttribute("sale",sale.getChuongTrinhGiamGiaGiayTheThao().getPhanTramGiam());

                }else {

                    model.addAttribute("sale",0);

                }

            }


        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("sale",null);
            model.addAttribute("giayTheThao", giayTheThao);


        }



    }

    //Todo code list giày thể thao Deals of the Week bên trang chủ

    @RequestMapping(value = "GiayTheThao/listGiayTheThaoDealsOfTheWeek")
    public String listGiayTheThaoDealsOfTheWeek(Model model,
                                                @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                                @RequestParam(value = "pageSize",required = false,defaultValue = "12") Integer pageSize){

        List<GiayTheThaoChiTiet> lstGiayTheThaoDealsOfTheWeek = giayTheThaoChiTietRepository.findAll();
        model.addAttribute("lstGiayTheThaoDealsOfTheWeek",lstGiayTheThaoDealsOfTheWeek);

        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        Page<GiayTheThaoChiTiet> page = giayTheThaoChiTietRepository.findAll(pageable);

        model.addAttribute("totalPage",page.getTotalPages());
        model.addAttribute("listPage",page.getContent());


        return "/templates/Users/index";

    }



    //Todo code lọc
    @GetMapping("/GiayTheThao/find/{idGiayTheThao}/{idMauSac}/{idSize}")
    public ResponseEntity<?> find(
            @PathVariable UUID idGiayTheThao,
            @PathVariable UUID idMauSac,
            @PathVariable UUID idSize) {

        System.out.println("Mã giầy thể thao :"+idGiayTheThao);
        System.out.println("Mã màu sắc: "+idMauSac);
        System.out.println("Mã size"+idSize);

        GiayTheThaoChiTiet giayTheThaoChiTiet = giayTheThaoChiTietRepository.findIdByIdGiayTheThaoMsSize(idGiayTheThao, idMauSac, idSize);

        return ResponseEntity.ok(giayTheThaoChiTiet.getSoLuong());

    }



    //Todo code detail giầy thể thao chi tiết
    @GetMapping("GiayTheThao/detailThongTinGiayTheThao/{id}")
    public String detailChiTietGiayTheThao(@PathVariable UUID id, Model model,//id là id của giầy thể thao
                                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "4") Integer pageSize,
                                           HttpSession session) {

        if(session.getAttribute("khachHangLog") != null){

            System.out.println("Đã đăng nhập tài khoản!");
            detailGiayTheThaoChiTietTrangChu(model,id,pageNum,pageSize);
            return "/templates/Users/Layouts/Shop/detailGiayTheThaoLogin";


        }else {

            System.out.println("Chưa đăng nhập tài khoản!");
            detailGiayTheThaoChiTietTrangChu(model,id,pageNum,pageSize);
            return "/templates/Users/Layouts/Shop/detailGiayTheThao";

        }
    }

    // Todo code giỏ hàng
    //Todo code log not login chưa đăng nhập mà add to cart giỏ hàng

    @GetMapping("GiayTheThao/NguoiDungNotLoginAddToCart")
    public String nguoiDungNotLoginAddToCart(){

        return "/templates/Users/Layouts/Log/AddToCartLogNotLogin";

    }

    //Todo code log succcess login đã đăng nhập khi mà add to cart giỏ hàng
    @GetMapping("GiayTheThao/NguoiDungSuccessLoginAddToCart")
    public String nguoiDungSuccessLoginAddToCart(){

        //Trả về đường dẫn hiện swal
        return "/templates/Users/Layouts/Log/AddToCartLogLogin";

    }




    //Todo code giỏ hàng cho giầy thể thao
    @PostMapping("GiayTheThao/NguoiDung/AddToCart/{id}")
    public String showAddToCartNguoiDung(Model model,
                                         HttpSession session,
                                         RedirectAttributes attributes,
                                         HttpServletRequest request
                                         ) {
        //Lấy dữ liệu trả từ form
        String mauSac = request.getParameter("mauSac");
        String size = request.getParameter("size");
        String soLuong = request.getParameter("soLuong");
        String idGiayTheThao = request.getParameter("idGiayTheThao");
        String giaBan = request.getParameter("gia");
        UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");

        if(idKhachHang != null) {

            UUID giayTheThaoId = UUID.fromString(idGiayTheThao);

            GiayTheThao giayTheThao = giayTheThaoRepository.findById(giayTheThaoId).orElse(null);
            model.addAttribute("giayTheThao", giayTheThao);

//            set lại giá bán cho giầy thể thao nếu nó có sản phẩm được giảm giá
            giayTheThao.setGiaBan(giaBan);
            giayTheThaoRepository.save(giayTheThao);

            //Check size và màu sắc khi chưa chọn gì
            if(size == null || mauSac == null){

                attributes.addFlashAttribute("erCheckAddToCart","Vui lòng chọn dữ liệu để có thể mua hàng!");
                return "redirect:/GiayTheThao/detailThongTinGiayTheThao/"+giayTheThaoId;

            }
            UUID sizeId = UUID.fromString(size);
            UUID mauSacId = UUID.fromString(mauSac);
            //Tìm kiếm id của giầy thể thao chi tiếtgiayTheThao
            UUID idGiayTheThaoChiTiet = giayTheThaoChiTietRepository.findIdByGiayTheThaoAndSizeAndMauSac(giayTheThaoId, sizeId, mauSacId);

            if (idGiayTheThaoChiTiet != null) {

                model.addAttribute("idGiayTheThaoChiTiet", idGiayTheThaoChiTiet);

                GioHang gioHang = gioHangRepository.findByKhachHangId(idKhachHang);

                model.addAttribute("khachHangView",session.getAttribute("khachHangLog"));

                GiayTheThaoChiTiet giayTheThaoChiTiet = giayTheThaoChiTietRepository.findByGiayTheThaoAndSizeAndMauSac(giayTheThaoId, sizeId, mauSacId);

                if (gioHang != null) {

                    if (soLuong == null || soLuong.trim().length() == 0 || soLuong.isEmpty()) {

                        attributes.addFlashAttribute("erCheckSoLuongAddToCart", "Không được để trống số lượng !");
                        return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                    }

                    int checkSoLuongAddToCart = Integer.parseInt(soLuong);
                    int soLuongTonKho = Integer.parseInt(giayTheThaoChiTiet.getSoLuong());

                    try {


                        if (checkSoLuongAddToCart < 0) {

                            attributes.addFlashAttribute("erSoLuongAddToCartMin", "Số lượng không được âm !");
                            return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                        }


                        if (checkSoLuongAddToCart > soLuongTonKho) {

                            attributes.addFlashAttribute("erSoLuongAddToCartMax", "Số lượng trong kho chỉ còn " + soLuongTonKho + " đối với size và màu sắc này");

                            return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                        }

                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                        attributes.addFlashAttribute("erCheckNumberSoLuongAddToCart", "Số lượng phải là số không được là chữ !");
                        return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                    }

                    GioHangChiTiet existingItem = gioHangChiTietRepository.findByGioHangAndGiayTheThaoChiTiet(gioHang, giayTheThaoChiTiet);

                    if (existingItem != null) {

                        // Sản phẩm đã có trong giỏ hàng, cập nhật số lượng
                        int existingQuantity = Integer.parseInt(existingItem.getSoLuong());
                        int newQuantity = existingQuantity + Integer.parseInt(soLuong);

                        if(newQuantity > soLuongTonKho){

                            attributes.addFlashAttribute("soLuongMax","Sản phẩm này trong kho chỉ còn"+ soLuongTonKho + "quý vị thông cảm");
                            existingItem.setSoLuong(String.valueOf(soLuongTonKho));
                            existingItem.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(soLuongTonKho)));
                            gioHangChiTietRepository.save(existingItem);

                        }else{

                            existingItem.setSoLuong(String.valueOf(newQuantity));
                            existingItem.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(newQuantity)));
                            gioHangChiTietRepository.save(existingItem);

                        }

                    } else {

                        // Sản phẩm chưa có trong giỏ hàng, thêm mới
                        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
                        gioHangChiTiet.setGioHang(gioHang);
                        gioHangChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTiet);
                        gioHangChiTiet.setSoLuong(String.valueOf(checkSoLuongAddToCart));
//                        gioHangChiTiet.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(checkSoLuongAddToCart)));

                        BigDecimal giaBanBigDeimal = new BigDecimal(giaBan);
                        gioHangChiTiet.setDonGia(giaBanBigDeimal.multiply(BigDecimal.valueOf(checkSoLuongAddToCart)));

                        gioHangChiTietRepository.save(gioHangChiTiet);

                    }

//                    return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
                      return "redirect:/GiayTheThao/NguoiDungSuccessLoginAddToCart";


                }

            } else {

                attributes.addFlashAttribute("erCheckSizeAndMuaSacNotFind", "Xin lỗi hiện tại màu sắc này đã hết!");
                return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

            }

        }else {

            System.out.println("Chưa đăng nhập tài khoản");
            return "redirect:/GiayTheThao/NguoiDungNotLoginAddToCart";

        }

            System.out.println("Đã đăng nhập tài khoản");
            model.addAttribute("successMessage", "Thêm sản phẩm vào giỏ hàng thành công!");

            return "redirect:/GiayTheThao/NguoiDungSuccessLoginAddToCart";

    }

    //Todo code list giỏ hàng chi tiết
    @GetMapping("GiayTheThao/NguoiDung/ViewGioHang")
    public String nguoiDungViewShowListGioHang(Model model, RedirectAttributes attributes,
                                               HttpSession session

                                               ){

        if(session.getAttribute("khachHangLog") != null){

            System.out.println("Đã đăng nhập tài khoản!");
            List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietRepository.findAll();
            model.addAttribute("listGioHangChiTiet",listGioHangChiTiet);
            return "/templates/Users/Layouts/Shop/gioHangView";

        }else {

            System.out.println("Chưa đăng nhập tài khoản!");
            return "redirect:/GiayTheThao/NguoiDungNotLoginAddToCart";

        }

    }







    //Todo code delete

//    @GetMapping("delete/{id}")
//    public String deleteProduct(@PathVariable("id") Integer id){
//
//        if(categoryRepository.existsById(id)){
//
//            categoryRepository.deleteById(id);
//
//        }
//
//        return "redirect:/category/list";
//
//    }


    //Todo code xóa giỏ hàng, id là của giỏ hàng chi tiết
//
//    @PostMapping("GiayTheThao/delete/{id}")
//    public String deleteGioHang(Model model,
//                                @PathVariable("id") UUID id //id của giỏ hàng chi tết,
//    ){
//
//
//            gioHangChiTietService.delete(id);
//            model.addAttribute("viewDeleteGioHang", "Xóa sản phẩm trong giỏ hàng thành công !");
//
//            return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
//
//    }


//    @PostMapping("/GiayTheThao/delete/*")
//    public String deleteGioHang(Model model,HttpServletRequest request,HttpSession session
//    ){
//
//        String url = request.getRequestURI();
//        String[] parts = url.split("/GiayTheThao/delete/");
//
//        UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
//        model.addAttribute("idKhachHang",idKhachHang);
//
//        String clear = request.getParameter("clear");
//
//        GioHangChiTiet gioHangChiTiet = gioHangChiTietImpl.findById(UUID.fromString(clear));
//
//        GioHangChiTiet gioHangChiTietNew = new GioHangChiTiet();
//
//        gioHangChiTietNew.setGioHang(gioHangChiTiet.getGioHang());
//        gioHangChiTietNew.setGiayTheThaoChiTiet(gioHangChiTiet.getGiayTheThaoChiTiet());
//        gioHangChiTietNew.setSoLuong(gioHangChiTiet.getSoLuong());
//        gioHangChiTiet.setDonGia(gioHangChiTiet.getDonGia());
//        gioHangChiTiet.setTrangThai(1);
//
//        gioHangChiTietImpl.update(gioHangChiTiet.getId(),gioHangChiTietNew);
//
//        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
//
//    }



    //Todo code add giầy thể thao chi tiết vào giỏ hàng chi tiết
    @PostMapping("/GiayTheThao/nguoiDung/addHoaDon")
    public String nguoiDungAddHoaDon(Model model,
                                     @RequestParam(value = "chon", required = false) List<String> chon,
                                     @RequestParam(value = "idGiayChiTiet", required = false) List<UUID> idGiayChiTiet,
                                     @RequestParam(value = "soLuong", required = false) List<String> soLuong,
                                     @RequestParam(value = "donGia", required = false) List<String> donGia,
                                     HttpSession session,
                                     RedirectAttributes attributes) {

        // Đã lưu mã vào session
        UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).orElse(null);

        // Chọn là null
        if (chon == null || chon.isEmpty()) {
            attributes.addFlashAttribute("erCheckNun", "Xin lỗi hãy chọn ít nhất một sản phẩm để thanh toán !");
            return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
        } else {
            HoaDon hoaDon = new HoaDon();
            // Thêm vào Hóa đơn
            LocalTime localTime = LocalTime.now();
            LocalDate ngayThanhToan = LocalDate.now();
            String ngayThanhToanToDate = ngayThanhToan.toString();

            hoaDon.setMaHoaDon("MaHD" + localTime.getHour() + localTime.getMinute() + localTime.getSecond());
            hoaDon.setKhachHang(khachHang);
            hoaDon.setTrangThai(0);
            hoaDon.setNgayThanhToan(ngayThanhToanToDate);
            hoaDon.setNgayTao(ngayThanhToanToDate);
            hoaDonRepository.save(hoaDon);
            int thanhTien = 0;
            // Thêm vào hóa đơn chi tiết
            for (String stt : chon) {

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTietRepository.findById(idGiayChiTiet.get(Integer.parseInt(stt))).orElse(null));
                hoaDonChiTiet.setSoLuong(String.valueOf(Integer.parseInt(soLuong.get(Integer.parseInt(stt)))));
                BigDecimal gia = new BigDecimal(donGia.get(Integer.parseInt(stt)));
                hoaDonChiTiet.setDonGia(gia);
                hoaDonChiTiet.setTrangThai(1);
                model.addAttribute("hoaDonChiTiet", hoaDonChiTiet);

                hoaDonChiTietRepository.save(hoaDonChiTiet);
                thanhTien += Integer.parseInt(donGia.get(Integer.parseInt(stt)));

            }
            hoaDon.setThanhTien(BigDecimal.valueOf(thanhTien));
            model.addAttribute("hoaDon", hoaDon);
            hoaDonRepository.save(hoaDon);
            return "redirect:/nguoiDung/HoaDon/" + hoaDon.getId();
        }
    }




//Finall dữ liệu trả về

    @ModelAttribute("thuongHieu")
    public List<ThuongHieu> getListThuongHieu(){

        return thuongHieuRepository.findAll();

    }

    @ModelAttribute("form")
    public List<Form> getListForm(){

        return formRepository.findAll();

    }

    @ModelAttribute("chatLieu")
    public List<ChatLieu> getListChatLieu(){

        return chatLieuRepository.findAll();

    }

    @ModelAttribute("congDung")
    public List<CongDung> getListCongDung(){

        return congDungRepository.findAll();

    }

    @ModelAttribute("trongLuong")
    public List<TrongLuong> getListTrongLuong(){

        return trongLuongRepository.findAll();

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


