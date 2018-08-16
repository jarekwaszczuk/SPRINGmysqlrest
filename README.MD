# MySQLREST application

## Utworzenie produkcyjnej bazy danych

```
create database db_mysqlrest;
create user 'mysqlrestuser'@'localhost' identified by 'ThePassword';
grant all on db_mysqlrest.* to 'mysqlrestuser'@'localhost';
```

## Utworzenie bazy danych na potrzeby profilu testowego

```
create database db_mysqlrest_test;
create user 'mysqlrestuser_test'@'localhost' identified by 'ThePassword';
grant all on db_mysqlrest_test.* to 'mysqlrestuser_test'@'localhost';
```

## Uruchomienie

```
mvnw spring-boot:run
```
    
## Przydatne żądania HTTP

### Utworzenie użytkownika

```
POST http://localhost:7777/users/
Content-Type: application/json

{
"firstName":"Adam",
"lastName":"Slodowy",
"email":"aslodowy@poczta.pl",
"password":"topsecret"
}
```

### Odczytanie wszystkich użytkowników

```
GET http://localhost:7777/users
```

### Odczytanie jednego użytkownika

```
GET http://localhost:7777/users/1
```

### Zmiana hasła użytkownika

```
POST http://localhost:7777/users/1/passwordDTO
Content-Type: application/json

{
"oldPassword":"oldPassword",
"newPassword":"newPassword"
}
```

### Aktywacja użytkownika

```
PUT http://localhost:7777/users/1/activate/905fafb4-c65c-4722-a6cb-a57bf8bb6ec3
```