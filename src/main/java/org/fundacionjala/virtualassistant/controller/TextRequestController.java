package org.fundacionjala.virtualassistant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("")
public class TextRequestController {
    @GetMapping("/textRequest/{userId}?contextId={contextID}")
    public ArrayList<Object> getAllStudents() {
        return null;
    }

}
