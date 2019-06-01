package com.scut.backend.controller;

import com.scut.backend.model.Result;
import com.scut.backend.model.Tag;
import com.scut.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "add")
    public Result addTag(@RequestBody Tag tag) {
        return tagService.insertTag(tag);
    }

    @GetMapping("delete")
    public Result deleteTag(@RequestParam Integer uid, @RequestParam String name) {
        return tagService.deleteTag(uid, name);
    }

    @PostMapping("update")
    public Result updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

    @GetMapping("get")
    public Result getTag(@RequestParam Integer uid) {
        return tagService.getTagByUid(uid);
    }
}
