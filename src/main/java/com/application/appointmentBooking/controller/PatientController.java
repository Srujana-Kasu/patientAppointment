package com.application.appointmentBooking.controller;

import com.application.appointmentBooking.dto.Appointment;
import com.application.appointmentBooking.dto.RequestDTO.AppointmentRequestDTO;
import com.application.appointmentBooking.dto.ResponseDTO.AppointmentResponseDTO;
import com.application.appointmentBooking.dto.ResponseDTO.DateResponseDTO;
import com.application.appointmentBooking.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    PatientService patientService;


    @GetMapping("{name}/appointments")
    public Map<String, List<Appointment>> getPatientAppointments(@PathVariable("name") String patientName){
        return patientService.getPatientAppointments(patientName);
    }

    @PostMapping("appointments")
    public AppointmentResponseDTO addAppointment (@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return patientService.addPatientAppointment(appointmentRequestDTO);
    }

    @GetMapping("appointments/{date}")
    public List<DateResponseDTO>getAllAppointments(@PathVariable("date") String date , @RequestParam(required = false) List<String>patientNames) {
        return patientService.getAllAppointments(date, patientNames);
    }
}
