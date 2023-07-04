INSERT INTO profiles (id,name, profileimage, bioinformation) VALUES (1001,'henk','jantjesmitdownload.jpg', 'test');
INSERT INTO users (id,username, password, email, profile_id) VALUES (1000,'henk', '$2a$12$0Oc6X4XBP8SCPd/eMkkG3eq9nZ8R3nz3P6h/0FnuZm.7/GNnL0XnK','henk@testy.com',1001); -- password is 1234
UPDATE profiles SET user_id=1000 WHERE id = 1001;

INSERT INTO authorities (username,authority) VALUES ('henk', 'ROLE_ADMIN');
INSERT INTO users_authorities (user_id,authorities_authority,authorities_username) VALUES (1000, 'ROLE_ADMIN', 'henk');

INSERT INTO profiles (id,name, profileimage, bioinformation) VALUES (1003,'Kees','jantjesmit2images.jpg', 'test');
INSERT INTO users (id,username, password, email, profile_id) VALUES (1002,'Kees', '$2a$12$r6cubfIh03WT8pPM3j3M2.XMv/APyovMiYZV03HDLxmYfW9hLnG8i','kees@testy.com',1003); -- password is 12345
UPDATE profiles SET user_id=1002 WHERE id = 1003;

INSERT INTO authorities (username,authority) VALUES ('Kees', 'ROLE_NORMAL_USER');
INSERT INTO users_authorities (user_id,authorities_authority,authorities_username) VALUES (1002, 'ROLE_NORMAL_USER', 'Kees');

INSERT INTO profiles (id,name, profileimage, bioinformation) VALUES (1005,'John','wow-jan-smit-wil-duet-zingen-jongere-zelf.jpg', 'test');
INSERT INTO users (id,username, password, email, profile_id) VALUES (1004,'John', '$2a$12$m50VAEN0h2kzVRmtuvlpFes6YgLDXOVYSwp9blecXrLahh8sAdwxy','john@testy.com',1005); -- password is 123456
UPDATE profiles SET user_id=1004 WHERE id = 1005;

INSERT INTO authorities (username,authority) VALUES ('John', 'ROLE_CELEB_USER');
INSERT INTO users_authorities (user_id,authorities_authority,authorities_username) VALUES (1004, 'ROLE_CELEB_USER', 'John');


INSERT INTO profiles (id,name, profileimage, bioinformation) VALUES (1007,'Jan','riza-aguasin-escapismart.jpg', 'test');
INSERT INTO users (id,username, password, email, profile_id) VALUES (1006,'Jan', '$2a$12$E/psue6KVnG/z5BLBJEPYeI9Kxy6KXzLcb0XDyQUGVl4UzSX3Xfta','jan@testy.com',1007); -- password is 1234567
UPDATE profiles SET user_id=1006 WHERE id = 1007;

INSERT INTO authorities (username,authority) VALUES ('Jan', 'ROLE_PAGE_ADMIN_USER');
INSERT INTO users_authorities (user_id,authorities_authority,authorities_username) VALUES (1006, 'ROLE_PAGE_ADMIN_USER', 'Jan');