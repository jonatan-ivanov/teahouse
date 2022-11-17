create table tealeaf (
    id uuid not null,
    name varchar(255) not null,
    suggested_amount varchar(255) not null,
    suggested_steeping_time varchar(255) not null,
    suggested_water_temperature varchar(255) not null,
    type varchar(255) not null,
    primary key (id)
);

alter table if exists tealeaf add constraint UK_9v6fqg4d3pq6ryc2xghqq0sqj unique (name);

