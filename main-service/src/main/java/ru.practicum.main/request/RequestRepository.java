package ru.practicum.main.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.status.Status;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findRequestById(long id);

    List<Request> findRequestsByIdIn(List<Long> ids);

    List<Request> findRequestsByEventId(long eventId);

    List<Request> findRequestsByRequesterId(long userId);

    @Modifying
    @Query(value = "update Request as r set r.status = ?2 where r.id in ?1")
    void updateRequestStatusByIdIn(List<Long> requestsIds, Status status);

    @Modifying
    @Query(value = "update Request as r set r.status = ?2 where r.id = ?1")
    void updateRequestStatusById(long requestsIds, Status status);

    @Modifying
    @Query(value = "update Request as r set r.status = ?3 where r.event.id = ?1 and r.status = ?2")
    void updateRequestStatusByEventId(long eventId, Status status, Status newStatus);
}
