delete from user_roles where 1 = 1;
delete from user_data where 1 = 1;
delete from user_personal_information where 1 = 1;
delete from user_display_settings where 1 = 1;

insert into user_personal_information(id, city, first_name, surname) VALUES (1, 'city', 'first_name', 'surname');
insert into user_personal_information(id, city, first_name, surname) VALUES (2, 'city', 'first_name', 'surname');
insert into user_display_settings(id, city_display_level, name_display_level, surname_display_level) VALUES (1, 'ALL', 'ALL', 'ALL');
insert into user_display_settings(id, city_display_level, name_display_level, surname_display_level) VALUES (2, 'ALL', 'ALL', 'ALL');

insert into user_data(id, displayName, email, login, password, ups_id, upi_id) values (1,  'lukgrebo1','lukgrebo@gmail.com', 'login1', 'passxd', 1, 1);
insert into user_data(id, displayName, email, login, password, ups_id, upi_id) values (2,  'lukgrebo2','lukgrebo2@gmail.com', 'login2', 'passxd', 2, 2);