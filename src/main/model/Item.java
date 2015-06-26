package main.model;

public abstract class Item implements Comparable<Item> {
	private String name;
	private String id;
	private Types type;
	private String parentId;

	public Item(String id, String name, Types type, String parentId) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.parentId = parentId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public int compareTo(Item o) {
		return this.getName().compareTo(o.getName());
	}
}
