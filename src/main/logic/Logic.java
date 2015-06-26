package main.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import main.model.Group;
import main.model.Item;
import main.model.Site;
import main.model.Widget;

public class Logic {
	private List<Site> sites;
	private Random rand = new Random();

	public Logic() {
		sites = new ArrayList<Site>();
	}

	public void addPredefineItems() {
		int sitesGenerationCount = rand.nextInt(10);
		for (int i = 0; i < sitesGenerationCount; i++) {
			String siteId = UUID.randomUUID().toString();
			Site site = new Site(siteId, "SITE_" + i);
			// Generate groups:
			int groupsGenerationCount = rand.nextInt(10);
			for (int j = 0; j < groupsGenerationCount; j++) {
				String groupId = UUID.randomUUID().toString();
				Group group = new Group(groupId, "GROUP_" + j, siteId);
				int innerGroupsGenerationCount = rand.nextInt(10);
				for (int k = 0; k < innerGroupsGenerationCount; k++) {
					String innerGroupId = UUID.randomUUID().toString();
					Group innerGroup = new Group(innerGroupId, "GROUP_" + j
							+ "_" + k, groupId);
					int widgetsGenerationCount = rand.nextInt(10);
					for (int l = 0; l < widgetsGenerationCount; l++) {
						Widget widget = new Widget(
								UUID.randomUUID().toString(), "WIDGET_" + l,
								innerGroupId);
						innerGroup.addWidget(widget);
					}
					group.addGroup(innerGroup);
				}
				site.addGroup(group);
			}

			this.sites.add(site);
		}
	}

	public void destroy() {
		this.sites.clear();
	}

	public Item findItem(String type, String id) {
		Item item = null;

		switch (type) {
		case "site":
			item = findSite(id);
			break;

		case "group":
			item = findGroup(id);
			break;

		case "widget":
			item = findWidget(id);
			break;

		default:
			// nothing
			break;
		}
		return item;
	}

	private Item findWidget(String id) {
		Widget widget = null;
		for (Site site : this.sites) {
			List<Group> groups = site.getGroups();
			for (Group group : groups) {
				widget = findWidgetInGroup(group, id);
				if (widget != null) {
					return widget;
				}
			}
		}
		return null;
	}

	private Widget findWidgetInGroup(Group group, String id) {
		Widget widgetResult = null;
		// widget can be inside the group widgets
		for (Widget widget : group.getWidgets()) {
			if (widget.getId().equals(id)) {
				return widget;
			}
		}
		// or inside inner groups:
		for (Group innerGroup : group.getInnerGroups()) {
			widgetResult = findWidgetInGroup(innerGroup, id);
			if (widgetResult != null) {
				return widgetResult;
			}
		}

		return null;
	}

	private Item findGroup(String id) {
		Group groupResult = null;
		for (Site site : this.sites) {
			List<Group> groups = site.getGroups();

			for (Group group : groups) {
				groupResult = findGroupInGroup(group, id);
				if (groupResult != null) {
					return groupResult;
				}
			}
		}
		return null;
	}

	private Group findGroupInGroup(Group group, String id) {
		Group groupResult = null;

		// Is it this group:
		if (group.getId().equals(id)) {
			return group;
		}

		// Or inside inner groups:
		for (Group innerGroup : group.getInnerGroups()) {
			groupResult = findGroupInGroup(innerGroup, id);
			if (groupResult != null) {
				return groupResult;
			}
		}
		return null;
	}

	private Item findSite(String id) {
		for (Site site : this.sites) {
			if (site.getId().equals(id)) {
				return site;
			}
		}
		return null;
	}

	public List<Site> getAll() {
		return this.sites;
	}

	public boolean deleteItem(String type, String id) {
		switch (type) {
		case "site":
			return deleteSite(id);

		case "group":
			return deleteGroup(id);

		case "widget":
			return deleteWidget(id);
		}
		return false;
	}

	private boolean deleteWidget(String id) {
		boolean isDeleted = false;
		Item widgetFound = this.findWidget(id);
		if (widgetFound != null) {
			String parentId = widgetFound.getParentId();
			Item parent = findParentById(parentId);

			if (parent != null) {
				isDeleted = ((Group) parent).getWidgets().remove(widgetFound);
			}
		}
		return isDeleted;
	}

	private boolean deleteGroup(String id) {
		boolean isDeleted = false;
		Item groupFound = this.findGroup(id);
		if (groupFound != null) {
			String parentId = groupFound.getParentId();
			Item parent = findParentById(parentId);

			if (parent != null) {
				main.model.Types parentType = parent.getType();
				if (parentType.equals(main.model.Types.SITE)) {
					isDeleted = ((Site) parent).getGroups().remove(groupFound);
				} else if (parentType.equals(main.model.Types.GROUP)) {
					isDeleted = ((Group) parent).getInnerGroups().remove(
							groupFound);
				}
			}
		}
		return isDeleted;
	}

	private Item findParentById(String id) {
		Item aSite = this.findSite(id);
		if (aSite != null) {
			return aSite;
		} else {
			Item aGroup = this.findGroup(id);
			if (aGroup != null) {
				return aGroup;
			}
		}
		return null;
	}

	private boolean deleteSite(String id) {
		Item siteFound = this.findSite(id);
		if (siteFound != null) {
			this.sites.remove(siteFound);
			return true;
		}
		return false;
	}

}
