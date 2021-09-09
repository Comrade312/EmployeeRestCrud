DELETE FROM DEPARTMENTS;

ALTER SEQUENCE departments_department_id_seq RESTART WITH 1;

INSERT INTO
	DEPARTMENTS(name)
VALUES
	('Tech'),
	('HR'),
	('Dev');


