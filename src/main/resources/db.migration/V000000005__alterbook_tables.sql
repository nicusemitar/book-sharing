ALTER TABLE IF EXISTS t_book
    ADD COLUMN book_status varchar(255) NOT NULL;
ALTER TABLE IF EXISTS t_book
    ALTER COLUMN book_status SET DEFAULT 'FREE'