package com.application.appointmentBooking.service;

import com.application.appointmentBooking.JavaUtil;
import com.application.appointmentBooking.database.PatientDb;
import com.application.appointmentBooking.dto.Patient;
import com.application.appointmentBooking.dto.RequestDTO.AppointmentRequestDTO;
import com.application.appointmentBooking.dto.ResponseDTO.AppointmentResponseDTO;
import com.application.appointmentBooking.dto.ResponseDTO.DateResponseDTO;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientDb patientDb;

    JavaUtil javaUtil = new JavaUtil();

    @Test
    public void testGetAllAppointments(){
        Patient patient1 =javaUtil.createPatient("JohnDoe","2023-08-04",3.0,4.0);
        Patient patient2 =javaUtil.createPatient("JohnDoe1","2023-08-04",4.0,5.0);
        Patient patient3 =javaUtil.createPatient("JohnDoe2","2023-08-04",5.0,6.0);
        when(patientDb.getAllPatients()).thenReturn(Arrays.asList(patient1,patient2,patient3));
        when(patientDb.getPatients(Arrays.asList("JohnDoe","JohnDoe1"))).thenReturn(Arrays.asList(patient1,patient2));
        List<DateResponseDTO> dateResponseDTOList =patientService.getAllAppointments("2023-08-04",null);
        assertEquals(3,dateResponseDTOList.size());
        dateResponseDTOList =patientService.getAllAppointments("2023-09-04",null);
        assertNotNull(dateResponseDTOList);
        assertEquals(0,dateResponseDTOList.size());
    }

    @Test
    public void testAppPatientAppointment(){
        Patient patient1 =javaUtil.createPatient("JohnDoe","2023-08-04",3.0,4.0);
        when(patientDb.getPatientByName("JohnDoe")).thenReturn(patient1);

        //adding appointment for already exiting patient and date
        AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO("JohnDoe","2023-08-4","5.0","7.0");
        AppointmentResponseDTO expectedDTO = new AppointmentResponseDTO( UUID.randomUUID(),"JohnDoe",true);
        AppointmentResponseDTO actualDTO = patientService.addPatientAppointment(appointmentRequestDTO);
        assertEquals(expectedDTO.isAppointmentCreated(),actualDTO.isAppointmentCreated());

        //adding existing appointment
        appointmentRequestDTO = new AppointmentRequestDTO("JohnDoe","2023-08-4","5.0","7.0");
        expectedDTO = new AppointmentResponseDTO( UUID.randomUUID(),"JohnDoe",false);
        actualDTO = patientService.addPatientAppointment(appointmentRequestDTO);
        assertEquals(expectedDTO.isAppointmentCreated(),actualDTO.isAppointmentCreated());
        assertEquals(expectedDTO.getPatientName(),expectedDTO.getPatientName());

        //adding conflicting appointment
        appointmentRequestDTO = new AppointmentRequestDTO("JohnDoe","2023-08-4","6.0","6.30");
        expectedDTO = new AppointmentResponseDTO( UUID.randomUUID(),"JohnDoe",false);
        actualDTO = patientService.addPatientAppointment(appointmentRequestDTO);
        assertEquals(expectedDTO.isAppointmentCreated(),actualDTO.isAppointmentCreated());
        assertEquals(expectedDTO.getPatientName(),expectedDTO.getPatientName());

        //adding new patient and new appointment
        appointmentRequestDTO = new AppointmentRequestDTO("JohnDoe","2023-08-4","6.0","6.30");
        when(patientDb.getPatientByName("JohnDoe")).thenReturn(null);
        expectedDTO = new AppointmentResponseDTO( UUID.randomUUID(),"JohnDoe",true);
        actualDTO = patientService.addPatientAppointment(appointmentRequestDTO);
        assertEquals(expectedDTO.isAppointmentCreated(),actualDTO.isAppointmentCreated());
        assertEquals(expectedDTO.getPatientName(),expectedDTO.getPatientName());
    }





}
