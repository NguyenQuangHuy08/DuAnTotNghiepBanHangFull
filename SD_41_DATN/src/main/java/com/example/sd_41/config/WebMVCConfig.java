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
                .addPathPatterns("");


        //Dành cho Admin
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns(
                        //Todo gắn link check phân quyền
                        "/GiayTheThao/create"
//                        "/khach-hang/edit/{maKH}"


                        );

    }






}
