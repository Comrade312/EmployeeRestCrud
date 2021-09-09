DELETE FROM EMPLOYEES;

ALTER SEQUENCE employees_employee_id_seq RESTART WITH 1;

INSERT INTO
	EMPLOYEES(first_name, last_name, job_title, department_id, gender, date_of_birth)
VALUES
	('Katya', 'Pushkina', 'HR assistant', 2, 'FEMALE', '1990-04-22'),
	('Roza', 'Kapushkina', 'Senior HR', 2, 'FEMALE', '1989-09-11'),
	('Oleg', 'Murashko', 'Support specialist', 1, 'MALE', '1978-08-10'),
	('Roman', 'Senkevich', 'Support specialist', 1, 'MALE', '1988-07-09'),
	('Artem', 'Butyagin', 'Junior Java Developer', 3, 'MALE', '1999-01-07');

