CREATE TABLE user(
  uid INT auto_increment,
  username VARCHAR (50),
  password VARCHAR(128),
  nickname VARCHAR(100),

  PRIMARY KEY(uid)
);

-- 聊天室表

CREATE TABLE chat_room(
  ctid INT auto_increment,
  name VARCHAR (128),
  description VARCHAR (1000),

  PRIMARY KEY (ctid)
);

-- 用户关系表
CREATE TABLE user_relationship(
  uid INT,
  friendid INT,

  PRIMARY KEY (uid,friendid),
  FOREIGN KEY (uid) REFERENCES user(uid),
  FOREIGN KEY (friendid) REFERENCES user(uid)
)

-- 用户聊天室表

CREATE TABLE user_chatroom_rela(
  uid INT,
  ctid INT,

  PRIMARY KEY (uid,ctid),

  FOREIGN KEY (uid) REFERENCES user(uid),
  FOREIGN KEY (ctid) REFERENCES chat_room(ctid)

);

-- user to user  chat record
CREATE TABLE record_user(
  fromid INT ,
  toid INT ,
  record VARCHAR(512),
  date TIMESTAMP,

  PRIMARY KEY(fromid,toid)
);

CREATE TABLE record_chatroom(
  ctid INT ,
  uid INT,
  record VARCHAR (512),
  date TIMESTAMP ,

  PRIMARY  KEY (ctid,uid)
);
