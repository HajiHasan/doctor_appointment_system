package az.spring.mapper;

import az.spring.dto.entities.UserDto;
import az.spring.dto.login.UserLoginRequest;
import az.spring.dto.request.UserRegisterRequest;
import az.spring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE
,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto modelToDto(User user);

    User dtoToModel(UserDto userDto);

    UserLoginRequest loginRequestToModel(UserLoginRequest userLoginRequest);
    User registerRequestToModel(UserRegisterRequest userRegisterRequest);

    User modelToRequestRegister(User user);

}
