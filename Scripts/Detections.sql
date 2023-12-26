CREATE TABLE Detections(id INTEGER NOT NULL primary key autoincrement,
Picid	INTEGER,
Classid	INTEGER,
XMin	REAL,
YMin	REAL,
XMax	REAL,
YMax	REAL,
Confidence	REAL);
