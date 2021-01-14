CREATE TABLE IF NOT EXISTS Games (
   g_gameID INTEGER PRIMARY KEY,
   g_name TEXT,
   g_year INTEGER,
   g_genre TEXT,
   g_developer TEXT
);


CREATE TABLE IF NOT EXISTS Songs (
   so_songID INTEGER PRIMARY KEY,
   so_name TEXT,
   so_type TEXT,
   so_notes TEXT
);

CREATE TABLE IF NOT EXISTS Items (
   i_itemID INTEGER PRIMARY KEY,
   i_name TEXT,
   i_desc TEXT,
   i_type TEXT,
   i_gameID INTEGER,
 FOREIGN KEY (i_gameID) REFERENCES Games (g_gameID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Stages (
   s_stageID INTEGER PRIMARY KEY,
   s_name TEXT,
   s_gameID INTEGER,
   s_size TEXT,
   s_type TEXT,
 FOREIGN KEY (s_gameID) REFERENCES Games (g_gameID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Users (
   u_userID INTEGER PRIMARY KEY,
   u_username TEXT,
   u_password TEXT,
   u_email TEXT
);

CREATE TABLE IF NOT EXISTS Characters (
   c_charID INTEGER PRIMARY KEY,
   c_name TEXT,
   c_size INTEGER
);

CREATE TABLE IF NOT EXISTS CharacterGames (
   cg_charID INTEGER,
   cg_gameID INTEGER,
    FOREIGN KEY (cg_charID)
       REFERENCES Characters (c_charID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (cg_gameID)
       REFERENCES Games (g_gameID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS StageSongs (
   ss_stageID INTEGER,
   ss_songID INTEGER,
   FOREIGN KEY (ss_stageID)
       REFERENCES Stages (s_stageID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (ss_songID)
       REFERENCES Songs (so_songID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS GameSongs (
   gs_songID INTEGER,
   gs_gameID INTEGER,
    FOREIGN KEY (gs_songID)
       REFERENCES Songs (so_songID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (gs_gameID)
       REFERENCES Games (gs_gameID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Rates (
  r_rateID INTEGER PRIMARY KEY,
  r_userID INTEGER,
  r_itemID INTEGER,
  r_charID INTEGER,
  r_stageID INTEGER,
  r_type TEXT,
  r_date DATETIME,
  r_score INTEGER,
  r_comment TEXT,
    FOREIGN KEY (r_userID)
       REFERENCES Users (u_userID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
  FOREIGN KEY (r_itemID)
       REFERENCES Items (i_itemID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
  FOREIGN KEY (r_charID)
       REFERENCES Characters (c_charID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
  FOREIGN KEY (r_stageID)
       REFERENCES Stages (s_stageID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

--INSERT OR IGNORE INTO Users(u_userID,u_username,u_password,u_email) VALUES (1,'user#1','password#1','email#1');
--INSERT OR IGNORE INTO Games(g_gameID,g_name,g_year,g_genre,g_developer) VALUES(1,'Animal Crossing: Wild World',2005,'Simulation','Nintendo');
--INSERT OR IGNORE INTO Characters(c_charID,c_name,c_size) VALUES(1,'Banjo & Kazooie',106);
--INSERT OR IGNORE INTO Items(i_itemID,i_name,i_desc,i_type,i_gameID) VALUES(1,'Smash Ball',"'Shatter this orb with an attack, then unleash your Final Smash with a special-move button.'",'Special',39);
--INSERT OR IGNORE INTO Songs(so_songID,so_name,so_type,so_notes) VALUES (1,'Title Theme - Animal Crossing: Wild World','New Remix','Arrangement: Maki Kirioka');
--INSERT OR IGNORE INTO Stages(s_stageID,s_name,s_gameID,s_size,s_type) VALUES (1,'Smashville',1,'medium','starter');
--INSERT OR IGNORE INTO CharacterGames(cg_charID,cg_gameID) VALUES (1,11);
--INSERT OR IGNORE INTO GameSongs(gs_songID,gs_gameID) VALUES (1,11);
--INSERT OR IGNORE INTO StageSongs(ss_stageID,ss_songID) VALUES (1,1);
--INSERT OR IGNORE INTO Rates(r_rateID,r_userID,r_itemID,r_charID,r_stageID,r_type,r_date,r_score,r_comment) VALUES(1,1,30,NULL,NULL,'Item',2019-10-10,58,'to the even regular platelets. regular ironic epitaphs nag e');

--1
SELECT count(*) 
FROM Characters, CharacterGames, Games
WHERE g_gameID = 51 AND
      g_gameID = cg_gameID AND
      cg_charID = c_charID;

--2
SELECT r_userID,r_rateID,r_type, r_itemID,i_name AS name
FROM Users, Rates, Items
WHERE r_userID = 50 AND 
r_type = 'Item' AND r_itemID = i_itemID
GROUP BY r_rateID
UNION
SELECT r_userID,r_rateID,r_type,  r_charID,c_name AS name
FROM Users, Rates, Characters
WHERE r_userID = 50 AND 
 r_type = 'character' AND r_charID = c_charID
GROUP BY r_rateID
UNION
SELECT  r_userID,r_rateID,r_type,r_stageID,s_name AS name
FROM Users, Rates, Stages
WHERE r_userID = 50 AND
 r_type = 'stage' AND r_stageID = s_stageID
GROUP BY r_rateID;

--3
SELECT g_name 
FROM Characters, CharacterGames, Games
WHERE c_name = 'Pikachu' AND
      cg_charID = c_charID AND
        cg_gameID = g_gameID;

--4
SELECT so_name
FROM Songs, Stages, StageSongs
WHERE s_name = 'Balloon Fight' AND
s_stageID = ss_stageID AND
ss_songID = so_songID;

--5
SELECT *
FROM Items
WHERE i_type = 'Shooting';

--6
SELECT DISTINCT g_name
FROM Games,GameSongs
where gs_songID IN(
SELECT DISTINCT so_songID
FROM Stages, StageSongs,  Games, Songs
WHERE s_name = 'Smashville' AND
s_stageID = ss_stageID AND 
ss_songID = so_songID)
AND gs_gameID = g_gameID;

--7
SELECT s_name
FROM Stages
WHERE s_type = 'banned'
ORDER BY s_name ASC;

--8
Select c_name 
From Characters
Where c_size >=  90;

--9
SELECT MAX (mycount)
FROM (SELECT r_userID, count(r_userID) As mycount  
FROM Rates, Users 
WHERE r_userID = u_userID
GROUP BY r_userID ) AS mycount;

--10
SELECT g_name, MAX (mycount) 
FROM Games,(SELECT g_gameID, count(so_songID) As mycount  
FROM GameSongs, Songs, Games
WHERE so_songID = gs_songID AND g_gameID = gs_gameID
GROUP BY gs_gameID ) AS maxcount;

--11
SELECT g_name
FROM Games, CharacterGames, Characters
WHERE g_gameID = cg_gameID And c_charID = cg_charID And c_name = "Luigi"
UNION
SELECT g_name
FROM Games, CharacterGames, Characters
WHERE g_gameID = cg_gameID And c_charID = cg_charID And c_name = "Peach";


--12
SELECT u_username
FROM Rates, Users
WHERE u_userID = r_userID AND r_type = "stage"
UNION
SELECT u_username
FROM Rates, Users
WHERE u_userID = r_userID AND r_type = "item";

--13
 SELECT c_name, c_size
FROM Characters
ORDER BY c_size ASC;

--14
SELECT g_name
FROM Games
WHERE g_genre = "Platformer" AND g_developer = "Nintendo" ;

--15
INSERT OR IGNORE INTO Rates(r_rateID,r_userID,r_itemID,r_charID,r_stageID,r_type,r_date,r_score,r_comment) VALUES(211,42,20,NULL,NULL,'Item',2019-10-10,58,'to the even regular platelets. regular ironic epitaphs nag e');

--16
INSERT OR IGNORE INTO Users(u_userID,u_username,u_password,u_email) VALUES (72,'user#72','password#72','email#72');

--17
UPDATE Rates
SET r_score = "80"
WHERE r_userID = 3 ; 

--18
Update Users
Set u_password =   "Highfive" , u_email = "jpaurbtod@ucmerced.edu"
Where  u_username = "user#25" ;

--19
DELETE FROM Rates
WHERE r_rateID = 211;

--20
DELETE FROM Users
WHERE u_userID = 72;