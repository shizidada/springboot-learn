package org.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.learn.bean.MemberBO;

/**
 * 会员 mapper
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberBO> {

    /**
     * 根据 NickName 查询是否存在相同 NickName
     *
     * @param memberBO
     * @return
     */
    MemberBO selectMemberByNickName(MemberBO memberBO);

    /**
     * 根据 UserName 查询是否存在相同 UserName
     *
     * @param memberBO
     * @return
     */
    MemberBO selectMemberByUserName(MemberBO memberBO);
}
