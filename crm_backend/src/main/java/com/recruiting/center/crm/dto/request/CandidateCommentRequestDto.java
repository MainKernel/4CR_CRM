package com.recruiting.center.crm.dto.request;

import com.recruiting.center.crm.dto.entity.CommentDto;

import java.util.List;

public record CandidateCommentRequestDto (List<CommentDto> comments){
}
