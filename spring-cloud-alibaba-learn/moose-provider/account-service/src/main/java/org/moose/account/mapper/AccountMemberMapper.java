package org.moose.account.mapper;

import org.account.model.dto.AccountDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/11 22:56
 * @see org.moose.account.mapper
 */
@Mapper
public interface AccountMemberMapper {

  AccountDTO findAccountById(String accountId);
}
