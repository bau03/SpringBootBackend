package com.innova.service;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.EditUserRoleForm;
import com.innova.dto.request.WriterForm;
import com.innova.exception.BadRequestException;
import com.innova.model.Role;
import com.innova.model.Roles;
import com.innova.model.User;
import com.innova.model.Writer;
import com.innova.repository.RoleRepository;
import com.innova.repository.UserRepository;
import com.innova.repository.WriteRepository;
import com.innova.security.services.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WriterService {
    @Autowired
    WriteRepository writeRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public WriterService(WriteRepository writeRepository,UserServiceImpl userServiceImpl){
        super();
        this.writeRepository=writeRepository;
        this.userServiceImpl=userServiceImpl;
    }

    public void save(WriterForm writerForm){
        Writer writer= new Writer(writerForm.getJob(),writerForm.getEducation(), writerForm.getBiography());
        writer.setTimestap(new Date());
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        writer.setUser(user);
        writeRepository.save(writer);
    }
    public List<Writer> getWriter(){
        return writeRepository.findAll();
    }
    public void editUserRole( EditUserRoleForm editUserRoleForm){
        User user = userRepository.findById(Integer.parseInt(editUserRoleForm.getId())).orElseThrow(() -> new BadRequestException("No such user", ErrorCodes.NO_SUCH_USER));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRole(Roles.ROLE_PM)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

}
