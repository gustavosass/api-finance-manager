CREATE TABLE IF NOT EXISTS public.transaction_item
(
    id serial primary key,
    installment_number integer NOT NULL,
    value real,
    status character varying(255),
    date_payment timestamp(6) without time zone,
    due_date timestamp(6) without time zone,
    transaction_id bigint,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    CONSTRAINT fk1wc2dvhj3oos47in473fqi3q8 FOREIGN KEY (transaction_id)
        REFERENCES public.transaction (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT transaction_item_status_check CHECK (status::text = ANY (ARRAY['PAID'::character varying, 'OPEN'::character varying]::text[]))
)