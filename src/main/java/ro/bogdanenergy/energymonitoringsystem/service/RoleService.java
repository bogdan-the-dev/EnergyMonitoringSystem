package ro.bogdanenergy.energymonitoringsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bogdanenergy.energymonitoringsystem.model.Role;
import ro.bogdanenergy.energymonitoringsystem.repository.IRoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository iRoleRepository;

    public Role createRole(Role role) {
        return this.iRoleRepository.save(role);
    }
    public Role getRoleByName(String name) {
        return this.iRoleRepository.getRoleByNameIs(name).orElse(null);
    }
    public Role getRoleById(Integer id) {
        return  this.iRoleRepository.findById(id).orElse(null);
    }
}

