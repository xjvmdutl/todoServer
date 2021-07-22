package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;

    //이후에 코드작성을 좀더 편리하게 하기 위해서 이후에 entity를 파라미터로 받는 생성자를 추가로 작성
    public TodoResponse(TodoModel todoModel){
        this.id = todoModel.getId();
        this.title = todoModel.getTitle();
        this.order = todoModel.getOrder();
        this.completed = todoModel.getCompleted();

        this.url = "http://localhost:8080/"+this.id;
        //하드 코딩되어있다 좋은 패턴이 아니다
        //베이스 url을 작성 하였다.
        //baseurl이 바뀌거나 포트가 바뀔경우 마다 수정이 필요

    }
}
