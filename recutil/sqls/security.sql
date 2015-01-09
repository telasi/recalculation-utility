create or replace PACKAGE       security
AS
---------------------------------- Constants -----------------------------------

   -- administrators role ID
   admin_role   CONSTANT NUMBER         := 1;

   FUNCTION encrypt (input_string VARCHAR2)
      RETURN VARCHAR2;

   FUNCTION decrypt (input_string VARCHAR2)
      RETURN VARCHAR2;

-------------------------- Link to BS security system --------------------------

  /**
   * Get billing system person ID, by the ID in our database.
   */
  FUNCTION get_bs_user (
    p_user_id NUMBER
  ) RETURN NUMBER;

------------------------------------- Logs -------------------------------------

   PROCEDURE make_log (
      p_user_id        NUMBER,
      p_action_id      NUMBER,
      p_message_req    VARCHAR2,
      p_message_resp   VARCHAR2
   );

--------------------------- Permission Manipulations ---------------------------

   PROCEDURE permission_insert (p_role_id IN NUMBER, p_action_id IN NUMBER);

   PROCEDURE permission_delete (p_role_id IN NUMBER, p_action_id IN NUMBER);

------------------------------ Role Manipulations ------------------------------
   PROCEDURE role_insert (
      p_role_name      IN       NVARCHAR2,
      p_role_desc      IN       NVARCHAR2,
      p_role_enabled   IN       NUMBER,
      p_role_id        OUT      NUMBER
   );

   PROCEDURE role_update (
      p_role_id       IN   NUMBER,
      p_role_name     IN   NVARCHAR2,
      p_role_desc     IN   NVARCHAR2,
      p_role_enable   IN   NUMBER
   );

   PROCEDURE role_delete (p_role_id NUMBER);

------------------------------ User manipulations ------------------------------

   PROCEDURE user_insert (
      p_user_name       IN       VARCHAR2,
      p_user_pswd       IN       VARCHAR2,
      p_user_fullname   IN       NVARCHAR2,
      p_user_role_id    IN       NUMBER,
      p_user_enabled    IN       NUMBER,
      p_advisor         IN       NUMBER,
      p_usernumber      IN       VARCHAR2,
      p_user_id         OUT      NUMBER
   );

   PROCEDURE user_update (
      p_user_id         IN   NUMBER,
      p_user_pswd       IN   VARCHAR2,
      p_user_fullname   IN   NVARCHAR2,
      p_user_role_id    IN   NUMBER,
      p_user_enabled    IN   NUMBER,
      p_advisor         IN   NUMBER,
      p_usernumber      IN   VARCHAR2
   );

   PROCEDURE user_delete (p_user_id IN NUMBER);

   PROCEDURE my_password_update (p_user_id NUMBER, p_new_password VARCHAR2);

--------------------------------- Validations ----------------------------------
   
   PROCEDURE valid_login (
      p_user_name   IN       VARCHAR2,
      p_user_pswd   IN       VARCHAR2,
      p_user_id     OUT      NUMBER
   );

   PROCEDURE permission_assert (p_user_id IN NUMBER, p_action IN NUMBER);
   
--------------------------------------------------------------------------------
END;                                                           -- package head
/

create or replace PACKAGE BODY       security
AS

-- Encryption/description

  Function encrypt(input_string VARCHAR2) return varchar2 is
  begin
    return recutil_encrypt(input_string);
  end;

  Function decrypt(input_string VARCHAR2) return varchar2 is
  begin
    return recutil_decrypt(input_string);
  end;

-------------------------- Link to BS security system --------------------------

  /**
   * Get billing system person ID, by the ID in our database.
   */
  FUNCTION get_bs_user (
    p_user_id NUMBER
  ) RETURN NUMBER IS
    p_username VARCHAR2(25);
    p_person NUMBER;
  BEGIN
    SELECT USER_NAME INTO p_username FROM users WHERE USER_ID = p_user_id;
    SELECT PERSKEY INTO p_person FROM bs.person WHERE LOGIN = UPPER(p_username);
    RETURN p_person;
  END get_bs_user;

------------------------------------- Logs -------------------------------------

   /**
    * Add log.
    */
   PROCEDURE make_log (
      p_user_id        NUMBER,
      p_action_id      NUMBER,
      p_message_req    VARCHAR2,
      p_message_resp   VARCHAR2
   )
   IS
   BEGIN
      -- add record to LOGS
      INSERT INTO logs
                  (user_id, action_id, message_req, message_resp
                  )
           VALUES (p_user_id, p_action_id, p_message_req, p_message_resp
                  );
   END;

--------------------------- Permission Manipulations ---------------------------

   /**
    * Adds permission for the group.
    */
   PROCEDURE permission_insert (p_role_id IN NUMBER, p_action_id IN NUMBER)
   IS
      p_temp   NUMBER;
   BEGIN
      -- look up whether already exists
      SELECT role_id
        INTO p_temp
        FROM permission
       WHERE role_id = p_role_id AND action_id = p_action_id;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         -- when can not be found, then add new permission row
         INSERT INTO permission
                     (role_id, action_id
                     )
              VALUES (p_role_id, p_action_id
                     );
   END;

/**
 * Remove permission from the group.
 */
   PROCEDURE permission_delete (p_role_id IN NUMBER, p_action_id IN NUMBER)
   IS
   BEGIN
-- nothing to delete for administrator role
      IF p_role_id = admin_role
      THEN
         RETURN;
      END IF;

-- delete appropriate row
      DELETE FROM permission
            WHERE role_id = p_role_id AND action_id = p_action_id;
   END;                                                   -- REMOVE_PERMISSION

------------------------------ Role Manipulations ------------------------------

   /**
   * Creates a new role. This role will be active.
   */
   PROCEDURE role_insert (
      p_role_name      IN       NVARCHAR2,
      p_role_desc      IN       NVARCHAR2,
      p_role_enabled   IN       NUMBER,
      p_role_id        OUT      NUMBER
   )
   IS
      p_new_role_enabled   NUMBER;
   BEGIN
-- adjust role enabled property
      IF NVL (p_role_enabled, 0) = 0
      THEN
         p_new_role_enabled := 0;
      ELSE
         p_new_role_enabled := 1;
      END IF;

-- inserting values
      INSERT INTO ROLE
                  (role_name, role_desc, role_enabled
                  )
           VALUES (p_role_name, p_role_desc, p_new_role_enabled
                  )
        RETURNING role_id
             INTO p_role_id;
   END;                                                         -- ROLE_INSERT

/**
 * Updates role.
 */
   PROCEDURE role_update (
      p_role_id       IN   NUMBER,
      p_role_name     IN   NVARCHAR2,
      p_role_desc     IN   NVARCHAR2,
      p_role_enable   IN   NUMBER
   )
   IS
      p_new_role_enable   NUMBER;
   BEGIN
      IF NVL (p_role_enable, 0) = 0
      THEN
         p_new_role_enable := 0;
      ELSE
         IF p_role_id = admin_role
         THEN
            -- can not disable administrator's role
            p_new_role_enable := 0;
            raise_application_error (-20000,
                                     'Can not disable administrators role.'
                                    );
         ELSE
            p_new_role_enable := 1;
         END IF;
      END IF;

-- update role
      UPDATE ROLE
         SET role_name = p_role_name,
             role_desc = p_role_desc,
             role_enabled = p_new_role_enable
       WHERE role_id = p_role_id;

-- disable all users which belongs to disabled role
      IF p_new_role_enable != 0
      THEN
         UPDATE users
            SET user_enabled = 1
          WHERE role_id = p_role_id;
      END IF;
   END;                                                         -- ROLE_UPDATE

/**
 * Delete role.
 */
   PROCEDURE role_delete (p_role_id NUMBER)
   IS
   BEGIN
-- can not delete administrator role
      IF p_role_id = admin_role
      THEN
         raise_application_error (-20000,
                                  'Can not delete administrator role');
      END IF;

-- delete role
      DELETE      ROLE
            WHERE role_id = p_role_id;
   END;                                                         -- ROLE_DELETE

------------------------------ User manipulations ------------------------------

   /**
   * Inserts new user. New user can not be added to the disabled role.
   */
   PROCEDURE user_insert (
      p_user_name       IN       VARCHAR2,
      p_user_pswd       IN       VARCHAR2,
      p_user_fullname   IN       NVARCHAR2,
      p_user_role_id    IN       NUMBER,
      p_user_enabled    IN       NUMBER,
      p_advisor         IN       NUMBER,
      p_usernumber      IN       VARCHAR2,
      p_user_id         OUT      NUMBER
   )
   IS
      p_role_enabled       NUMBER;
      p_new_user_enabled   NUMBER;
   BEGIN
      -- get role status
      SELECT role_enabled
        INTO p_role_enabled
        FROM ROLE
       WHERE role_id = p_user_role_id;

      IF NVL (p_role_enabled, 0) != 0
      THEN
         raise_application_error (-20000,
                                  'Can not add user to disabled role');
      END IF;

      -- check user number
      IF p_usernumber IS NULL
      THEN
        raise_application_error (-20000,
                                  'Define user number');
      END IF;

      -- adjust enable status
      IF NVL (p_user_enabled, 0) = 0
      THEN
         p_new_user_enabled := 0;
      ELSE
         p_new_user_enabled := 1;
      END IF;

      -- inserting new user into database
      INSERT INTO users
                  (user_name, user_pswd, user_fullname,
                   role_id, user_enabled, advisor,
                   user_number
                  )
           VALUES (p_user_name, encrypt (p_user_pswd), p_user_fullname,
                   p_user_role_id, p_new_user_enabled, p_advisor,
                   p_usernumber
                  )
        RETURNING user_id
             INTO p_user_id;
   END;                                                         -- USER_INSERT

/**
 * Update user.
 */
   PROCEDURE user_update (
      p_user_id         IN   NUMBER,
      p_user_pswd       IN   VARCHAR2,
      p_user_fullname   IN   NVARCHAR2,
      p_user_role_id    IN   NUMBER,
      p_user_enabled    IN   NUMBER,
      p_advisor         IN   NUMBER,
      p_usernumber      IN   VARCHAR2
   )
   IS
      p_new_user_enabled   NUMBER;
   BEGIN
      -- adjust enable status
      IF NVL (p_user_enabled, 0) = 0
      THEN
         p_new_user_enabled := 0;
      -- TODO: check that the role is active
      ELSE
         p_new_user_enabled := 1;
      END IF;

      -- check user number
      IF p_usernumber IS NULL
      THEN
        raise_application_error (-20000,
                                  'Define user number');
      END IF;

      -- updating user
      UPDATE users
         SET user_fullname = p_user_fullname,
             role_id = p_user_role_id,
             user_enabled = p_new_user_enabled,
             advisor = p_advisor,
             user_number = p_usernumber
       WHERE user_id = p_user_id;

      -- update password
      IF p_user_pswd IS NOT NULL AND LENGTH (TRIM (p_user_pswd)) != 0
      THEN
         UPDATE users
            SET user_pswd = encrypt (p_user_pswd)
          WHERE user_id = p_user_id;
      END IF;
   END;                                                         -- USER_UPDATE

/**
 * User delete.
 */
   PROCEDURE user_delete (p_user_id IN NUMBER)
   IS
   BEGIN
-- delete user
      DELETE      users
            WHERE user_id = p_user_id;
   END;                                                         -- USER_DELETE

/**
 * Update user password.
 */
   PROCEDURE my_password_update (p_user_id NUMBER, p_new_password VARCHAR2)
   IS
   BEGIN
      UPDATE users
         SET user_pswd = encrypt (p_new_password)
       WHERE user_id = p_user_id;
   END;                                                  -- MY_PASSWORD_UPDATE

--------------------------------- Validations ----------------------------------

   /**
   * Validates login and returns user ID as an outer parameter. Returns NULL when
   * username/password pair can not be found in the database.
   */
   PROCEDURE valid_login (
      p_user_name   IN       VARCHAR2,
      p_user_pswd   IN       VARCHAR2,
      p_user_id     OUT      NUMBER
   )
   IS
   BEGIN
      SELECT user_id
        INTO p_user_id
        FROM users
       WHERE user_name = TRIM (p_user_name)
         AND TRIM (decrypt (user_pswd)) = p_user_pswd;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         p_user_id := NULL;
   END;                                                         -- VALID_LOGIN

/**
 * Asserts permission for the user on given action.
 */
   PROCEDURE permission_assert (p_user_id IN NUMBER, p_action IN NUMBER)
   IS
      p_user_enabled   NUMBER;
      p_role_id        NUMBER;
      p_temp           NUMBER;
   BEGIN
-- get user status and role
      SELECT role_id, user_enabled
        INTO p_role_id, p_user_enabled
        FROM users
       WHERE user_id = p_user_id;

-- #1: when user disabled it has no permissions
      IF NVL (p_user_enabled, 0) != 0
      THEN
         raise_application_error (-20000,
                                     'User with ID '
                                  || p_user_id
                                  || ' is disabled.'
                                 );
      END IF;

-- #2: administrator role has all permissions
      IF p_role_id = admin_role
      THEN
         RETURN;
      END IF;

-- #3: look up for the permission
      BEGIN
         SELECT role_id
           INTO p_temp
           FROM permission
          WHERE action_id = p_action AND role_id = p_role_id;
      EXCEPTION
         WHEN NO_DATA_FOUND
         THEN
            raise_application_error
                                   (-20000,
                                       'Permission denied for the action ID '
                                    || p_action
                                    || '.'
                                   );
      END;                                                -- permission lookup
   END;
                                                          -- PERMISSION_ASSERT
--------------------------------------------------------------------------------
END;
-- package body
/
