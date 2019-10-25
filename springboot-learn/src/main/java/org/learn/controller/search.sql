-- select a.* from t_excel_info a, t_excel_info b where a.receiver != b.receiver and a.phone != b.phone and a.address != b.address order by receiver

-- select a.iccid, a.operators, a.receiver, a.phone, a.address from t_excel_info a, t_excel_info b where a.receiver = b.receiver and a.phone =
-- b.phone and a.address = b.address order by receiver

-- select * from t_user u inner join t_address a on u.id = a.user_id;

-- select a.iccid, a.operators, a.receiver, a.phone, a.address from t_excel_info a inner join t_excel_info b on a.receiver = b.receiver and a.phone = b.phone and a.address = b.address order by receiver

-- select t1.* from t_excel_info t1, t_excel_info t2 where t1.receiver = t2.receiver and t1.phone = t2.phone and t1.address = t2.address

-- select count(receiver) from t_excel_info

-- select * from t_excel_info where receiver in (select receiver from t_excel_info group by receiver having count(receiver) > 1) group by receiver

-- select receiver, count(receiver) from t_excel_info group by receiver having count(receiver) > 1

-- select * from t_excel_info where receiver in (select receiver from t_excel_info group by receiver having count(receiver) > 1) group by receiver

select * from t_excel_info a where (a.receiver,a.phone,a.address) in (select receiver,phone,address from t_excel_info group by receiver,phone,address having count(*) > 1) order by receiver -- group by receiver

-- select * from t_excel_info a where (a.receiver,a.phone,a.address) in (select receiver,phone,address from t_excel_info group by receiver,phone,address having count(*) <= 1) order by receiver -- group by receiver

