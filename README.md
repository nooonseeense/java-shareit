# Shareit
Service for sharing things. Sharing as an economy is currently using an increasing polarity.
# Technologies
- Java 11
- Spring Boot
- Spring Data JPA
- Hibernate ORM
- REST API
- Lombok
- PostgreSQL
- Maven

# REST API

### Booking service

**POST /bookings**
Adding a new booking

**PATCH /bookings/{bookingId}?approved={approved}**
Approving or declining a booking

**GET /bookings/{bookingId}**
Getting a specific booking

**GET /bookings?state={state}&from={from}&size={size}**
Getting a list of a user's bookings

**GET /bookings/owner?state={state}&from={from}&size={size}**
Getting a list of the user's item reservations

### Item service

**POST /items**
Adding a new item

**PATCH /items/{id}**
Change a thing

**GET /items/{id}**
Getting a specific item

**GET /items?from={from}&size={size}**
Getting a list of user items

**POST /items/{itemId}/comment**
Create a review for an item

### User service
POST /users 
Adding a user

PATCH /users/{id}
User change

GET /users/{id}
Getting a specific user

GET /users
Getting a list of all users
