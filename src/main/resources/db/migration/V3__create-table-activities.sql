CREATE TABLE activities (
    id uuid primary key,
    title varchar(255) not null,
    occurs_at timestamp not NULL ,
    trip_id uuid,
    foreign key (trip_id) references trips(id) on delete cascade
)