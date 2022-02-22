-- permissions
INSERT INTO user_management.permission (id, name, description) VALUES (1, 'USERS_GET_USER', null);
INSERT INTO user_management.permission (id, name, description) VALUES (2, 'USERS_GET_ME', null);
INSERT INTO user_management.permission (id, name, description) VALUES (3, 'USERS_GET_USER_ROLES', null);
INSERT INTO user_management.permission (id, name, description) VALUES (4, 'USERS_GET_ME_ROLES', null);
INSERT INTO user_management.permission (id, name, description) VALUES (5, 'USERS_UPDATE_USER_ROLES', null);
INSERT INTO user_management.permission (id, name, description) VALUES (6, 'USERS_UPDATE_ME_ROLES', null);
INSERT INTO user_management.permission (id, name, description) VALUES (7, 'USERS_UPDATE_USER_PROFILE', null);
INSERT INTO user_management.permission (id, name, description) VALUES (8, 'USERS_UPDATE_ME_PROFILE', null);
INSERT INTO user_management.permission (id, name, description) VALUES (9, 'USERS_CHANGE_ME_PASSWORD', null);
INSERT INTO user_management.permission (id, name, description) VALUES (10, 'USERS_CHANGE_USER_PASSWORD', null);
INSERT INTO user_management.permission (id, name, description) VALUES (11, 'USERS_GET_USERS', null);
INSERT INTO user_management.permission (id, name, description) VALUES (12, 'ROLES_GET_ROLES', null);
INSERT INTO user_management.permission (id, name, description) VALUES (13, 'ROLES_CREATE_ROLE', null);
INSERT INTO user_management.permission (id, name, description) VALUES (14, 'ROLES_UPDATE_ROLE', null);
INSERT INTO user_management.permission (id, name, description) VALUES (15, 'ROLES_DELETE_ROLE', null);



-- role
INSERT INTO user_management.role (id, role_name, description, status, created_at, updated_at, created_by) VALUES (1, 'Super Admin', null, 'enabled', 1645496068952, 1645496068952, 1);

INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (1, 1, 1);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (2, 1, 2);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (3, 1, 3);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (4, 1, 4);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (5, 1, 5);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (6, 1, 6);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (7, 1, 7);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (8, 1, 8);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (9, 1, 9);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (10, 1, 10);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (11, 1, 11);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (12, 1, 12);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (13, 1, 13);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (14, 1, 14);
INSERT INTO user_management.role_permission (id, role_id, permission_id) VALUES (15, 1, 15);



-- user
INSERT INTO user_management.user (id, full_name, email, gender, birthday, created_at, created_by, updated_at) VALUES (1, 'Ying Sun', 'ying@email.com', 'male', 1453680000000, 1645495157818, null, 1645495157818);

INSERT INTO user_management.user_credential (id, user_id, credential_type, credential_id, password, salt, status, created_at, updated_at) VALUES (1, 1, 'email', 'ying@email.com', '84b493223e77b33f13d5cf428331c9d851d133000dcf9a7f1ce2668d9e2e99ab901d30c39d83110a71290a955b7897b014e577a85201a9f9802c6698980fc9b11c6a8a0a77672a8e2ee6b277372a0abd782f4728146b265d946326b07687afa680da8a9bc315f1031c5c7b32e08372003443a0e451c1f3be545a27b36c374c7d', '1f2589c5a2bd8cdfc76a97825dadcca91b7d73c0a47aade228db950d19c1ba349b2e59db54654cdc5d6fb70fd6b195a5c5484a57fc585403483b30fa003a88ce', 'enabled', 1645495158132, 1645495158132);

INSERT INTO user_management.user_role (id, user_id, role_id) VALUES (1, 1, 1);