CREATE TABLE Users (
                      id VARCHAR(255) PRIMARY KEY,
                      password VARCHAR(255) NOT NULL,
                      name VARCHAR(255),
                      phoneNumber VARCHAR(20),
                      gender VARCHAR(10),
                      role VARCHAR(50),
                      email VARCHAR(255),
                      birthYear varchar(12)
);
drop table Users;