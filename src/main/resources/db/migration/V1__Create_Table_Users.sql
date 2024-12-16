CREATE TABLE public.users (
    id serial PRIMARY KEY,
    full_name character varying(255),
    username character varying(255) UNIQUE,
    password character varying(255),
    account_non_expired boolean DEFAULT NULL,
  	account_non_locked boolean DEFAULT NULL,
  	credentials_non_expired boolean DEFAULT NULL,
  	enabled boolean DEFAULT NULL,
  	created_at timestamp,
  	updated_at timestamp
);