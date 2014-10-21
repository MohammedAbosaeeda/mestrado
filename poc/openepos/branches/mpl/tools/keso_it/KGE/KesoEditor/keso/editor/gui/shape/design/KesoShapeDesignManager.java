package keso.editor.gui.shape.design;

import keso.editor.KGE;
import keso.editor.gui.shape.KesoShape;

public class KesoShapeDesignManager {

	public static IKesoShapeDesign generateRenderere(KesoShape shape) {
		String identifier = shape.getData().getIdentifier();
		/* if (identifier.equals(KGE.WORLD)) {
			return new WorldKesoShapeDesign(shape);
		} else */ if (identifier.equals(KGE.PUBLICDOMAIN)) {
			return new PublicDomainKesoShapeDesign(shape);
		} else if (identifier.equals(KGE.NETWORK)) {
			return new NetworkKesoShapeDesign(shape);
		} else if (identifier.equals(KGE.IMPORT)) {
			return new ImportKesoShapeDesign(shape);
		} else if (identifier.equals(KGE.SERVICE)) {
			return new ServiceKesoShapeDesign(shape);
		} else {
			return new KesoShapeDesign(shape);
		}
	}

}
