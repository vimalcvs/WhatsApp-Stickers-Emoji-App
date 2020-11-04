/*
 * Copyright 2020  Vimal CVS
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
package com.virmana.stickers_app.editor.picchooser;

class GridItem {
    final String name;
    final String path;
    final String imageTaken;
    final long imageSize;
    public GridItem(final String n, final String p,final String imageTaken,final long imageSize) {
        name = n;
        path = p;
        this.imageTaken = imageTaken;
        this.imageSize = imageSize;
    }
}
