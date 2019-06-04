package com.scut.backend.dao;

import com.scut.backend.model.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TagMapper {

    @Select("select uid,name,`desc`,attribute,children from tag where uid=#{uid};")
    Tag[] getTagByUid(@Param("uid") int uid);

    @Select("select * from tag where uid=#{uid} and name=#{name}")
    Tag getTagByName(@Param("uid") int uid, @Param("name") String name);

    @Insert("insert into tag (uid, name, `desc`, attribute, children) " +
            "values(#{tag.uid}, #{tag.name},#{tag.desc},#{tag.attribute},#{tag.children});")
    int insertTag(@Param("tag") Tag tag);

    @Update("update tag set  `desc`=#{tag.desc}, attribute=#{tag.attribute}, children=#{tag.children}" +
            "where uid=#{tag.uid} and name=#{tag.name};")
    int updateTag(@Param("tag") Tag tag);

    @Delete("delete from tag where uid=#{uid} and  name=#{name}")
    int deleteTag(@Param("uid") int uid, @Param("name") String name);
}
