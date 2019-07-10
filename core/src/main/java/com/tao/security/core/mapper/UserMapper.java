package com.tao.security.core.mapper;

import com.tao.security.core.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.Mapping;

/**
 * @ClassName User
 * @Descriiption 用户
 * @Author yanjiantao
 * @Date 2019/7/10 13:56
 **/
@Mapper
public interface UserMapper {

    /**
     * 通过用户名查找用户
     * @param username  username
     * @return  user
     */
    @Select("select * from tb_user where username = #{username}")
    User findByUserName(String username);
}
