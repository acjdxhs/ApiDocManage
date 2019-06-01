package com.scut.backend.service;

import com.scut.backend.dao.UserMapper;
import com.scut.backend.model.Result;
import com.scut.backend.model.User;
import com.scut.backend.uits.RedisUtil;
import com.scut.backend.uits.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param user
     * @return
     */
    public Result login(User user) {
        User old = userMapper.getUserByAccount(user.getAccount());
        if (old == null) {
            return ResultUtil.returnBadResult("账号不存在");
        }
        if (!old.getPassword().equals(user.getPassword())) {
            return ResultUtil.returnBadResult("账号密码不匹配");
        }
        old.setPassword(null);
        String token = RedisUtil.setTokenSession(old.getUid());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("uid", old.getUid());
        map.put("account", old.getAccount());
        return ResultUtil.returnGoodResult(map);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public Result signUp(User user) {
        User old = userMapper.getUserByAccount(user.getAccount());
        if (old != null) {
            return ResultUtil.returnBadResult("账号已存在");
        }
        try{
            int result = userMapper.insertUser(user);
            return ResultUtil.returnGoodResult(result);
        }catch (Exception e) {
            return ResultUtil.returnBadResult("注册失败");
        }
    }
}
