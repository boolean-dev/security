package com.tao.security.core.controller;

import com.tao.security.core.result.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SecurityController
 * @Descriiption 测试Controller
 * @Author yanjiantao
 * @Date 2019/7/8 10:18
 **/
@Controller
public class SecurityController {

    @ResponseBody
    @GetMapping("/user/{id}")
    public Object user(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        return result;

    }

    @GetMapping("/")
    public String toIndex() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/user/signIn")
    public String signId() {
        return "signIn";
    }

    @GetMapping("/user/me")
    @ResponseBody
    public Result me(@AuthenticationPrincipal UserDetails user) {
        return Result.successMsg(user);
    }
}
