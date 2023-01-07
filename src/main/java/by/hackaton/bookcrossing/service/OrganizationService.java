package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.OrganizationDto;
import by.hackaton.bookcrossing.entity.Organization;
import by.hackaton.bookcrossing.entity.enums.OrganizationType;
import by.hackaton.bookcrossing.repository.OrganizationRepository;
import by.hackaton.bookcrossing.service.exceptions.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private ModelMapper modelMapper;

    public OrganizationService(OrganizationRepository organizationRepository, ModelMapper modelMapper) {
        this.organizationRepository = organizationRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrganizationDto> getOrganizations() {
        return organizationRepository.findByAvailableTrue().stream().map(b -> modelMapper.map(b, OrganizationDto.class)).collect(toList());
    }

    public OrganizationDto getOrganizationById(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Organization not fount")
        );
        return modelMapper.map(organization, OrganizationDto.class);
    }

    public List<OrganizationDto> getOrganizationByType(OrganizationType type) {
        List<Organization> organizations = organizationRepository.findByTypeAndAvailableTrue(type);
        return organizations.stream().map(o -> modelMapper.map(o, OrganizationDto.class)).collect(toList());
    }

    public void createOrganization(OrganizationDto dto) {
        Organization organization = modelMapper.map(dto, Organization.class);
        organizationRepository.save(organization);
    }

    public void updateOrganization(Long id, OrganizationDto dto) {
        Organization organization = organizationRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Organization not fount")
        );
        modelMapper.map(organization, dto);
        organizationRepository.save(organization);
    }
}
