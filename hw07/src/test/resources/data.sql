insert into authors(full_name)
values ('Стивен Кинг'), ('Говард Лавкрафт'), ('Эдгар Аллан По');

insert into genres(genre)
values ('Ужасы'), ('Кошмары'), ('Триллеры');

insert into books(title, author_id, genre_id)
values ('Свинка Пеппа', 1, 1), ('Барашек Шон', 2, 2), ('Синий трактор', 3, 3);

insert into comments(text, book_id)
values ('Страшно', 1),
       ('Очень страшно', 1),
       ('Мы не знаем, что это такое', 2),
       ('Если бы мы знали, что это такое, но мы не знаем, что это такое', 3);