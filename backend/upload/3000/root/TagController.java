package com.scut.backend.controller;

import com.scut.backend.model.Result;
import com.scut.backend.model.Tag;
import com.scut.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


	/**
	* <TagController nodeDesc="">
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	* </TagController>
	* */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 新增标签
     *
     * @param tag
     * @return
     */
	/**
	*
	* */
	/**
	* <addTag nodeDesc="">
	*     <Method nodeDesc="方法" method=""/>
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	*     <Parameters nodeDesc="参数">
	*         <Parameter nodeDesc="" name="tag" type="Tag" desc=""/>
	*     </Parameters>
	*     <Responses nodeDesc="响应" type="Result" desc=""/>
	* </addTag>
	* */
    @PostMapping(value = "add")
    public Result addTag(@RequestBody Tag tag) {
        return tagService.insertTag(tag);
    }

	/**
	*
	* */
	/**
	* <deleteTag nodeDesc="">
	*     <Method nodeDesc="方法" method=""/>
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	*     <Parameters nodeDesc="参数">
	*         <Parameter nodeDesc="" name="uid" type="Integer" desc=""/>
	*         <Parameter nodeDesc="" name="name" type="String" desc=""/>
	*     </Parameters>
	*     <Responses nodeDesc="响应" type="Result" desc=""/>
	* </deleteTag>
	* */
    @GetMapping("delete")
    public Result deleteTag(@RequestParam Integer uid, @RequestParam String name) {
        return tagService.deleteTag(uid, name);
    }

	/**
	*
	* */
	/**
	* <updateTag nodeDesc="">
	*     <Method nodeDesc="方法" method=""/>
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	*     <Parameters nodeDesc="参数">
	*         <Parameter nodeDesc="" name="tag" type="Tag" desc=""/>
	*     </Parameters>
	*     <Responses nodeDesc="响应" type="Result" desc=""/>
	* </updateTag>
	* */
    @PostMapping("update")
    public Result updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

	/**
	*
	* */
	/**
	* <getTag nodeDesc="">
	*     <Method nodeDesc="方法" method=""/>
	*     <Server nodeDesc="服务器" uri=""/>
	*     <Path nodeDesc="路径" path=""/>
	*     <Parameters nodeDesc="参数">
	*         <Parameter nodeDesc="" name="uid" type="Integer" desc=""/>
	*     </Parameters>
	*     <Responses nodeDesc="响应" type="Result" desc=""/>
	* </getTag>
	* */
    @GetMapping("get")
    public Result getTag(@RequestParam Integer uid) {
        return tagService.getTagByUid(uid);
    }
}
