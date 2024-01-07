CREATE DATABASE IF NOT EXISTS `new_website_kchat`;
CREATE DATABASE IF NOT EXISTS `new_website_ps`;

CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
