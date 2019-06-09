delete from avatar where 1 = 1;
delete from friendship where 1 = 1;
delete from friend_request where 1 = 1;
delete from user_roles where 1 = 1;
delete from user_data where 1 = 1;
delete from user_personal_information where 1 = 1;
delete from user_display_settings where 1 = 1;


insert into user_display_settings(id, city_display_level, name_display_level, surname_display_level, about_me_display_level)values (1,'ADMIN', 'ADMIN', 'ADMIN', 'ADMIN');
insert into user_display_settings(id, city_display_level, name_display_level, surname_display_level, about_me_display_level)values (2,'ADMIN', 'ADMIN', 'ADMIN', 'ADMIN');

insert into user_personal_information(id, city, first_name, surname, aboutMe) values (1, 'city', 'fname', 'surname', 'boutme');
insert into user_personal_information(id, city, first_name, surname, aboutMe) values (2, 'city', 'fname', 'surname', 'boutme');

insert into user_data(id, displayName, email, login, password, ups_id, upi_id)
values (1, 'user 1', 'email moj', 'log', 'pass', 1, 1);
insert into user_data(id, displayName, email, login, password, ups_id, upi_id)
values (2, 'user 1', 'email moj', 'log', 'pass', 2, 2);

insert into avatar(id, bytes, user_id, dataFormat) VALUES (1, '9fad5e9eefdfb449', 1, 'pdfxDng');
insert into avatar(id, bytes, user_id, dataFormat) VALUES (2, '9fad5e9eefdfb449', 2, 'pdfxDng');

insert into friendship(id, user1, user2, since) values (1, 1, 2, '2019-05-05');
insert into friend_request(id, status,  requester_id, target_id, message) values (1, 'ACCEPTED', 1, 2, 'dawaj zią');
insert into friend_request(id, status,  requester_id, target_id, message) values (2, 'ACCEPTED', 2, 1, 'dawaj zią');
