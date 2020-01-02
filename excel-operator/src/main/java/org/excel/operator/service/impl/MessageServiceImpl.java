package org.excel.operator.service.impl;

import com.mongodb.client.result.UpdateResult;
import java.util.Date;
import org.excel.operator.mongo.entity.Message;
import org.excel.operator.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author taohua
 */
@Service
public class MessageServiceImpl implements MessageService {

  private static final Integer MESSAGE_IS_READ = 1;

  private static final Integer MESSAGE_UN_READ = 2;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override public Message saveMessage(Message message) {
    return this.mongoTemplate.save(message);
  }

  /**
   * 根据id更新会话是否已读状态
   */
  @Override
  public UpdateResult updateMessageState(String messageId, Integer status) {

    Query query = Query.query(Criteria.where("id").is(messageId));
    Update update = Update.update("is_read", status);
    // 更新发送时间
    if (MESSAGE_IS_READ.equals(status)) {
      update.set("send_date", new Date());
      // 更新阅读时间
    } else if (MESSAGE_UN_READ.equals(status)) {
      update.set("read_date", new Date());
    }
    return this.mongoTemplate.updateFirst(query, update, Message.class);
  }
}
