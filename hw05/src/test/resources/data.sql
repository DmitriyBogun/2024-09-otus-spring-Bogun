insert into authors(full_name)
values ('Author1'), ('Author2'), ('Author3');

insert into genres(genre)
values ('Genre1'), ('Genre2'), ('Genre3');

insert into books(title, author_id, genre_id)
values ('TestBook1', 1, 1), ('TestBook2', 2, 2), ('TestBook3', 3, 3);
