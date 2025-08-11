package com.harrish.jimmer.user.api.mappers;

import com.harrish.jimmer.user.api.dtos.UserResponse;
import com.harrish.jimmer.user.core.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);
}