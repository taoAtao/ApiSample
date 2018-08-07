package com.example.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @PostMapping(value = "/queryInfo")
    public String queryInfo(@RequestParam("name") String name) {
        return "根据Name返回Log："+name;
    }
    
    @DeleteMapping(value = "/delete")
    public String deleteInfo(@RequestParam("name") String name) {
        return "根据Name删除Log："+name;
    }
    
}