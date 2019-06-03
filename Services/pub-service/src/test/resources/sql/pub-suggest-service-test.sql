Delete from visit where 1 = 1;
delete from tag_subscription_tag where 1 = 1;
delete from tag_subscription where 1 = 1;
delete from pub_tag where 1 = 1;
delete from tag where 1 = 1;
Delete from pub where 1 = 1;
Delete from address where 1 = 1;

insert into address(id, city, number, street) values (1, 'Warszawa', '1233', '123');
insert into address(id, city, number, street) values (2, 'Marki', '1233', '123');
insert into address(id, city, number, street) values (3, 'Janki', '1233', '123');

insert into pub(id, added, name, address_id, description) values (1, '2019-04-06', 'w wawie', 1, 'desc1');
insert into pub(id, added, name, address_id, description) values (2, '2019-04-06', 'w markach', 2, 'desc1');
insert into pub(id, added, name, address_id, description) values (3, '2019-04-06', 'w jankach', 3, 'desc1');
insert into pub(id, added, name, address_id, description) values (4, '2019-04-06', 'w wawie2', 1, 'desc1');
insert into pub(id, added, name, address_id, description) values (5, '2019-04-06', 'w wawie3', 1, 'desc1');
insert into pub(id, added, name, address_id, description) values (6, '2019-04-06', 'w wawie4', 1, 'desc1');
insert into pub(id, added, name, address_id, description) values (7, '2019-04-06', 'w wawie5', 1, 'desc1');

insert into tag(id, description, name) VALUES (1, 'tag1', 'tag1');
insert into tag(id, description, name) VALUES (2, 'tag2', 'tag2');
insert into tag(id, description, name) VALUES (3, 'tag3', 'tag3');

insert into pub_tag(Pub_id, tags_id) values (1,1);
insert into pub_tag(Pub_id, tags_id) values (2,1);
insert into pub_tag(Pub_id, tags_id) values (2,2);
insert into pub_tag(Pub_id, tags_id) values (3,2);

insert into tag_subscription(userId) values (1);
insert into tag_subscription(userId) values (2);

insert into tag_subscription_tag(TagSubscription_userId, subscriptions_id) VALUES (1, 1);
insert into tag_subscription_tag(TagSubscription_userId, subscriptions_id) VALUES (1, 2);
insert into tag_subscription_tag(TagSubscription_userId, subscriptions_id) VALUES (2, 1);




