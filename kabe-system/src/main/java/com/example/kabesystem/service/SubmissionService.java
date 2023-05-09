package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Submission;

import java.util.List;

/**
 * 申请 服务类
 *
 * @author pluck
 * @since 2023-05-10
 */
public interface SubmissionService extends IService<Submission> {
    boolean createSubmission(Long submitterId, String action);

    void updateSubmission(Long submissionId, String state);

    List<Submission> getPendingSubmission();
}
