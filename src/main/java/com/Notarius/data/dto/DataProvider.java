package com.Notarius.data.dto;

import java.util.Collection;

/**
 * QuickTickets Dashboard backend API.
 */
public interface DataProvider {

    User authenticate(String userName, String password);

    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();

    /**
     * @return Notifications for the current user.
     */
    Collection<DashboardNotification> getNotifications();

    /**
     * @return The total summed up revenue of sold movie tickets
     */

}
