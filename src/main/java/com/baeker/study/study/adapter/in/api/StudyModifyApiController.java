package com.baeker.study.study.adapter.in.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${custom.mapping.study.api}")
@RequiredArgsConstructor
public class StudyModifyApiController {
}
