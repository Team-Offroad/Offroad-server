INSERT INTO Member (id, name, email, gender, age, nick_name, current_character_name, current_emblem_name, sub, social_platform, created_at, updated_at) VALUES (1, 'John Doe', 'john.doe@example.com', 'MALE', 30, 'johnny', 'CharacterA', '오프로드 스타터', 'sub123', 'GOOGLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO place (name, address, short_introduction, place_category, latitude, longitude, created_at, updated_at) VALUES
                                                                                                                       ('Central Park', 'New York, NY, USA', 'A large public park in New York City.', 'PARK', 40.785091, -73.968285, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                       ('Louvre Museum', 'Paris, France', 'A historic monument and the world', 'CULTURE', 48.860611, 2.337644, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                        ('Bondi Beach', 'Sydney, Australia', 'A popular beach and the name of the surrounding suburb in Sydney.', 'SPORT', -33.891475, 151.276684, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO character (name, description, character_base_image_url, character_code, created_at, updated_at) VALUES
                                                                                                                ('Mario', 'A small, chubby Italian plumber who goes on adventures.', 'https://example.com/mario.png', 'MARIO123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                ('Luigi', 'Marios younger brother, taller and thinner.', 'https://example.com/luigi.png', 'LUIGI123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                ('Peach', 'The princess of the Mushroom Kingdom.', 'https://example.com/peach.png', 'PEACH123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO character_motion (character_id, place_category, motion_image_url, created_at, updated_at) VALUES
                                                                                                          (1, 'PARK', 'https://example.com/mario_park.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                          (2, 'CULTURE', 'https://example.com/luigi_culture.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                          (3, 'SPORT', 'https://example.com/peach_sport.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO gained_character (member_id, character_id, created_at, updated_at) VALUES
                                                                                   (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                   (1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                   (1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO gained_character_motion (member_id, character_motion_id, created_at, updated_at) VALUES
                                                                                                 (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 (1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 (1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);