package com.globexial.service;

import com.globexial.model.dto.request.PartyRequestDto;
import com.globexial.model.dto.response.PartyResponseDto;
import com.globexial.service.base.PartyCrud;

public interface PartyService extends PartyCrud<PartyResponseDto, PartyRequestDto,Long> {
}
