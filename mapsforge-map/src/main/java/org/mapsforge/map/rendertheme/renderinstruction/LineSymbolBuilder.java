/*
 * Copyright 2010, 2011, 2012, 2013 mapsforge.org
 * Copyright 2014 Ludwig M Brinckmann
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.map.rendertheme.renderinstruction;

import java.io.IOException;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.GraphicFactory;
import org.mapsforge.map.model.DisplayModel;
import org.mapsforge.map.rendertheme.XmlUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A builder for {@link LineSymbol} instances.
 */
public class LineSymbolBuilder extends RenderInstructionBuilder {

	boolean alignCenter;
	Bitmap bitmap;
	boolean repeat;

	public LineSymbolBuilder(GraphicFactory graphicFactory, DisplayModel displayModel, String elementName,
			Attributes attributes, String relativePathPrefix) throws IOException, SAXException {
		extractValues(graphicFactory, displayModel, elementName, attributes, relativePathPrefix);

		this.bitmap = createBitmap(graphicFactory, displayModel, relativePathPrefix, src);
		XmlUtils.checkMandatoryAttribute(this.elementName, SRC, this.bitmap);

	}

	/**
	 * @return a new {@code LineSymbol} instance.
	 */
	public LineSymbol build() {
		return new LineSymbol(this);
	}

	private void extractValues(GraphicFactory graphicFactory, DisplayModel displayModel, String elementName,
			Attributes attributes, String relativePathPrefix) throws IOException, SAXException {

		this.elementName = elementName;

		for (int i = 0; i < attributes.getLength(); ++i) {
			String name = attributes.getQName(i);
			String value = attributes.getValue(i);

			if (SRC.equals(name)) {
				this.src = value;
			} else if (ALIGN_CENTER.equals(name)) {
				this.alignCenter = Boolean.parseBoolean(value);
			} else if (REPEAT.equals(name)) {
				this.repeat = Boolean.parseBoolean(value);
			} else {
				throw XmlUtils.createSAXException(elementName, name, value, i);
			}
		}

	}
}
