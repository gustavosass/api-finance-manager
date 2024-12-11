CREATE TABLE public.users (
    id serial PRIMARY KEY,
    full_name character varying(255),
    username character varying(255) UNIQUE,
    password character varying(255),
    account_non_expired bit(1) DEFAULT NULL,
  	account_non_locked bit(1) DEFAULT NULL,
  	credentials_non_expired bit(1) DEFAULT NULL,
  	enabled bit(1) DEFAULT NULL
);