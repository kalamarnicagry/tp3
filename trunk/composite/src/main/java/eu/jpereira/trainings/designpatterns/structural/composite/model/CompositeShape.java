package eu.jpereira.trainings.designpatterns.structural.composite.model;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeShape extends Shape {

	List<Shape> shapes;

	public CompositeShape() {
		this.shapes = createShapesList();
	}

	@Override
	public CompositeShape asComposite() {
		return this;
	}

	public boolean removeShape(Shape shape) {
		return this.shapes.remove(shape);
	}

	public int getShapeCount() {
		return this.shapes.size();
	}

	public void addShape(Shape shape) {
		this.shapes.add(shape);
	}

	public List<Shape> getShapes() {
		return this.shapes;
	}

	public List<Shape> getShapesByType(ShapeType type) {
		List<Shape> result = new ArrayList<Shape>();
		for (Shape shape : this.shapes) {
			if (shape.getType() == type) {
				result.add(shape);
			}
			if (shape.asComposite() != null) {
				result.addAll(shape.asComposite().getShapesByType(type));
			}
		}
		return result;
	}

	public List<Shape> getLeafShapes() {
		List<Shape> leafs = new ArrayList<Shape>();
		for (Shape shape : this.shapes) {
			if (shape.asComposite() == null) {
				leafs.add(shape);
			} else {
				leafs.addAll(shape.asComposite().getLeafShapes());
			}
		}
		return leafs;
	}

	protected List<Shape> createShapesList() {
		return new ArrayList<Shape>();
	}
}