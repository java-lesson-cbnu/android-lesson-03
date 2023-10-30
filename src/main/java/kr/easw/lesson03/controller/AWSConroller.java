package kr.easw.lesson03.controller;

import kr.easw.lesson03.model.dto.AWSKeyDto;
import kr.easw.lesson03.model.dto.ResultDto;
import kr.easw.lesson03.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// 해당 클래스를 Rest 컨트롤러로 지정합니다.
// 모든 응답이 JSON으로 매핑되며, 이 클래스가 컨트롤러로 선언됩니다.
@RestController
// final로 지정된 모든 필드를 파라미터로 가지는 생성자를 생성합니다.
@RequiredArgsConstructor
// 이 클래스의 기반 엔드포인트를 /api/v1/rest/aws로 설정합니다.
@RequestMapping("/api/v1/rest/aws")
public class AWSConroller {
    // 컨트롤러를 생성자 주입으로 선언합니다.
    private final AWSService awsController;

    // 이 메서드의 엔드포인트를 /check로 설정합니다. POST만 허용됩니다.
    @PostMapping("/check")
    private ResultDto onUpload(
            // 이 파라미터의 값을 URL의 파라미터에서 가져옵니다.
            @RequestParam("type") String type,
            // 이 파라미터의 값을 HTML의 body에서 파싱해 가져옵니다.
            @RequestBody AWSKeyDto key) {
        switch (type) {
            // 만약 URL 파라미터 "test"가 s3일 경우,
            case "s3":
                // 주어진 값으로 s3 엔드포인트를 테스트합니다.
                return awsController.testS3(key);
            // 만약 URL 파라미터 "test"가 dynamo일 경우,
            case "dynamo":
                // 주어진 값으로 DynamoDB 엔드포인트를 테스트합니다.
                return awsController.testDynamo(key);
        }
        return new ResultDto("Unknown type");
    }



}
