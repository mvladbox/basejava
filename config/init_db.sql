create table resume
(
  uuid      varchar(36) not null
    constraint resume_pkey
    primary key,
  full_name text        not null
);

create table contact
(
  id          serial      not null
    constraint contact_pkey
    primary key,
  resume_uuid varchar(36) not null
    constraint contact_resume_uuid_fk
    references resume
    on delete cascade,
  type        text        not null,
  value       text        not null
);

create unique index contact_uuid_type_index
  on contact (resume_uuid, type);

create table section
(
	resume_uuid varchar(36) not null
	  constraint section_resume_uuid_fk
		references resume
		on delete cascade,
	type        text        not null,
	value       text        not null
);

create unique index section_uuid_type_index
	on section (resume_uuid, type);
