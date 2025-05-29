package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.request.CandidateCommentRequestDto;
import com.recruiting.center.crm.service.controller.CommentControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentControllerService commentControllerService;

    @PostMapping("/{id}/add")
    public void addCandidateComment(
            @PathVariable Long id,
            @RequestBody CandidateCommentRequestDto comments
            ) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        commentControllerService.addComment(id, comments.comments(), userName);

    }


}
