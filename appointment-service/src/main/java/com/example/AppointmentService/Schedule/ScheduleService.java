package com.example.AppointmentService.Schedule;

import com.example.AppointmentService.Appointment.Appointment;
import com.example.AppointmentService.Appointment.AppointmentRepository;
import com.example.AppointmentService.Exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return ResponseEntity.ok(scheduleRepository.findAll());
    }
    public ResponseEntity<Schedule> getSchedule(long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not Found"));
        return ResponseEntity.ok(schedule);
    }
    public ResponseEntity<List<Schedule>> getByDate(Date date) {
        List<Schedule> scheduleList = scheduleRepository.findByDate(date).orElseThrow(() ->new ResourceNotFoundException("Schedules not found"));
        if(scheduleList.isEmpty()) throw new ResourceNotFoundException("Schedules not found");
        return ResponseEntity.ok(scheduleList);
    }
    public ResponseEntity<List<Map<String,Object>>> report(Date date){
        List<Schedule> scheduleList = scheduleRepository.findByDate(date).orElseThrow(() -> new ResourceNotFoundException("Schedule not Found"));
        if(scheduleList.isEmpty()) throw new ResourceNotFoundException("Schedules not found");

        List<Map<String,Object>> reportList = new ArrayList<>();
        for(Schedule schedule : scheduleList){
            Map<String, Object> map= new HashMap<>();
            map.put("center",schedule.getCenterId());
            map.put("vaccineTypeId",schedule.getVaccineTypeId());
            map.put("shotsAdministered",schedule.getCompletedSlot());
            reportList.add(map);
        }
        log.info(reportList.toString());
        return ResponseEntity.ok(reportList);
    }

    public ResponseEntity<Map<String,Object>> book(long scheduleId, long userId){
        Map<String,Object> map = new HashMap<>();
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        List<Appointment> appointmentList = schedule.getAppointmentList();
        Optional<Appointment> appointmentToSchedule = appointmentList.stream()
                .filter(appointment -> appointment.getStatus().equalsIgnoreCase("open"))
                .findFirst();
        appointmentToSchedule.ifPresent(appointment -> {
            appointment.setUserId(userId);
            appointment.setStatus("Scheduled");
        });
        if(appointmentToSchedule.isPresent()){
            appointmentRepository.save(appointmentToSchedule.get());
            map.put("booked",true);
            map.put("centerId",schedule.getCenterId());
            map.put("confirmId",appointmentToSchedule.get().getConfirmationId());
        }else{
            map.put("booked",false);
            map.put("error", "No appointments available");
        }
        return ResponseEntity.ok(map);
    }


}
