package com.application.appointmentBooking.controller;

import com.application.appointmentBooking.JavaUtil;
import com.application.appointmentBooking.dto.Appointment;
import com.application.appointmentBooking.dto.Patient;
import com.application.appointmentBooking.dto.RequestDTO.AppointmentRequestDTO;
import com.application.appointmentBooking.dto.ResponseDTO.AppointmentResponseDTO;
import com.application.appointmentBooking.dto.ResponseDTO.DateResponseDTO;
import com.application.appointmentBooking.service.PatientService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private PatientController controller;

    @Mock
    private PatientService service;


    @Test
    public void testGetPatientAppointmentByName(){
        String date="2023-08-04";
        String name ="johnDoe";
        JavaUtil util = new JavaUtil();
        Patient patient = util.createPatient(name,date,3.00,4.00);
        when(service.getPatientAppointments(name)).thenReturn(patient.getAppointmentMap());
        Map<String,List<Appointment>> response = controller.getPatientAppointments(name);
        assertTrue(response.size()==1);
        assertTrue(response.containsKey(date));
        assertEquals(response.get(date).size(),1);
        when(service.getPatientAppointments(name)).thenReturn(null);
        response = controller.getPatientAppointments(name);
        assertNull(response);
    }

    @Test
    public void testGetAllPatientAppointments(){
        String date ="2023-08-04";
        DateResponseDTO dateResponseDTO1 =  new DateResponseDTO("johnDoe",UUID.randomUUID(),Arrays.asList(new Appointment(3,4)));
        DateResponseDTO dateResponseDTO2 =  new DateResponseDTO("johnDoe1",UUID.randomUUID(),Arrays.asList(new Appointment(4.30,5.30)));
        DateResponseDTO dateResponseDTO3 =  new DateResponseDTO("johnDoe2",UUID.randomUUID(),Arrays.asList(new Appointment(5.30,6.30)));
        List<DateResponseDTO> dateResponseDTOList = Arrays.asList(dateResponseDTO1,dateResponseDTO2,dateResponseDTO3);
        when(service.getAllAppointments(date,null)).thenReturn(dateResponseDTOList);
        assertEquals(3,controller.getAllAppointments(date,null).size());
        when(service.getAllAppointments(date,Arrays.asList("johnDoe","johnDoe1"))).thenReturn(Arrays.asList(dateResponseDTO1,dateResponseDTO2));
        assertEquals(2,controller.getAllAppointments(date,Arrays.asList("johnDoe","johnDoe1")).size());
    }
    @Test
    public void testCreateAppointment(){
        String date ="2023-08-04";
        AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO("JohnDoe","2023-08-04",3,4);
        AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO(UUID.randomUUID(),"JohnDoe",true);
        when(service.addPatientAppointment(appointmentRequestDTO)).thenReturn(appointmentResponseDTO);
        assertEquals(appointmentResponseDTO,controller.addAppointment(appointmentRequestDTO));
    }
}
