package com.application.appointmentBooking;

import com.application.appointmentBooking.dto.Appointment;
import com.application.appointmentBooking.dto.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JavaUtil {
    public Patient createPatient(String name, String date, double startTime , double endTime){
        Map<String, List<Appointment>> appointmentMap = new HashMap<>();
        List<Appointment> appointmentList = new ArrayList<>();
        Appointment appointment = new Appointment(startTime,endTime);
        appointmentList.add(appointment);
        appointmentMap.put(date,appointmentList);
        Patient patient = Patient.builder().name(name).id(UUID.randomUUID()).appointmentMap(appointmentMap).build();
        return patient;
    }
}
