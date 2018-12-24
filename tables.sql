CREATE TABLE usuario(
	id INT PRIMARY KEY,
	nome VARCHAR(255),
	updated BOOLEAN,
	deleted BOOLEAN
);

CREATE TABLE aux_lock(
	last_id INT,
	instance VARCHAR(255)
);