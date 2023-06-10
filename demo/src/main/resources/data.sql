INSERT INTO profiles (id,name, profileimage, bioinformation) VALUES (1001,'henk','jantjesmitdownload.jpg', 'test');
INSERT INTO users (id,username, password, email, profile_id) VALUES (1000,'henk', '$2a$12$0Oc6X4XBP8SCPd/eMkkG3eq9nZ8R3nz3P6h/0FnuZm.7/GNnL0XnK','test@testy.com',1001); -- password is 1234
UPDATE profiles SET user_id=1000 WHERE id = 1001;

INSERT INTO authorities (username,authority) VALUES ('henk', 'ROLE_ADMIN');
INSERT INTO users_authorities (user_id,authorities_authority,authorities_username) VALUES (1000, 'ROLE_ADMIN', 'henk');