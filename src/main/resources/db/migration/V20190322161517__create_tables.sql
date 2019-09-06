create table Locations
(
    Location_ID int not null identity(1,1),
    Street_Address varchar(25) not null,
    City varchar(25) not null,
    State varchar(2) not null,
    Postal varchar(10) not null,
    Country varchar(25) not null,
    constraint Locations_pk
        primary key (Location_ID)
);

create table County
(
    County_ID int not null identity(1,1),
    County_Name varchar(25) not null,
    constraint County_pk
        primary key (County_ID)
);

create table Phone_Number
(
    Phone_ID int not null identity(1,1),
    First_Name varchar(25) not null,
    Last_Name varchar(25) not null,
    Phone_Number varchar(15) not null,
    constraint Phone_Number_pk
        primary key (Phone_ID)
);

create table Client
(
    Client_ID int not null identity(1,1),
    Client_First varchar(25) not null,
    Client_Last varchar(25) not null,
    Client_Email varchar(50) not null,
    Client_Phone varchar(25) not null,
    Client_Street varchar(25) not null,
    Client_City varchar(25) not null,
    Client_State varchar(2) not null,
    Client_Comments varchar(100),
    Location_ID int not null,
    constraint Client_pk
        primary key (Client_ID)
);

create table Client_Type
(
    Client_Type_ID int not null identity(1,1),
    Client_ID int not null,
    First_Name varchar(25) not null,
    New_Client bit not null,
    Returning_Client bit not null,
    constraint Client_Type_pk
        primary key (Client_Type_ID)
);

create table Client_Location
(
    Location_ID int not null identity(1,1),
    Client_ID int not null,
    Street_Address varchar(25) not null,
    City varchar(25) not null,
    State varchar(2) not null,
    Postal varchar(10) not null,
    constraint Client_Location_pk
        primary key (Location_ID)
);

create table PostalCode
(
    PostalCode_ID int not null identity(1,1),
    PostalCode varchar(25) not null,
    County varchar(25) not null,
    constraint PostalCode_pk
        primary key (PostalCode_ID)
);

create table Payment_Method
(
    Payment_ID int not null identity(1,1),
    Client_ID int not null,
    Payment_Type varchar(25) not null,
    Payment_Amount decimal(18,2) not null,
    constraint Payment_Method_pk
        primary key (Payment_ID)
);

create table Booking_Status
(
    Booking_ID int not null identity(1,1),
    Booking_Date date not null,
    Client_ID int not null,
    Booking_Status varchar(25) not null,
    constraint Booking_Status_pk
        primary key (Booking_ID)
);

create table Employee
(
    Employee_ID int not null identity(1,1),
    First_Name varchar(25) not null,
    Middle_Name varchar(25),
    Last_Name varchar(25) not null,
    Birthdate date not null,
    Street_Address varchar(50) not null,
    City varchar(25) not null,
    State varchar(2) not null,
    Phone varchar(25) not null,
    SSN varchar(15) null,
    Position varchar(25) not null,
    Location_ID int not null,
    constraint Employee_pk
        primary key (Employee_ID)
);

create table Employee_Type
(
    Employee_Type_ID int not null identity(1,1),
    Employee_ID int not null,
    New_Emp bit not null,
    Returning_Emp bit not null,
    constraint Employee_Type_pk
        primary key (Employee_Type_ID)
);


create table Employee_Title
(
    Title_ID int not null identity(1,1),
    Employee_ID int not null,
    Emp_Title varchar(25) not null,
    constraint Employee_Title_pk
        primary key (Title_ID)
);

create table Employee_Status
(
    Status_ID int not null identity(1,1),
    First_Name varchar(25) not null,
    Last_Name varchar(25) not null,
    Employement_Status varchar(25) not null,
    Employee_ID int not null,
    Comments varchar(100),
    constraint Employee_Status_pk
        primary key (Status_ID)
);

create table Employee_Training_Request
(
    Training_RequestID int not null identity(1,1),
    Employee_ID int not null,
    Training_Date date not null,
    Training_Description varchar(100) not null,
    Training_Type varchar(25) not null,
    constraint Employee_Training_Request_pk
        primary key (Training_RequestID)
);

create table Employee_Training_Status
(
    Training_Status_ID int not null identity(1,1),
    Employee_ID int not null,
    Training_ID int not null,
    Training_Status varchar(25) not null,
    constraint Employee_Training_Status_pk
        primary key (Training_Status_ID)
);

create table Timesheet
(
    Timesheet_ID int not null identity(1,1),
    Employee_ID int not null,
    Work_day date not null,
    Time_in time not null,
    Time_out time,
    constraint Timesheet_pk
        primary key (Timesheet_ID)
);

create table Timesheet_Status
(
    TimeStatus_ID int not null identity(1,1),
    Timesheet_ID int not null,
    Status varchar(25) not null,
    Comments varchar(25),
    constraint Timesheet_Status_pk
        primary key (TimeStatus_ID)
);

create table Vacation_Request
(
    Vacation_ID int not null identity(1,1),
    Employee_ID int not null,
    Reason varchar(25) not null,
    Date_Requested date not null,
    Date_Return date not null,
    constraint Vacation_Request_pk
        primary key (Vacation_ID)
);

create table Vacation_Status
(
    Vacation_StatusID int not null identity(1,1),
    Vacation_ID int not null,
    Status varchar(25) not null,
    Comments varchar(25),
    constraint Vacation_Status_pk
        primary key (Vacation_StatusID)
);

create table User_Info
(
    User_ID int not null identity(1,1),
    User_Login varchar(25) not null,
    User_Password varchar(60) not null,
    User_Status varchar(25) not null,
    Employee_ID int not null,
    First_Name varchar(25) not null,
    Last_Name varchar(25) not null,
    constraint User_Info_pk
        primary key (User_ID)
);

create table User_Login
(
    Login_ID int not null identity(1,1),
    User_ID int not null,
    Login_Time time not null,
    Logout_Time time not null,
    constraint User_Login_pk
        primary key (Login_ID)
);

create table Payroll
(
    Payroll_ID int not null identity(1,1),
    Timesheet_ID int not null,
    Time_in time not null,
    Time_out time not null,
    Wage int not null,
    constraint Payroll_pk
        primary key (Payroll_ID)
);

create table Event
(
    Event_ID int not null identity(1,1),
    Service_Requested varchar(25),
    Event_Date date not null,
    Client_ID int not null,
    Start_Time time not null,
    End_Time time not null,
    Event_Location varchar(25) not null,
    constraint Event_pk
        primary key (Event_ID)
);

create table Event_Location
(
    Location_ID int not null identity(1,1),
    Event_ID int not null,
    Street_Address varchar(25) not null,
    City varchar(25) not null,
    State varchar(2) not null,
    Postal varchar(10) not null,
    constraint Event_Location_pk
        primary key (Location_ID)
);

create table Event_Date
(
    Event_Date_ID int not null identity(1,1),
    Event_ID int not null,
    Event_Date date not null,
    Event_Start time not null,
    Event_End time not null,
    constraint Event_Date_pk
        primary key (Event_Date_ID)
);

create table Event_Type
(
    Event_Type_ID int not null identity(1,1),
    Event_ID int not null,
    Event_Type varchar(25) not null,
    constraint Event_Type_pk
        primary key (Event_Type_ID)
);

create table Service_Request
(
    Request_ID int not null identity(1,1),
    Event_ID int not null,
    Service_Requested varchar(25),
    constraint Service_Request_pk
        primary key (Request_ID)
);

create table Event_Expenses
(
    Expense_ID int not null identity(1,1),
    Event_ID int not null,
    Service varchar(25) not null,
    Service_Cost int not null,
    Other_Expenses varchar(25),
    Other_Cost int,
    constraint Event_Expenses_pk
        primary key (Expense_ID)
);

create table Event_Status
(
    Status_ID int not null identity(1,1),
    Event_ID int not null,
    Event_Date date not null,
    Status varchar(25) not null,
    constraint Event_Status_pk
        primary key (Status_ID)
);

create table Music
(
    Song_ID int not null identity(1,1),
    Song_Name varchar(25) not null,
    Artist_Name varchar(25) not null,
    Album_Name varchar(25),
    Genre_Type varchar(25) not null,
    constraint Music_pk
        primary key (Song_ID)
);
