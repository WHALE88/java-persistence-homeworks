INSERT INTO advisory_schema.users
(id, username, email, "type")
VALUES(1, 'Maks', 'Email@gmail.com', 'applicant'::advisory_schema.user_type);

INSERT INTO advisory_schema.applicants
(id, first_name, last_name, ssn)
VALUES(1, 'Maks', 'Oliinyk', '213214');

INSERT INTO advisory_schema.addresses
(applicant_id, city, street, "number", zip, apt)
VALUES(1, 'Kyiv', 'Street', '12S', '44446', NULL);

INSERT INTO advisory_schema.phone_numbers
(applicant_id, "number", "type")
VALUES(1, '0501228954', 'work'::advisory_schema.phone_number_type);

INSERT INTO advisory_schema.applications
(id, "money", status, created_at, assigned_at, applicant_id, advisor_id)
VALUES(1, 200.3200, 'new'::advisory_schema.application_status, '2023-12-20 22:30:23.995', NULL, 1, NULL);

--won't be work, because of trigger
--INSERT INTO advisory_schema.advisors
--(id, "role")
--VALUES(1, 'partner'::advisory_schema.advisors_role);

INSERT INTO advisory_schema.users
(id, username, email, "type")
VALUES(2, 'Oleg', 'oleg@gmail.com', 'advisor'::advisory_schema.user_type);

INSERT INTO advisory_schema.advisors
(id, "role")
VALUES(2, 'partner'::advisory_schema.advisors_role);

UPDATE advisory_schema.applications
SET "money"=200.3200, status='assigned'::advisory_schema.application_status, created_at='2023-12-20 22:30:23.995', assigned_at=NULL, applicant_id=1, advisor_id=2
WHERE id=1;



