package Donnees;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class defines the Resource object for the system. Besides the basic
 * attributes, there are two lists maintained. alreadyAssignedProjectList is a
 * ProjectList object that maintains a list of projects that the resource was
 * already assigned to prior to this execution of the system.
 * projectsAssignedList is also a ProjectList object that maintains a list of
 * projects assigned to the resource durint the current execution or session.
 * 
 * @author A.J. Lattanze, CMU
 * @version 1.6, 2013-Sep-13
 */

/* Modification Log
 ****************************************************************************
 * v1.6, R. Champagne, 2013-Sep-13 - Various refactorings for new lab.
 * 
 * v1.5, R. Champagne, 2012-Jun-19 - Various refactorings for new lab.
 * 
 * v1.4, R. Champagne, 2012-May-31 - Various refactorings for new lab.
 * 
 * v1.3, R. Champagne, 2012-Feb-02 - Various refactorings for new lab.
 * 
 * v1.2, 2011-Feb-02, R. Champagne - Various refactorings, javadoc comments.
 *  
 * v1.1, 2002-May-21, R. Champagne - Adapted for use at ETS. 
 * 
 * v1.0, 12/29/99, A.J. Lattanze - Original version.

 ****************************************************************************/

public class Resource {

	/**
	 * Resource's last name
	 */
	private String lastName;
	
	/**
	 * Resource's first name
	 */
	private String firstName;
	
	/**
	 * Resource's identification number
	 */
	private String id;
	
	/**
	 * Resource role 
	 */
	private String role;

	/**
	 *  List of projects the resource is already allocated to
	 */
	private ProjectList alreadyAssignedProjectList = new ProjectList();

	/**
	 *  List of projects assigned to the resource in this session
	 */
	private ProjectList projectsAssignedList = new ProjectList();

	/**
	 * Assigns a project to a resource.
	 * 
	 * @param project
	 */
	public void assignProject(Project project) {

		getProjectsAssigned().addProject(project);

	}
	
	public boolean canAssignProject(Project newProject){
		
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date newProjectStartDate = formatter.parse(newProject.getStartDate());
			Date newProjectEndDate = formatter.parse(newProject.getEndDate());
			Date startDate;
			Date endDate;
			
			int newProjectWorkCharge = 0;
			
			if(newProject.getPriority().equalsIgnoreCase("L")){
				newProjectWorkCharge += 25;
			}else if(newProject.getPriority().equalsIgnoreCase("M")){
				newProjectWorkCharge += 50;
			}else if(newProject.getPriority().equalsIgnoreCase("H")){
				newProjectWorkCharge += 100;
			}
			
			boolean isListDone = false;
			int currentWorkCharge = 0;
			Project project;
			projectsAssignedList.goToFrontOfList();
			alreadyAssignedProjectList.goToFrontOfList();
			
			
			while(!isListDone){
				
				project = projectsAssignedList.getNextProject();
				
				if(project == null) {
					isListDone = true;
				}else {
				
					startDate = formatter.parse(project.getStartDate());
					endDate = formatter.parse(project.getEndDate());
					
					if(!(startDate.compareTo(newProjectStartDate) <= 0 && endDate.compareTo(newProjectEndDate) <= 0) 
							&& !(startDate.compareTo(newProjectEndDate) >= 0 && endDate.compareTo(newProjectStartDate) >= 0))
					{
					
						if(project.getPriority().equalsIgnoreCase("L")){
							currentWorkCharge += 25;
						}else if(project.getPriority().equalsIgnoreCase("M")){
							currentWorkCharge += 50;
						}else if(project.getPriority().equalsIgnoreCase("H")){
							currentWorkCharge += 100;
						}
					}
				}
			}
			
			isListDone = false;
			
			while(!isListDone){
				
				project = alreadyAssignedProjectList.getNextProject();
				
				
				if(project == null) {
					isListDone = true;
				}else{
					
					startDate = formatter.parse(project.getStartDate());
					endDate = formatter.parse(project.getEndDate());
				
					if(!(startDate.before(newProjectStartDate) && endDate.before(newProjectStartDate)) || !(endDate.equals(newProjectStartDate)))
					{
						if(!(startDate.after(newProjectEndDate) && endDate.after(newProjectEndDate)) || !(startDate.equals(newProjectEndDate))){
							if(project.getPriority().equalsIgnoreCase("L")){
								currentWorkCharge += 25;
							}else if(project.getPriority().equalsIgnoreCase("M")){
								currentWorkCharge += 50;
							}else if(project.getPriority().equalsIgnoreCase("H")){
								currentWorkCharge += 100;
							}
						}
					}
				}
			}
			
			return currentWorkCharge + newProjectWorkCharge <= 100;
		
		}catch(Exception e){
			return false;
		}
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setPreviouslyAssignedProjectList(ProjectList projectList) {
		this.alreadyAssignedProjectList = projectList;
	}

	public ProjectList getPreviouslyAssignedProjectList() {
		return alreadyAssignedProjectList;
	}

	public void setProjectsAssigned(ProjectList projectList) {
		this.projectsAssignedList = projectList;
	}

	public ProjectList getProjectsAssigned() {
		return projectsAssignedList;
	}

} // Resource class