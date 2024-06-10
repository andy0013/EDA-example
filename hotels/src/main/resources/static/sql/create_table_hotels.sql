CREATE TABLE hotels (
    id SERIAL PRIMARY KEY,
    hotelId VARCHAR(255),
    name VARCHAR(255),
    address VARCHAR(255),
    number_of_rooms INT,
    rating DOUBLE PRECISION
);
