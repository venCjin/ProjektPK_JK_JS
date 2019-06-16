use master
if exists(select 1 from master.dbo.sysdatabases where name = 'Organizer') drop database Organizer
GO

CREATE DATABASE Organizer
GO

USE Organizer;
GO

--create tables--
CREATE TABLE "Events"(
"ID_Event" INTEGER NOT NULL IDENTITY(0,1),
"Name" VARCHAR(100) NOT NULL,
"Description" VARCHAR(100),
"Place" VARCHAR(100),
"StartDate" Datetime NOT NULL,
"EndDate" Datetime NOT NULL,
"AlarmDate" Datetime
)

select * from Events 
--select top 1 ID_Event from Events order by ID_Event DESC