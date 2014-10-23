Create database dbNTB

go
drop database dbNTBa

use dbNTB

go

Create table Account
(
        AId int identity(1,1) primary key,
        AUsername nvarchar(200),
        APassword nvarchar(200),
		AFullName nvarchar(100),
        AEmail nvarchar(100),
        APhone nvarchar(15),
        AAddress nvarchar(500),
        ARole nvarchar(100)
)
go

insert into Account values('anngc00492','fpt','Nguyen Trong An','HoangMai','090000492','anntgc00492@fpt.edu.vn','admin')
insert into Account values('tunggc00641','fpt','Pham Thanh Tung','ThanhTri','090000641','tunggc00641@fpt.edu.vn','admin')
insert into Account values('vinhhv00574','fpt','Hoang Van Vinh','Ha Nam','09000074','vinhhvgc00574@fpt.edu.vn','admin')
insert into Account values('guesta','fpt','Nguyen Van A','DongDa','090000001','guesta@yahoo.com.vn','guest')
insert into Account values('guestb','fpt','Nguyen Van B','ThanhXuan','090000002','guestb@yahoo.com.vn','guest')
go


Create table Land
(
        LLandId int identity primary key,
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

insert into Land values('No 1 Thai Ha St, Trung Liet','Dong Da hospital, Vietnam Trade Union University','Dong Da','Hanoi',100,1100,1200,'1/1/2013','Yet received the occupancy permit')
insert into Land values('No 2 Thai Ha St, Trung Liet','Dong Da hospital, Vietnam Trade Union University','Dong Da','Hanoi',200,1100,1200,'2/2/2013','Waiting for the occupancy permit')
insert into Land values('No 3 Xuan Thuy Street, Quan Hoa','Thu Le zoo, Vietnam National Hospital of Pediatrics','Cau Giay','Hanoi',300,1300,1400,'3/3/2013','Yet to be applied for the occupancy permit')
insert into Land values('No 4 Xuan Thuy Street, Quan Hoa','Thu Le zoo, Vietnam National Hospital of Pediatrics','Cau Giay','Hanoi',400,1300,1400,'4/4/2013','Not yet to be applied for the occupancy permit')
insert into Land values('No 5 Ho Tung Mau St, My Dinh','University of commerce,198 hospital,My Dinh Stadium','Tu Liem','Hanoi',500,1500,1600,'5/5/2013','Yet to be contructed')
insert into Land values('No 6 Ho Tung Mau St, My Dinh','University of commerce,198 hospital,My Dinh Stadium','Tu Liem','Hanoi',600,1500,1600,'5/5/2013','Not yet to be contructed')
insert into Land values('No 7 Pham Hung St, My Dinh','My Dinh bus station,My Dinh Stadium','Tu Liem','Hanoi',700,1700,1800,'6/6/2013','Yet received the building permit')
insert into Land values('No 8 Pham Hung St, My Dinh','My Dinh bus station,My Dinh Stadium','Tu Liem','Hanoi',800,1700,1800,null,'Waiting for the building permit')
insert into Land values('No 9 Ton That Thuyet St, My Dinh','FPT University, My Dinh bus station, My Dinh Stadium','Tu Liem','Hanoi',900,1900,2000,null,'Yet to be applied for the building permit')
insert into Land values('No 10 Ton That Thuyet St, My Dinh','FPT University, My Dinh bus station, My Dinh Stadium','Tu Liem','Hanoi',1000,1900,2000,null,'Not yet to be applied for the building permit')
go


select * from Lands
go


Create table Building
(
        BId int identity primary key,
		LId int foreign key references Lands(LLandId),
        BBuildingName nvarchar(200),
        BBuildingType nvarchar(200),
        BFloorNumber int,
        BRoomNumber int,
		BStartDate nvarchar(100) ,
        BCompletionDate nvarchar(100),
		BOccupancyDate nvarchar(100),
		BStatus nvarchar(200),      
)
go

insert into Building values(1,'ThatHa1 official compelex','official',11,22,'1/1/2013','1/1/2014','1/1/2014','Yet received the occupancy permit')
insert into Building values(2,'ThaiHa2 official compelex','official',12,24,'2/2/2013','2/2/2014',null,'Waiting for the occupancy permit')
insert into Building values(3,'XuanThuy1 residental compelex','residental',13,26,'3/3/2013','3/3/2014',null,'Yet to be applied for the occupancy permit')
insert into Building values(4,'XuanThuy2 residental compelex','residental',14,28,'4/4/2013','4/4/2014',null,'Not yet to be applied for the occupancy permit')
insert into Building values(5,'HoTungMau1 shop compelex','shop',15,15,'5/5/2014','5/12/2014',null,'Not yet to be completely constructed')
go
select * from Building
go



Create table Apartment
(
		APId int identity(1,1)primary key,
		BId int foreign key references Building(BId),
		APOnFloor int,
		APArea int,
		ApCost int     
)
go

insert into Apartment values(1,1,50,null)
insert into Apartment values(1,2,50,null)
go

-- Create table Customers
-- (
        -- CustomerId int identity primary key,
        -- CustomerName nvarchar(200),
        -- Sex nvarchar(50),
        -- [Address] nvarchar(500),
        -- [Phone] nvarchar(15),
        -- Email nvarchar(100),
        -- [Status] bit
-- )

-- go

Create table Sale int identity primary key,
(
        SId int identity primary key,
		APId int foreign key references Apartment(APId),
		AId int foreign key references Account(AId),
		STotalCost int,
		SPaymentType nvarchar(100),
		SAmountPaid int,
		SAmmountDue int,
		SStatus nvarchar(100),
		STax nvarchar(100),	
		SGivenDay nvarchar(100)
)		
go
