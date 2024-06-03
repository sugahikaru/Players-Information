DROP TABLE IF EXISTS players;

CREATE TABLE players (
 id int unsigned AUTO_INCREMENT,
 name VARCHAR(20) NOT NULL,
 number int(20) NOT NULL,
 team VARCHAR(20) NOT NULL,
 PRIMARY KEY(id)
);
INSERT INTO players (name,number,team) VALUES ('柳田悠岐',9,"ソフトバンク");
INSERT INTO players (name,number,team) VALUES ('イチロー',55,"マリナーズ");
INSERT INTO players (name,number,team) VALUES ('大谷翔平',17,"ドジャース");
