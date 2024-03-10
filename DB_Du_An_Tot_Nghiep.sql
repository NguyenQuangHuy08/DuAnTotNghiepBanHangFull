 go
---create database SD_41_DATN
go
use SD_41_DATN

---create bảng form 
create table Form

(
		
		Id_Form				 UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
		tenForm				 nvarchar(max)							  null,
		ghiChu				 nvarchar(max)							  null,
		ngayTao				 Date									  null,
		ngaySua			     Date									  null,
		trangThai			 int									  null,
   
)
---create table công dụng
create table CongDung
(
  
     Id_CongDung			UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	 tenCongDung			nvarchar(max)							null,
	 ghiChu					nvarchar(max)							null,
	 ngayTao				Date									null,
	 ngaySua				Date									null,
	 trangThai				int										null

)
---create bảng chất liệu
create table ChatLieu
(
  
     Id_ChatLieu			UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	 tenChatLieu			nvarchar(max)							null,
	 ghiChu					nvarchar(max)							null,						
	 ngayTao				Date									null,
	 ngaySua				Date									null,
	 trangThai				int										null

)
---create bảng hướng dẫn bảo quản
create table HuongDanBaoQuan
(
   
     Id_HuongDanBaoQuan				UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	 tenHuongDanBaoQuan				nvarchar(max)							null,
	 ghiChu							nvarchar(max)							null,
	 ngayTao						Date									null,
	 ngaySua						Date									null,
	 trangThai						int										null

)
---create bảng Lót giầy
create table LotGiay
(
		
	Id_LotGiay				UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenLotGiay			    nvarchar(max)							null,
	ghiChu					nvarchar(max)							null,
	ngayTao				    Date									null,
	ngaySua					Date									null,
	trangThai				int										null

)
---create bảng Cổ giầy
create table CoGiay
(

	Id_CoGiay               UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenCoGiay			    nvarchar(max)								null,
	ghiChu					nvarchar(max)							null,
	ngayTao				    Date									null,
	ngaySua					Date									null,
	trangThai				int										null

)
---create bảng Đế giầy
create table DeGiay
(

	Id_DeGiay              UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenDeGiay			   nvarchar(max)							   null,
	ghiChu				   nvarchar(max)						   null,
	ngayTao				   Date									   null,
	ngaySua				   Date									   null,
	trangThai			   int									   null

)
---create bảng Dây giầy
create table DayGiay
(

	Id_DayGiay					UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenDayGiay					nvarchar(max)							null,
	chieuDaiDayGiay				nvarchar(max)						    null,	
	ghiChu						nvarchar(max)							null,
	ngayTao						Date									null,
	ngaySua						Date									null,
	trangThai					int										null

)
---create bảng trọng lượng 
create table TrongLuong
(

	Id_TrongLuong					UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenTrongLuong					nvarchar(max)							null,
	ghiChu							nvarchar(max)							null,
	ngayTao							Date									null,
	ngaySua							Date									null,
	trangThai						int										null

)
---create bảng thương hiệu
create table ThuongHieu
(

	Id_ThuongHieu					UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenThuongHieu					nvarchar(max)							null,
	ghiChu							nvarchar(max)							null,
	ngayTao							Date									null,
	ngaySua							Date									null,
	trangThai						int										null

)

---create bảng kiểu buộc
create table KieuBuoc
(
  
     Id_KieuBuoc				UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	 tenKieuBuoc				nvarchar(max)							 null,
	 ghiChu						nvarchar(max)							 null,
	 ngayTao						Date								 null,
	 ngaySua						Date								 null,
	 trangThai						int									 null

)
---create bảng Đinh tán giầy
create table DinhTanGiay
(
    
	Id_DinhTanGiay				UNIQUEIDENTIFIER DEFAULT NEWID()    PRIMARY KEY,
	tenDinhTanGiay				nvarchar(max)								null,
	ghiChu						nvarchar(max)								null,
	ngayTao						Date										null,
	ngaySua						Date										null,
	trangThai					int											null,

)

---create bảng Sản Phẩm

create table GiayTheThao
(
  
    Id_GiayTheThao              UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	Id_Form						UNIQUEIDENTIFIER 						null,
	Id_CongDung     			UNIQUEIDENTIFIER							,
	Id_ChatLieu					UNIQUEIDENTIFIER							,
	Id_HuongDanBaoQuan			UNIQUEIDENTIFIER							,
	Id_LotGiay					UNIQUEIDENTIFIER							,
	Id_CoGiay					UNIQUEIDENTIFIER							,
	Id_DeGiay					UNIQUEIDENTIFIER						    ,
	Id_DayGiay					UNIQUEIDENTIFIER							,
	Id_TrongLuong				UNIQUEIDENTIFIER							,
	Id_ThuongHieu				UNIQUEIDENTIFIER							,
	Id_KieuBuoc					UNIQUEIDENTIFIER							,
	Id_DinhTanGiay				UNIQUEIDENTIFIER							,
	gioiTinh					nvarchar(5)								null,
	tenGiayTheThao				nvarchar(max)							null,
	giaBan						DECIMAL(20, 0) DEFAULT 0				null,
	soTienGiamGia				DECIMAL(20, 0) DEFAULT 0				null,
	moTa						nvarchar(max)							null,
	ghiChu						nvarchar(max)							null,
	ngayTao						Date									null,
	ngaySua						Date									null,
	trangThai					int										null
		 
	
	 Constraint FK_GiayTheThao_Form						Foreign key(Id_Form)				References Form,
	 Constraint FK_GiayTheThao_CongDung					Foreign key(Id_CongDung)			References CongDung,
	 Constraint FK_GiayTheThao_ChatLieu					Foreign key(Id_ChatLieu)			References ChatLieu,
	 Constraint FK_GiayTheThao_HuongDanBaoQuan			Foreign key(Id_HuongDanBaoQuan)		References HuongDanBaoQuan,
	 Constraint FK_GiayTheThao_LotGiay					Foreign key(Id_LotGiay)				References LotGiay,
	 Constraint FK_GiayTheThao_CoGiay					Foreign key(Id_CoGiay)				References CoGiay,
	 Constraint FK_GiayTheThao_DeGiay					Foreign key(Id_DeGiay)				References DeGiay,
	 Constraint FK_GiayTheThao_DayGiay					Foreign key(Id_DayGiay)				References DayGiay,
	 Constraint FK_GiayTheThao_TrongLuong				Foreign key(Id_TrongLuong)			References TrongLuong,
	 Constraint FK_GiayTheThao_ThuongHieu				Foreign key(Id_ThuongHieu)			References ThuongHieu,
	 Constraint FK_GiayTheThao_KieuBuoc					Foreign key(Id_KieuBuoc)			References KieuBuoc,
	 Constraint FK_GiayTheThao_DinhTanGiay				Foreign key(Id_DinhTanGiay)			References DinhTanGiay

)
---create bảng image
create table Image
(
	
	Id_Image              UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	Id_GiayTheThao		  UNIQUEIDENTIFIER							,
	Url_Image			  nvarchar(max)							null,
	ghiChu			      nvarchar(max)							null,
	ngayTao				  Date								    null,
	ngaySua				  Date									null,
	trangThai			  int									null

	Constraint FK_Image_GiayTheThao					Foreign key(Id_GiayTheThao)		References GiayTheThao,

)
---create bảng chương trình giảm giá
create table ChuongTrinhGiamGiaGiayTheThao
(
	
	Id_ChuongTrinhGiamGiaGiayTheThao              UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenChuongTrinhGiamGia						  nvarchar(max)							  null,
	phanTramGiam								  int									  null,
	ngayBatDau									  Date									  null,
	ngayKetThuc									  Date									  null,
	ghiChu										  nvarchar(max)							  null,
	ngayTao										  Date								      null,
	ngaySua										  Date									  null,
	trangThai									  int									  null

)
---create bảng chương trình giảm giá chi tiết giầy thể thao
create table ChuongTrinhGiamGiaChiTietGiayTheThao
(
	
	Id_ChuongTrinhGiamGiaChiTietGiayTheThao              UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	Id_ChuongTrinhGiamGiaGiayTheThao					 UNIQUEIDENTIFIER							 ,
	Id_GiayTheThao										 UNIQUEIDENTIFIER							 ,
	soTienDaGiam										 DECIMAL(20, 0) DEFAULT 0				 null,
	ghiChu												 nvarchar(max)							 null,			
	ngayTao												 Date								     null,
	ngaySua												 Date									 null,
	trangThai											 int									 null

	Constraint FK_ChuongTrinhGiamGiaChiTietGiayTheThao_ChuongTrinhGiamGiaGiayTheThao		  Foreign key(Id_ChuongTrinhGiamGiaGiayTheThao)		References ChuongTrinhGiamGiaGiayTheThao,
	Constraint FK_ChuongTrinhGiamGiaChiTietGiayTheThao_GiayTheThao							  Foreign key(Id_GiayTheThao)						References GiayTheThao

)
---create bảng Size
create table Size
(
	
	Id_Size              UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	size			     nvarchar(max)							 null,
	ghiChu				 nvarchar(max)							 null,
	ngayTao				 Date								     null,
	ngaySua				 Date									 null,
	trangThai			 int									 null

)
---create table Màu sắc
create table MauSac
(
	
	Id_MauSac              UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	tenMauSac			   nvarchar(max)								null,
	ghiChu				   nvarchar(max)							null,
	ngayTao				   Date										null,
	ngaySua				   Date										null,
	trangThai			   int										null

)
---create bảng Chi tiết sản phẩm
create table GiayTheThaoChiTiet
(
	
	Id_GiayTheThaoChiTiet					UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	Id_GiayTheThao							UNIQUEIDENTIFIER							,
	Id_Size									UNIQUEIDENTIFIER							,
	Id_MauSac								UNIQUEIDENTIFIER							,
	soLuong									int										null,	
	ghiChu									nvarchar(max)							null,	
	ngayTao									Date									null,
	ngaySua									Date									null,
	trangThai								int										null

	 Constraint FK_GiayTheThaoChiTiet_GiayTheThao			Foreign key(Id_GiayTheThao)			References GiayTheThao,
     Constraint FK_GiayTheThaoChiTiet_Size					Foreign key(Id_Size)				References Size,
     Constraint FK_ChiTietSanPham_MauSac					Foreign key(Id_MauSac)				References MauSac,

)
---create table User
create table Users
( 

        Id_User				  UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
		maUser				  nvarchar(20)							 unique,
	    Url_Image			  nvarchar(max)							   null,
		email			      nvarchar(50)							   null,
		matKhau				  nvarchar(30)							   null,
		tenUser				  nvarchar(30)							   null,
		gioiTinh			  nvarchar(5)							   null,
		ngaySinh              Date									   null,
		maCCCD				  nvarchar(30)							   null,
		hoKhau				  nvarchar(30)							   null,
		soDienThoai			  nvarchar(30)							   null,
		diaChi				  nvarchar(max)							   null,	
		ghiChu				  nvarchar(max)							   null,
		ngayTao				  Date									   null,
		ngaySua				  Date									   null,
		trangThai			  int									   null,
	    role				  nvarchar(30)							   null,


)
---create bảng 


create table KhachHang
(
 
       Id_KhachHang			  UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	   maKhachHang			  nvarchar(30)							unique,
	   Url_Image			  nvarchar(max)							null,
	   email			      nvarchar(30)							   null,
	   matKhau				  nvarchar(30)							   null,
	   tenKhachHang			  nvarchar(max)							   null,
	   gioiTinh				  nvarchar(5)							   null,
	   ngaySinh				  Date									   null,
	   soDienThoai			  nvarchar(30)							   null,
	   diaChi				  nvarchar(max)							   null,
	   thanhPho				  nvarchar(max)							   null,
	   huyen				  nvarchar(max)							   null,
	   xa				      nvarchar(max)							   null,
	   ghiChu				  nvarchar(max)							   null,
	   ngayTao				  Date									   null,
	   ngaySua				  Date									   null,
	   trangThai			  int									   null,

)
---create bảng HoaDon
create table HoaDon
(
      Id_HoaDon						   UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	  Id_KhachHang					   UNIQUEIDENTIFIER							   ,	
	  Id_User						   UNIQUEIDENTIFIER							   ,
	  maHoaDon						   nvarchar(50)							 unique,
	  thanhTien						   DECIMAL(20, 0) DEFAULT 0			       null,
	  phiShip						   DECIMAL(20, 0) DEFAULT 0			       null,
	  ghiChu						   nvarchar(max)						   null,
	  mess							   nvarchar(max)						   null,
	  ngayTao						   DATETIME								   null,
	  ngayThanhToan					   DATETIME								   null,
	  ngaySua						   DATETIME								   null,
	  hinhThuc						   int									   null,
	  hinhThucThanhToan				   int									   null,
	  trangThaiMoney				   int									   null,
	  trangThai					       int									   null,

	 Constraint FK_HoaDon_KhachHang						Foreign key(Id_KhachHang)			References KhachHang,
	 Constraint FK_HoaDon_Users							Foreign key(Id_User)				References Users,

)
---create bảng chương trình giảm giá hóa đơn
create table ChuongTrinhGiamGiaHoaDon
(
        Id_ChuongTrinhGiamGiaHoaDon			UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
		tenChuongTrinh						nvarchar(max)							null,
		phanTramGiam						int										null,
        soLuongSanPham						int										null,
		soTienHoaDon						DECIMAL(20, 0) DEFAULT 0				null,
		ngayBatDau							Date									null,
		ngayKetThuc							Date									null,
		ghiChu							    nvarchar(max)							null,
		ngayTao							    Date									null,
		ngaySua								Date									null,
		trangThai							int										null,

)
---create bảng chương trình giảm giá chi tiết hóa đơn
create table ChuongTrinhGiamGiaChiTietHoaDon
(
           Id_ChuongTrinhGiamGiaChiTietHoaDon			UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
           Id_ChuongTrinhGiamGiaHoaDon			        UNIQUEIDENTIFIER							,
		   Id_HoaDon									UNIQUEIDENTIFIER							,
		   soTienDaGiam									DECIMAL(20, 0) DEFAULT 0				null,
		   ghiChu										nvarchar(max)							null,
		   ngayTao										Date									null,
		   ngaySua										Date									null,
		   trangThai									int										null,
        
		Constraint FK_ChuongTrinhGiamGiaChiTietHoaDon_ChuongTrinhGiamGiaHoaDon  Foreign key(Id_ChuongTrinhGiamGiaHoaDon) References ChuongTrinhGiamGiaHoaDon,
		Constraint FK_ChuongTrinhGiamGiaChiTietHoaDon_HoaDon					Foreign key(Id_HoaDon) References HoaDon,
      
)
---create phiếu giảm giá hóa đơn
create table PhieuGiamGiaHoaDon
(
      Id_PhieuGiamGiaHoaDon							UNIQUEIDENTIFIER DEFAULT NEWID()	PRIMARY KEY,
	  tenPhieuGiamGiaHoaDon						    nvarchar(max)								null,
	  phanTramGiam									int											null,
	  ghiChu										nvarchar(max)								null,
	  ngayTao										Date										null,
	  ngaySua										Date										null,
	  trangThai									    int											null,

)
---create table bảng PhieuGiamGiaHoaDonChiTiet
create table PhieuGiamGiaHoaDonChiTiet
(

		Id_PhieuGiamGiaHoaDonChiTiet				UNIQUEIDENTIFIER DEFAULT NEWID()   PRIMARY KEY,
		Id_PhieuGiamGiaHoaDon						UNIQUEIDENTIFIER							  ,
		Id_HoaDon									UNIQUEIDENTIFIER							  ,
		tenPhieuGiamGiaHoaDonChiTiet				nvarchar(max)							    null,
		soTienHoaDonDaGiam							DECIMAL(20, 0) DEFAULT 0				    null,
		soLuongPhieu								int											null,								
		ghiChu										nvarchar(max)								null,
		ngayTao										Date										null,
	    ngaySua										Date										null,
	    trangThai									    int										null,

		Constraint FK_PhieuGiamGiaHoaDonChiTiet_PhieuGiamGiaHoaDon		Foreign key(Id_PhieuGiamGiaHoaDon) References PhieuGiamGiaHoaDon,
		Constraint FK_PhieuGiamGiaHoaDonChiTiet_HoaDon					Foreign key(Id_HoaDon) References HoaDon,
      
)
---create bảng Hóa Đơn Chi Tiết
create table HoaDonChiTiet
(
   
     Id_HoaDonChiTiet			UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	 Id_HoaDon					UNIQUEIDENTIFIER							,
	 Id_GiayTheThaoChiTiet		UNIQUEIDENTIFIER							,
	 soLuong					int										 null,
	 donGia						DECIMAL(20, 0) DEFAULT 0			     null,	
     ghiChu						nvarchar(max)							 null,
	 ngayTao				    DATETIME								 null,
	 ngaySua					DATETIME								 null,
	 trangThai					int										 null,

	 Constraint FK_HoaDonChiTiet_HoaDon							Foreign key(Id_HoaDon)								References HoaDon,
	 Constraint FK_HoaDonChiTiet_GiayTheThaoChiTiet				Foreign key(Id_GiayTheThaoChiTiet)				References GiayTheThaoChiTiet,

)

---create bảng Giỏ Hàng
create table GioHang
(
			
      Id_GioHang				UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	  Id_User					UNIQUEIDENTIFIER DEFAULT NEWID()			,
	  Id_KhachHang				UNIQUEIDENTIFIER DEFAULT NEWID()			,
	  ghiChu					nvarchar(max)							 null,
	  ngayTao					Date									 null,
	  ngaySua					Date									 null,
	  trangThai					int										 null,

	 Constraint FK_GioHang_Users						Foreign key(Id_User)						References Users,
	 Constraint FK_GioHang_KhachHang					Foreign key(Id_KhachHang)					References KhachHang,

)
---create bảng Giỏ hàng chi tiết
create table GioHangChiTiet
(
	
	 Id_GioHangChiTiet				UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	 Id_GiayTheThaoChiTiet			UNIQUEIDENTIFIER DEFAULT NEWID(),
	 Id_GioHang						UNIQUEIDENTIFIER DEFAULT NEWID(),
	 soLuong					    int										null,
	 donGia							DECIMAL(20, 0) DEFAULT 0			    null,
	 donGiaKhiGiam					DECIMAL(20, 0) DEFAULT 0			    null,	
	 ngayTao						Date									null,
	 ngaySua						Date									null,
	 trangThai					    int									    null,

	 Constraint FK_GioHangChiTiet_GiayTheThaoChiTiet					Foreign key(Id_GiayTheThaoChiTiet)					References GiayTheThaoChiTiet,
	 Constraint FK_GioHangChiTiet_GioHang								Foreign key(Id_GioHang)								References GioHang,

)

---create table bảng ví shop

create table ViTien
(
	 Id_ViTien						UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	 Id_KhachHang					UNIQUEIDENTIFIER DEFAULT NEWID()			,
	 maViTien					    nvarchar(max)							 null,
	 thanhTien						DECIMAL(20, 0) DEFAULT 0			     null,
	 ngayTao						DATETIME								 null,
	 ngaySua						DATETIME								 null,
	 trangThai						int										 null,

	Constraint FK_ViTien_KhachHang			Foreign key(Id_KhachHang)					References KhachHang,

)

---create bảng giao dịch ví shop
create table GiaoDichViChiTiet
(

	Id_GiaoDichViChiTiet				UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
	Id_ViTien							UNIQUEIDENTIFIER DEFAULT NEWID()			,
	Id_User								UNIQUEIDENTIFIER DEFAULT NEWID()			,
	maGiaoDichViChiTiet					nvarchar(max)							null,
    donGia								DECIMAL(20, 0) DEFAULT 0			    null,
	ngayGiaoDich					    DATETIME								null,
	hinhThuc							int										null,
	trangThai							int										null,

	Constraint FK_GiaoDichViChiTiet_ViTien			Foreign key(Id_ViTien)					References ViTien,
	Constraint FK_GiaoDichViChiTiet_User			Foreign key(Id_User)					References Users

)

select * from ViTien
select * from GiaoDichViChiTiet
select * from KhachHang
select * from GioHang
---insert dữ liệu

---insert dữ liệu bảng chương trình giảm giá hóa đơn
insert into ChuongTrinhGiamGiaHoaDon(tenChuongTrinh, phanTramGiam , soLuongSanPham , soTienHoaDon , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình khai xuân giảm giá hóa đơn',5,4,300000,'2023-09-27','2023-10-01',N'Chương trình giảm giá có hạn','2023-09-27',1)
insert into ChuongTrinhGiamGiaHoaDon(tenChuongTrinh, phanTramGiam , soLuongSanPham , soTienHoaDon , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình vui tết trung thu',10,6,200000,'2023-09-27','2023-10-01',N'Chương trình giảm giá có hạn','2023-09-27',1)
insert into ChuongTrinhGiamGiaHoaDon(tenChuongTrinh, phanTramGiam , soLuongSanPham , soTienHoaDon , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình vui tết thiếu nhi 1/6',20,4,300000,'2023-09-27','2023-10-01',N'Chương trình giảm giá có hạn','2023-09-27',1)
insert into ChuongTrinhGiamGiaHoaDon(tenChuongTrinh, phanTramGiam , soLuongSanPham , soTienHoaDon , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình khai chương quán',5,4,300000,'2023-09-27','2023-10-01',N'Chương trình giảm giá có hạn','2023-09-27',1)


select * from ChuongTrinhGiamGiaHoaDon
---delete from ChuongTrinhGiamGiaHoaDon where Id_ChuongTrinhGiamGiaHoaDon = '20750783-4D49-4C6E-8CEA-FD720529EA35'
---delete from ChuongTrinhGiamGiaHoaDon


---insert dữ liệu bảng chương trình giảm giá giầy thể thao
insert into ChuongTrinhGiamGiaGiayTheThao(tenChuongTrinhGiamGia, phanTramGiam , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình giảm giá giầy thể thao 1/6',5,'2023-09-27','2023-10-01',N'Chương trình giảm giá có hạn','2023-09-27',1)
insert into ChuongTrinhGiamGiaGiayTheThao(tenChuongTrinhGiamGia, phanTramGiam , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình giảm giá giầy thể thao 2/6',8,'2023-09-27','2023-10-01',N'','2023-09-27',1)
insert into ChuongTrinhGiamGiaGiayTheThao(tenChuongTrinhGiamGia, phanTramGiam , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình giảm giá giầy thể thao ngày 6/6',7,'2023-09-27','2023-10-01',N'','2023-09-27',0)
insert into ChuongTrinhGiamGiaGiayTheThao(tenChuongTrinhGiamGia, phanTramGiam , ngayBatDau , ngayKetThuc , ghiChu ,ngayTao, trangThai) values(N'Chương trình giảm giá giầy thể thao ngày vàng nhân đôi 1/1',10,'2023-09-27','2023-10-01',N'','2023-09-27',1)

select * from ChuongTrinhGiamGiaGiayTheThao
---delete from ChuongTrinhGiamGiaGiayTheThao where Id_ChuongTrinhGiamGiaGiayTheThao 
---delete from ChuongTrinhGiamGiaGiayTheThao



---insert dữ liệu bảng form
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form đẹp chất lượng cao', N'','2023-10-01','',1)
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form đẹp chất lượng', N'','2023-10-01','',1)
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form giầy đẹp cao cấp', N'','2023-10-01','',0)
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form đẹp chất cao cấp', N'','2023-10-01','',1)
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form đẹp mẫu mã đa dạng', N'','2023-10-01','',0)
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form đẹp mũi cao', N'','2023-10-01','',0)
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form giầy basic', N'','2023-10-01','',1)
insert into Form(tenForm,ghiChu,ngayTao,ngaySua,trangThai) values(N'Form mẫu mã đẹp, đứng chân', N'','2023-10-01','',1)

select * from Form

---insert dữ liệu bảng công dụng
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chống thấm mồ hôi chân',N'Tốt ốm chân','2023-10-02','',1)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chống thấm nước',N'','2023-10-05','',1)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chống trượt',N'Tốt ốm chân','2023-10-10','',0)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chống mướt không kích mũi chân',N'Tốt ốm chân','2023-10-02','',1)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chống va đập chấn thương đá bóng',N'Tốt ốm chân','2023-10-02','',0)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giảm thiểu sự va đập đau với đối phương',N'Tốt ốm chân','2023-10-02','',1)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Hỗ trợ sút bóng tầm xa',N'Tốt ốm chân','2023-10-20','',0)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Hỗ trợ đỡ bước 1 có cảm giác',N'Tốt ốm chân','2023-10-21','',1)
insert into CongDung(tenCongDung,ghiChu,ngayTao,ngaySua,trangThai) values(N'Tăng sự êm chân, giảm thiểu sự chấn thương',N'Tốt ốm chân','2023-10-02','',1)

select * from CongDung

---insert bảng chất liệu

insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu da Kangaroo',N'Chất liệu da sản phẩm này tốt','2023-12-12','',1)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu da bê',N'','2023-12-12','',0)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu da dê',N'Chất liệu da sản phẩm này tốt','2023-12-12','',0)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu da bò',N'Chất liệu cao cấp đắt tiền','2023-12-23','',1)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu da PU',N'Chất liệu da sản phẩm này tốt','2023-12-12','',1)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu da Microfiber',N'Chất liệu da cao cấp','2023-12-12','',0)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Nikeskin',N'Chất liệu da sản phẩm này tốt','2023-12-12','',1)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'FluroSkin',N'Chất liệu da sản phẩm này tốt','2023-12-12','',1)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu K-Leather',N'','2023-12-12','',0)
insert into ChatLieu(tenChatLieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Chất liệu Calfskin Leather',N'Đắt tiền','2023-12-12','',1)

select * from ChatLieu

---insert into bảng Hướng dẫn bảo quản
insert into HuongDanBaoQuan(tenHuongDanBaoQuan,ghiChu,ngayTao,ngaySua,trangThai) values(N'Để nơi khô thoáng','','2023-12-12','',1);
insert into HuongDanBaoQuan(tenHuongDanBaoQuan,ghiChu,ngayTao,ngaySua,trangThai) values(N'Tránh nơi ẩm mốc','','2023-12-12','',1);
insert into HuongDanBaoQuan(tenHuongDanBaoQuan,ghiChu,ngayTao,ngaySua,trangThai) values(N'Bảo quản giày thể thao trong hộp đựng giày.','','2023-12-12','',1);
insert into HuongDanBaoQuan(tenHuongDanBaoQuan,ghiChu,ngayTao,ngaySua,trangThai) values(N'Sử dụng sản phẩm hỗ trợ Bọt làm sạch. Xịt khử mùi. Xịt nano','','2023-12-12','',1);
insert into HuongDanBaoQuan(tenHuongDanBaoQuan,ghiChu,ngayTao,ngaySua,trangThai) values(N'Sử dụng giấy báo để giữ form giày','','2023-12-12','',1);
insert into HuongDanBaoQuan(tenHuongDanBaoQuan,ghiChu,ngayTao,ngaySua,trangThai) values(N'Không giặt giày thường xuyên và phơi giày dưới ánh nắng','','2023-12-12','',1);
insert into HuongDanBaoQuan(tenHuongDanBaoQuan,ghiChu,ngayTao,ngaySua,trangThai) values(N'Áp dụng các mẹo làm sạch giày','','2023-12-12','',1);

select * from HuongDanBaoQuan

---insert into bảng lót giầy
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy Mika','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy Miko','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy Kamito','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy Mizuno','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy Unisex','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy Ximo','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy thể thao 4D','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy thể thao 5D','Tốt','2021-12-12','',1)
insert into LotGiay(tenLotGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Lót giầy thể thao Xilones','Tốt','2021-12-12','',1)

select * from LotGiay

---insert into cổ giẩy
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy cao Mizuno','Ôm sát cổ chân','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy thấp Mizuno','Ôm khít cổ chân','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy trung Mizuno','','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy cao Simo','','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy cao Mika','Ôm sát cổ chân','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy thấp Mika','Ôm sát cổ chân','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy cao Miko','Ôm sát cổ chân','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy thấp Miko dưới gót','Ôm sát cổ chân','2023-12-12','',1)
insert into CoGiay(tenCoGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cổ giầy cao Mizuno 5D qua gót','Ôm sát cổ chân','2023-12-12','',1)

select * from CoGiay

---insert into bảng Đế giầy

insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh FG',N'Tốt','2021-12-02','',1)
insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh SG' ,N'Tốt','2021-12-2','',1)
insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh HG' ,N'Tốt','2021-12-2','',1)
insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh MG' ,N'Tốt','2021-12-2','',1)
insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh AG' ,N'Tốt','2021-12-2','',1)
insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh TF' ,N'Tốt','2021-12-2','',1)
insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh IC' ,N'Tốt','2021-12-2','',1)
insert into DeGiay(tenDeGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Giày đế đinh BC' ,N'Tốt','2021-12-2','',1)

select * from DeGiay

---insert into dây giầy

insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây Mizuno xanh','800mm','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây Mika ','1m','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây Miko ','600mm','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây Unisex ','600mm','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây Ximo ','850mm','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây 4D ','1m2','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây 6D ','300mm','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây cao cấp ','400mm','','','',1)
insert into DayGiay(tenDayGiay,chieuDaiDayGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Dây dây vải ','1m2','','','',1)

select * from DayGiay

---insert into trọng lượng

insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('140G',N'Nhẹ','2023-12-10','',1)
insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('150G',N'Nhẹ','2023-12-10','',1)
insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('150G',N'','2023-12-10','',1)
insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('160G',N'Nhẹ','2023-12-10','',1)
insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('190G',N'','2023-12-10','',1)
insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('170G',N'Nhẹ','2023-12-10','',1)
insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('180G',N'','2023-12-10','',1)
insert into TrongLuong(tenTrongLuong,ghiChu,ngayTao,ngaySua,trangThai) values('200G',N'','2023-12-10','',1)

select * from TrongLuong

---insert into thương hiệu 

insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Like',N'Cao cấp','2023-12-12','',1)
insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Mizuno',N'Cao cấp','2023-12-12','',1)
insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'GuGCi',N'Cao cấp','2023-12-12','',1)
insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Mika',N'Cao cấp','2023-12-12','',1)
insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Miko',N'Cao cấp','2023-12-12','',1)
insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Simo',N'Cao cấp','2023-12-12','',1)
insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kamito',N'Cao cấp','2023-12-12','',1)
insert into ThuongHieu(tenThuongHieu,ghiChu,ngayTao,ngaySua,trangThai) values(N'UNisex',N'Cao cấp','2023-12-12','',1)

select * from ThuongHieu

---insert dữ liệu bảng giầy thể thao


---insert into dữ liệu bảng màu sắc 
insert into MauSac(tenMauSac,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đỏ','','','','')
insert into MauSac(tenMauSac,ghiChu,ngayTao,ngaySua,trangThai) values(N'Cam','','','','')
insert into MauSac(tenMauSac,ghiChu,ngayTao,ngaySua,trangThai) values(N'Xanh','','','','')
insert into MauSac(tenMauSac,ghiChu,ngayTao,ngaySua,trangThai) values(N'Vàng','','','','')
insert into MauSac(tenMauSac,ghiChu,ngayTao,ngaySua,trangThai) values(N'Tím','','','','')
insert into MauSac(tenMauSac,ghiChu,ngayTao,ngaySua,trangThai) values(N'Hường','','','','')

---insert into dữ liệu bảng size
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('38','','','','')
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('39','','','','')
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('40','','','','')
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('41','','','','')
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('42','','','','')
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('43','','','','')
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('44','','','','')
insert into Size(size,ghiChu,ngayTao,ngaySua,trangThai) values('45','','','','')

---insert into dữ liệu bảng đinh tán giầy
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy 12cm loại vừa','','','',1)
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy cỡ lớn','','','',1)
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy cỡ vừa 6 mắt','','','',1)
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy 12 mắt đinh loại nhỏ','','','',1)
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy 12cm loại vừa','','','',1)
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy cao cấp GTA','','','',1)
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy loại trung phổ biến','','','',1)
insert into DinhTanGiay(tenDinhTanGiay,ghiChu,ngayTao,ngaySua,trangThai) values(N'Đinh tán giầy Kio 10 mắt','','','',1)

select * from DinhTanGiay

---insert into bảng kiểu buộc
insert into KieuBuoc(tenKieuBuoc,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kiểu thắt dây giày đá bóng chéo','','','',1)
insert into KieuBuoc(tenKieuBuoc,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kiểu thắt xỏ dây nút chạy','','','',1)
insert into KieuBuoc(tenKieuBuoc,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kiểu thắt dây giày giấu nút','','','',1)
insert into KieuBuoc(tenKieuBuoc,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kiểu buộc dây giày cố định lòng bàn chân','','','',1)
insert into KieuBuoc(tenKieuBuoc,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kiểu thắt dây giày Ladder Lacing','','','',1)
insert into KieuBuoc(tenKieuBuoc,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kiểu buộc thắt dây chéo vắt lên trên','','','',1)
insert into KieuBuoc(tenKieuBuoc,ghiChu,ngayTao,ngaySua,trangThai) values(N'Kiểu buộc giây giày theo phương pháp giấu dây','','','',1)

select * from KieuBuoc

select * from Users
---insert dữ liệu cho bảng giầy thể thao
insert into GiayTheThao(Id_Form,Id_CongDung,Id_ChatLieu,Id_HuongDanBaoQuan,Id_LotGiay,Id_CoGiay,Id_DeGiay,Id_DayGiay,Id_TrongLuong,Id_ThuongHieu,Id_KieuBuoc,Id_DinhTanGiay,gioiTinh,tenGiayTheThao,giaBan,moTa,ghiChu,trangThai) values('B151C8E4-BE9F-49D0-8C82-069C8EBA5259','6491BB3D-0B2E-431B-B178-16136E844CFE','550BE6F5-C382-4997-B2DD-1356F644B1EA','D06E068B-798E-4F66-B61B-3A3BFB4B671C','BFB447C5-5CA3-46CD-9759-13485C963711','27C73101-07E0-43B4-A494-0F16B543C8F1','709B5E0C-73A3-4357-A484-0DD68523C251','B84A955C-944E-4634-A089-0562616F035F','761F9DB7-6D7A-4342-B8CF-1A3A235E9A45','EFBD5AAC-BCCF-405D-81B3-05DD9449283C','09A47446-BAA1-43F0-B09A-277FC8D4EF7F','D5D61594-AC5D-4F80-8E54-0B8EE4E0B345','Nam',N'Giầy đá bóng sân NIKE ZOOM',12000000,'','',1)

insert into GiayTheThao(Id_Form,Id_CongDung,Id_ChatLieu,Id_HuongDanBaoQuan,Id_LotGiay,Id_CoGiay,Id_DeGiay,Id_DayGiay,Id_TrongLuong,Id_ThuongHieu,Id_KieuBuoc,Id_DinhTanGiay,gioiTinh,tenGiayTheThao,giaBan,moTa,ghiChu,trangThai) values(
'E4F933C1-E365-4CD9-8A57-151C845EC7EF',
'6686F069-BEA2-4EF7-ABA6-32808E99CC0D',
'06768B93-FDA5-4007-8E84-7E4B22F4103B',
'F4AE36A9-6982-42C3-B47A-442E21F81733',
'E23DE32A-539F-4D16-955E-19C0077CB912',
'5B78977B-2922-4DA2-8AE5-1C0467B74CC7',
'A8C46524-6F4B-42A0-B25A-4B9EFCBE2F07',
'83E37B17-2440-4B68-A4DD-929152EFAE2D',
'AECD46E6-EBB8-4761-BC35-9D19CA66721D',
'99838E11-5EB8-40BF-ADB6-4FF1E046017C',
'6A98EF23-E86E-4D1E-A428-5392B22AB26F',
'679C8270-9A9C-4BC2-B4BF-A5B7606ABA63',
'Nữ',N'Giầy đá bóng tầm trung Mizuno 3 sọc',
15000000,
'',
'',
1)



SELECT SUM(hd.thanhTien) FROM HoaDon hd WHERE hd.trangThai = 3
select sum(hd.thanhTien) from HoaDon hd
SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 3 

SELECT SUM(h.soLuong * h.donGia) FROM HoaDonChiTiet h
SELECT SUM(sp.soLuong) FROM GiayTheThaoChiTiet sp



select * from MauSac
select * from Image
select * from GioHang
select * from Size
select * from MauSac
select * from GiayTheThao 
select * from GiayTheThaoChiTiet 

select * from KhachHang
select * from HoaDon
select * from GiayTheThaoChiTiet
select * from HoaDonChiTiet
select * from ViTien
select * from GiaoDichViChiTiet
select * from GioHang
select * from GioHangChiTiet
select * from HoaDon
select * from HoaDonChiTiet

delete from GioHangChiTiet
delete from HoaDon
delete from HoaDonChiTiet

select * from GiaoDichViChiTiet
select * from ViTien

select * from GiayTheThaoChiTiet
select * from Users
select * from PhieuGiaoHang
select * from KhachHang
select * from Users


--- 1 là trạng thái đã được kích hoạt
--- 0 là trạng thái chưa được kích hoạt

select * from ChuongTrinhGiamGiaChiTietGiayTheThao
select * from ChuongTrinhGiamGiaGiayTheThao

---Ghi chú
/*
  Trạng thái của chương trình giảm giá giầy thể thao 

   1		: là còn hạn
  -1		: là hết hạn
*/

select * from ChuongTrinhGiamGiaHoaDon


/*

			delete from GiayTheThao 
			delete from GiayTheThaoChiTiet
			delete from Image
			delete from GioHang 
			delete from GioHangChiTiet where Id_GiayTheThaoChiTiet = 'A4FA1D36-6F37-4BDC-82DA-5BA961052725'
			delete from KhachHang where Id_KhachHang = 'E4FDA8D1-0099-462B-A0DB-F7545A786B4E'
			delete from GioHang where Id_GioHang = '51DE497D-9652-4B0C-B5A2-AE0AC5E99988'
			delete from HoaDon
			delete from HoaDonChiTiet
			delete from Users
			delete from ChuongTrinhGiamGiaChiTietGiayTheThao
			delete from ChuongTrinhGiamGiaGiayTheThao
			delete from ChuongTrinhGiamGiaChiTietHoaDon
			delete from ChuongTrinhGiamGiaHoaDon
			delete from ViTien
			delete from GiaoDichViChiTiet
 
*/


/*

   Chú thích:

   Hóa đơn sẽ có các trạng thái 

   Hình thức (Mua hàng) --- hinhThuc
				 
				  0 : là oneline
			      1 : là tại quầy

  Hình thức thanh toán(Hình thức thanh toán)   --- hinhThucThanhToan
				
				  0: là thanh toán tại quầy
				  1: là thanh toán bằng ví điện tử
				  2: là thanh toán bằng momo
				  3: là thanh toán khi nhận hàng

   TrangThaiMoney (Trạng thái thanh toán) ---trangThaiMoney

			      0	: là chưa thanh toán
				  1	: là đã thanh toán

   Trạng thái (Trạng thái đơn hàng)  ---trangThai
			
				  0: là chưa thanh toán xong
				  1: là chờ xác nhận
				  2: là đang đóng gói hàng
				  3: là đang giao
				  4: là giao hàng thành công
				  5: là hủy đơn hàng

*/
