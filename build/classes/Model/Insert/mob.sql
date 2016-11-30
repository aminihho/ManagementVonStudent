CREATE OR REPLACE FUNCTION public.mob(text)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$

DECLARE
    name text;


BEGIN

 SELECT aktivitaet_name into name FROM aktivitaet WHERE beschreibung = $1;

 RAISE NOTICE 'der Name der Aktivitaet ist %)', name;

 IF substr(name, 1, 2) = 'M_'

 THEN
     RETURN 1;

    ELSE
        RETURN 0;

 END IF;

END;
$function$

CREATE OR REPLACE FUNCTION public.mobName(text)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$

DECLARE
    name text;


BEGIN

 IF substr($1, 1, 2) = 'M_'

 THEN
     RETURN 1;

    ELSE
        RETURN 0;

 END IF;

END;
$function$

