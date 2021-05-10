package pl.jolawitaszak.party.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jolawitaszak.party.domain.Event;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EventRepository extends CrudRepository<Event, Long> {
}
