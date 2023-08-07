package com.application.appointmentBooking.dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRequestDTO {

    @NotNull(message="Name of the patient is required")
    @NotBlank
    private String name;

    @NotNull(message = "Appointment Date is required")
    @NotBlank
    private String date;

    @NotNull(message = "Appointment start time is required")
    private double startTime;

    @NotNull(message = "Appointment end time is required")
    private double endTime;

}
