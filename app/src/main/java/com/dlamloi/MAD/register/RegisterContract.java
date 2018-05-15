package com.dlamloi.MAD.register;

/**
 * Created by Don on 14/05/2018.
 */

public interface RegisterContract {

    interface View {

        void showMissingDetails();

        void showRegistrationError();

    }

    interface Presenter {


        void register(String firstName, String surname, String email, String password, String confirmPassword);
    }


}
