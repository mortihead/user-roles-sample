-- Добавление ролей
INSERT INTO roles (name) VALUES
('admin'),        -- Администратор
('user'),         -- Обычный пользователь
('moderator'),    -- Модератор
('manager_toyota'), -- Менеджер Тойота
('manager_nissan'); -- Менеджер Ниссан

-- Добавление пользователей
INSERT INTO users (login, uid, email, first_name, surname, patronymic) VALUES
('ivanova.ai', 'f26e1785-1ab9-4b4e-bb51-e59868dfddde', 'alice@example.com', 'Алиса', 'Иванова', 'Петровна'),
('smirnov.b', '7c4cca2d-3d81-4641-a5e1-919c53853c0b', 'bob@example.com', 'Боб', 'Смирнов', NULL),
('petrov.is', '5adfa472-db0b-41fb-8c6b-9344f5bbe727','tom@example.com', 'Иван', 'Петров', 'Сергеевич'),
('sidorova.oi', 'dc14096a-033d-4ad8-a7c6-a528505e7325', 'ivan@example.com', 'Ольга', 'Сидорова', 'Ивановна');

-- Назначение ролей пользователям
INSERT INTO user_roles (user_login, role_id) VALUES
('ivanova.ai', 1),
('ivanova.ai', 2),
('petrov.is', 2),
('petrov.is', 4),
('sidorova.oi', 5);