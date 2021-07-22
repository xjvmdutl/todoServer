package org.example.serviece;

import lombok.AllArgsConstructor;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.example.repository.TodoRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    /*
        1	todo 리스트 목록에 아이템을 추가
        2	todo  리스트 목록 중 특정 아이템을 조회
        3	todo 리스트 전체 목록을 조회
        4	todo 리스트 목록 중 특정 아이템을 수정
        5	todo 리스트 목록 중 특정 아이템을 삭제
        6	todo 리스트 전체 목록을 삭제
    */

    //아이템을 추가 하는 메소드
    public TodoModel add(TodoRequest request){
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(request.getTitle());
        todoModel.setOrder(request.getOrder());
        todoModel.setOrder(request.getOrder());
        todoModel.setCompleted(request.getCompleted());
        return this.todoRepository.save(todoModel);
    }

    //ID 기준으로 검색 메소드
    public TodoModel searchById(Long id){
        //404에러코드를 내려주는 에러를 발생
        return this.todoRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    //전체 조회
    public List<TodoModel> searchAll(){
        return this.todoRepository.findAll();
    }
    //수정
    public TodoModel updateById(Long id, TodoRequest request){
        //request = 수정할 데이터
        TodoModel todoModel = this.searchById(id);
        if(request.getTitle() != null)
            todoModel.setTitle(request.getTitle());
        if(request.getOrder() != null)
            todoModel.setOrder(request.getOrder());
        if(request.getCompleted() != null)
            todoModel.setCompleted(request.getCompleted());
        return this.todoRepository.save(todoModel);
    }
    //ID기준 삭제
    public void deleteById(Long id){
        this.todoRepository.deleteById(id);
    }
    //전체 삭제
    public void deleteAll(){
        this.todoRepository.deleteAll();
    }
}
