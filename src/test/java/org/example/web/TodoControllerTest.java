package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.serviece.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;

    private TodoModel expected;

    @BeforeEach
    void setup(){
        //각 테스트가 실해되기 전마다 expect값을 초기화 해준다.
        this.expected = new TodoModel();
        this.expected.setId(123L);
        this.expected.setTitle("Test Title");
        this.expected.setOrder(0L);
        this.expected.setCompleted(false);
    }

    @Test
    void create() throws Exception {
        when(this.todoService.add(any(TodoRequest.class)))
                .then((i)->{
                    TodoRequest request = i.getArgument(0,TodoRequest.class);
                    return new TodoModel(this.expected.getId(),
                            request.getTitle(),
                            this.expected.getOrder(),
                            this.expected.getCompleted());
                });
        TodoRequest request = new TodoRequest();
        request.setTitle("Any Title");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        ResultActions any_title = this.mvc.perform((RequestBuilder) post("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Any Title"));

    }

    @Test
    void readOne() {
    }
}