create table water (
    id uuid not null,
    amount varchar(255) not null,
    size varchar(255) not null,
    primary key (id)
);

alter table if exists water add constraint UK_g79d6y6paax86h0g1xdlbh269 unique (amount);
alter table if exists water add constraint UK_ogxi7e4a41s1tp5bbrap35j2e unique (size);
