-- TODO: Fill in all TODOs
CREATE TEMPORARY FUNCTION phonenumberformatterbonus AS 'TODO';

DESCRIBE FUNCTION phonenumberformatterbonus;
DESCRIBE FUNCTION extended phonenumberformatterbonus;


SELECT phonenumberformatterbonus(from_phone, "TODO") AS fromformatted, phonenumberformatterbonus(to_phone, "TODO") AS toformatted FROM calldetails LIMIT 1;

