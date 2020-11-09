package com.innova.service;

import com.innova.dto.request.WriterForm;
import com.innova.model.User;
import com.innova.model.Writer;
import com.innova.repository.WriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WriterService {
    @Autowired
    WriteRepository writeRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

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
}
