package org.learn.service;


import org.learn.entity.MemberDO;
import org.learn.entity.MemberPasswordDO;

public interface MemberService {

    boolean register(MemberDO memberDO, MemberPasswordDO memberPasswordDO) throws Exception;
}
