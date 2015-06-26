package main.model;

import java.util.ArrayList;
import java.util.List;

public class Site extends Item {
	private List<Group> groups;

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Site(String id, String name) {
		super(id, name, Types.SITE, null);
		groups = new ArrayList<Group>();
	}

	public void addGroup(Group group) {
		groups.add(group);		
	}

}
