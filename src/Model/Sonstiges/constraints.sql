%%%%%%%%%%%%%%%%%%%% student_status %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
alter table student_status drop constraint "student_status_status_typ_fkey";

ALTER TABLE student_status
ADD CONSTRAINT student_status_status_typ_fkey
   FOREIGN KEY (status_typ)
   REFERENCES status(status_typ)
   ON DELETE CASCADE ON UPDATE CASCADE;

%%%%%%%%%%%%%%%%%%%%%% m_a %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
alter table m_a drop constraint "m_a_aktivitaet_name_fkey";

ALTER TABLE m_a
ADD CONSTRAINT m_a_aktivitaet_name_fkey
    FOREIGN KEY (aktivitaet_name)
    REFERENCES aktivitaet(aktivitaet_name)
    ON DELETE CASCADE ON UPDATE CASCADE;


alter table m_a drop constraint "m_a_massnahme_name_fkey";

ALTER TABLE m_a
ADD CONSTRAINT m_a_massnahme_name_fkey
    FOREIGN KEY (massnahme_name)
    REFERENCES massnahme(massnahme_name)
    ON DELETE CASCADE ON UPDATE CASCADE;

%%%%%%%%%%%%%%%%%%%% student_mob %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
"student_mob_id_s_m_a_fkey" FOREIGN KEY (id_s_m_a) REFERENCES s_m_a(id)

ALTER TABLE student_mob DROP CONSTRAINT "student_mob_id_s_m_a_fkey";

ALTER TABLE student_mob
ADD CONSTRAINT student_mob_id_s_m_a_fkey
    FOREIGN KEY (id_s_m_a)
    REFERENCES s_m_a(id)
    ON DELETE CASCADE;

%%%%%%%%%%%%%%%%%%%%%% s_m_a %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

"verschiedene_akt" UNIQUE CONSTRAINT, btree (urz, id_m_a, semester)

alter table s_m_a drop constraint "verschiedene_akt";

ALTER TABLE s_m_a ADD CONSTRAINT verschiedene_akt UNIQUE (urz, id_m_a);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

"s_m_a_id_m_a_fkey" FOREIGN KEY (id_m_a) REFERENCES m_a(id)
"s_m_a_urz_fkey" FOREIGN KEY (urz) REFERENCES student(urz)

ALTER TABLE s_m_a DROP CONSTRAINT "s_m_a_id_m_a_fkey";

ALTER TABLE s_m_a DROP CONSTRAINT "s_m_a_urz_fkey";


ALTER TABLE s_m_a
ADD CONSTRAINT s_m_a_urz_fkey
        FOREIGN KEY (urz)
        REFERENCES student(urz)
        ON DELETE CASCADE;

    ALTER TABLE m_a ADD CONSTRAINT unique_aktivitaet UNIQUE (aktivitaet_name);


