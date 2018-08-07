package com.example.Service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("log-service")
public interface LogService {
    @PostMapping(value = "/log/queryInfo")
    public String queryInfo(@RequestParam("name") String name);
    
    @DeleteMapping(value = "/log/delete")
    public String deleteInfo(@RequestParam("name") String name);

}