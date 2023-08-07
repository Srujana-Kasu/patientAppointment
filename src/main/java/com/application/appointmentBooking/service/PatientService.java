package com.application.appointmentBooking.service;


import com.application.appointmentBooking.database.PatientDb;
import com.application.appointmentBooking.dto.Appointment;
import com.application.appointmentBooking.dto.Patient;
import com.application.appointmentBooking.dto.RequestDTO.AppointmentRequestDTO;
import com.application.appointmentBooking.dto.ResponseDTO.AppointmentResponseDTO;
import com.application.appointmentBooking.dto.ResponseDTO.DateResponseDTO;
import com.application.appointmentBooking.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.Double.parseDouble;

@Service
public class PatientService {

    @Autowired
    PatientDb patientDb;
    public Map<String,List<Appointment>> getPatientAppointments(String  patientName){
       Patient patient = patientDb.getPatientByName(patientName.toLowerCase());
       if(null != patient){
               return patient.getAppointmentMap();
           }
       return new HashMap<>();
    }
    public AppointmentResponseDTO addPatientAppointment(AppointmentRequestDTO appointmentRequestDTO){
        AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO();
        Patient patient = patientDb.getPatientByName(appointmentRequestDTO.getName());
        String name = appointmentRequestDTO.getName().toLowerCase();
        double startTime = parseDouble(appointmentRequestDTO.getStartTime());
        double endTime = parseDouble(appointmentRequestDTO.getEndTime());
        String date = appointmentRequestDTO.getDate();
        if(parseDouble(appointmentRequestDTO.getStartTime()) > parseDouble(appointmentRequestDTO.getEndTime())){
            throw new ApiRequestException("appointment startTime cannot be greater than appointment endTime");
        }
        if(!isValid(date)){
            throw new ApiRequestException("appointment Date is Invalid");
        }
        if(null !=patient){
                List<Appointment> appointmentList = patient.getAppointmentMap().getOrDefault(date,new ArrayList<>());
                for (Appointment appointment : appointmentList) {
                    if (appointment.getStartTime() < endTime && appointment.getEndTime() > startTime) {
                        appointmentResponseDTO= AppointmentResponseDTO.builder().isAppointmentCreated(false).patientUuid(patient.getId()).patientName(name).build();
                        return  appointmentResponseDTO;
                    }
                }
                appointmentList.add(new Appointment(startTime,endTime));
                patient.getAppointmentMap().put(date,appointmentList);
                appointmentResponseDTO= AppointmentResponseDTO.builder().isAppointmentCreated(true).patientUuid(patient.getId()).patientName(name).build();
                return appointmentResponseDTO;
        }else {
            Map<String,List<Appointment>> appointmentMap = new HashMap<>();
            List<Appointment> appointmentList = new ArrayList<>();
            appointmentList.add(new Appointment(startTime,endTime));
            appointmentMap.putIfAbsent(date,appointmentList);
            Patient newPatient = Patient.builder().name(appointmentRequestDTO.getName().toLowerCase()).id(UUID.randomUUID()).appointmentMap(appointmentMap).build();
            patientDb.addPatient(newPatient);
            appointmentResponseDTO= AppointmentResponseDTO.builder().isAppointmentCreated(true).patientUuid(newPatient.getId()).patientName(name).build();
            return appointmentResponseDTO;
        }

    }
    public List<DateResponseDTO> getAllAppointments(String date,List<String> patientNames){
        List<Patient> patientList;
        if(patientNames==null || patientNames.size() ==0) {
           patientList = patientDb.getAllPatients();
        }else{

            patientList = patientDb.getPatients(patientNames);
        }
        List<DateResponseDTO> newPatientList = new ArrayList<>();
        for(Patient patient: patientList){
            if(patient.getAppointmentMap().containsKey(date)){
                 DateResponseDTO dateResponseDTO = new DateResponseDTO();
                 dateResponseDTO.setPatientName(patient.getName());
                 dateResponseDTO.setPatientId(patient.getId());
                 dateResponseDTO.setAppointmentList(patient.getAppointmentMap().get(date));
                 newPatientList.add(dateResponseDTO);
            }
        }
        return newPatientList;
    }
    private boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
