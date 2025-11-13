# Banking App - Full Stack Application

A full-stack banking application built with Spring Boot (backend) and Angular 19 (frontend).

## Technologies Used

### Backend
- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Security** with JWT authentication
- **Spring Data JPA** with Hibernate
- **MySQL** database
- **Lombok** for boilerplate reduction
- **ModelMapper** for DTO mapping
- **Springdoc OpenAPI 2.3.0** for API documentation

### Frontend
- **Angular 19** with standalone components
- **SCSS** for styling
- **RxJS** for reactive programming
- **TypeScript 5.6**

## Prerequisites

- **Java 17** or higher
- **Maven 3.8+**
- **Node.js 18+** and **npm**
- **MySQL 8.0+**

## Backend Setup

### 1. Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE banking_app;
```

### 2. Configure Application Properties

Update `src/main/resources/application.properties`:

```properties
spring.application.name=Banking-APP
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/banking_app?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_mysql_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
app.jwt-secret=your_jwt_secret_key_here_minimum_256_bits_for_HS256_algorithm
app.jwt-expiration-milliseconds=604800000
```

### 3. Run Backend

```bash
cd banking-app
mvn clean install
mvn spring-boot:run
```

Backend will start on **http://localhost:8080**

### 4. API Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Frontend Setup

### 1. Install Dependencies

```bash
cd banking-app-ui
npm install
```

### 2. Run Development Server

```bash
npm start
# or
ng serve
```

Frontend will start on **http://localhost:4200**

### 3. Build for Production

```bash
npm run build
# Output will be in dist/ folder
```

## Default Credentials

The application creates a default admin user on startup:

- **Username**: `admin`
- **Password**: `Admin@123`

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login and get JWT token

### Customers (Admin Only)
- `POST /api/customers` - Create customer (auto-creates account)
- `GET /api/customers` - List all customers

### Accounts
- `GET /api/accounts/user/{userId}` - Get account by user ID
- `POST /api/accounts/{accountNumber}/deposit` - Deposit money
- `POST /api/accounts/{accountNumber}/withdraw` - Withdraw money
- `POST /api/accounts/transfer` - Transfer money between accounts
- `GET /api/accounts/{accountNumber}/balance` - Get account balance
- `GET /api/accounts/{accountNumber}/transactions` - Get transaction history

## Data Model

### User (merged with Customer)
- One-to-One relationship with Account
- Contains authentication and profile information
- Fields: id, username, email, firstName, lastName, password, roles

### Account
- One-to-One relationship with User
- One-to-Many relationship with Transaction
- Fields: id, accountNumber, accountType, balance

### Transaction
- Many-to-One relationship with Account
- Types: CREDIT, DEBIT, TRANSFER
- Fields: id, type, amount, description, createdAt, referenceId

## Features

### Admin Features
- Create customers (automatically creates an account)
- View all customers
- Full access to all endpoints

### User Features
- View account details
- Deposit money
- Withdraw money
- Transfer money to other accounts
- View transaction history

## Testing with Postman

Import the Postman collection and environment from the `postman/` folder:

1. Import `BankingApp.postman_collection.json`
2. Login using the admin credentials
4. JWT token will be automatically extracted and used for subsequent requests

## Security

- **JWT-based authentication** with stateless sessions
- **BCrypt password encoding**
- **Role-based access control** (ROLE_ADMIN, ROLE_USER)
- **CORS configuration** for Angular frontend
- **HTTP security** with CSRF disabled for REST API

## Development Notes

- Backend uses `ddl-auto=update` for automatic schema updates during development
- Account numbers are generated using `ACCT + timestamp` (should use UUID in production)
- Auto-account creation happens inline when admin creates a customer
- Frontend uses standalone components (Angular 19 pattern)
- JWT tokens are stored in localStorage
- All HTTP requests include JWT token via interceptor

## Troubleshooting

### Backend Issues
- Ensure MySQL is running and credentials are correct
- Check if port 8080 is available
- Verify Java 17 is installed: `java -version`

### Frontend Issues
- Clear node_modules and reinstall: `rm -rf node_modules package-lock.json && npm install`
- Check if port 4200 is available
- Verify Node.js version: `node -v` (should be 18+)

### CORS Issues
- Ensure `SecurityConfig.java` has proper CORS configuration
- Frontend must run on http://localhost:4200

## License

This project is for educational purposes.

## Future Enhancements

- Implement pagination for transaction history
- Add email notifications for transactions
- Implement account statements (PDF export)
- Add two-factor authentication
- Use UUID for account number generation
- Add password reset functionality
- Implement rate limiting
- Add audit logging
