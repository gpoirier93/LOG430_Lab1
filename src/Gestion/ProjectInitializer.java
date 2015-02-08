package Gestion;

import Donnees.Project;
import Donnees.Resource;
import Donnees.ResourceList;
import Donnees.List;

public class ProjectInitializer {
	
	public ProjectReader projectReader;
	public ResourceReader resourceReader;
	
	public ProjectInitializer(String projectFile, String resourceFile){
		
		projectReader = new ProjectReader(projectFile);

		resourceReader = new ResourceReader(resourceFile);
		resourceReader.getListOfResources().goToFrontOfList();
		boolean done = false;
		
		Project resourceProject;
		Project project;
		while (!done) {

			Resource resource = resourceReader.getListOfResources().getNextResource();

			if (resource == null) {

				done = true;

			} else {
				
				boolean projectAssignedDone = false;
				resource.getPreviouslyAssignedProjectList().goToFrontOfList();
				
				while(!projectAssignedDone){
					
					resourceProject = resource.getPreviouslyAssignedProjectList().getNextProject();
					if(resourceProject == null || resourceProject.getID().equals("")){
						projectAssignedDone = true;
					}else{
					
						project = projectReader.getListOfProjects().findProjectByID(resourceProject.getID());
						
						if(project != null){
							resourceProject.setStartDate(project.getStartDate());
							resourceProject.setEndDate(project.getEndDate());
							resourceProject.setPriority(project.getPriority());
							resourceProject.setProjectName(project.getProjectName());
							project.assignResourceInAlreadyAssignedRessource(resource);
						}
					}
				}

			} // if

		} // while
	}
	


}
