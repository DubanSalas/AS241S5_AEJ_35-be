CREATE TABLE IF NOT EXISTS youtube_video (
    id SERIAL PRIMARY KEY NOT NULL,
    query VARCHAR(255) NOT NULL,
    title VARCHAR(500),
    channel VARCHAR(255),
    video_id VARCHAR(100),
    thumbnail_url VARCHAR(500),
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS spotify_track (
    id SERIAL PRIMARY KEY NOT NULL,
    artist VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    lyrics TEXT,
    album VARCHAR(255),
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP
);
