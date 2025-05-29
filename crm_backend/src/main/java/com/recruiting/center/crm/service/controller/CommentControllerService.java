package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.entity.CommentDto;
import com.recruiting.center.crm.dto.request.CandidateCommentRequestDto;
import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.entity.candidate.CandidateComment;
import com.recruiting.center.crm.service.candidate.CandidateCommentsService;
import com.recruiting.center.crm.service.candidate.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentControllerService {
    private final CandidateCommentsService candidateCommentsService;
    private final CandidateService candidateService;

    public void addComment(Long candidateId, List<CommentDto> comments, String userName) {
        Candidate candidateById = candidateService.findCandidateById(candidateId);

        for(CommentDto com : comments){
            candidateCommentsService.saveComment(CandidateComment.builder()
                    .commentedBy(userName)
                    .comment(com.comment())
                    .commentDate(LocalDate.now())
                    .candidate(candidateById)
                    .build());
        }

    }
}
