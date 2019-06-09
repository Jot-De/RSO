DELETE from picture where 1 = 1;
delete from visit where 1 = 1;
Delete from pub_tag where 1 = 1;
Delete from pub_registration_request where 1 = 1;
Delete from pub where 1 = 1;
Delete from address where 1 = 1;
Delete from TAG_SUBSCRIPTION_TAG where 1 = 1;
Delete from tag where 1 = 1;

Insert into address(id, city, number, street) values (1,'Warszawa', '21', 'Ulicowa');
Insert into pub_registration_request(id, added, name, status, userId, address_id, pub_id, description)
  values (1, '2019-12-10', 'name', 'REGISTERED', 1, 1, null, 'desc') ;
Insert into pub (id, added, name, address_id, description) values (1, '2019-05-20', 'pub123', 1, 'Opis pubu');
Insert into picture(id, bytes, description, name, pub_id, format) VALUES (1, '9fad5e9eefdfb449', 'desc', 'name', 1, 'jpg');
Update pub_registration_request set pub_id = 1 where id = 1;

Insert into tag(id, description, name)  values (1, 'Desc', 'name1');
Insert into pub_tag (Pub_id, tags_id)  values (1,1);
insert into visit(id, userId, visitStatus, visited, pub_id) VALUES (1, 2, 'VISITED', '2019-05-05', 1);
insert into visit(id, userId, visitStatus, visited, pub_id) VALUES (2, 3, 'VISITED', '2019-05-05', 1);



