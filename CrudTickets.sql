create table tbUsuario(
UUID_Usuario varchar2(50) primary key not null,
nombre varchar2(50) not null,
correo varchar2(50) not null,
contrasena varchar2(50) not null                  
);

create table tbTicket(
NumeroTicket int primary key, //uuid?
tituloTicket varchar2(100) not null,
descripcion varchar2(1000) not null,
fechaCreacion date not null,
estadoTicket int not null,
fechaFinalizacion date not null,

usuario varchar2(50),
constraint FK_tbUsuario_Id_Usuario
foreign key(usuario)
references tbUsuario(UUID_Usuario)
);