-- Вставки в candidate_status
INSERT INTO candidate_status (status, color) VALUES ('В роботі', '808080');
INSERT INTO candidate_status (status, color) VALUES ( 'ВЛК', '808080');
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
