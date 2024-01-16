package az.spring.mapper;

import az.spring.dto.entities.DoctorDto;
import az.spring.dto.login.UserLoginRequest;
import az.spring.dto.request.DoctorRegisterRequest;
import az.spring.dto.request.UserRegisterRequest;
import az.spring.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {
    DoctorDto modelToDto(Doctor doctor);
    Doctor dtoToModel(DoctorDto doctorDto);
    UserLoginRequest loginRequestToModel(UserLoginRequest userLoginRequest);
    Doctor registerRequestToModel(DoctorRegisterRequest doctorRegisterRequest);


}
