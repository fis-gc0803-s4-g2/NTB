--drop database dbNTB
Create database dbNTB
go
use dbNTB
go

/*Non-Financial*/
create table Manager(
	MId int primary key identity,
	MUsername nvarchar(100),
	MPassword nvarchar(100),
	MFullName nvarchar(100),
)
go
insert into Manager values('admin','admin','admin')
insert into Manager values('anntgc00492','fpt','Nguyen Trong An')
insert into Manager values('tungptgc00641','fpt','Pham Thanh Tung')
insert into Manager values('vinhhv00574','fpt','Hoang Van Vinh')
insert into Manager values('hoangnd00567','fpt','Ngo Dinh Hoang')
go
select * from Manager
go


Create table Land(
    LId int identity primary key,
    LAddress nvarchar(500),
    LNearByLandmark nvarchar(500),
	LDist nvarchar(100),
    LLocaltion nvarchar(100),
	LArea int,
	LPurchasedCost int,
	LPresentCost int,
	LBuildingPermissionDate nvarchar(100),
    LStatus nvarchar(100)
)
go
insert into Land values('No 1 Thai Ha St, Trung Liet','Dong Da hospital, Vietnam Trade Union University','Dong Da','Hanoi',100,100000,110000,'2013-1-1','To be received the occupancy permit')
insert into Land values('No 2 Thai Ha St, Trung Liet','Dong Da hospital, Vietnam Trade Union University','Dong Da','Hanoi',100,110000,120000,'2013-2-2','To be Received the occupancy permit')
insert into Land values('No 3 Xuan Thuy Street, Quan Hoa','Thu Le zoo, Vietnam National Hospital of Pediatrics','Cau Giay','Hanoi',100,120000,130000,'2013-3-3','To be applied for the occupancy permit')
insert into Land values('No 4 Xuan Thuy Street, Quan Hoa','Thu Le zoo, Vietnam National Hospital of Pediatrics','Cau Giay','Hanoi',100,130000,140000,'2013-4-4','Not to be applied for the occupancy permit')
insert into Land values('No 5 Ho Tung Mau St, My Dinh','University of commerce,198 hospital,My Dinh Stadium','Tu Liem','Hanoi',100,140000,150000,'2013-5-5','To be conpletely contructed')
insert into Land values('No 6 Ho Tung Mau St, My Dinh','University of commerce,198 hospital,My Dinh Stadium','Tu Liem','Hanoi',100,150000,160000,'2013-6-6','Not to be conpletely contructed')
insert into Land values('No 7 Pham Hung St, My Dinh','My Dinh bus station,My Dinh Stadium','Tu Liem','Hanoi',100,160000,170000,'2013-7-7','To be received the building permit')
insert into Land values('No 8 Pham Hung St, My Dinh','My Dinh bus station,My Dinh Stadium','Tu Liem','Hanoi',100,170000,180000,'2013-8-8','Not to be received the building permit')
insert into Land values('No 9 Ton That Thuyet St, My Dinh','FPT University, My Dinh bus station, My Dinh Stadium','Tu Liem','Hanoi',100,180000,190000,null,'To be applied for the building permit')
insert into Land values('No 10 Ton That Thuyet St, My Dinh','FPT University, My Dinh bus station, My Dinh Stadium','Tu Liem','Hanoi',100,190000,200000,null,'Not to be applied for the building permit')
go
select * from Land
go


Create table Building(
    BId int identity primary key,
	LId int foreign key references Land(LId),
    BBuildingName nvarchar(200),
    BBuildingType nvarchar(200),
    BFloorNumber int,
    BDepartmentNumber int,
	BStartDate nvarchar(100) ,
    BCompletionDate nvarchar(100),
	BOccupancyDate nvarchar(100),
	BImage nvarchar(500),
	BDescription nvarchar(500),
	BStatus nvarchar(100),      
)
go
insert into Building values(1,'ThaiHa1 official compelex','official',11,22,'2013-1-1','2014-1-1','2014-1-1','thaiha1.jpg','40-50-60 m2 available','To be received the occupancy permit')
insert into Building values(2,'ThaiHa2 official compelex','official',12,24,'2013-2-2','2014-2-2','2014-2-2','thaiha2.jpg','40-50-60 m2 available','To be received the occupancy permit')
insert into Building values(3,'XuanThuy1 residental compelex','residental',13,26,'2013-3-3','2014-3-3',null,'xuanthuy1.jpg','40-50-60 m2 available','To be applied for the occupancy permit')
insert into Building values(4,'XuanThuy2 residental compelex','residental',14,28,'2013-4-4','2014-4-4',null,'xuanthuy2.jpg','40-50-60 m2 available','Not to be applied for the occupancy permit')
insert into Building values(5,'HoTungMau1 shop compelex','shop',15,15,'2014-5-5','2014-12-5',null,'hotungmau1.jpg','90m2 availabe','To be completely constructed')
insert into Building values(6,'HoTungMau2 shop compelex','shop',15,15,'2014-5-5','2014-12-5',null,'hotungmau1.jpg','90m2 availabe','Not to be completely constructed')
go
select * from Building
go

--drop table Apartment
Create table Apartment(
	APId int identity primary key,
	BId int foreign key references Building(BId),
	APOnFloor int,
	APArea int,
	APCost int,
)
go
insert into Apartment values(1,2,50,55000)
insert into Apartment values(1,2,50,55000)
insert into Apartment values(1,3,50,55000)
insert into Apartment values(1,3,50,55000)
insert into Apartment values(1,4,40,44000)
insert into Apartment values(1,4,60,66000)
go
select * from Apartment
go

Create table Response(
	RId int primary key identity,
	RFullName nvarchar(100),
    RAddress nvarchar(100),
    REmail nvarchar(100),
    RPhone nvarchar(50),
    RContent nvarchar(500),
)
go
insert into Response values('Nguyen Van A','Hanoi','090000001','guesta@yahoo.com.vn','I want to know information about building ...')
insert into Response values('Nguyen Van B','HaiPhong','090000002','guestb@yahoo.com.vn','I want to know information about building ...')
insert into Response values('Nguyen Van C','DaNang','090000003','guestc@yahoo.com.vn','I want to know information about building ...')
insert into Response values('Nguyen Van B','HoChiMinh','090000002','guestd@yahoo.com.vn','I want to know information about building ...')
go
select * from Response
go

/*Financial*/

--drop table Customer
Create table Customer(
    CId int identity primary key,
    CUsername nvarchar(100),
    CPassword nvarchar(100),
	CFullName nvarchar(100),
    CAddress nvarchar(100),
    CEmail nvarchar(100),
    CPhone nvarchar(50),
)

go
insert into Customer values('guesta','fpt','Nguyen Van A','Hanoi','090000001','guesta@yahoo.com.vn')
insert into Customer values('guestb','fpt','Nguyen Van B','HaiPhong','090000002','guestb@yahoo.com.vn')
insert into Customer values('guestc','fpt','Nguyen Van C','DaNang','090000003','guestc@yahoo.com.vn')
insert into Customer values('guestd','fpt','Nguyen Van B','HoChiMinh','090000002','guestd@yahoo.com.vn')
go
select * from Customer
go

--drop table Contract
Create table [Contract](
    SAId int identity primary key,
	CId int foreign key references Customer(CId),
	APId int foreign key references Apartment(APId),
	SAPaymentType nvarchar(100),
	SATotalCost int,
	SATotalPayment int,
	SAAmountPaid float,
	SAAmmountDue float,
	SATax int,
	SACreateDate nvarchar(100),
	SANote nvarchar(500),
	SAStatus nvarchar(100),
)		
go 
insert into [Contract] values(1,1,'One time payment',55000,55000,55000,0,250,'2014-10-24','','Registration and Stamp Duty Done')
insert into [Contract] values(2,2,'One time payment',55000,55000,55000,0,0,'2014-10-24','','Yet to be registered')
insert into [Contract] values(3,3,'Payment through Installments on a monthly basis for 2 years',55000,57750,57750.0/24,(57750.0/24)*23,250,'2014-10-24','','Payment Not Received')
insert into [Contract] values(4,4,'Payment through Installments on a yearly basis for 2 years',55000,56650,56650.0/2,56650.0/2,250,'2014-10-24','','Payment Not Received')
go
select * from Contract
go

Create table PaymentDetail(
	PDId int primary key identity,
	SAId int foreign key references [Contract](SAId),
	PDDueDate nvarchar(100),
	PDAmountDue float,
	PDPaidDate nvarchar(100),
	PDAmountPaid float
)
go
insert into PaymentDetail values(3,'2014-10-24',57750.0/24,'2014-10-24',57750.0/24)
insert into PaymentDetail values(4,'2014-10-24',56650.0/2,'2014-10-24',56650.0/2)
go
select * from PaymentDetail
go

