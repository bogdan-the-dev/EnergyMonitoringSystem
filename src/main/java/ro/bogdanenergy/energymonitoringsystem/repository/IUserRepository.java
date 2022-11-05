package ro.bogdanenergy.energymonitoringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findUserByEmailIsIgnoreCase(String email);

    Optional<AppUser> findUserByUsernameIsIgnoreCase(String username);

    Optional<AppUser> findAppUserByUsernameIs(String username);
    Optional<List<AppUser>> findAppUsersByRoleId(int roleId);
}
