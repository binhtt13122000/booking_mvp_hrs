# MVP Hotel Booking

### Postman Collection
[Download Postman Collection](https://github.com/binhtt13122000/booking_mvp_hrs/blob/main/MVP_Hotel.postman_collection.json)
### How to install
#### 1. Requirements:
- Docker
- Docker-compose
- maven 3.6
#### 2. Deploy from Source Code:
Need maven 3.6 to package this project
```shell
mvn clean package
```
Then, add docker.env file to root folder (because application need AWS to run)

Then, run docker compose:

```shell
 docker-compose --env-file docker.env up --build
```

### Swagger

http://localhost:8080/swagger-ui/index.html

### How to run

Because we assume all users have the same role and permission levels, so the login function is only for authentication.
Authorization will be implemented in the future.

#### Step 1: Create new User

```shell
curl --location 'localhost:8080/api/v1/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "admin@gmail.com",
    "password": "admin"
}'
```

#### Step 2: Login with User
```shell
curl --location 'localhost:8080/api/v1/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "admin@gmail.com",
    "password": "admin2"
}'
```

#### Step 3: Test booking service
- Add JWT to Authorization: Bearer ...
- Run step by step with postman collection

#### 4. Technical:
- Spring
- Java
- Terraform (Provisioning RDS and Param Storage)
- Configs are stored on AWS Param Storage

#### 5. Future Implement:
- Because Application run on local env, so we need add IAM account key to application (Must change to IAM role when deploying to AWS or use OIDC)
- Use micrometer and AWS cloudwatch to collect metric in future
- Unit tests are being missed (only apply for ProductService)
