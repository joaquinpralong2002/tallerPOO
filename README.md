# Taller de Programación Orientada a Objetos
## Alumnos: 
- Bozzo Emanuel
- Casabillanos Jesús
- Pralong Gastón
- Pralong Joaquín
## Aclaraciones sobre la base de datos
En base a los argumentos pasados a la línea de comandos se podrá alterar la configuración de la base de datos (el proyecto fue programado para bases de datos MySQL).
### Args[0] : si se le pasa un 1, se crearán objetos de prueba. Si se pasa un 0, no lo hará.
### Args[1] : url
### Args[2] : nombre de usuario
### Args[3] : contraseña
Si no se pasa ningún comando, el programa intentará utilizar una base de datos local (sin crear ningún objeto) con los siguientes datos.
- Url: "jdbc:mysql://localhost:3306/tallerdb"
- Usuario: "usuario"
- Contraseña: "basededatostallerpoo123"
- Driver: "com.mysql.cj.jdbc.Driver"
