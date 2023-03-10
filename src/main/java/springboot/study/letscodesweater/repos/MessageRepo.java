package springboot.study.letscodesweater.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.study.letscodesweater.domain.Message;
import springboot.study.letscodesweater.domain.User;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    Page<Message> findAll(Pageable pageable);
    Page<Message> findByAuthor(User user, Pageable pageable);
    Page<Message> findByTag(String tag, Pageable pageable);
}
