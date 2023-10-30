package kr.easw.lesson03.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// final로 지정된 모든 필드를 파라미터로 가지는 생성자를 생성합니다.
@RequiredArgsConstructor
public class AWSKeyDto {
    @Getter
    private final String apiKey;

    @Getter
    private final String apiSecretKey;
}
