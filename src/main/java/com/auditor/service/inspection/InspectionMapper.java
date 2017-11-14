package com.auditor.service.inspection;

import com.auditor.domain.Inspection;
import com.auditor.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface InspectionMapper extends EntityMapper<InspectionDTO, Inspection> {}
