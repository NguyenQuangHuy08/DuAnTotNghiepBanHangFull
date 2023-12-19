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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

            //Tim kiếm giầy thể thao được áp dụng cho giảm giá
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
                                           HttpSession session,
                                           HttpServletRequest request) {

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
        //Giá bán
        String giaBan = request.getParameter("gia");
        attributes.addFlashAttribute("giaBan", giaBan);
//        session.setAttribute("giaBan",giaBan);

        UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");

        if(idKhachHang != null) {

            UUID giayTheThaoId = UUID.fromString(idGiayTheThao);

            GiayTheThao giayTheThao = giayTheThaoRepository.findById(giayTheThaoId).orElse(null);

            model.addAttribute("giayTheThao", giayTheThao);

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


                        if (checkSoLuongAddToCart <= 0) {

                            attributes.addFlashAttribute("erSoLuongAddToCartMin", "Số lượng sản phẩm mua phải lớn hơn hoặc bằng 0!");
                            return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                        }


                        if (checkSoLuongAddToCart > soLuongTonKho) {

                            attributes.addFlashAttribute("erSoLuongAddToCartMax", "Không mua được sản phẩm vì số lượng trong kho chỉ còn " + soLuongTonKho + " đối với size và màu sắc này");
                            return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                        }

                        if(checkSoLuongAddToCart > 5){

                            attributes.addFlashAttribute("erSoLuongAddToCartMaxGioiHan", "Xin lỗi quý khách! số lượng mua cho một sản phẩm không lớn hơn 5!");
                            return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                        }

                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                        attributes.addFlashAttribute("erCheckNumberSoLuongAddToCart", "Số lượng phải là số không được là chữ !");
                        return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                    }

                    //Trường hợp check sản phẩm đã có
                    //Đây là số lượng
                    GioHangChiTiet existingItem = gioHangChiTietRepository.findByGioHangAndGiayTheThaoChiTiet(gioHang, giayTheThaoChiTiet);

                    if (existingItem != null) {

                        // Sản phẩm đã có trong giỏ hàng, cập nhật số lượng
                        int existingQuantity = Integer.parseInt(existingItem.getSoLuong());
                        int newQuantity = existingQuantity + Integer.parseInt(soLuong);

                        //Dùng cho set lại giá bán
//                        BigDecimal giaBanBigDeimal = new BigDecimal(giaBan);

                        if(newQuantity > 5){

                            System.out.println("Sản phẩm cộng dồn");
                            int soLuongMaxCongDon = 5;
             //               existingItem.setSoLuong(String.valueOf(soLuongTonKho));
                            existingItem.setSoLuong(String.valueOf(soLuongMaxCongDon));
                            //Set đơn giá cho sản phẩm đã có trong giỏ hàng
                            existingItem.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(soLuongMaxCongDon)));
                            attributes.addFlashAttribute("soLuongMax","Xin lỗi quý khách chỉ được thêm số lượng tối đa là 5 cho một sản phẩm !");
                            model.addAttribute("soLuongMaxModel","Xin lỗi quý khách chỉ được thêm số lượng tối đa là 5 cho một sản phẩm !");
                            gioHangChiTietRepository.save(existingItem);

                        }else{

                            //Dành cho sản phẩm chưa có trong giỏ hàng
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
                        BigDecimal giaBanBigDeimal = new BigDecimal(giaBan);
                        gioHangChiTiet.setDonGia(giaBanBigDeimal.multiply(BigDecimal.valueOf(checkSoLuongAddToCart)));

                        gioHangChiTietRepository.save(gioHangChiTiet);

                    }

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
//          Lấy dữ liệu từ trong db
            String giaBan = (String) attributes.getAttribute("giaBan");
            model.addAttribute("giaBan",giaBan);
            attributes.addFlashAttribute("giaBan",giaBan);
            List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietRepository.findAll();
            model.addAttribute("listGioHangChiTiet",listGioHangChiTiet);

            return "/templates/Users/Layouts/Shop/gioHangView";

        }else {

            System.out.println("Chưa đăng nhập tài khoản!");
            return "redirect:/GiayTheThao/NguoiDungNotLoginAddToCart";

        }

    }



    //Todo code add giầy thể thao chi tiết vào giỏ hàng chi tiết
    @PostMapping("/GiayTheThao/nguoiDung/addHoaDon")
    public String nguoiDungAddHoaDon(Model model,
                                     @RequestParam(value = "chon", required = false) List<String> chon,
                                     @RequestParam(value = "idGiayChiTiet", required = false) List<UUID> idGiayChiTiet,
                                     @RequestParam(value = "soLuong", required = false) List<String> soLuong,
                                     @RequestParam(value = "donGia", required = false) List<String> donGia,

                                     HttpSession session,
                                     HttpServletRequest request,
                                     RedirectAttributes attributes) {

        // Đã lưu mã vào session
        UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).orElse(null);

        // Chọn là null
        if (chon == null || chon.isEmpty()) {

            attributes.addFlashAttribute("erCheckNun", "Xin lỗi hãy chọn ít nhất một sản phẩm để thanh toán !");
            return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";

        } else {

            BigDecimal tongDonGiaHoaDon = BigDecimal.ZERO;

            for (String stt : chon) {

               int soLuongChon = Integer.parseInt(soLuong.get(Integer.parseInt(stt)));

               if(soLuongChon <=0){

                   System.out.println("Xin lỗi số lượng mua không được nhỏ hơn hoặc bằng 0");
                   attributes.addFlashAttribute("erCheckSoLuongAm", "Xin lỗi số lượng mua sản phẩm không được nhỏ hơn hoặc bằng 0 !");
                   return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";

               }else if(soLuongChon > 5){

                   System.out.println("Xin lỗi số lượng mua không được lớn hơn 5");
                   attributes.addFlashAttribute("erCheckSoLuongMax", "Xin lỗi quý khách số lượng mua cho một sản phẩm không quá hơn 5 !");
                   return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";

               }

                //Thêm vào hóa đơn
                HoaDon hoaDon = new HoaDon();
                LocalTime localTime = LocalTime.now();
                LocalDate ngayThanhToan = LocalDate.now();
                String ngayThanhToanToDate = ngayThanhToan.toString();

                hoaDon.setMaHoaDon("MaHD" + localTime.getHour() + localTime.getMinute() + localTime.getSecond());
                hoaDon.setKhachHang(khachHang);
                hoaDon.setTrangThai(0);
                hoaDon.setNgayThanhToan(ngayThanhToanToDate);
                hoaDon.setNgayTao(ngayThanhToanToDate);

                hoaDonRepository.save(hoaDon);

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTietRepository.findById(idGiayChiTiet.get(Integer.parseInt(stt))).orElse(null));

                //Todo code mới
                GiayTheThaoChiTiet giayTheThaoChiTiet = giayTheThaoChiTietRepository.findById(idGiayChiTiet.get(Integer.parseInt(stt))).orElse(null);

                System.out.println("Số lượng request trả về : "+ soLuong);

                int soLuongMua = Integer.parseInt(soLuong.get(Integer.parseInt(stt)));
                int soLuongCo = Integer.parseInt(giayTheThaoChiTiet.getSoLuong());

                //Giỏ hàng chi tiết đã chon

                if(giayTheThaoChiTiet != null) {
                    if (soLuongMua > soLuongCo) {

                        System.out.println("Er");
                        attributes.addFlashAttribute("erSoLuong","Xin lỗi hiện tại sản phẩm này chỉ còn duy nhất "+soLuongCo + " đôi !");
                        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";

                    }
                    if(soLuongMua <= 0){

                        System.out.println("Er");
                        attributes.addFlashAttribute("erSoLuongAm","Xin lỗi số lượng mua không được âm !");
                        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";

                    }
                }

                System.out.println("Số lượng mua int: "+ soLuongMua);
                System.out.println("Số lượng có int : "+ soLuongCo);

                //
                System.out.println("Danh sách idGiayChiTiet đã chọn:");
                System.out.println(idGiayChiTiet.get(Integer.parseInt(stt)));

                System.out.println("Danh sách idGiayChiTiet đã chọn:");
                UUID selectedGiayChiTietId = idGiayChiTiet.get(Integer.parseInt(stt));
                System.out.println(selectedGiayChiTietId);

                // Tìm và cập nhật GioHangChiTiet
                GioHangChiTiet gioHangChiTietSetSoLuong = gioHangChiTietRepository.findByGiayTheThaoChiTiet_Id(selectedGiayChiTietId);

                if (gioHangChiTietSetSoLuong != null) {
                    // Cập nhật số lượng mua
                    System.out.println("Set số lượng lại thành công !");
                    gioHangChiTietSetSoLuong.setSoLuong(String.valueOf(soLuongMua));

                    // Get the associated GiayTheThao
                    GiayTheThaoChiTiet giayTheThaoChiTietSL = gioHangChiTietSetSoLuong.getGiayTheThaoChiTiet();

                    if (giayTheThaoChiTietSL != null) {

                        GiayTheThao giayTheThao = giayTheThaoChiTiet.getGiayTheThao();

                        System.out.println("Id của GiayTheThao: " + giayTheThao.getId());
                        String giaBanNew = giayTheThao.getGiaBan();

                        // Convert the String to BigDecimal
                        BigDecimal giaBan = new BigDecimal(giaBanNew);
                        // Calculate DonGia
                        BigDecimal donGiaNew = giaBan.multiply(BigDecimal.valueOf(soLuongMua));
                        // Set the calculated value for DonGia
                        gioHangChiTietSetSoLuong.setDonGia(donGiaNew);
                        //Set cho đơn giá
                        //set số lượng vào hóa đơn chi tiết
                        hoaDonChiTiet.setSoLuong(String.valueOf(Integer.parseInt(soLuong.get(Integer.parseInt(stt)))));
                        // Convert the String to BigDecimal
                        BigDecimal giaBanHoaDonChiTiet = new BigDecimal(giaBanNew);

                        BigDecimal donGiaHoaDonChiTiet = giaBanHoaDonChiTiet.multiply(BigDecimal.valueOf(soLuongMua));

                        hoaDonChiTiet.setDonGia(donGiaHoaDonChiTiet);
                        hoaDonChiTiet.setTrangThai(1);
                        System.out.println("Đơn giá mới của hóa đơn là : "+donGiaHoaDonChiTiet);

                        hoaDonChiTietRepository.save(hoaDonChiTiet);

                        //Sau khi lưu đơn giá của hóa đơn chi tiết xong tôi muốn set thành tiền cho hóa đơn thành tiền
                        //của hóa đơn sẽ là tổng đơn giá trong hóa đơn chi tiết


                        tongDonGiaHoaDon = tongDonGiaHoaDon.add(donGiaHoaDonChiTiet);
                        hoaDon.setThanhTien(tongDonGiaHoaDon);

                        hoaDonRepository.save(hoaDon);

                    }

                    gioHangChiTietRepository.save(gioHangChiTietSetSoLuong);
                    model.addAttribute("hoaDon", hoaDon);
                    model.addAttribute("hoaDonChiTiet", hoaDonChiTiet);

                    return "redirect:/nguoiDung/HoaDon/" + hoaDon.getId();

                } else {

                    System.out.println("Không tìm thấy GioHangChiTiet cho id: " + selectedGiayChiTietId);

                }

            }

        }

        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";

    }


    //Todo code delete sản phẩm chi tiết trong giỏ hàng
    @PostMapping("delete/{id}")
    public String deleteProduct(Model model,
                                @PathVariable("id") UUID id){

        gioHangChiTietService.delete(id);
        model.addAttribute("viewDelete","Xóa giỏ hàng chi tiết thành công");
        return "redirect:/GiayTheThao/NguoiDung/delete";

    }

    //Todo code thông báo xóa sản phẩm giầy thành công
    @GetMapping("GiayTheThao/NguoiDung/delete")
    public String nguoiDungXoaGiayTheThaoGioHangChiTiet(){

        //Trả về đường dẫn hiện swal
        return "/templates/Users/Layouts/Log/XoaGioHang";

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


