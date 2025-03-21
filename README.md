# gogo-x-roads

FIXME: description

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar gogo-x-roads-0.1.0-standalone.jar [args]

## Structure

gogo-x-roads/
├── src/
│   ├── gogo_x_roads/              # ✅ Clojure code (underscores match hyphens)
│   │   ├── core.clj              # Main entry: (ns gogo-x-roads.core ...)
│   │   ├── db.clj
│   │   └── gif.clj               # GIF generation code
│   └── java/                     # ✅ Java code directory
│       └── gogo_x_roads/         # Matches package name
│           └── GifSequenceWriter.java # ✅ package gogo_x_roads;
├── resources/
│   └── public/
│       ├── images/               # Output dir for coffee_dance.gif
│       ├── styles.css
│       └── app.js
└── project.clj                   # Fixed config below

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2025 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
