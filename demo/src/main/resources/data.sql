INSERT INTO users (id,username, password, email) VALUES (1000,'henk', '$2a$12$0Oc6X4XBP8SCPd/eMkkG3eq9nZ8R3nz3P6h/0FnuZm.7/GNnL0XnK','test@testy.com'); -- password is 1234
INSERT INTO profiles (id,name) VALUES (1001,'henk');

INSERT INTO authorities (username, authority) VALUES ('henk', 'ROLE_ADMIN');