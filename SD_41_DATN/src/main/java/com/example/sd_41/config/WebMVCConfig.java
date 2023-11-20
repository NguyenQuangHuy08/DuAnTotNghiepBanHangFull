package com.example.sd_41.config;

import com.example.sd_41.filter.LogGinInterceptor;
import com.example.sd_41.filter.adminInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

	//Cấu hình cho file ảnh
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/");	
        registry
        .addResourceHandler("/upload/**")
        .addResourceLocations("/upload/");
    }


    //Todo code quên mật khẩu khách hàng



    //Todo code phân quyền cho bên Admin

    @Autowired
    LogGinInterceptor logGinInterceptor;

	@Autowired
    adminInterceptor adminInterceptor;

	public void addInterceptors(InterceptorRegistry registry){

	    //Member
        registry.addInterceptor(logGinInterceptor)
                .addPathPatterns(
                        "/CoGiay/list",
                        "/CoGiay/view-create",
                        "/kieuBuoc/hien-thi",
                        "/kieuBuoc/view-add",
                        "/chatLieu/hien-thi",
                        "/chatLieu/view-add",
                        "/congDung/hien-thi",
                        "/congDung/view-add",
                        "/dayGiay/hien-thi",
                        "/dayGiay/view-add",
                        "/deGiay",
                        "/deGiay/create",
                        "/dinhTanGiay/hien-thi",
                        "/dinhTanGiay/view-add",
                        "/thuongHieu",
                        "/thuongHieu/create",
                        "/trongLuong/hien-thi",
                        "/trongLuong/view-add",
                        "/form/hien-thi",
                        "/form/view-add",
                        "/HuongDanBaoQuan/list",
                        "/HuongDanBaoQuan/create",
                        "/KhachHang/list",
                        "/KhachHang/view-create"
                        );


        //Dành cho Admin
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns(
                        //Todo gắn link check phân quyền
                        "/CoGiay/list",
                        "/CoGiay/view-create",
                        "/kieuBuoc/hien-thi",
                        "/kieuBuoc/view-add",
                        "/chatLieu/hien-thi",
                        "/chatLieu/view-add",
                        "/congDung/hien-thi",
                        "/congDung/view-add",
                        "/dayGiay/hien-thi",
                        "/dayGiay/view-add",
                        "/deGiay",
                        "/deGiay/create",
                        "/dinhTanGiay/hien-thi",
                        "/dinhTanGiay/view-add",
                        "/thuongHieu",
                        "/thuongHieu/create",
                        "/trongLuong/hien-thi",
                        "/trongLuong/view-add",
                        "/form/hien-thi",
                        "/form/view-add",
                        "/HuongDanBaoQuan/list",
                        "/HuongDanBaoQuan/create",
                        "/user/view-add",
                        "/user/hien-thi",
                        "/KhachHang/list",
                        "/KhachHang/view-create"

                        );

    }






}
