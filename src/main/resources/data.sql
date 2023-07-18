insert into user(user_id, nickname, email, password) values(1, "admin", "admin@test.com", "1234");
insert into user(user_id, nickname, email, password) values(2, "test", "test@test.com", "1234");
insert into user(user_id, nickname, email, password) values(3, "test2", "test2@test.com", "1234");


insert into notification(notification_id, target_id, sender_id, receiver_id, type, detail) values(1, 1, 1, 2, "COMMENT","글제목~" );
insert into notification(notification_id, target_id, sender_id, receiver_id, type) values(2, 1, 1, 2, "MESSAGE");
insert into notification(notification_id, target_id, sender_id, receiver_id, type, detail) values(3, 1, 1, 3, "ACCOMPANY", "동행제목~");

insert into notification(notification_id, target_id, sender_id, receiver_id, type, detail) values(4, 5, 3, 2, "COMMENT", "글제목2");
insert into notification(notification_id, target_id, sender_id, receiver_id, type) values(5, 5, 3, 2, "MESSAGE");
insert into notification(notification_id, target_id, sender_id, receiver_id, type) values(6, 5, 3, 2, "REPLYCOMMENT");