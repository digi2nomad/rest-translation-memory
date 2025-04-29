BEGIN
  EXECUTE IMMEDIATE 'create table Language (
		id numeric(19,0) not null,
		langcode varchar2(5) not null,
		language varchar2(20) not null,
		constraint Language_pk primary key (id)
	)';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE = -955 THEN
         NULL; -- Table already exists
      ELSE
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'create table SegmentType (
		id numeric(19,0) not null,
		type varchar2(9) not null,
		description varchar2(20) not null,
		constraint SegmentType_pk primary key (id)
	)';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE = -955 THEN
         NULL; -- Table already exists
      ELSE
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'create table TranslationProject (
    	id numeric(19,0) not null,
    	name varchar2(50) not null,
    	description varchar2(250) not null,
    	createDate timestamp not null,
    	constraint TranslationProject_pk primary key (id)
	)';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE = -955 THEN
         NULL; -- Table already exists
      ELSE
         RAISE;
      END IF;
END;	
/

BEGIN
   EXECUTE IMMEDIATE 'create table TranslationUnit (
  		id numeric(19,0) not null,
  		projId numeric(19,0) not null,
  		segmentType numeric(19,0) not null,
  		createDate timestamp not null,
  		constraint TranslationUnit_pk primary key (id)
	)';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE = -955 THEN
         NULL; -- Table already exists
      ELSE
         RAISE;
      END IF;
END;	
/

BEGIN
   EXECUTE IMMEDIATE 'alter table TranslationUnit
    add constraint TranslationUnit_SegmentType_fk foreign key (segmentType) references SegmentType(id)';
EXCEPTION
   WHEN OTHERS THEN
	NULL; -- Foreign key already exists
END;	
/

BEGIN
   EXECUTE IMMEDIATE 'create table TranslationUnitVariant (
  		id numeric(19,0) not null,
  		tuid numeric(19,0) not null,
  		language numeric(19,0) not null,
  		segment varchar2(250) not null,
  		createDate timestamp not null,
  		useDate timestamp,
  		useCount int,
  		reviewed number(1), /*boolean*/
  		constraint TranslationUnitVariant_pk primary key (id)
	)';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE = -955 THEN
         NULL; -- Table already exists
      ELSE
         RAISE;
      END IF;
END;	
/

BEGIN
   EXECUTE IMMEDIATE 'alter table TranslationUnit
    add constraint TranslationUnit_Project_fk foreign key (projId) references TranslationProject(id)';
EXCEPTION
   WHEN OTHERS THEN
	NULL; -- Foreign key already exists
END;	
/
    
BEGIN
   EXECUTE IMMEDIATE 'alter table TranslationUnitVariant
    add constraint TranslationVariant_Unit_fk foreign key (tuid) references TranslationUnit(id)';
EXCEPTION
   WHEN OTHERS THEN
	NULL; -- Foreign key already exists
END;
/
       
BEGIN
   EXECUTE IMMEDIATE 'alter table TranslationUnitVariant
    add constraint TranslationVariant_Language_fk foreign key (language) references Language(id)'; 
EXCEPTION
   WHEN OTHERS THEN
	NULL; -- Foreign key already exists
END;
/
           
BEGIN
   EXECUTE IMMEDIATE 'create index idx_tuid 
    on TranslationUnitVariant(tuid)';
EXCEPTION
   WHEN OTHERS THEN
	NULL; -- Index already exists
END;
/

BEGIN
   EXECUTE IMMEDIATE 'create index idx_lang 
    on TranslationUnitVariant(language)';
EXCEPTION
   WHEN OTHERS THEN
	NULL; -- Index already exists
END;
/

