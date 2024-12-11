CREATE TABLE IF NOT EXISTS public.transaction
(
    id serial primary key,
    installment_numbers integer NOT NULL,
    value real,
    account_id bigint,
    due_date timestamp(6) without time zone,
    accounting_entry_type character varying(255),
    status character varying(255),
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    CONSTRAINT FK_ACCOUNT_ID FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT transaction_accounting_entry_type_check CHECK (accounting_entry_type::text = ANY (ARRAY['CREDIT'::character varying, 'DEBIT'::character varying]::text[])),
    CONSTRAINT transaction_status_check CHECK (status::text = ANY (ARRAY['PAID'::character varying, 'OPEN'::character varying]::text[]))
)