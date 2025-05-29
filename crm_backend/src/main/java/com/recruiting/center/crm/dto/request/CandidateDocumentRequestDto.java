package com.recruiting.center.crm.dto.request;

import com.recruiting.center.crm.dto.entity.DocumentDto;

import java.util.List;

public record CandidateDocumentRequestDto (List<DocumentDto> documents){
}
