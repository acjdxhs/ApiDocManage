package com.scut.backend.dao;

import com.scut.backend.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select * from user where account=#{account}")
    User getUserByAccount(@Param("account") String account);

    @Select("select * from user where uid=#{uid}")
    User getUserByUid(@Param("uid") int uid);

    @Insert("insert into user(account, password) values(#{account}, #{password})")
    int insertUser(User user);
}
