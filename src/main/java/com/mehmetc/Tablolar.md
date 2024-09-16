CREATE TABLE users (
user_id SERIAL PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,  -- Şifreleri güvenli bir şekilde saklayın
profile_image_url VARCHAR(255),  -- Profil resmi için URL
bio TEXT,  -- Kullanıcı biyografisi
status VARCHAR(20) DEFAULT 'active',  -- Kullanıcı durumu (active, suspended, deleted)
created_at BIGINT DEFAULT EXTRACT(epoch FROM now()),  -- epoch zamanında oluşturulma tarihi
updated_at BIGINT DEFAULT EXTRACT(epoch FROM now())  -- epoch zamanında güncellenme tarihi
);

CREATE TABLE videos (
video_id SERIAL PRIMARY KEY,
user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
title VARCHAR(255) NOT NULL,
description TEXT,
video_url VARCHAR(255) NOT NULL,
thumbnail_url VARCHAR(255),  -- Video küçük resmi URL'si
category VARCHAR(100),  -- Video kategorisi
tags TEXT[],  -- Videoya ait etiketler (array olarak saklanabilir)
views INT DEFAULT 0,  -- İzlenme sayısı
likes_count INT DEFAULT 0,  -- Beğeni sayısı
dislikes_count INT DEFAULT 0,  -- Beğenmeme sayısı
created_at BIGINT DEFAULT EXTRACT(epoch FROM now()),  -- epoch zamanında oluşturulma tarihi
updated_at BIGINT DEFAULT EXTRACT(epoch FROM now())  -- epoch zamanında güncellenme tarihi
);

CREATE TABLE likes (
like_id SERIAL PRIMARY KEY,
video_id INT REFERENCES videos(video_id) ON DELETE CASCADE,
user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
reaction_type VARCHAR(10) CHECK (reaction_type IN ('like', 'dislike')),
created_at BIGINT DEFAULT EXTRACT(epoch FROM now()),  -- epoch zamanında oluşturulma tarihi
updated_at BIGINT DEFAULT EXTRACT(epoch FROM now()),  -- epoch zamanında güncellenme tarihi
UNIQUE (video_id, user_id)  -- Her kullanıcı için bir video başına yalnızca bir tepki
);

CREATE TABLE comments (
comment_id SERIAL PRIMARY KEY,
video_id INT REFERENCES videos(video_id) ON DELETE CASCADE,
user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
comment_text TEXT NOT NULL,
parent_comment_id INT REFERENCES comments(comment_id) ON DELETE SET NULL,  -- Yanıtlanan yorum
likes_count INT DEFAULT 0,  -- Yorumun beğeni sayısı
dislikes_count INT DEFAULT 0,  -- Yorumun beğenmeme sayısı
created_at BIGINT DEFAULT EXTRACT(epoch FROM now()),  -- epoch zamanında oluşturulma tarihi
updated_at BIGINT DEFAULT EXTRACT(epoch FROM now())  -- epoch zamanında güncellenme tarihi
);