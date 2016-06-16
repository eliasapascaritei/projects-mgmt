# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "account_hash" ("id_hash" SERIAL NOT NULL PRIMARY KEY,"id_user" INTEGER NOT NULL,"hash" VARCHAR(254) NOT NULL,"date" TIMESTAMP NOT NULL);
create table "courses" ("id_course" SERIAL NOT NULL PRIMARY KEY,"id_special" INTEGER NOT NULL,"id_user" INTEGER NOT NULL,"name" VARCHAR(254) NOT NULL,"year" INTEGER NOT NULL);
create table "project_to_user" ("id_project_to_user" SERIAL NOT NULL PRIMARY KEY,"id_project" INTEGER NOT NULL,"id_user" INTEGER NOT NULL);
create table "projects" ("id_project" SERIAL NOT NULL PRIMARY KEY,"id_user" INTEGER NOT NULL,"id_course" INTEGER NOT NULL,"name" VARCHAR(254) NOT NULL,"description" VARCHAR(254) NOT NULL,"isT_taken" BOOLEAN NOT NULL,"is_accepted" BOOLEAN NOT NULL,"s3_name" VARCHAR(254));
create table "specializations" ("id_special" SERIAL NOT NULL PRIMARY KEY,"name" VARCHAR(254) NOT NULL);
create table "users" ("id_user" SERIAL NOT NULL PRIMARY KEY,"id_special" INTEGER,"email" VARCHAR(254) NOT NULL,"lastName" VARCHAR(254),"firstName" VARCHAR(254),"role" VARCHAR(254) NOT NULL,"password" VARCHAR(254) NOT NULL);
create unique index "profile_email_unique" on "users" ("email");
alter table "projects" add constraint "project_user_fk" foreign key("id_user") references "users"("id_user") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "projects" drop constraint "project_user_fk";
drop table "users";
drop table "specializations";
drop table "projects";
drop table "project_to_user";
drop table "courses";
drop table "account_hash";

