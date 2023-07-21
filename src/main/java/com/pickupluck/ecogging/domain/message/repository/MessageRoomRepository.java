package com.pickupluck.ecogging.domain.message.repository;

import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {

    @Query (
            value = "SELECT message_room_id FROM message_room "
                    + "WHERE (initial_receiver_id = :receiverId AND initial_sender_id = :senderId)",
            nativeQuery = true
    )
    Optional<Long> findIdByInitialSenderAndInitialReceiver(
            @Param("senderId") Long senderId, @Param("receiverId") Long receiverId);


    @Query (
            value = "SELECT message_room_id FROM message_room "
                    + "WHERE (initial_receiver_id = :senderId AND initial_sender_id = :receiverId)",
            nativeQuery = true
    )
    Optional<Long> findIdByInitialReceiverAndInitialSender(
            @Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
