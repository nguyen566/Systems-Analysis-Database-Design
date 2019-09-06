Alter table Client_Type
    Add constraint Fk_Client_Type_ID
        Foreign key(Client_ID) references Client(Client_ID);

Alter table Client_Location
    Add constraint Fk_Client_Location_ID
        Foreign key(Client_ID) references Client(Client_ID);

Alter table Payment_Method
    Add constraint Fk_Payment_ID
        Foreign key(Client_ID) references Client(Client_ID);

Alter table Booking_Status
    Add constraint Fk_Booking_ID
        Foreign key(Client_ID) references Client(Client_ID);

Alter table Employee_Type
    Add constraint Fk_Employee_Type_ID
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table Employee_Title
    Add constraint Fk_Employee_Title_ID
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table Employee_Status
    Add constraint Fk_Employee_Status_ID
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table Employee_Training_Request
    Add constraint Fk_Employee_Training_RequestID
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table Employee_Training_Status
    Add constraint Fk_Employee_Training_StatusID
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table Employee_Training_Status
    Add constraint Fk_Employee_Training
        Foreign key(Training_ID) references Employee_Training_Request(Training_RequestID);

Alter table Timesheet
    Add constraint Fk_Timesheet_ID
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table Timesheet_Status
    Add constraint Fk_Timesheet_StatusID
        Foreign key(Timesheet_ID) references Timesheet(Timesheet_ID);

Alter table Payroll
    Add constraint Fk_Payroll
        Foreign key(Timesheet_ID) references Timesheet(Timesheet_ID);

Alter table Vacation_Request
    Add constraint Fk_Vacation_Request
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table Vacation_Status
    Add constraint Fk_Vacation_Status
        Foreign key(Vacation_ID) references Vacation_Request(Vacation_ID);

Alter table User_Info
    Add constraint Fk_User_Info
        Foreign key(Employee_ID) references Employee(Employee_ID);

Alter table User_Login
    Add constraint Fk_User_Login
        Foreign key(User_ID) references User_Info(User_ID);

Alter table Event
    Add constraint Fk_Event_ID
        Foreign key(Client_ID) references Client(Client_ID);

Alter table Event_Location
    Add constraint Fk_Event_Location
        Foreign key(Event_ID) references Event(Event_ID);

Alter table Event_Date
    Add constraint Fk_Event_Date
        Foreign key(Event_ID) references Event(Event_ID);

Alter table Event_Type
    Add constraint Fk_Event_Type
        Foreign key(Event_ID) references Event(Event_ID);

Alter table Service_Request
    Add constraint Fk_Service_Request
        Foreign key(Event_ID) references Event(Event_ID);

Alter table Event_Expenses
    Add constraint Fk_Event_Expenses
        Foreign key(Event_ID) references Event(Event_ID);

Alter table Event_Status
    Add constraint Fk_Event_Status
        Foreign key(Event_ID) references Event(Event_ID);

Alter table Client
    Add constraint Fk_Location_Client_ID
        foreign key(Location_ID) references Locations(Location_ID);

Alter table Employee
    Add constraint Fk_Location_Employee_ID
        foreign key(Location_ID) references Locations(Location_ID);

