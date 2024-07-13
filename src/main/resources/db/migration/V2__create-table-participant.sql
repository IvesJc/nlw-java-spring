CREATE TABLE participants (
    id uuid PRIMARY KEY,
    name varchar(255) not null,
    email varchar(255) not null,
    is_confirmed boolean not null,
    trip_id uuid,
    FOREIGN KEY (trip_id) REFERENCES trips(id)
)