#### moose operator service

#### TODO
- 登录方式校验 (AOP ValueIn annotation) ✅
- 判断是否已经登录，在调用登录接口时，应直接返回登录信息
- 是否频繁调用登录接口
- 


```mysql
  SELECT
      dynamic_record.dr_id dr_id,
      dynamic_record.user_id user_id,
      dynamic_record.title,
      dynamic_record.content,
      dynamic_record.create_time,
      file_record.file_url file_url,
      file_record.fr_id fr_id,
      file_record.e_tag e_tag
    FROM
      t_dynamic_record dynamic_record,
      t_dynamic_record_attachment_relation dynamic_record_attachment_relation,
      t_file_record file_record
    WHERE
      dynamic_record.dr_id = dynamic_record_attachment_relation.dr_id
      AND dynamic_record_attachment_relation.fr_id = file_record.fr_id
      AND dynamic_record.user_id=#{userId}
    ORDER BY
      dynamic_record.create_time DESC
```
