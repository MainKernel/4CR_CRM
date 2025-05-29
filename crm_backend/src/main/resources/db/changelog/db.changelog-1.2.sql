-- Вставки в candidate_status
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('В роботі', '808080', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ( 'ВЛК', '808080', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('Справа сформована', 'D2CE00', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('На БЗВП', '10FF00', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('У В/Ч', '10FF00', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('В роздумах', '000060', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('Відмова В/Ч', 'C20000', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('Відмова кандидата', 'C20000', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('Н/Д', 'C20000', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('Відмова ТЦК/ВЛК', 'C20000', FALSE);
INSERT INTO candidate_status (status, color, is_disabled) VALUES ('Не погоджений ГШ', 'C20000', FALSE);

-- Вставки в duty_type
INSERT INTO duty_type (status, color, is_disabled) VALUES ('Контракт', 'FFFFFF', FALSE);
INSERT INTO duty_type (status, color, is_disabled) VALUES ('Контракт 18-24', 'FFFFFF', FALSE);
INSERT INTO duty_type (status, color, is_disabled) VALUES ('Мобілізація', 'FFFFFF', FALSE);

-- Вставки в psychological_test
INSERT INTO psychological_test (status, color, is_disabled) VALUES ('Пройдено успішно', '10FF00', FALSE);
INSERT INTO psychological_test (status, color, is_disabled) VALUES ('Не проходив', 'FFFFFF', FALSE);
INSERT INTO psychological_test (status, color, is_disabled) VALUES ('Не пройшов тестування', 'E30000', FALSE);
INSERT INTO psychological_test (status, color, is_disabled) VALUES ('Результати отримано', '00DA00', FALSE);
