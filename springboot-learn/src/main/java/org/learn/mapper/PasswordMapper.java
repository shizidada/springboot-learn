package org.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.learn.bean.MemberPasswordBO;

@Mapper
public interface PasswordMapper extends BaseMapper<MemberPasswordBO> {
}
