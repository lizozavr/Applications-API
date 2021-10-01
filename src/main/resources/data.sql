DROP TABLE IF EXISTS applications;

CREATE TABLE applications (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              name VARCHAR(128) NOT NULL,
                              version VARCHAR NOT NULL,
                              content_rate INT NOT NULL
);

INSERT INTO applications(name, version, content_rate) VALUES
('Facebook', '12.0.11', 3),
('Google', '5.4.1', 7),
('Cisco', '1.0.9', 16),
('Dota 2', '3.2.0', 12),
('Rider', '6.4.5', 18);