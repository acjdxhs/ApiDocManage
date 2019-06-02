package com.scut.backend.service;

import com.scut.backend.core.MyDoclet;
import com.scut.backend.core.ScanCodeModule;
import com.scut.backend.model.ApiNodeResult;
import com.scut.backend.model.Result;
import com.scut.backend.uits.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CoreService {

    @Autowired
    private ScanCodeModule scanCodeModule;

    public Result toCode() {
        return null;
    }

    public Result toApi(String path){
        scanCodeModule.execute(path);
        ApiNodeResult data = MyDoclet.getResult();
        if (data == null) {
            return ResultUtil.returnBadResult("TO API失败");
        }else {
            return ResultUtil.returnGoodResult("TO API 成功", data);
        }
    }
}
