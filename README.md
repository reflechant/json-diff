# json-diff

A CLI tool to produce **structural diff** of two JSON files

## Usage

    $ java -jar json-diff.jar [-m {visual|patch}] file1.json file2.json

`-m` or `--mode` lets you choose output format.

`visual` prints **all** JSON, marking difference in-place. It's made with [deep-diff2](https://github.com/lambdaisland/deep-diff2).

`patch` prints the minimal necessary changes needed to make file2 from file1. It's made with [editscript](https://github.com/juji-io/editscript).

## Screenshots

![json-diff in visual and patch modes](https://raw.githubusercontent.com/reflechant/json-diff/screenshots/screenshots/json-diff%20in%20action.png)

## License

Copyright © 2020 Roman Gerasimov

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
