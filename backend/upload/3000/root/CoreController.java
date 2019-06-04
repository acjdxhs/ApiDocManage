package com.scut.backend.controller;
import com.alibaba.fastjson.JSONObject;
import com.scut.backend.model.ApiNodeResult;
import com.scut.backend.model.Result;
import com.scut.backend.service.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

	/**
	* <CoreController nodeDesc="">
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	* </CoreController>
	* */
@RestController
@RequestMapping(value = "core")
public class CoreController {

    @Autowired
    private CoreService coreService;

    /**
     * <xml></xml>
     * @param path
     * @param apiNode
     * @return
     */
	/**
	* <toCode nodeDesc="">
	*     <Method nodeDesc="方法" method=""/>
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	*     <Parameters nodeDesc="参数">
	*         <Parameter nodeDesc="" name="path" type="String" desc=""/>
	*         <Parameter nodeDesc="" name="apiNode" type="String" desc=""/>
	*     </Parameters>
	*     <Responses nodeDesc="响应" type="Result" desc=""/>
	* </toCode>
	* */
    @PostMapping("toCode")
    public Result toCode(@RequestParam String path, @RequestParam String apiNode) {
        ApiNodeResult res = JSONObject.parseObject(apiNode, ApiNodeResult.class);
        return coreService.toCode(path, res);
    }

	/**
	* <toApi nodeDesc="">
	*     <Method nodeDesc="方法" method=""/>
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	*     <Parameters nodeDesc="参数">
	*         <Parameter nodeDesc="" name="path" type="String" desc=""/>
	*     </Parameters>
	*     <Responses nodeDesc="响应" type="Result" desc=""/>
	* </toApi>
	* */
    @PostMapping("toApi")
    public Result toApi(@RequestParam String path) {
        return coreService.toApi(path);
    }
}
