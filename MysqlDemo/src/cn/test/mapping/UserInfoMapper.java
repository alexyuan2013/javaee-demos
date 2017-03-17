package cn.test.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.test.entity.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    UserInfo selectByName(@Param(value="name")String username);
    
    List<UserInfo> selectAll();
    
}