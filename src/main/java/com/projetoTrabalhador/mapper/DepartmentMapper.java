package com.projetoTrabalhador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.projetoTrabalhador.entities.Department;
import com.projetoTrabalhador.requests.DepartmentPostRequestBody;
import com.projetoTrabalhador.requests.DepartmentPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class DepartmentMapper {
	
	public static final DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

	public abstract Department toDepartment(DepartmentPostRequestBody departmentPostRequestBody);
	
	public abstract Department toDepartment(DepartmentPutRequestBody departmentPutRequestBody);
}
