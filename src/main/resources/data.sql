insert into user(user_id, nickname, email, password) values(1, "admin", "admin@test.com", "1234");
insert into user(user_id, nickname, email, password) values(2, "test", "test@test.com", "1234");
insert into user(user_id, nickname, email, password) values(3, "test2", "test2@test.com", "1234");
insert into user(user_id, nickname, email, password, telephone, profile_image_url) values(4, "kim", "qaws2051@test.com", "123123123", "01012341234", "https://ecogging-kosta.s3.ap-northeast-2.amazonaws.com/profile/profile-default.png");


insert into notification(notification_id, target_id, sender_id, receiver_id, type, detail, is_read) values(1, 1, 1, 2, "COMMENT","글제목~", 0);
insert into notification(notification_id, target_id, sender_id, receiver_id, type, is_read) values(2, 1, 1, 2, "MESSAGE",0);
insert into notification(notification_id, target_id, sender_id, receiver_id, type, detail, is_read) values(3, 1, 1, 3, "ACCOMPANY", "동행제목~",0);

insert into notification(notification_id, target_id, sender_id, receiver_id, type, detail, is_read) values(4, 5, 3, 2, "COMMENT", "글제목2",0);
insert into notification(notification_id, target_id, sender_id, receiver_id, type, is_read) values(5, 5, 3, 2, "MESSAGE",0);
insert into notification(notification_id, target_id, sender_id, receiver_id, type, is_read) values(6, 5, 3, 2, "REPLYCOMMENT",0);
