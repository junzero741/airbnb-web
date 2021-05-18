CREATE user 'bat'@'%' IDENTIFIED BY 'bat1234';
GRANT ALL PRIVILEGES ON *.* TO 'bat'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

ALTER DATABASE airbnb DEFAULT CHARACTER SET utf8;

CREATE TABLE airbnb.room (
    title VARCHAR(45) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT="Any baseball";