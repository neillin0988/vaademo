DROP TABLE menu;
    
CREATE TABLE
    menu
    (
        uuid CHARACTER VARYING(36) NOT NULL,
        KEY CHARACTER VARYING(36) NOT NULL,
        parentkey CHARACTER VARYING(36),
        isAlive CHARACTER VARYING(1) NOT NULL DEFAULT 'Y',
        sort SMALLINT,
        component CHARACTER VARYING(100),
        name CHARACTER VARYING(36) NOT NULL,
        
        creator CHARACTER VARYING(20) NOT NULL DEFAULT 'system',
        createdate DATE DEFAULT current_date,
        editor CHARACTER VARYING(20),
        editdate DATE,
        PRIMARY KEY (uuid)
    );