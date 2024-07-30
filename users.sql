CREATE TABLE Users (
                      id VARCHAR(255) PRIMARY KEY,
                      password VARCHAR(255) NOT NULL,
                      name VARCHAR(255),
                      phoneNumber VARCHAR(20),
                      gender VARCHAR(10),
                      role VARCHAR(50),
                      email VARCHAR(255),
                      birthYear varchar(12)
            ,region varchar(50)
);
INSERT INTO Users (id, password, name, phoneNumber, gender, role, email, birthYear) VALUES
                                                                                        ('user1', 'password123', 'Alice Johnson', '555-1234', 'Female', 'User', 'alice.johnson@example.com', '1985'),
                                                                                        ('user2', 'securePass456', 'Bob Smith', '555-5678', 'Male', 'Admin', 'bob.smith@example.com', '1990'),

                                                                                        ('user3', 'myPassword789', 'Carol White', '555-8765', 'Female', 'User', 'carol.white@example.com', '1988');


describe  users;