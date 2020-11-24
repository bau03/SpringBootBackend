create table if not exists "cloud_users"
(
    id          serial primary key,
    username    varchar(50)  unique,
    email       varchar(100) not null unique,
    password    varchar(255) ,
    enabled     boolean      not null,
    name        varchar(25),
    lastname    varchar(25),
    age         varchar(3),
    phone_number varchar(10)
);

create table if not exists "cloud_roles"
(
    id               serial primary key,
    role             varchar(50) not null unique,
    is_account_admin boolean     not null default false
);

create table if not exists "user_roles"
(
    user_id int not null references cloud_users (id),
    role_id int not null references cloud_roles (id)
);

create table if not exists  "token_blacklist" (
      token text primary key not null unique,
      type varchar(50) not null
);

create table if not exists "email_templates"(
     id serial primary key,
     template_name text not null unique ,
     content text not null
);

create table if not exists  "user_attempt" (
    ip varchar(255) primary key,
    attempt_counter int not null,
    first_attempt_date timestamp without time zone NOT NULL
);

create table if not exists  "active_sessions" (
    refresh_token varchar(255) primary key,
    access_token varchar(255),
    user_agent text,
    issue_date timestamp without time zone NOT NULL,
    expire_date timestamp without time zone NOT NULL,
    user_id int not null references cloud_users(id)
);

create table if not exists "writer_user"(
 id    serial primary key,
 job varchar (255),
 education varchar(255),
 biography varchar(255),
 timestap date ,
 user_id int not null references cloud_users(id)
);

create table if not exists "content_category"(
 id    serial primary key,
 categoryName varchar (60)
);

create table if not exists "content"(
 id    serial primary key,
 user_id int not null references cloud_users(id),
 category_id int not null references content_category(id),
 content varchar,
 contentHeader varchar(60),
 content_like_number int,
 content_dislike_number int,
 comment_number int,
 timestap date
);

create table if not exists "content_like"(
 content_id int not null references  content(id),
 user_id int not null references cloud_users(id)
);

create table if not exists "content_dislike"(
 content_id int not null references  content(id),
 user_id int not null references cloud_users(id)
);

create table if not exists "comment"(
 id    serial primary key,
 user_id int not null references cloud_users(id),
 content_id int not null references  content(id),
 comment varchar,
 comment_like_number int,
 comment_dislike_number int,
 timestap date
);

create table if not exists "comment_like"(
 comment_id int not null references  comment(id),
 user_id int not null references cloud_users(id)
);

create table if not exists "comment_dislike"(
 comment_id int not null references  comment(id),
 user_id int not null references cloud_users(id)
);