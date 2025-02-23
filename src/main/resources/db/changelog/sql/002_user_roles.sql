-- Создание таблицы users
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    uid        varchar(128) not null UNIQUE,        -- Уникальный идентификатор пользователя
    login      VARCHAR(128) NOT NULL UNIQUE,        -- Логин пользователя (уникальный)
    email      VARCHAR(128) NOT NULL,               -- Электронная почта пользователя
    first_name VARCHAR(512) NOT NULL,               -- Имя пользователя
    surname    VARCHAR(512) NOT NULL,               -- Фамилия пользователя
    patronymic VARCHAR(512),                        -- Отчество пользователя (необязательное поле)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Дата создания записи
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Дата обновления записи
);

COMMENT ON TABLE users IS 'Таблица для хранения информации о пользователях';
COMMENT ON COLUMN users.id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN users.login IS 'Логин пользователя (уникальный)';
COMMENT ON COLUMN users.email IS 'Электронная почта пользователя';
COMMENT ON COLUMN users.first_name IS 'Имя пользователя';
COMMENT ON COLUMN users.surname IS 'Фамилия пользователя';
COMMENT ON COLUMN users.patronymic IS 'Отчество пользователя (необязательное поле)';
COMMENT ON COLUMN users.created_at IS 'Дата создания записи';
COMMENT ON COLUMN users.updated_at IS 'Дата обновления записи';

-- Создание таблицы roles
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,         -- Уникальный идентификатор роли
    name VARCHAR(50) NOT NULL UNIQUE -- Название роли (уникальное)
);

COMMENT ON TABLE roles IS 'Таблица для хранения ролей пользователей';
COMMENT ON COLUMN roles.id IS 'Уникальный идентификатор роли';
COMMENT ON COLUMN roles.name IS 'Название роли (уникальное)';

-- Создание связующей таблицы user_roles
CREATE TABLE user_roles
(
    id         SERIAL PRIMARY KEY,
    user_login VARCHAR(50) NOT NULL,                                     -- Логин пользователя
    role_id    INT         NOT NULL,                                     -- Идентификатор роли
    FOREIGN KEY (user_login) REFERENCES users (login) ON DELETE CASCADE, -- Внешний ключ на таблицу users
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,       -- Внешний ключ на таблицу roles
    unique (user_login, role_id)
);

COMMENT ON TABLE user_roles IS 'Таблица для связи пользователей и их ролей';
COMMENT ON COLUMN user_roles.user_login IS 'Логин пользователя';
COMMENT ON COLUMN user_roles.role_id IS 'Идентификатор роли';

-- Опционально: создание индексов для ускорения поиска
CREATE INDEX idx_users_login ON users (login);
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_roles_name ON roles (name);

