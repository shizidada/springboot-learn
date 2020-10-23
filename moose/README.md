#### moose operator service

#### TODO
- 登录方式校验 (AOP ValueIn annotation) ✅
- 判断是否已经登录，在调用登录接口时，应直接返回登录信息
- 是否频繁调用登录接口
- 


```xml
  <select id="selectDynamicRecordWithAssociationInfo" resultMap="BaseResultMap">
    SELECT
      dynamic_record.dr_id dr_id,
      dynamic_record.user_id user_id,
      dynamic_record.title,
      dynamic_record.content,
      dynamic_record.create_time,
      attachment_record.file_url file_url,
      attachment_record.attach_id attach_id,
      attachment_record.e_tag e_tag
    FROM
      t_dynamic_record dynamic_record,
      t_dynamic_record_attachment_relation dynamic_record_attachment_relation,
      t_attachment_record attachment_record
    WHERE
      dynamic_record.dr_id = dynamic_record_attachment_relation.dr_id
      AND dynamic_record_attachment_relation.attach_id = attachment_record.attach_id
    ORDER BY
      dynamic_record.create_time DESC
  </select>
```
