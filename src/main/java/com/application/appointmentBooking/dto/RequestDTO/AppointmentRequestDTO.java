package com.application.appointmentBooking.dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "([01]?[0-9]|2[0-3]).[0-5][0-9]")
    private String startTime;

    @NotNull(message = "Appointment end time is required")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]).[0-5][0-9]")
    private String endTime;

}
