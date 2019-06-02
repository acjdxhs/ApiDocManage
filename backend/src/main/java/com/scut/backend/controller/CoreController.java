package com.scut.backend.controller;

import com.scut.backend.model.Result;
import com.scut.backend.service.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "core")
public class CoreController {

    @Autowired
    private CoreService coreService;

    @RequestMapping("toCode")
    public Result toCode() {
        return coreService.toCode();
    }

    @PostMapping("toApi")
    public Result toApi(@RequestParam String path) {
        return coreService.toApi(path);
    }
}
