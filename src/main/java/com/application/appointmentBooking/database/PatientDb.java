package com.application.appointmentBooking.database;

import com.application.appointmentBooking.dto.Patient;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientDb {

    private static List<Patient> patientList = new ArrayList<>();

    public void addPatient(Patient patient){
        patientList.add(patient);
    }

    public List<Patient> getAllPatients(){
        return patientList;
    }

    public Patient getPatientByName(String patientName){
        for(Patient patient:patientList){
            if(patientName.equals(patient.getName())){
                return patient;
            }
        }
        return null;
    }

    public List<Patient> getPatients(List<String> patientNames){
        List<Patient> filterPatientList =new ArrayList<>();
        for(Patient patient:patientList){
            if(patientNames.contains(patient.getName())){
                filterPatientList.add(patient);
            }
        }
        return filterPatientList;
    }
}
