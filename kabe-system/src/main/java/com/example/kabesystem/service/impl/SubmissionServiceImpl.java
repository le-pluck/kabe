package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.SubmissionMapper;
import com.example.kabesystem.model.Submission;
import com.example.kabesystem.model.UserAccount;
import com.example.kabesystem.service.SubmissionService;
import com.example.kabesystem.service.UserAccountService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 申请 服务实现类
 *
 * @author pluck
 * @since 2023-05-10
 */
@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionMapper, Submission>
        implements SubmissionService {
    private final UserAccountService userAccountService;

    public SubmissionServiceImpl(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public boolean createSubmission(Long submitterId, String action) {
        LambdaQueryWrapper<Submission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Submission::getSubmitterId, submitterId)
                .eq(Submission::getAction, action)
                .eq(Submission::getState, "pending");
        if (baseMapper.selectList(wrapper).size() != 0) {
            return false;
        }

        baseMapper.insert(new Submission(null, submitterId, action, null));
        return true;
    }

    @Override
    public void updateSubmission(Long submissionId, String state) {
        Submission submission = baseMapper.selectById(submissionId);

        if (!"beUploader".equals(submission.getAction())) {
            return;
        }

        switch (state) {
            case "fulfilled":
                UserAccount userAccount = new UserAccount();
                userAccount.setId(submission.getSubmitterId());
                userAccount.setIsUploader(true);
                userAccountService.updateById(userAccount);
                break;
            case "rejected":
            default:
                break;
        }

        submission.setState(state);
        baseMapper.updateById(submission);
    }

    @Override
    public List<Submission> getPendingSubmission() {
        LambdaQueryWrapper<Submission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Submission::getState, "pending");
        return baseMapper.selectList(wrapper);
    }
}
