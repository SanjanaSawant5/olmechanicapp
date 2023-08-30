package com.mechanicapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.mechanicapp.exception.AppointmentNotFoundException;
import com.mechanicapp.model.Appointment;

public interface AppointmentDao {
	List<Appointment> displayAllAppointment() throws SQLException;
	Appointment appointmentfindById(int appointmentId) throws AppointmentNotFoundException, SQLException;
	boolean insertappointment(Appointment appointment) throws SQLException;
	boolean updateappointment(Appointment appointment) throws SQLException;
	boolean deleteappointment(Appointment appointment) throws SQLException;

}
