drop table mapping_record;
drop table mapping_meal;
drop table record;
drop table meal;
drop table ingredient;

create table ingredient(
	id 				integer primary key autoincrement
   ,name	 		text not null
   ,calorieValue	integer not null
);

create table meal(
	id 				integer primary key autoincrement
   ,name	 		text not null
   ,calorieValue	integer not null
);

create table record(
	id 				integer primary key autoincrement
   ,registerDate	text not null
);

create table mapping_record(
	id 			integer primary key autoincrement
   ,record_id 	integer not null
   ,group_id	integer not null
   ,type		text not null
   ,foreign key (record_id) references record(id)	
);

create table mapping_meal(
	id 				integer primary key autoincrement
   ,meal_id 		integer not null
   ,ingredient_id	integer not null
   ,foreign key (meal_id) references meal(id)	
   ,foreign key (ingredient_id) references ingredient(id)
);

