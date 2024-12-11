CREATE TABLE IF NOT EXISTS public.accounts_users
(
    account_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT PK_ACCOUNTS_USERS PRIMARY KEY (account_id, user_id),
    CONSTRAINT FK_ACCOUNT FOREIGN KEY (account_id)
        REFERENCES public.users (id),
    CONSTRAINT FK_USERS FOREIGN KEY (user_id)
        REFERENCES public.account (id)
)