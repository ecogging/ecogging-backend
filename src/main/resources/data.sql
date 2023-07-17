insert into user(user_id, nickname, email, password, last_received_noti_id) values(1, "admin", "admin@test.com", "1234", 0);
insert into user(user_id, nickname, email, password, last_received_noti_id) values(2, "test", "test@test.com", "1234",0);
insert into user(user_id, nickname, email, password, last_received_noti_id) values(3, "test2", "test2@test.com", "1234",0);


insert into notification(notification_id, target_id, sender_id, receiver_id, type) values(1, 1, 1, 2, "COMMENT");
insert into notification(notification_id, target_id, sender_id, receiver_id, type) values(2, 1, 1, 2, "COMMENT");
insert into notification(notification_id, target_id, sender_id, receiver_id, type) values(3, 1, 1, 3, "COMMENT");