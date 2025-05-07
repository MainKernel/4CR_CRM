-- Вставки в candidate_status
INSERT INTO candidate_status (status, color) VALUES ('В роботі', '808080');
INSERT INTO candidate_status (status, color) VALUES ('ВЛК', '808080');
INSERT INTO candidate_status (status, color) VALUES ('Справа сформована', 'D2CE00');
INSERT INTO candidate_status (status, color) VALUES ('На БЗВП', '10FF00');
INSERT INTO candidate_status (status, color) VALUES ('У В/Ч', '10FF00');
INSERT INTO candidate_status (status, color) VALUES ('В роздумах', '000060');
INSERT INTO candidate_status (status, color) VALUES ('Відмова В/Ч', 'C20000');
INSERT INTO candidate_status (status, color) VALUES ('Відмова кандидата', 'C20000');
INSERT INTO candidate_status (status, color) VALUES ('Н/Д', 'C20000');
INSERT INTO candidate_status (status, color) VALUES ('Відмова ТЦК/ВЛК', 'C20000');
INSERT INTO candidate_status (status, color) VALUES ('Не погоджений ГШ', 'C20000');

-- Вставки в duty_type
INSERT INTO duty_type (status, color) VALUES ('Контракт', 'FFFFFF');
INSERT INTO duty_type (status, color) VALUES ('Контракт 18-24', 'FFFFFF');
INSERT INTO duty_type (status, color) VALUES ('Мобілізація', 'FFFFFF');

-- Вставки в psychological_test
INSERT INTO psychological_test (status, color) VALUES ('Пройдено успішно', '10FF00');
INSERT INTO psychological_test (status, color) VALUES ('Не проходив', 'FFFFFF');
INSERT INTO psychological_test (status, color) VALUES ('Не пройшов тестування', 'E30000');
INSERT INTO psychological_test (status, color) VALUES ('Результати отримано', '00DA00' );

-- Вставки в military_unit
INSERT INTO military_unit (unit_name, strict_unit_name, comment) VALUES ('Військова частина А1234' , '110 ОБр', 'Передано до Морської піхоти');
INSERT INTO military_unit (unit_name, comment) VALUES ('Військова частина A5678', '211 ОБр', 'Пріоритетна частина | Контракти 18-24');

-- Вставки в candidates
INSERT INTO candidates (
    surname, name, middle_name, status, phone_number, duty_type, military_unit,
    recommendation_letter, recruiter, curator, escorted_by,
    psychological_test, enrolment_order, order_number, territory_center_record
) VALUES
('Іваненко', 'Петро', 'Миколайович', 1, '+380501112233', 1, 1,
 '2025-04-01', 'Рекрутер А', 'Куратор 1', 'Провідник 1', 1, '2025-04-10', '№123', 'ЦТК 1'),
('Сидоренко', 'Олена', 'Іванівна', 2, '+380671234567', 2, 2,
 '2025-04-05', 'Рекрутер Б', 'Куратор 2', 'Провідник 2', 2, NULL, NULL, 'ЦТК 2');

-- Вставки в candidate_comments
INSERT INTO candidate_comments (comment_date, commented_by, comment, candidate_id)
VALUES
('2025-04-02', 'Куратор 1', 'Кандидат має високий рівень мотивації.', 1),
('2025-04-06', 'Куратор 2', 'Потрібно надати додаткові документи.', 2);

-- Вставки в candidate_documents
INSERT INTO candidate_documents (
    file_name, file_type, file_path, uploaded_date, uploaded_by, type, candidate_id
) VALUES
('passport.pdf', 'application/pdf', '/docs/passport.pdf', '2025-04-01', 'Рекрутер А', 'Паспорт', 1),
('certificate.jpg', 'image/jpeg', '/docs/certificate.jpg', '2025-04-05', 'Рекрутер Б', 'Сертифікат', 2);
