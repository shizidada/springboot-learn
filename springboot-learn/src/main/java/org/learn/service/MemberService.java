package org.learn.service;

import org.learn.bean.MemberBO;
import org.learn.bean.MemberPasswordBO;

public interface MemberService {

    boolean register(MemberBO memberBO, MemberPasswordBO passwordBO) throws Exception;
}
