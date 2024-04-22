CREATE TABLE public."attachment"
(
    id            serial4   NOT NULL,
    name          varchar   NOT NULL,
    page_count    int4      NOT NULL,
    "path"        varchar   NOT NULL,
    creation_date timestamp NOT NULL,
    employee_id   int4      NOT NULL,
    CONSTRAINT attachment_pk PRIMARY KEY (id),
    CONSTRAINT attachment_fk FOREIGN KEY (employee_id) REFERENCES public.user (id)
);

CREATE TABLE public.user
(
    id            serial4 NOT NULL,
    first_name    varchar NOT NULL,
    middle_name   varchar NULL,
    second_name   varchar NOT NULL,
    phone_number  varchar NULL,
    card_number   int4 NULL,
    contract_type varchar NOT NULL,
    CONSTRAINT employee_pk PRIMARY KEY (id)
);

CREATE TABLE public.report
(
    id          serial4   NOT NULL,
    "name"      varchar   NOT NULL,
    description varchar   NOT NULL,
    date_filed  timestamp NOT NULL,
    task_id     int4      NOT NULL,
    CONSTRAINT report_pk PRIMARY KEY (id),
    CONSTRAINT report_fk_1 FOREIGN KEY (task_id) REFERENCES public.task (id)
);

CREATE TABLE public.task
(
    id          serial4 NOT NULL,
    "name"      varchar NOT NULL,
    description varchar NOT NULL,
    due_time    timestamp NULL,
    assignee    int4    NOT NULL,
    status      varchar NOT NULL,
    CONSTRAINT task_pk PRIMARY KEY (id),
    CONSTRAINT task_fk FOREIGN KEY (assignee) REFERENCES public.user (id)
);

CREATE TABLE public.vacation
(
    id          int4      NOT NULL,
    time_start  timestamp NOT NULL,
    time_finish timestamp NOT NULL,
    employee_id int4      NOT NULL,
    description varchar NULL,
    status      varchar   NOT NULL,
    CONSTRAINT vacation_pk PRIMARY KEY (id),
    CONSTRAINT vacation_fk FOREIGN KEY (employee_id) REFERENCES public.user (id)
);

CREATE TABLE public.work_hours
(
    id          serial4   NOT NULL,
    time_start  timestamp NOT NULL,
    time_finish timestamp NOT NULL,
    employee_id int4      NOT NULL,
    "comment"   varchar NULL,
    CONSTRAINT work_hours_pk PRIMARY KEY (id),
    CONSTRAINT work_hours_fk FOREIGN KEY (employee_id) REFERENCES public.user (id)
);