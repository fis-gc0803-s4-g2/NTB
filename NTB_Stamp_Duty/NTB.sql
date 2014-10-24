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
	LBuildingPermissionDate nvarchar(50),
    LStatus nvarchar(100)
)
go
insert into Land values('No 1 Thai Ha St, Trung Liet','Dong Da hospital, Vietnam Trade Union University','Dong Da','Hanoi',100,1000,1100,'1/1/2013','Yet received the occupancy permit')
insert into Land values('No 2 Thai Ha St, Trung Liet','Dong Da hospital, Vietnam Trade Union University','Dong Da','Hanoi',200,1100,1200,'2/2/2013','Yet received the occupancy permit')
insert into Land values('No 3 Xuan Thuy Street, Quan Hoa','Thu Le zoo, Vietnam National Hospital of Pediatrics','Cau Giay','Hanoi',300,1200,1300,'3/3/2013','Yet to be applied for the occupancy permit')
insert into Land values('No 4 Xuan Thuy Street, Quan Hoa','Thu Le zoo, Vietnam National Hospital of Pediatrics','Cau Giay','Hanoi',400,1300,1400,'4/4/2013','Not yet to be applied for the occupancy permit')
insert into Land values('No 5 Ho Tung Mau St, My Dinh','University of commerce,198 hospital,My Dinh Stadium','Tu Liem','Hanoi',500,1400,1500,'5/5/2013','Yet to be contructed')
insert into Land values('No 6 Ho Tung Mau St, My Dinh','University of commerce,198 hospital,My Dinh Stadium','Tu Liem','Hanoi',600,1500,1600,'6/6/2013','Not yet to be contructed')
insert into Land values('No 7 Pham Hung St, My Dinh','My Dinh bus station,My Dinh Stadium','Tu Liem','Hanoi',700,1600,1700,'7/7/2013','Yet received the building permit')
insert into Land values('No 8 Pham Hung St, My Dinh','My Dinh bus station,My Dinh Stadium','Tu Liem','Hanoi',800,1700,1800,'8/8/2013','Yet received the building permit')
insert into Land values('No 9 Ton That Thuyet St, My Dinh','FPT University, My Dinh bus station, My Dinh Stadium','Tu Liem','Hanoi',900,1800,1900,null,'Yet to be applied for the building permit')
insert into Land values('No 10 Ton That Thuyet St, My Dinh','FPT University, My Dinh bus station, My Dinh Stadium','Tu Liem','Hanoi',1000,1900,2000,null,'Not yet to be applied for the building permit')
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
insert into Building values(1,'ThatHa1 official compelex','official',11,22,'1/1/2013','1/1/2014','1/2/2014','thatha1.jpg','40-50-60 m2 available','Yet received the occupancy permit')
insert into Building values(2,'ThaiHa2 official compelex','official',12,24,'2/2/2013','2/2/2014','2/3/2014','thatha2.jpg','40-50-60 m2 available','Yet received the occupancy permit')
insert into Building values(3,'XuanThuy1 residental compelex','residental',13,26,'3/3/2013','3/3/2014',null,'xuanthuy1,jpg','40-50-60 m2 available','Yet to be applied for the occupancy permit')
insert into Building values(4,'XuanThuy2 residental compelex','residental',14,28,'4/4/2013','4/4/2014',null,'xuanthuy2.jpg','40-50-60 m2 available','Not yet to be applied for the occupancy permit')
insert into Building values(5,'HoTungMau1 shop compelex','shop',15,15,'5/5/2014','5/12/2014',null,'hotungmau1.jpg','90m2 availabe','Not yet to be completely constructed')
go
select * from Building
go

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

Create table Sale(
    SAId int identity primary key,
	CId int foreign key references Customer(CId),
	APId int foreign key references Apartment(APId),
	SAPaymentType nvarchar(100),
	SATotalCost int,
	SATotalPayment int,
	SAAmountPaid float,
	SAAmmountDue float,
	SATax int,
	SCreateDate nvarchar(50),
	SNote nvarchar(500),
	SAStatus nvarchar(100),
)		
go 
insert into Sale values(1,1,'One time payment',55000,55000,55000,0,250,'24/10/2014','','Registration and Stamp Duty Done')
insert into Sale values(2,2,'One time payment',55000,55000,55000,0,0,'24/10/2014','','Not yet to be registered')
insert into Sale values(3,3,'Payment through Installments on a monthly basis for 2 years',55000,57750,7218.75,50531.25,250,'24/10/2014','','Payment Not Received')
insert into Sale values(4,4,'Payment through Installments on a yearly basis for 2 years',55000,56650,7081.25,49568.75,250,'24/10/2014','','Payment Not Received')
go
select * from Sale
go

Create table PaymentDetail(
	PDId int primary key identity,
	SAId int foreign key references Sale(SAId),
	PDDueDate nvarchar(50),
	PDAmountDue float,
	PDPaidDate nvarchar(50),
	PDAmountPaid float,	
)
go
insert into PaymentDetail values(3,'24/10/2014',7218.75,'24/10/2014',7218.75)
go
select * from PaymentDetail
go
