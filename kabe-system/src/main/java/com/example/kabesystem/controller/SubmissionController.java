package com.example.kabesystem.controller;

import com.example.kabesystem.service.SubmissionService;
import com.example.kabesystem.util.Result;

import org.springframework.web.bind.annotation.*;

/**
 * 申请 前端控制器
 *
 * @author pluck
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("")
    public Result<?> createSubmission(@RequestAttribute Long userId, @RequestParam String action) {
        return Result.success(submissionService.createSubmission(userId, action));
    }

    @PutMapping("/{submissionId}")
    public Result<?> updateSubmission(
            @PathVariable(value = "submissionId") Long submissionId, @RequestParam String state) {
        submissionService.updateSubmission(submissionId, state);
        return Result.success(null);
    }

    @GetMapping("/pending")
    public Result<?> getPendingSubmission() {
        return Result.success(submissionService.getPendingSubmission());
    }
}
