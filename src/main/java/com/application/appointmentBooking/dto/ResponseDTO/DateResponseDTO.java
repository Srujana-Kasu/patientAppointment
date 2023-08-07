package com.application.appointmentBooking.dto.ResponseDTO;

import com.application.appointmentBooking.dto.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateResponseDTO {

    private String patientName;

    private UUID patientId;

    private List<Appointment> appointmentList;
}
