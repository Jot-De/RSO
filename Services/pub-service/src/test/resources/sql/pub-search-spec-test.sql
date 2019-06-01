Delete from pub_tag where 1 = 1;
Delete from pub where 1 = 1;
Delete from address where 1 = 1;
Delete from tag where 1 = 1;

Insert into address(id, city, number, street) values (1,'Warszawa', '21', 'Ulicowa');
Insert into address(id, city, number, street) values (2,'Białystok', '21', 'Fajna');

Insert into pub (id, added, name, address_id, description) values (1, '2019-05-20', 'pub123', 1, 'Opis pubu');
Insert into pub (id, added, name, address_id, description) values (2, '2019-05-18', 'pub321', 2, 'Opis pubu');

Insert into tag(id, description, name)  values (1, 'Desc', 'name1');
Insert into tag(id, description, name)  values (2, 'Desc2', 'name2');
Insert into tag(id, description, name)  values (3, 'Desc3', 'name3');

Insert into pub_tag (Pub_id, tags_id)  values (1,1);
Insert into pub_tag (Pub_id, tags_id)  values (1,2);
Insert into pub_tag (Pub_id, tags_id)  values (2,2);


