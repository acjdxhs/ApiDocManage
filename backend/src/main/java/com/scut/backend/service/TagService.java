package com.scut.backend.service;

import com.scut.backend.dao.TagMapper;
import com.scut.backend.model.Result;
import com.scut.backend.model.Tag;
import com.scut.backend.uits.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 获取标签
     * @param uid
     * @return
     */
    public Result getTagByUid(int uid) {
        try {
            Tag[] result = tagMapper.getTagByUid(uid);
            return ResultUtil.returnGoodResult(result);
        }catch (Exception e) {
            return ResultUtil.returnBadResult("查询标签异常");
        }
    }

    /**
     * 插入标签
     * @param tag
     * @return
     */
    public Result insertTag(Tag tag) {
        try {
            int result = tagMapper.insertTag(tag);
            return ResultUtil.returnGoodResult(result);
        }catch (Exception e) {
            return ResultUtil.returnBadResult("插入标签异常");
        }
    }

    /**
     * 删除标签
     * @param uid
     * @param name
     * @return
     */
    public Result deleteTag(int uid, String name) {
        try {
            int result = tagMapper.deleteTag(uid, name);
            return ResultUtil.returnGoodResult(result);
        }catch (Exception e) {
            return ResultUtil.returnBadResult("删除标签异常");
        }
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    public Result updateTag(Tag tag) {
        try {
            int result = tagMapper.updateTag(tag);
            return ResultUtil.returnGoodResult(result);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.returnBadResult("更新标签异常");
        }
    }
}
