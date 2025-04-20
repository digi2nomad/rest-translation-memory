create table if not exists Language (
	id identity,
	langcode varchar(5) not null,
	language varchar(20) not null
);

create table if not exists SegmentType (
	id identity,
	type varchar(9) not null,
	description varchar(20) not null
);

create table if not exists TranslationProject (
    id identity,
    name varchar(50) not null,
    description varchar(250) not null,
    createDate timestamp not null
); 

create table if not exists TranslationUnit (
  id identity,
  projId bigint not null, 
  segmentType bigint not null,
  createDate timestamp not null
);

alter table TranslationUnit
    add foreign key (segmentType) references SegmentType(id);
    
create table if not exists TranslationUnitVariant (
  id identity,
  tuid bigint not null,
  language bigint not null,
  segment varchar(250) not null,
  createDate timestamp not null,
  useDate timestamp,
  useCount int,
  reviewed boolean
);

alter table TranslationUnit
    add foreign key (projId) references TranslationProject(id);
    
alter table TranslationUnitVariant
    add foreign key (tuid) references TranslationUnit(id);
   
alter table TranslationUnitVariant
    add foreign key (language) references Language(id); 
    
create index if not exists idx_tuid 
    on TranslationUnitVariant(tuid);

create index if not exists idx_lang 
    on TranslationUnitVariant(language);