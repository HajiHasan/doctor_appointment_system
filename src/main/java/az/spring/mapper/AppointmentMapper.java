package az.spring.mapper;

import az.spring.dto.entities.AppointmentDto;
import az.spring.model.Appointment;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE
        ,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping( target = "status", expression = "java(appointment.getStatus().name())")
    AppointmentDto modelToDto(Appointment appointment);

    @InheritInverseConfiguration
    Appointment dtoToModel(AppointmentDto appointmentDto);
}
