package TestPackage;
import static org.junit.Assert.*;
import Donnees.Project;
import Donnees.ProjectList;
import Donnees.Resource;

import org.junit.Test;

public class LOG430_Test {

	@Test
	public void testResourceCanAddProjectHasOverlaps() {
		Resource resource = new Resource();
		Project project1 = new Project();
		project1.setID("1");
		project1.setPriority("L");
		project1.setStartDate("2014-04-01");
		project1.setEndDate("2014-05-15");
		
		Project project2 = new Project();
		project2.setID("2");
		project2.setPriority("H");
		project2.setStartDate("2014-05-01");
		project2.setEndDate("2014-06-01");
	
		ProjectList projectList = new ProjectList();
		projectList.addProject(project1);
		
		resource.setPreviouslyAssignedProjectList(projectList);
		
		boolean canAddProject = resource.canAssignProject(project2);
	
		assertFalse(canAddProject);
	}
	
	@Test
	public void testResourceCanAddProjectStartDateEqualsEndDate(){
		
		Resource resource = new Resource();
		Project project1 = new Project();
		project1.setID("1");
		project1.setPriority("L");
		project1.setStartDate("2014-04-01");
		project1.setEndDate("2014-05-01");
		
		Project project2 = new Project();
		project2.setID("2");
		project2.setPriority("H");
		project2.setStartDate("2014-05-01");
		project2.setEndDate("2014-06-01");
	
		ProjectList projectList = new ProjectList();
		projectList.addProject(project1);
		
		resource.setPreviouslyAssignedProjectList(projectList);
		
		boolean canAddProject = resource.canAssignProject(project2);
	
		assertFalse(canAddProject);
	}

	@Test
	public void testResourceCanAddProjectEndDateEqualsStartDate(){
		
		Resource resource = new Resource();
		Project project1 = new Project();
		project1.setID("1");
		project1.setPriority("L");
		project1.setStartDate("2014-04-01");
		project1.setEndDate("2014-05-01");
		
		Project project2 = new Project();
		project2.setID("2");
		project2.setPriority("H");
		project2.setStartDate("2014-02-01");
		project2.setEndDate("2014-04-01");
	
		ProjectList projectList = new ProjectList();
		projectList.addProject(project1);
		
		resource.setPreviouslyAssignedProjectList(projectList);
		
		boolean canAddProject = resource.canAssignProject(project2);
	
		assertFalse(canAddProject);
	}

}
