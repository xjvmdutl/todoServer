package org.example.serviece;

import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    //Mock을 사용하는이유는
    //외부 시스템에 의존하지 않고 자체 시스템이 동작해야되기 때문에
    //실제 DB를 사용하게 되면 DB값이 변경이 되기때문에 테스트를 하기 위해서는 실제 DB랑 연결을 안하기 위해
    @Mock
    private TodoRepository todoRepository;

    //Mock을 주입받아 사용할 서비스
    @InjectMocks
    private TodoService todoService;

    @Test
    void add() {
        //테스트 코드 작성
       when(this.todoRepository.save(any(TodoModel.class)))
               .then(AdditionalAnswers.returnsFirstArg());
        //todoRepository가 save메소드를 호출해서 TodoEntity값을 받은뒤
        //Entitiy값을 반환하도록 한다.
        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test title");
        //title 세팅
        TodoModel actual = this.todoService.add(expected);
        //TodoEntity를 반환한다.
        assertEquals(expected.getTitle(),actual.getTitle());

    }

    @Test
    void searchById() {
        TodoModel entity = new TodoModel();

        entity.setId(123L);
        entity.setTitle("TITLE");
        entity.setOrder(0L);
        entity.setCompleted(false);

        Optional<TodoModel> optional = Optional.of(entity);
        given(this.todoRepository.findById(anyLong()))
                .willReturn(optional);
        TodoModel actual = this.todoService.searchById(123L);
        TodoModel expected = optional.get();
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getOrder(),actual.getOrder());
        assertEquals(expected.getCompleted(),expected.getCompleted());

    }

    @Test
    public void searchByIdFailed(){
        //에러를 발생 시키는 메소드

        given(this.todoRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,()->{
            this.todoService.searchById(123L);
        });
    }
}