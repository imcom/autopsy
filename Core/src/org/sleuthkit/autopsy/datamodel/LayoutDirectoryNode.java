/*
 * Autopsy Forensic Browser
 * 
 * Copyright 2011 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.datamodel;

import java.util.LinkedHashMap;
import java.util.Map;
import org.openide.nodes.Sheet;
import org.sleuthkit.autopsy.datamodel.LayoutFileNode.LayoutContentPropertyType;
import org.sleuthkit.datamodel.LayoutDirectory;
import org.sleuthkit.datamodel.LayoutFile;

/**
 * Node for layout dir
 */
public class LayoutDirectoryNode extends AbstractAbstractFileNode<LayoutDirectory> {

    public static String nameForLayoutFile(LayoutDirectory ld) {
        return ld.getName();
    }

    public LayoutDirectoryNode(LayoutDirectory ld) {
        super(ld);

        this.setDisplayName(nameForLayoutFile(ld));
        this.setIconBaseWithExtension("org/sleuthkit/autopsy/images/file-icon-deleted.png");
    }

    @Override
    protected Sheet createSheet() {
        Sheet s = super.createSheet();
        Sheet.Set ss = s.get(Sheet.PROPERTIES);
        if (ss == null) {
            ss = Sheet.createPropertiesSet();
            s.put(ss);
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        fillPropertyMap(map, content);

        ss.put(new NodeProperty("Name", "Name", "no description", getName()));

        final String NO_DESCR = "no description";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            ss.put(new NodeProperty(entry.getKey(), entry.getKey(), NO_DESCR, entry.getValue()));
        }
        // @@@ add more properties here...

        return s;
    }

    @Override
    public <T> T accept(ContentNodeVisitor<T> v) {
        return v.visit(this);
    }

    @Override
    public <T> T accept(DisplayableItemNodeVisitor<T> v) {
        return v.visit(this);
    }

    @Override
    public TYPE getDisplayableItemNodeType() {
        return TYPE.CONTENT;
    }

    private static void fillPropertyMap(Map<String, Object> map, LayoutDirectory content) {
        map.put(LayoutContentPropertyType.NAME.toString(), content.getName());
        map.put(LayoutContentPropertyType.SIZE.toString(), content.getSize());
    }
}
