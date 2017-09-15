package com.Notarius.data.dao;


import com.Notarius.data.dto.DashboardNotification;
import com.Notarius.data.dto.User;
import com.Notarius.data.dto.Identificable;

import java.util.Collection;
import java.util.List;


public interface DAO<T extends Identificable> {
	public boolean create(T entidad);
	public List<T> readAll();
	public boolean update(T entidad);
	public boolean delete(T entidad);
	public boolean save(T entidad);
	public T findById(Long id);
	User authenticate(String userName, String password);
	int getUnreadNotificationsCount();
	Collection<DashboardNotification> getNotifications();


}
