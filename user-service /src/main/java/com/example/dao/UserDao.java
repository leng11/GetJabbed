package  com.example.dao;

import com.example.entity.Certificate;
import com.example.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserDao extends CrudRepository<User,Long> {
    User findById(final String official_id);

}