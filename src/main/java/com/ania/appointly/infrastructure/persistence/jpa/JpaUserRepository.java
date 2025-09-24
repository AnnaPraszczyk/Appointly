package com.ania.appointly.infrastructure.persistence.jpa;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import com.ania.appointly.domain.repository.UserRepository;
import com.ania.appointly.infrastructure.entity.UserEntity;
import com.ania.appointly.infrastructure.mapper.MapperFacade;
import com.ania.appointly.infrastructure.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository springRepository;
    private final MapperFacade mappers;

    @Override
    public User save(User user) {
        UserEntity entity = mappers.userMapper.toEntity(user, mappers);
        UserEntity saved = springRepository.save(entity);
        return mappers.userMapper.toDomain(saved, mappers);
    }

    @Override
    public boolean existsById(UUID id) {
        return springRepository.existsById(id);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springRepository.findById(id)
                .map(entity -> mappers.userMapper.toDomain(entity, mappers));
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return springRepository.findByEmail(email)
                .map(entity -> mappers.userMapper.toDomain(entity, mappers));
    }

    @Override
    public List<User> findAll() {
        return springRepository.findAll().stream()
                .map(entity -> mappers.userMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByCompanyId(UUID companyId) {
        return springRepository.findByCompany_Id(companyId).stream()
                .map(entity -> mappers.userMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRole(Role role) {
        return springRepository.findByRole(role).stream()
                .map(entity -> mappers.userMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllPaged(Pageable pageable) {
        return springRepository.findAll(pageable).stream()
                .map(entity -> mappers.userMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springRepository.deleteById(id);
    }
}
