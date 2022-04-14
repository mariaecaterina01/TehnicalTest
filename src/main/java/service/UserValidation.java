package service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

public class UserValidation {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserValidation.class);
    public Boolean validate(User user){
        if(user.getCountryResidance().equals("France")) {
            if (calculateAge(user.getBirthDate()) > 18) {
                return true;
            } else {
                LOGGER.error("The user must be un adult. The age od the user " + user.getUserName() +
                        "is " + calculateAge(user.getBirthDate()));
                return false;
            }
        } else {
            LOGGER.error("The country of residence must be France! The user " + user.getUserName() +
                    "has the residence country as " + user.getCountryResidance());
            return false;
        }
    }

    private int calculateAge(Date birthDate){
        int years;
        int months;

        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0)
        {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            years--;
        }

        return years;
    }
}
