package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.User;
import com.ania.appointly.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user, MapperFacade mappers) {
        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .company(user.getCompany() != null ? mappers.companyMapper.toEntity(user.getCompany(),mappers) : null)
                .build();
    }

    public User toDomain(UserEntity entity, MapperFacade mappers) {
        return User.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .role(entity.getRole())
                .company(entity.getCompany() != null ? mappers.companyMapper.toDomain(entity.getCompany(), mappers) : null)
                .build();
    }
}
