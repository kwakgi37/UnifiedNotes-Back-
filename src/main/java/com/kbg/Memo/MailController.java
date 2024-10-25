package com.kbg.Memo;

import com.kbg.Memo.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class MailController {

    @Autowired
    private MailService mailService;
    private int number;

    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(@RequestBody MailDto dto) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            number = mailService.sendMail(dto.getMail());
            String num = String.valueOf(number);

            map.put("success", Boolean.TRUE);
            map.put("number", num);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {
        boolean isMatch = userNumber.equals(String.valueOf(number));

        return ResponseEntity.ok(isMatch);
    }
}
