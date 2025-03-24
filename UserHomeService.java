package library.libraryapp.service;

import library.libraryapp.repository.UserHomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;

@Service
public class UserHomeService {

    private final UserHomeRepository userHomeRepository;

    @Autowired
    public UserHomeService(UserHomeRepository userHomeRepository) {
        this.userHomeRepository = userHomeRepository;
    }

    public String getUserNameById(int userId) {
        return userHomeRepository.findUserNameById(userId);
    }

    public List<String> getRecentActivity(int userId) {
        List<Map<String, Object>> recentActivity = userHomeRepository.findRecentActivityByUserId(userId);


        List<String> activityList = new ArrayList<>();
        for (Map<String, Object> activity : recentActivity) {
            String type = (String) activity.get("Type");
            String model = (String) activity.get("Model");
            Timestamp checkoutDate = (Timestamp) activity.get("CheckoutDate");
            Date checkinDate = (Date) activity.get("CheckinDate");
            String location = (String) activity.get("Location");  // Adding location

            if (checkinDate != null) {
                LocalDate localCheckinDate = checkinDate.toLocalDate();
                activityList.add("Checked in " + type + " (" + model + ") on " + localCheckinDate + " at " + location);
            } else if (checkoutDate != null) {
                LocalDate localCheckoutDate = checkoutDate.toLocalDateTime().toLocalDate();
                activityList.add("Checked out " + type + " (" + model + ") on " + localCheckoutDate + " at " + location);
            }
        }
        return activityList;
    }

    public List<String> getUpcomingCheckins(int userId) {
        List<Map<String, Object>> upcomingCheckins = userHomeRepository.findUpcomingCheckinsByUserId(userId);

        List<String> checkinList = new ArrayList<>();
        for (Map<String, Object> checkin : upcomingCheckins) {
            String type = (String) checkin.get("Type");
            String model = (String) checkin.get("Model");
            Date dueDate = (Date) checkin.get("DueDate");
            String location = (String) checkin.get("Location");


            LocalDate localDueDate = dueDate.toLocalDate();


            checkinList.add(type + " (" + model + ") needs to be checked in at " + location + " by " + localDueDate);
        }

        return checkinList;
    }

}