-- step 1. create the database by the name api_demo

-- step 2. create the customer table with the following command

CREATE TABLE customer(
                         customer_id SERIAL PRIMARY KEY,
                         first_name VARCHAR(200) NOT NULL,
                         last_name VARCHAR(200),
                         email VARCHAR(200) NOT NULL,
                         identity_proof_path VARCHAR(200) NOT NULL,
                         address_proof_path VARCHAR(200) NOT NULL
);