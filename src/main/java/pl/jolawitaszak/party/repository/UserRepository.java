package pl.jolawitaszak.party.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jolawitaszak.party.domain.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();
}
