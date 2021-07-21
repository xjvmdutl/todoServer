package repository;

import model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,Long> {
    //<테이블과 연결될 객체, 해당 객체의 id의 타입>을 넣어준다.
    //request,response는 응답을 받고 전달해주는 역활이기 때문에 데이터를 데이터 베이스에 넣을 필요가 없다.

}
