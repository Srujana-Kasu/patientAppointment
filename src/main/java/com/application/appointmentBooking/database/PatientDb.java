package com.application.appointmentBooking.database;

import com.application.appointmentBooking.dto.Patient;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            if(patientName.toLowerCase().equals(patient.getName())){
                return patient;
            }
        }
        return null;
    }

    public List<Patient> getPatients(List<String> patientNames){
        //converting patientNames to lower case
        List<String> patientNamesFormatted = patientNames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        List<Patient> filterPatientList =new ArrayList<>();
        for(Patient patient:patientList){
            if(patientNamesFormatted.contains(patient.getName().toLowerCase())){
                filterPatientList.add(patient);
            }
        }
        return filterPatientList;
    }
}
