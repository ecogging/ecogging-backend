package com.pickupluck.ecogging.domain.message.repository;

import com.pickupluck.ecogging.domain.message.dto.MessageRoomsWithLastMessages;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Query (
            value = "SELECT mr.message_room_id as messageRoomId, mr.initial_receiver_id as initialReceiverId, mr.initial_sender_id as initialSenderId, m1.created_at as createdAt, m1.content as content "
                    + "from message_room as mr "
                    + "inner join message as m1 on mr.message_room_id=m1.message_room_id "
                    + "inner join (select max(created_at) as max_created_at, message_room_id "
                    + "from message "
                    + "group by message_room_id) as m2 on m1.created_at=m2.max_created_at "
                    + "where (initial_receiver_id=:id or initial_sender_id=:id) ",
            nativeQuery = true,
            countQuery = "select count(*) "
                    + "from message_room as mr "
                    + "inner join message as m1 on mr.message_room_id=m1.message_room_id "
                    + "inner join (select max(created_at) as max_created_at, message_room_id "
                    + "from message "
                    + "group by message_room_id) as m2 on m1.created_at=m2.max_created_at "
                    + "where (initial_receiver_id=:id or initial_sender_id=:id) ")
    Page<MessageRoomsWithLastMessages> findMessageRoomsAndLastMessagesByUserId(@Param("id") Long userId, Pageable pageable);

}
