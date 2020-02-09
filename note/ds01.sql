INSERT INTO ds01.permission (id, appName, name, "key", description) VALUES (1, 'app01', 'permission01', 'PM_01', null);
INSERT INTO ds01.role (id, appName, name, parentId, description, type) VALUES (1, 'app01', 'role01', null, 'role description', 'InterfaceAndBusinessAppControlled');
INSERT INTO ds01.role_x_permission (roleId, appName) VALUES (1, 1);
INSERT INTO ds01.user (appName, `key`, name) VALUES ('app01', 'user01', 'yeecode');
INSERT INTO ds01.user (appName, `key`, name) VALUES ('app01', 'user02', 'top');
INSERT INTO ds01.user_x_permission (fullUserKey, permissionKeys) VALUES ('app01-user01', 'PM_01');
INSERT INTO ds01.user_x_role (appName, userKey) VALUES ('app01-user01', 1);