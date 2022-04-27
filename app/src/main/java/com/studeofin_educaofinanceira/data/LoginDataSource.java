package com.studeofin_educaofinanceira.data;

import com.studeofin_educaofinanceira.conta;
import com.studeofin_educaofinanceira.data.model.LoggedInUser;
import com.studeofin_educaofinanceira.data.model.YourPreference;
import com.studeofin_educaofinanceira.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser User =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            username);
            return new Result.Success<>(User);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }


    public void logout() {
        // TODO: revoke authentication
    }
}