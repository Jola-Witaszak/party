package pl.jolawitaszak.party.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.GpsPosition;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface GpsPositionRepository extends CrudRepository<GpsPosition, Long> {

    @Override
    List<GpsPosition> findAll();
}
