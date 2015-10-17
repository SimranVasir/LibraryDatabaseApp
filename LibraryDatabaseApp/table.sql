drop table HoldRequest;
drop table Fine;
drop table Borrowing;
drop table Borrower;
drop table HasAuthor;
drop table HasSubject;
drop table BookCopy;
drop table Book;
drop table Borrowertype;

create table Borrowertype
(
	type integer not null PRIMARY KEY,
    bookTimeLimit integer not null,
    bookDailyFine integer not null
);

grant select on Borrowertype to public;

create table Borrower
(
	bid integer not null PRIMARY KEY,
    password varchar(20) not null,
    name varchar(40) not null,
    address varchar(40) not null,
    phone char(10) not null,
    emailAddress varchar(40),
    sinOrStNo varchar(15) not null,
    expiryDate date not null,
    type integer not null,
    foreign key (type) references Borrowertype
);
    
grant select on Borrower to public;

create table Book 
(
	callNumber varchar(40) not null PRIMARY KEY,
    isbn varchar(13) not null,
    title varchar(40) not null, 
    mainAuthor varchar (40) not null,
    publisher varchar (40) not null,
    year integer not null
);

grant select on Book to public;

create table HasAuthor
(
	callNumber varchar(40) not null,
    name varchar(40) not null,
    PRIMARY KEY (callNumber,name),
    foreign key (callNumber) references Book ON DELETE CASCADE
);

grant select on HasAuthor to public;


create table HasSubject
(
	callNumber varchar(40) not null,
    subject varchar(20) not null,
    PRIMARY KEY (callNumber,subject),
    foreign key (callNumber) references Book ON DELETE CASCADE
);
    
grant select on HasSubject to public;

create table BookCopy
(
	callNumber varchar(40) not null,
    copyNo integer not null,
    status integer not null,
    PRIMARY KEY (callNumber, copyNo),
    foreign key (callNumber) references Book ON DELETE CASCADE
);

grant select on BookCopy to public;

create table HoldRequest
(
	hid integer not null PRIMARY KEY,
    bid integer not null,
    callNumber varchar(40) not null,
    issueDate date not null,
    foreign key (bid) references Borrower ON DELETE CASCADE,
    foreign key (callNumber) references Book ON DELETE CASCADE
);
    
grant select on HoldRequest to public;

create table Borrowing 
(
	borid integer not null PRIMARY KEY,
    bid integer not null,
    callNumber varchar(40) not null,
    copyNo integer not null,
    outDate date not null,
    dueDate date,    
    inDate date,
    foreign key (bid) references Borrower ON DELETE CASCADE,
    foreign key (callNumber,copyNo) references BookCopy ON DELETE CASCADE
);

grant select on Borrowing to public;

create table Fine
(
        fid integer not null PRIMARY KEY,
    type integer not null,
    amount int not null,
    issueDate date not null,
    paidDate date,
    borid integer not null,
    foreign key (type) references Borrowertype ON DELETE CASCADE,
    foreign key (borid) references Borrowing ON DELETE CASCADE
);

grant select on Fine to public;

INSERT INTO BORROWERTYPE VALUES (1,20,1);

INSERT INTO BORROWERTYPE VALUES (2,25,1);

INSERT INTO BORROWERTYPE VALUES (3,30,1);

DROP SEQUENCE bid_counter;	
CREATE SEQUENCE bid_counter
START WITH 1
INCREMENT BY 1;


DROP SEQUENCE hid_counter;	
CREATE SEQUENCE hid_counter
START WITH 1
INCREMENT BY 1;


DROP SEQUENCE borid_counter;	
CREATE SEQUENCE borid_counter
START WITH 1
INCREMENT BY 1;

DROP SEQUENCE fid_counter;	
CREATE SEQUENCE fid_counter
START WITH 1
INCREMENT BY 1;




















