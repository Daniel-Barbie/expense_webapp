CREATE TABLE expense (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    amount FLOAT(53) NOT NULL,
    userid UUID NOT NULL,
    date DATE NOT NULL
);