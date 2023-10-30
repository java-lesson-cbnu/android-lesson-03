package kr.easw.lesson03.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// final로 지정된 모든 필드를 파라미터로 가지는 생성자를 생성합니다.
@AllArgsConstructor
@Getter
public class ResultDto {
    private String status;
}
