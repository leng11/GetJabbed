package com.example.AppointmentService.Appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/vaccine")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments/list")
    public ResponseEntity<List<Appointment>> getAll(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable long id){
        return appointmentService.getAppointment(id);
    }
    @PutMapping("/appointments/cancel")
    public ResponseEntity<Map<String,Object>> cancel(@RequestParam long confirmationId){
        return appointmentService.cancel(confirmationId);
    }
    @PutMapping("/appointments/completed")
    public ResponseEntity<Appointment> completed(@RequestParam long confirmationId){
        return appointmentService.completed(confirmationId);
    }
}
