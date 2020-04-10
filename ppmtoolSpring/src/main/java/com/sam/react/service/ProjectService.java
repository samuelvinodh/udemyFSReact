package com.sam.react.service;

import com.sam.react.domain.Project;
import com.sam.react.exception.ProjectIdException;
import com.sam.react.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdate(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch(Exception e){
            throw new ProjectIdException("Project Id "+project.getProjectIdentifier().toUpperCase()+" already exists");
        }

    }

    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID "+projectId+" does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Unable to delete as Project ID "+projectId+" does not exist");
        }
        projectRepository.delete(project);
    }
}
