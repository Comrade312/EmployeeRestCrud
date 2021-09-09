INSERT INTO
	DEPARTMENTS(name)
VALUES
	('Tech support'),
	('HR'),
	('Dev department');

INSERT INTO
	EMPLOYEES(first_name, last_name, job_title, gender, date_of_birth)
VALUES
	('Alla', 'Kognitok', 'Cleaner', 'FEMALE', '1975-11-10'),
	('Larissa', 'Labuda', 'Cleaner', 'FEMALE', '1980-05-10'),
	('Vasiliy', 'Vasilyevich', 'Repairer', 'MALE', '1969-08-15');

INSERT INTO
	EMPLOYEES(first_name, last_name, job_title, department_id, gender, date_of_birth)
VALUES
	('Katya', 'Pushkina', 'HR assistant', 2, 'FEMALE', '1990-04-22'),
	('Roza', 'Kapushkina', 'Senior HR', 2, 'FEMALE', '1989-09-11'),
	('Oleg', 'Murashko', 'Support specialist', 1, 'MALE', '1978-08-10'),
	('Roman', 'Senkevich', 'Support specialist', 1, 'MALE', '1988-07-09'),
	('Maksim', 'Shurygin', 'Support specialist', 1, 'MALE', '1998-11-06'),
	('Artem', 'Butyagin', 'Junior Java Developer', 3, 'MALE', '1999-01-07'),
	('Oleg', 'Krisevich', 'Middle Java Developer', 3, 'MALE', '1995-06-16'),
	('Sergey', 'Zdasykevich', 'Senior Java Developer', 3, 'MALE', '1993-05-25'),
	('Vladimir', 'Milauskas', 'Team Lead', 3, 'MALE', '1990-10-02');