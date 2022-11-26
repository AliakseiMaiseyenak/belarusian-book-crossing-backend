package by.hackaton.bookcrossing.repository;

import by.hackaton.bookcrossing.entity.Organization;
import by.hackaton.bookcrossing.entity.enums.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByType(OrganizationType type);
}
