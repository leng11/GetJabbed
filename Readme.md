### USER-SERVICE 

# Steps to Use the User-Service 

- Go to application.proprties and update the MySql Root password 
- Start the springboot server 

```
 mvn spring-boot:run

 ```


 - Go to postman and write the following api(s)

```
localhost:8080/v1/vaccine/users/register

```

```

{
    "name":"Subho",
    "official_id":444,
    "address":"INDIA"
    }

```

- This Will add the particular user to the USERS TABLE 

```
localhost:8080/vaccine/users/list?official_id=<your number>

```

- This will Fetch the data based on the official id

```
localhost:8080/v1/vaccine/users/add

```

```

{
    "name":"subho",
    "date": "1999-05-12",
    "vaccine_id":233,
    "location":"INDIA",
    "officialId":444,
    "lot":4

}

```
- This will add a particular certificate in the CERTIFICATE Table

```

localhost:8080/v1/vaccine/users/retriveCertificate?officialId = <your number>

```

- This will fetch the certificate based on the particular user official id or SSN number

