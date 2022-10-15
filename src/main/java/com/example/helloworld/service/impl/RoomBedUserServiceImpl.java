package com.example.helloworld.service.impl;


import com.example.helloworld.entity.RoomBedUserEntity;
import com.example.helloworld.repository.RoomBedUserRepository;
import com.example.helloworld.service.RoomBedUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:18
 */
@Service
public class RoomBedUserServiceImpl implements RoomBedUserService {

    @Resource
    private RoomBedUserRepository roomBedUserRepository;

    @Override
    public void save(RoomBedUserEntity roomBedUser) {
        roomBedUserRepository.save(roomBedUser);
    }

    @Override
    public void deleteById(Long id) {
        roomBedUserRepository.deleteById(id);
    }

    @Override
    public Optional<RoomBedUserEntity> findById(Long id) {
        return roomBedUserRepository.findById(id);
    }

    @Override
    public List<RoomBedUserEntity> findById(Collection<Long> ids) {
        Iterable<RoomBedUserEntity> iterable = roomBedUserRepository.findAllById(ids);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

}

