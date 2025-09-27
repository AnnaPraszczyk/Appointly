package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.Reservation;
import com.ania.appointly.infrastructure.entity.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationEntity toEntity(Reservation reservation, MapperFacade mappers) {
        return ReservationEntity.builder()
                .id(reservation.getId())
                .user(mappers.userMapper.toEntity(reservation.getUser(),mappers))
                .service(mappers.serviceMapper.toEntity(reservation.getService(), mappers))
                .employee(mappers.employeeMapper.toEntity(reservation.getEmployee(), mappers))
                .dateTime(reservation.getDateTime())
                .status(reservation.getStatus())
                .price(reservation.getPrice())
                .totalPrice(reservation.getTotalPrice())
                .createdAt(reservation.getCreatedAt())
                .status(reservation.getStatus())
                .build();
    }

    public Reservation toDomain(ReservationEntity entity, MapperFacade mappers) {
        return Reservation.builder()
                .id(entity.getId())
                .user(mappers.userMapper.toDomain(entity.getUser(), mappers))
                .service(mappers.serviceMapper.toDomain(entity.getService(), mappers))
                .employee(mappers.employeeMapper.toDomain(entity.getEmployee(), mappers))
                .dateTime(entity.getDateTime())
                .price(entity.getPrice())
                .totalPrice(entity.getTotalPrice())
                .createdAt(entity.getCreatedAt())
                .status(entity.getStatus())
                .build();
    }
}
