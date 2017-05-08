insert into user values(null,'test1','test1','test1');
insert into user values(null,'test2','test2','test2');
insert into user values(null,'test3','test3','test3');
insert into user values(null,'test3','test3','test3');

insert into chat_room values(null,'java','讨论java的地方');
insert into chat_room values(null,'python','讨论python的地方');
insert into chat_room values(null,'golang','讨论golang的地方');

-- java聊天室
insert into user_chatroom_rela values(1,1);
insert into user_chatroom_rela values(2,1);
insert into user_chatroom_rela values(3,1);
insert into user_chatroom_rela values(4,1);

-- python聊天室
insert into user_chatroom_rela values(1,2);
insert into user_chatroom_rela values(2,2);



INSERT into user_relationship values(1,2);
INSERT into user_relationship values(1,3);
INSERT into user_relationship values(1,4);

INSERT into user_relationship values(2,1);
INSERT into user_relationship values(2,3);

INSERT into user_relationship values(3,2);

