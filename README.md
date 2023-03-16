# Energy Monitoring System Website
<br />
This project is composed of Java SpringBoot Backend, PostgreSQL database, Angular Front-End, and a ChatService in .NET
<br />
<br />
Each user is able to create a user account, which will see all the devices assigned to him. He is able to see a chart containing the device consumption over a period of one day. Each device has a maximum consumption limit, and when that limit is reached, a warning is displayed to the user. 
<br />
The admin user is able to perform CRUD operations and assign devices and create other admin accounts.
<br />
A producer Java app will generate measurements and will send them to the backend via RabbitMq. Before storing them, the hourly consumption will be computed, and if it exceeds the maximum allowed, a warning will be sent to the front-end using WebSocket.
<br />
This project also includes a chatting capability, using the gRPC protocol. 

