package main.model;

import java.util.ArrayList;
import java.util.List;

public class Group extends Item {
	private List<Group> innerGroups;
	private List<Widget> widgets;

	public List<Group> getInnerGroups() {
		return innerGroups;
	}

	public void setInnerGroups(List<Group> innerGroups) {
		this.innerGroups = innerGroups;
	}

	public List<Widget> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<Widget> widgets) {
		this.widgets = widgets;
	}

	public Group(String id, String name, String parentId) {
		super(id, name, Types.GROUP, parentId);
		innerGroups = new ArrayList<Group>();
		widgets = new ArrayList<Widget>();
	}

	public void addGroup(Group innerGroup) {
		innerGroups.add(innerGroup);		
	}

	public void addWidget(Widget widget) {
		widgets.add(widget);		
	}
}
