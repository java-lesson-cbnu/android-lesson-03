package kr.easw.lesson03.controller;

import kr.easw.lesson03.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 이 클래스를 컨트롤러로 선언합니다.
@Controller
@RequiredArgsConstructor
public class BaseWebController {

    // 이 메서드의 엔드포인트를 /check로 설정합니다. 모든 접속 타입을 허용합니다.
    @RequestMapping("/")
    public ModelAndView onIndex() {
        // 이 엔드포인트로 접속할경우, api_key_test.html로 리다이렉트시킵니다.
        return new ModelAndView("api_key_test.html");
    }
}



