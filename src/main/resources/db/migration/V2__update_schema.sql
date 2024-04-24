-- Making user_id unique
ALTER TABLE avatars
ADD CONSTRAINT unique_user_id UNIQUE (user_id);