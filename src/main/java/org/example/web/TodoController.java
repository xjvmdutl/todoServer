package org.example.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.example.serviece.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class TodoController {

    private final TodoService service;

    @PostMapping//Post 메핑을 해준다.
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request){
        //TotoRequest를 RequestBody로 받는다.
        //생성 API
        log.info("create");
        if(ObjectUtils.isEmpty(request.getTitle()))
            return ResponseEntity.badRequest().build();
        if(ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);//디폴트값 설정
        if(ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);
        TodoModel result = this.service.add(request);
        return ResponseEntity.ok(new TodoResponse(result));//성공적 반환
    }

    @GetMapping("{id}")//경로에 ID값을 입력받는다
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id){
        //파라미터를 매개변수로 받는다는 뜻

        log.info("READ ONE");
        TodoModel result = this.service.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll(){
        log.info("READ ALL");

        List<TodoModel> list = this.service.searchAll();
        List<TodoResponse> response = list.stream().map(TodoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request){
        log.info("UPDATE");
        TodoModel result = this.service.updateById(id,request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        log.info("DELETE");
        this.service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        log.info("DELETE ALL");
        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
