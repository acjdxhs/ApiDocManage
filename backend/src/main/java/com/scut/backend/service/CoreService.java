package com.scut.backend.service;

import com.scut.backend.core.CodeGenModule;
import com.scut.backend.core.ScanCodeModule;
import com.scut.backend.model.ApiNodeResult;
import com.scut.backend.model.Result;
import com.scut.backend.uits.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoreService {

    @Autowired
    private ScanCodeModule scanCodeModule;
    @Autowired
    private CodeGenModule codeGenModule;

    public Result toCode(String path, ApiNodeResult apiNodeResult) {
        return codeGenModule.apiToCode(path, apiNodeResult);
    }

    public Result toApi(String path) {
        ApiNodeResult data = scanCodeModule.codeToApi(path);
        if (data == null) {
            return ResultUtil.returnBadResult("TO API失败");
        } else {
            return ResultUtil.returnGoodResult("TO API 成功", data);
        }
    }
}
