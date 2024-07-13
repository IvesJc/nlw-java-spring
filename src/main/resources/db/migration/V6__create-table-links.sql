CREATE TABLE links(
    id uuid primary key,
    title varchar(255) not null ,
    url varchar(255) not null ,
    trip_id uuid,
    FOREIGN KEY (trip_id) references trips(id) ON DELETE CASCADE
)