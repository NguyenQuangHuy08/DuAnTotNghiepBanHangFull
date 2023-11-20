package com.example.sd_41.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrangChuLogController {

    @RequestMapping(value = "/TrangChu/Admin/home")
    public String homeAdmin(){

        return "/TrangChuAdmin/home";

    }


}
