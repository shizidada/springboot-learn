package org.excel.operator.web.service;

import com.mongodb.client.result.UpdateResult;
import org.excel.operator.mongo.entity.Message;

public interface MessageService {

  Message saveMessage(Message message);

  UpdateResult updateMessageState(String messageId, Integer status);
}
